package com.blackshuck.bookFlight.controller;

import com.blackshuck.bookFlight.entity.*;
import com.blackshuck.bookFlight.enums.BookingStatus;
import com.blackshuck.bookFlight.repository.BookingRepository;
import com.blackshuck.bookFlight.service.FlightService;
import com.blackshuck.bookFlight.service.PassengerService;
import com.blackshuck.bookFlight.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class PassengerController
{
    private final PassengerService passengerService;

    private final UserService userService;

    private final FlightService flightService;

    private final BookingRepository bookingRepository;

    @Autowired
    public PassengerController(PassengerService passengerService,
                               UserService userService, FlightService flightService,
                               BookingRepository bookingRepository)
    {
        this.passengerService = passengerService;
        this.userService = userService;
        this.flightService = flightService;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/passengerInfo")
    public String makePassengerInfo(@RequestParam("passengerNum") int passengerNum,
                                    @RequestParam("flightNumber") int flightNumber,
                                    @RequestParam("seatClass") String seatClass,
                                    @RequestParam("priceByClass") int priceByClass,
                                    Model model)
    {
        PassengerForm passengerForm = new PassengerForm();

        List<String> reservedSeats = new ArrayList<>();

        Flight flight = flightService.serachFlight(flightNumber);

        for (int i = 0; i < passengerNum; i++)
        {
            passengerForm.getPassengers().add(new Passenger());

            reservedSeats.add(flightService.getAvailableSeat(flight.getCapacity(), flightNumber, seatClass));
        }

        System.out.println("reservedSeats (makePassengerInfo): " + reservedSeats);

        model.addAttribute("passengerForm", passengerForm);

        model.addAttribute("flightNumber", flightNumber);

        model.addAttribute("reservedSeats", reservedSeats);

        model.addAttribute("priceByClass", priceByClass);

        model.addAttribute("seatClass", seatClass);

        return "passenger-page";
    }

    @PostMapping("/setPassengerInfo")
    public String insertPassengerInfo(@Valid @ModelAttribute("passengerForm") PassengerForm passengerForm,
                                      @RequestParam("flightNumber") int flightNumber,
                                      @RequestParam("priceByClass") int priceByClass,
                                      @RequestParam("reservedSeats") List<String> reservedSeats,
                                      @RequestParam("seatClass") String seatClass,
                                      BindingResult bindingResult, HttpSession httpSession)
    {
        if (bindingResult.hasErrors())
        {
            return "passenger-page";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
        {
            String currentName = authentication.getName();

            User user = userService.findByEmail(currentName);

            passengerService.addNewPassenger(user, passengerForm, flightNumber);

            List<Booking> bookings = bookingRepository.findAllByUserIdAndCreatedAtBefore(
                    user.getUserId(), LocalDateTime.now().minusMinutes(15), BookingStatus.PENDING);

            List<Passenger> passengers = bookings.stream().map(Booking::getPassengerId)
                    .distinct().collect(Collectors.toList());

            System.out.println("reservedSeats (insertPassengerInfo): " + reservedSeats);

            List<String> cleanedSeats = reservedSeats.stream().flatMap(s -> s.contains("[") ?
                            Arrays.stream(s.replaceAll("[\\[\\]\\s]", "")
                            .split(",")): Stream.of(s)).distinct()
                            .collect(Collectors.toList());

            System.out.println("cleaned seats (insertPassengerInfo): " + cleanedSeats);

            httpSession.setAttribute("passengers", passengers);

            httpSession.setAttribute("priceByClass", priceByClass);

            httpSession.setAttribute("reservedSeats", cleanedSeats);

            httpSession.setAttribute("seatClass", seatClass);
        }

        return "redirect:/paymentGateway";
    }
}
