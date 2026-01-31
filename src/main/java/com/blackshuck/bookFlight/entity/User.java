package com.blackshuck.bookFlight.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`user`")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "email", nullable = false, unique = true)
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email is not valid")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@(emai\\.com|gmail\\.com)", message = "Email is not valid")
    private String email;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Column(name = "registration_date", updatable = false, nullable = false)
    @CreationTimestamp
    private Date registrationDate;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @OneToMany(targetEntity = Booking.class, mappedBy = "bookingId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookingId;

    public User()
    {

    }

    public User(Integer userId, String email, String password, Date registrationDate, boolean isActive,
                boolean isAdmin, List<Booking> bookingId)
    {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.isActive = isActive;
        this.isAdmin = isAdmin;
        this.bookingId = bookingId;
    }

    @Override
    public String toString()
    {
        return "User = {" +
                "userId = " + userId +
                ", email = '" + email + '\'' +
                ", password = '" + password + '\'' +
                ", registrationDate = " + registrationDate +
                ", isActive = " + isActive +
                ", isAdmin = " + isAdmin +
                '}';
    }

    public Integer getUserId()
    {
        return userId;
    }
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public Date getRegistrationDate()
    {
        return registrationDate;
    }
    public void setRegistrationDate(Date registrationDate)
    {
        this.registrationDate = registrationDate;
    }

    public boolean isActive()
    {
        return isActive;
    }
    public void setActive(boolean active)
    {
        isActive = active;
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }
    public void setAdmin(boolean admin)
    {
        isAdmin = admin;
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
