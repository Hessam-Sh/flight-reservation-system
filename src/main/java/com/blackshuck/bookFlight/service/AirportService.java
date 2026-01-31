package com.blackshuck.bookFlight.service;

import com.blackshuck.bookFlight.entity.Airport;
import com.blackshuck.bookFlight.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirportService
{
    private final AirportRepository airportRepository;

    @Autowired
    public AirportService(AirportRepository airportRepository)
    {
        this.airportRepository = airportRepository;
    }

    public Airport addNew(Airport airport)
    {
        return airportRepository.save(airport);
    }
}
