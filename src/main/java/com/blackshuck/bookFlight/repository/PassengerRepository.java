package com.blackshuck.bookFlight.repository;

import com.blackshuck.bookFlight.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Integer>
{
    Optional<Passenger> findByNationalCode(String nationalCode);
}
