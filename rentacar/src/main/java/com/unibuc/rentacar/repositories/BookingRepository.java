package com.unibuc.rentacar.repositories;

import com.unibuc.rentacar.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUserId(Integer userId);

    @Query("SELECT COUNT(bc.id) FROM Booking b " +
            "JOIN b.cars bc " +
            "WHERE b.user.id = :userId " +
            "AND b.startDate <= :endDate " +
            "AND b.endDate >= :startDate")
    long countActiveCarsByUser(
            @Param("userId") Integer userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}
