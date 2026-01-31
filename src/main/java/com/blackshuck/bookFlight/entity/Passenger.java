package com.blackshuck.bookFlight.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "passenger")
public class Passenger
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private Integer passengerId;

    @Column(name = "first_name", nullable = false)
    @NotEmpty(message = "First name is required")
    private String firstName;

    @Column(name = "last_name",  nullable = false)
    @NotEmpty(message = "Last name is required")
    private String lastName;

    @Column(name = "national_code", nullable = false, unique = true)
    @NotEmpty(message = "National code is required")
    @Size(min = 10, max = 10, message = "National code must be 10 characters")
    @Pattern(regexp = "[0-9]+", message = "National code must be numeric")
    private String nationalCode;

    @Column(name = "born_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Birthday must be in the past")
    private Date bornDate;

    @Column(name = "phone_number", nullable = false, unique = true)
    @NotEmpty(message = "Phone number is required")
    private String phoneNumber;

    @OneToMany(targetEntity = Ticket.class, mappedBy = "passengerId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> ticketId;

    @OneToMany(targetEntity = Booking.class, mappedBy = "bookingId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookingId;

    public Passenger()
    {

    }

    public Passenger(Integer passengerId, String firstName, String lastName, String nationalCode,
                     Date bornDate, String phoneNumber, List<Ticket> ticketId, List<Booking> bookingId)
    {
        this.passengerId = passengerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.bornDate = bornDate;
        this.phoneNumber = phoneNumber;
        this.ticketId = ticketId;
        this.bookingId = bookingId;
    }

    @Override
    public String toString()
    {
        return "Passenger = {" +
                "passengerId = " + passengerId +
                ", firstName = '" + firstName + '\'' +
                ", lastName = '" + lastName + '\'' +
                ", nationalCode = '" + nationalCode + '\'' +
                ", bornDate = " + bornDate +
                ", phoneNumber = '" + phoneNumber + '\'' +
                '}';
    }

    public Integer getPassengerId()
    {
        return passengerId;
    }
    public void setPassengerId(Integer passengerId)
    {
        this.passengerId = passengerId;
    }

    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getNationalCode()
    {
        return nationalCode;
    }
    public void setNationalCode(String nationalCode)
    {
        this.nationalCode = nationalCode;
    }

    public Date getBornDate()
    {
        return bornDate;
    }
    public void setBornDate(Date bornDate)
    {
        this.bornDate = bornDate;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
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
