package com.unibuc.rentacar.repositories;

import com.unibuc.rentacar.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query("""
    SELECT c FROM Car c 
    WHERE c.id NOT IN (
        SELECT DISTINCT bc.id 
        FROM Booking b 
        JOIN b.cars bc 
        WHERE (b.startDate <= :endDate AND b.endDate >= :startDate)
    )
""")
    List<Car> findAvailableCars(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
