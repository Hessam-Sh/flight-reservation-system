package com.blackshuck.bookFlight.entity;

import com.blackshuck.bookFlight.enums.BookingStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking", uniqueConstraints = @UniqueConstraint(columnNames = {"passenger_id", "flight_id"}))
public class Booking
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;

    @Column(name = "booking_code", nullable = false, unique = true)
    private String bookingCode;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false)
    private BookingStatus bookingStatus;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "passenger_id", referencedColumnName = "passenger_id", nullable = false)
    private Passenger passengerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id", referencedColumnName = "flight_id", nullable = false)
    private Flight flightId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User userId;

    public Booking()
    {

    }

    public Booking(Integer bookingId, String bookingCode, LocalDateTime bookingDate,
                   BookingStatus bookingStatus, LocalDateTime createdAt, Passenger passengerId, Flight flightId, User userId)
    {
        this.bookingId = bookingId;
        this.bookingCode = bookingCode;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.createdAt = createdAt;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.userId = userId;
    }

    @Override
    public String toString()
    {
        return "Booking = {" +
                "bookingId = " + bookingId +
                ", bookingCode = '" + bookingCode + '\'' +
                ", bookingDate = " + bookingDate +
                ", bookingStatus = " + bookingStatus +
                ", createdAt = " + createdAt +
                '}';
    }

    public Integer getBookingId()
    {
        return bookingId;
    }
    public void setBookingId(Integer bookingId)
    {
        this.bookingId = bookingId;
    }

    public String getBookingCode()
    {
        return bookingCode;
    }
    public void setBookingCode(String bookingCode)
    {
        this.bookingCode = bookingCode;
    }

    public LocalDateTime getBookingDate()
    {
        return bookingDate;
    }
    public void setBookingDate(LocalDateTime bookingDate)
    {
        this.bookingDate = bookingDate;
    }

    public BookingStatus getBookingStatus()
    {
        return bookingStatus;
    }
    public void setBookingStatus(BookingStatus bookingStatus)
    {
        this.bookingStatus = bookingStatus;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    public Passenger getPassengerId()
    {
        return passengerId;
    }
    public void setPassengerId(Passenger passengerId)
    {
        this.passengerId = passengerId;
    }

    public Flight getFlightId()
    {
        return flightId;
    }
    public void setFlightId(Flight flightId)
    {
        this.flightId = flightId;
    }

    public User getUserId()
    {
        return userId;
    }
    public void setUserId(User userId)
    {
        this.userId = userId;
    }
}
