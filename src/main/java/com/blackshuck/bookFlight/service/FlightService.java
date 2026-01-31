package com.blackshuck.bookFlight.service;

import com.blackshuck.bookFlight.entity.Airline;
import com.blackshuck.bookFlight.entity.Airport;
import com.blackshuck.bookFlight.entity.Flight;
import com.blackshuck.bookFlight.repository.AirlineRepository;
import com.blackshuck.bookFlight.repository.AirportRepository;
import com.blackshuck.bookFlight.repository.FlightRepository;
import com.blackshuck.bookFlight.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FlightService
{
    private final FlightRepository flightRepository;

    private final TicketRepository ticketRepository;

    private final AirportRepository airportRepository;

    private final AirlineRepository airlineRepository;

    private int count = 0;

    @Autowired
    public FlightService(FlightRepository flightRepository, TicketRepository ticketRepository,
                         AirportRepository airportRepository, AirlineRepository airlineRepository)
    {
        this.flightRepository = flightRepository;
        this.ticketRepository = ticketRepository;
        this.airportRepository = airportRepository;
        this.airlineRepository = airlineRepository;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public String getAvailableSeat(int capacity, int flightId, String seatClass)
    {
        List<String> allFirstClassSeats = new ArrayList<>();

        List<String> allBusinessClassSeats = new ArrayList<>();

        List<String> allEconomyClassSeats = new ArrayList<>();

        String pendingFirstClassSeats;

        String pendingBusinessClassSeats;

        String pendingEconomyClassSeats;

        String[] letters = {"A", "B", "C", "D", "E", "F"};

        int rows = capacity / letters.length;

        for (int i = 1; i <= 3; i++)
        {
            for (String letter : letters)
            {
                allFirstClassSeats.add(letter + i);
            }
        }

        for (int i = 4; i <= 6; i++)
        {
            for (String letter : letters)
            {
                allBusinessClassSeats.add(letter + i);
            }
        }

        for (int i = 7; i <= rows; i++)
        {
            for (String letter : letters)
            {
                allEconomyClassSeats.add(letter + i);
            }
        }

        List<String> reservedSeats = ticketRepository.findAllSeatsByFlightId(flightId)
                .stream().distinct().collect(Collectors.toList());

        System.out.println("Available Seats: " + reservedSeats);

        allFirstClassSeats.removeAll(reservedSeats);
        allBusinessClassSeats.removeAll(reservedSeats);
        allEconomyClassSeats.removeAll(reservedSeats);

        System.out.println("Economy Seats after removing: " + allEconomyClassSeats);

        if (seatClass.equals("First"))
        {
            if (allFirstClassSeats.isEmpty())
            {
                throw new RuntimeException("There is no extra First Class seat");
            }
            else
            {
                pendingFirstClassSeats = allFirstClassSeats.get(count);

                count++;

                return pendingFirstClassSeats;
            }
        }
        else if (seatClass.equals("Business"))
        {
            if (allBusinessClassSeats.isEmpty())
            {
                throw new RuntimeException("There is no extra Business seat");
            }
            else
            {
                pendingBusinessClassSeats = allBusinessClassSeats.get(count);

                count++;

                return pendingBusinessClassSeats;
            }
        }
        else
        {
            if (allEconomyClassSeats.isEmpty())
            {
                throw new RuntimeException("There is no extra Economy seat");
            }
            else
            {
                pendingEconomyClassSeats = allEconomyClassSeats.get(count);

                count++;

                return pendingEconomyClassSeats;
            }
        }
    }

    public int showAvailableSeat(int capacity, Integer flightId, String seatClass)
    {
        List<String> allFirstClassSeats = new ArrayList<>();

        List<String> allBusinessClassSeats = new ArrayList<>();

        List<String> allEconomyClassSeats = new ArrayList<>();

        String[] letters = {"A", "B", "C", "D", "E", "F"};

        int rows = capacity / letters.length;

        for (int i = 1; i <= 3; i++)
        {
            for (String letter : letters)
            {
                allFirstClassSeats.add(letter + i);
            }
        }

        for (int i = 4; i <= 6; i++)
        {
            for (String letter : letters)
            {
                allBusinessClassSeats.add(letter + i);
            }
        }

        for (int i = 7; i <= rows; i++)
        {
            for (String letter : letters)
            {
                allEconomyClassSeats.add(letter + i);
            }
        }

        List<String> reservedSeats = ticketRepository.findAllSeatsByFlightId(flightId);

        allFirstClassSeats.removeAll(reservedSeats);
        allBusinessClassSeats.removeAll(reservedSeats);
        allEconomyClassSeats.removeAll(reservedSeats);

        if (seatClass.equals("First"))
        {
            return allFirstClassSeats.size();
        }
        else if (seatClass.equals("Business"))
        {
            return allBusinessClassSeats.size();
        }
        else
        {
            return allEconomyClassSeats.size();
        }
    }

    public Flight addNew(Flight flight, int originAirportId, int destinationAirportId, int airlineId)
    {
        Airport originAirport = airportRepository.findById(originAirportId)
                .orElseThrow(() -> new RuntimeException("Origin Airport not found"));

        Airport destinationAirport = airportRepository.findById(destinationAirportId)
                .orElseThrow(() -> new RuntimeException("Origin Airport not found"));

        Airline airline = airlineRepository.findById(airlineId)
                .orElseThrow(() -> new RuntimeException("Airline not found"));

        flight.setOriginAirportId(originAirport);

        flight.setDestinationAirportId(destinationAirport);

        flight.setAirlineId(airline);

        return flightRepository.save(flight);
    }

    public List<Flight> searchFlights(String origin, String destination, LocalDate departureDate)
    {
        return flightRepository.searchForFlights(origin, destination, departureDate);
    }

    public Flight serachFlight(int flightNumber)
    {
        return flightRepository.findById(flightNumber).orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    public int getPriceByClass(String seatClass)
    {
        return switch (seatClass)
        {
            case "First" -> 6000;
            case "Business" -> 4500;
            case "Economy" -> 2300;
            default -> 0;
        };
    }
}
