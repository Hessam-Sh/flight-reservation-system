package com.blackshuck.bookFlight.repository;

import com.blackshuck.bookFlight.entity.Passenger;
import com.blackshuck.bookFlight.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer>
{
    @Query(value = "SELECT t.seatNumber FROM Ticket t WHERE t.flightId.flightId = :flightId")
    List<String> findAllSeatsByFlightId(@Param("flightId") int flightId);

    @Query("SELECT t FROM Ticket t JOIN FETCH t.passengerId p JOIN FETCH t.flightId f JOIN FETCH f.originAirportId" +
            " JOIN FETCH f.destinationAirportId WHERE t.passengerId = :passenger ORDER BY t.ticketDate DESC")
    List<Ticket> findLatestTicket(@Param("passenger") Passenger passenger);
}
