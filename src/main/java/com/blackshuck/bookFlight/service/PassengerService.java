package com.blackshuck.bookFlight.service;

import com.blackshuck.bookFlight.entity.*;
import com.blackshuck.bookFlight.enums.BookingStatus;
import com.blackshuck.bookFlight.repository.BookingRepository;
import com.blackshuck.bookFlight.repository.FlightRepository;
import com.blackshuck.bookFlight.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PassengerService
{
    private final PassengerRepository passengerRepository;

    private final FlightRepository flightRepository;

    private final BookingRepository bookingRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository, FlightRepository flightRepository,
                            BookingRepository bookingRepository)
    {
        this.passengerRepository = passengerRepository;
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Passenger> addNewPassenger(User user, PassengerForm passengerForm, int flightNumber)
    {
        List<Passenger> passengers = passengerForm.getPassengers();

        Flight flight = flightRepository.findById(flightNumber)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        List<Passenger> passengersList = new ArrayList<>();

        for (Passenger passenger : passengers)
        {
            Booking booking = new Booking();

            booking.setBookingCode(UUID.randomUUID().toString());

            booking.setBookingStatus(BookingStatus.PENDING);

            booking.setBookingDate(LocalDateTime.now());

            booking.setFlightId(flight);

            booking.setUserId(user);

            Passenger existingPassenger = passengerRepository.findByNationalCode(passenger.getNationalCode())
                    .orElseGet(() -> passengerRepository.save(passenger));

            booking.setPassengerId(existingPassenger);

            bookingRepository.save(booking);

            passengersList.add(passenger);
        }

        return passengersList;
    }
}
