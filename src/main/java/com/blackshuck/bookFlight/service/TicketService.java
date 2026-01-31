package com.blackshuck.bookFlight.service;

import com.blackshuck.bookFlight.entity.*;
import com.blackshuck.bookFlight.enums.BookingStatus;
import com.blackshuck.bookFlight.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService
{
    private final TicketRepository ticketRepository;

    private final FlightRepository flightRepository;

    private final FlightService flightService;

    private final PassengerRepository passengerRepository;

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository,
                         FlightRepository flightRepository,
                         FlightService flightService, PassengerRepository passengerRepository,
                         BookingRepository bookingRepository, UserRepository userRepository)
    {
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
        this.flightService = flightService;
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public void cancelTicket(int id)
    {
        List<Booking> bookings = bookingRepository.findAllByUserIdUserId(id);

        for (Booking booking : bookings)
        {
            if (!(booking.getBookingStatus().equals(BookingStatus.PENDING)))
            {
                booking.setBookingStatus(BookingStatus.CANCELED);
            }
        }

        flightService.setCount(0);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cancelExpiredPendingBookings()
    {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(60);

        List<Booking> expired = bookingRepository.findAllByBooKingStatusAndCreatedAtBefore(BookingStatus.PENDING, threshold);

        if (!expired.isEmpty())
        {
            expired.forEach(b -> b.setBookingStatus(BookingStatus.CANCELED));
        }

        flightService.setCount(0);

        System.out.println("All " + expired.size() + " pending booking/bookings has/have been expired");;
    }

    @Transactional
    public List<Ticket> addNewTickets(List<Passenger> passengers, int priceByClass, String seatClass, List<String> reservedSeats)
    {
        List<Ticket> tickets = new ArrayList<>();

        for (Passenger passenger : passengers)
        {
            Ticket ticket = new Ticket();

            ticket.setTicketDate(LocalDateTime.now());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String currentName;

            User user;

            if (!(authentication instanceof AnonymousAuthenticationToken))
            {
                currentName = authentication.getName();

                user = userRepository.findByEmail(currentName).orElseThrow(() -> new RuntimeException("User not found"));

                List<Booking> bookings = bookingRepository.findAllByUserIdUserId(user.getUserId());

                Booking booking = bookings.stream()
                        .filter(b -> b.getPassengerId().getPassengerId().equals(passenger.getPassengerId()))
                        .filter(b -> b.getBookingStatus().equals(BookingStatus.PENDING))
                        .sorted(Comparator.comparing(Booking::getCreatedAt).reversed())
                        .findFirst().orElseThrow(() -> new RuntimeException("Booking not found"));

                booking.setBookingStatus(BookingStatus.CONFIRMED);

                ticket.setFlightId(booking.getFlightId());

                Passenger attachedPassenger = passengerRepository.findById(passenger.getPassengerId())
                        .orElseThrow(() -> new RuntimeException("Passenger not found!"));

                ticket.setPassengerId(attachedPassenger);

                ticket.setPrice(priceByClass);

                ticket.setSeatClass(seatClass);

                System.out.println("ReservedSeats (addNewTickets): " + reservedSeats);

                ticket.setSeatNumber(reservedSeats.stream().findFirst()
                        .orElseThrow(() -> new RuntimeException("SeatNumber list is empty")));

                reservedSeats.remove(0);

                tickets.add(ticketRepository.save(ticket));
            }
        }

        flightService.setCount(0);

        return tickets;
    }
}
