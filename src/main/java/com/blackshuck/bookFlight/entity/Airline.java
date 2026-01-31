package com.blackshuck.bookFlight.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "airline")
public class Airline
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airline_id")
    private Integer airlineId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "plane_type", nullable = false)
    private String planeType;

    @Column(name = "logoUrl", nullable = true)
    private String logoUrl;

    @OneToMany(targetEntity = Flight.class, mappedBy = "airlineId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Flight> flightId;

    public Airline()
    {

    }

    public Airline(Integer airlineId, String name, String planeType, String logoUrl, List<Flight> flightId)
    {
        this.airlineId = airlineId;
        this.name = name;
        this.planeType = planeType;
        this.logoUrl = logoUrl;
        this.flightId = flightId;
    }

    @Transient
    public String getLogosImagePath()
    {
        if (logoUrl == null)
        {
            return null;
        }

        return "logos/airline/" + airlineId + "/" + logoUrl;
    }

    @Override
    public String toString()
    {
        return "Airline = {" +
                "airlineId = " + airlineId +
                ", name = '" + name + '\'' +
                ", planeType = '" + planeType + '\'' +
                ", logoUrl = '" + logoUrl + '\'' +
                '}';
    }

    public Integer getAirlineId()
    {
        return airlineId;
    }
    public void setAirlineId(Integer airlineId)
    {
        this.airlineId = airlineId;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getPlaneType()
    {
        return planeType;
    }
    public void setPlaneType(String planeType)
    {
        this.planeType = planeType;
    }

    public String getLogoUrl()
    {
        return logoUrl;
    }
    public void setLogoUrl(String logoUrl)
    {
        this.logoUrl = logoUrl;
    }

    public List<Flight> getFlightId()
    {
        return flightId;
    }
    public void setFlightId(List<Flight> flightId)
    {
        this.flightId = flightId;
    }
}
