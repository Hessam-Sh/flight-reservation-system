package com.blackshuck.bookFlight.repository;

import com.blackshuck.bookFlight.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Integer>
{

}

