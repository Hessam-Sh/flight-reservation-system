package com.blackshuck.bookFlight.repository;

import com.blackshuck.bookFlight.entity.Booking;
import com.blackshuck.bookFlight.entity.Passenger;
import com.blackshuck.bookFlight.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer>
{
    List<Booking> findAllByUserIdUserId(int id);

    @Query("SELECT b FROM Booking b WHERE b.bookingStatus = :bookingStatus AND b.createdAt < :threshold")
    List<Booking> findAllByBooKingStatusAndCreatedAtBefore(@Param("bookingStatus") BookingStatus bookingStatus,
                                                        @Param("threshold") LocalDateTime threshold);

    @Query("SELECT b FROM Booking b WHERE b.userId.userId = :userId AND b.createdAt >= :threshold" +
            " AND b.bookingStatus = :bookingStatus")
    List<Booking> findAllByUserIdAndCreatedAtBefore(@Param("userId") int userId,
                                                    @Param("threshold") LocalDateTime threshold,
                                                    @Param("bookingStatus") BookingStatus bookingStatus);

    @Query("SELECT b FROM Booking b WHERE b.passengerId = :passenger AND " +
            "b.bookingStatus = 'CONFIRMED' ORDER BY b.bookingDate DESC")
    List<Booking> findConfirmedBookingsByPassengerOrdered(@Param("passenger")Passenger passenger);
}
