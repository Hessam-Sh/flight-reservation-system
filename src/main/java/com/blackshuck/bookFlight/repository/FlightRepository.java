package com.blackshuck.bookFlight.repository;

import com.blackshuck.bookFlight.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer>
{
    @Query("SELECT f FROM Flight f " +
            "WHERE f.origin = :origin " +
            "AND f.destination = :destination " +
            "AND f.departureDate = :departureDate")
    List<Flight> searchForFlights(@Param("origin") String origin, @Param("destination") String destination,
                                  @Param("departureDate") LocalDate departureDate);
}
