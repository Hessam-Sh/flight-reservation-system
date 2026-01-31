package com.blackshuck.bookFlight.controller;

import com.blackshuck.bookFlight.entity.Flight;
import com.blackshuck.bookFlight.entity.Passenger;
import com.blackshuck.bookFlight.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController
{
    private final FlightService flightService;

    @Autowired
    public SearchController(FlightService flightService)
    {
        this.flightService = flightService;
    }

    @GetMapping("/index")
    public String indexPage()
    {
        return "index";
    }

    @GetMapping("/search")
    public String searchForFlights(@RequestParam("origin") String origin,
                                   @RequestParam("destination") String destination,
                                   @RequestParam("departureDate")
                                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDate,
                                   @RequestParam("seatClass") String seatClass,
                                   @RequestParam("passengerNum") int passengerNum,
                                   Model model)
    {
        List<Flight> flights = flightService.searchFlights(origin, destination, departureDate);

        Map<Integer, Integer> numberOfSeat = new HashMap<>();

        for (Flight flight : flights)
        {
            numberOfSeat.put(flight.getFlightId(),
                    flightService.showAvailableSeat(flight.getCapacity(), flight.getFlightId(), seatClass));
        }

        Map<String, Object> searchINFO = new HashMap<>();

        searchINFO.put("origin", origin);
        searchINFO.put("destination", destination);
        searchINFO.put("departureDate", departureDate);
        searchINFO.put("seatClass", seatClass);
        searchINFO.put("passengerNum", passengerNum);

        int priceByClass = flightService.getPriceByClass(seatClass);

        model.addAttribute("flight", flights);

        model.addAttribute("numberOfSeat", numberOfSeat);

        model.addAttribute("searchINFO", searchINFO);

        model.addAttribute("priceByClass", priceByClass);

        return "flight";
    }
}
