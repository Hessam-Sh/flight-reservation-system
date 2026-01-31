package com.blackshuck.bookFlight.repository;

import com.blackshuck.bookFlight.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Integer>
{

}
