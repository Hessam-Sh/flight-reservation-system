package com.blackshuck.bookFlight.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "ticket", uniqueConstraints = @UniqueConstraint(columnNames = {"flight_id", "seat_number"}))
public class Ticket
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Integer ticketId;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(name = "seat_class", nullable = false)
    private String seatClass;

    @Column(name = "ticket_date")
    @CreationTimestamp
    private LocalDateTime ticketDate;

    @Column(name = "price", nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id", referencedColumnName = "flight_id", nullable = false)
    private Flight flightId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "passenger_id", referencedColumnName = "passenger_id", nullable = false)
    private Passenger passengerId;

    public Ticket()
    {

    }

    public Ticket(Integer ticketId, String seatNumber, String seatClass, LocalDateTime ticketDate, int price,
                  Flight flightId, Passenger passengerId)
    {
        this.ticketId = ticketId;
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.ticketDate = ticketDate;
        this.price = price;
        this.flightId = flightId;
        this.passengerId = passengerId;
    }

    @Override
    public String toString()
    {
        return "Ticket = {" +
                "ticketId = " + ticketId +
                ", seatNumber = '" + seatNumber + '\'' +
                ", seatClass = '" + seatClass + '\'' +
                ", ticketDate = " + ticketDate +
                ", price = " + price +
                '}';
    }

    public Integer getTicketId()
    {
        return ticketId;
    }
    public void setTicketId(Integer ticketId)
    {
        this.ticketId = ticketId;
    }

    public String getSeatNumber()
    {
        return seatNumber;
    }
    public void setSeatNumber(String seatNumber)
    {
        this.seatNumber = seatNumber;
    }

    public String getSeatClass()
    {
        return seatClass;
    }
    public void setSeatClass(String seatClass)
    {
        this.seatClass = seatClass;
    }

    public LocalDateTime getTicketDate()
    {
        return ticketDate;
    }
    public void setTicketDate(LocalDateTime ticketDate)
    {
        this.ticketDate = ticketDate;
    }

    public int getPrice()
    {
        return price;
    }
    public void setPrice(int price)
    {
        this.price = price;
    }

    public Flight getFlightId()
    {
        return flightId;
    }
    public void setFlightId(Flight flightId)
    {
        this.flightId = flightId;
    }

    public Passenger getPassengerId()
    {
        return passengerId;
    }
    public void setPassengerId(Passenger passengerId)
    {
        this.passengerId = passengerId;
    }
}
