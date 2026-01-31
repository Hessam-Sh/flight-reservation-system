package com.blackshuck.bookFlight.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "airport")
public class Airport
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id")
    private Integer airportId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @OneToMany(targetEntity = Flight.class, mappedBy = "originAirportId",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Flight> originFlightId;

    @OneToMany(targetEntity = Flight.class, mappedBy = "destinationAirportId",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Flight> destinationFlightId;

    public Airport()
    {

    }

    public Airport(Integer airportId, String name, String city, String country,
                   List<Flight> originFlightId, List<Flight> destinationFlightId)
    {
        this.airportId = airportId;
        this.name = name;
        this.city = city;
        this.country = country;
        this.originFlightId = originFlightId;
        this.destinationFlightId = destinationFlightId;
    }

    @Override
    public String toString()
    {
        return "Airport = {" +
                "airportId = " + airportId +
                ", name = '" + name + '\'' +
                ", city = '" + city + '\'' +
                ", country = '" + country + '\'' +
                '}';
    }

    public Integer getAirportId()
    {
        return airportId;
    }
    public void setAirportId(Integer airportId)
    {
        this.airportId = airportId;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCountry()
    {
        return country;
    }
    public void setCountry(String country)
    {
        this.country = country;
    }

    public List<Flight> getOriginFlightId()
    {
        return originFlightId;
    }
    public void setOriginFlightId(List<Flight> originFlightId)
    {
        this.originFlightId = originFlightId;
    }

    public List<Flight> getDestinationFlightId()
    {
        return destinationFlightId;
    }
    public void setDestinationFlightId(List<Flight> destinationFlightId)
    {
        this.destinationFlightId = destinationFlightId;
    }
}
