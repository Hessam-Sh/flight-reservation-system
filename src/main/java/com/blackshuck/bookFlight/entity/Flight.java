package com.blackshuck.bookFlight.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "flight")
public class Flight
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Integer flightId;

    @Column(name = "origin", nullable = false)
    @NotEmpty(message = "Choose an origin!")
    private String origin;

    @Column(name = "destination", nullable = false)
    @NotEmpty(message = "Choose a destination!")
    private String destination;

    @Column(name = "departure_date", nullable = false)
    @NotNull(message = "Choose a departure date!")
    @Future(message = "Departure date must be after today")
    private LocalDate departureDate;

    @Column(name = "takeoff_time")
    private LocalTime takeoffTime;

    @Column(name = "landing_time")
    private LocalTime landingTime;

    @Column(name = "capacity")
    private int capacity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "origin_airport_id", referencedColumnName = "airport_id", nullable = false)
    private Airport originAirportId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_airport_id", referencedColumnName = "airport_id", nullable = false)
    private Airport destinationAirportId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "airline_id", referencedColumnName = "airline_id", nullable = false)
    private Airline airlineId;

    @OneToMany(targetEntity = Ticket.class, mappedBy = "flightId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> ticketId;

    @OneToMany(targetEntity = Booking.class, mappedBy = "bookingId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookingId;

    public Flight()
    {

    }

    public Flight(Integer flightId, String origin, String destination, LocalDate departureDate,
                  LocalTime takeoffTime, LocalTime landingTime, int capacity, Airport originAirportId,
                  Airport destinationAirportId, Airline airlineId, List<Ticket> ticketId, List<Booking> bookingId)
    {
        this.flightId = flightId;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.takeoffTime = takeoffTime;
        this.landingTime = landingTime;
        this.capacity = capacity;
        this.originAirportId = originAirportId;
        this.destinationAirportId = destinationAirportId;
        this.airlineId = airlineId;
        this.ticketId = ticketId;
        this.bookingId= bookingId;
    }

    @Override
    public String toString()
    {
        return "Flight = {" +
                "flightId = " + flightId +
                ", origin = '" + origin + '\'' +
                ", destination = '" + destination + '\'' +
                ", departureDate = " + departureDate +
                ", takeoffTime = " + takeoffTime +
                ", landingTime = " + landingTime +
                ", capacity = " + capacity +
                '}';
    }

    public Integer getFlightId()
    {
        return flightId;
    }
    public void setFlightId(Integer flightId)
    {
        this.flightId = flightId;
    }

    public String getOrigin()
    {
        return origin;
    }
    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public String getDestination()
    {
        return destination;
    }
    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    public LocalDate getDepartureDate()
    {
        return departureDate;
    }
    public void setDepartureDate(LocalDate departureDate)
    {
        this.departureDate = departureDate;
    }

    public LocalTime getTakeoffTime()
    {
        return takeoffTime;
    }
    public void setTakeoffTime(LocalTime takeoffTime)
    {
        this.takeoffTime = takeoffTime;
    }

    public LocalTime getLandingTime()
    {
        return landingTime;
    }
    public void setLandingTime(LocalTime landingTime)
    {
        this.landingTime = landingTime;
    }

    public int getCapacity()
    {
        return capacity;
    }
    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public Airport getOriginAirportId()
    {
        return originAirportId;
    }
    public void setOriginAirportId(Airport originAirportId)
    {
        this.originAirportId = originAirportId;
    }

    public Airport getDestinationAirportId()
    {
        return destinationAirportId;
    }
    public void setDestinationAirportId(Airport destinationAirportId)
    {
        this.destinationAirportId = destinationAirportId;
    }

    public Airline getAirlineId()
    {
        return airlineId;
    }
    public void setAirlineId(Airline airlineId)
    {
        this.airlineId = airlineId;
    }

    public List<Ticket> getTicketId()
    {
        return ticketId;
    }
    public void setTicketId(List<Ticket> ticketId)
    {
        this.ticketId = ticketId;
    }

    public List<Booking> getBookingId()
    {
        return bookingId;
    }
    public void setBookingId(List<Booking> bookingId)
    {
        this.bookingId = bookingId;
    }
}
