package com.blackshuck.bookFlight.entity;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

public class PassengerForm
{
    @Valid
    private List<Passenger> passengers = new ArrayList<>();

    public List<Passenger> getPassengers()
    {
        return passengers;
    }
    public void setPassengers(List<Passenger> passengers)
    {
        this.passengers = passengers;
    }
}
