package com.blackshuck.bookFlight.entity;

public class TicketFormDate
{
    private Airline airline;

    private Airport originAirport;

    private Airport destinationAirport;

    private Ticket ticket;

    private Flight flight;

    public TicketFormDate()
    {

    }

    public TicketFormDate(Airline airline, Airport originAirport,
                          Airport destinationAirport, Ticket ticket, Flight flight)
    {
        this.airline = airline;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
        this.ticket = ticket;
        this.flight = flight;
    }

    public Airline getAirline()
    {
        return airline;
    }
    public void setAirline(Airline airline)
    {
        this.airline = airline;
    }

    public Airport getOriginAirport()
    {
        return originAirport;
    }
    public void setOriginAirport(Airport originAirport)
    {
        this.originAirport = originAirport;
    }

    public Airport getDestinationAirport()
    {
        return destinationAirport;
    }
    public void setDestinationAirport(Airport destinationAirport)
    {
        this.destinationAirport = destinationAirport;
    }

    public Ticket getTicket()
    {
        return ticket;
    }
    public void setTicket(Ticket ticket)
    {
        this.ticket = ticket;
    }

    public Flight getFlight()
    {
        return flight;
    }
    public void setFlight(Flight flight)
    {
        this.flight = flight;
    }
}
