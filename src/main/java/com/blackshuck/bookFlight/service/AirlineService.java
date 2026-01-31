package com.blackshuck.bookFlight.service;

import com.blackshuck.bookFlight.entity.Airline;
import com.blackshuck.bookFlight.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirlineService
{
    private final AirlineRepository airlineRepository;

    @Autowired
    public AirlineService(AirlineRepository airlineRepository)
    {
        this.airlineRepository = airlineRepository;
    }

    public Airline addNew(Airline airline)
    {
        return airlineRepository.save(airline);
    }
}
