package com.unibuc.rentacar.repositories;

import com.unibuc.rentacar.entities.Car;
import com.unibuc.rentacar.json.FuelType;
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

    @Query("""
        SELECT DISTINCT c FROM Car c 
        WHERE c.id NOT IN (
            SELECT bc.id 
            FROM Booking b 
            JOIN b.cars bc 
            WHERE (b.startDate <= :endDate AND b.endDate >= :startDate)
        )
        AND (:manufacturerId IS NULL OR c.manufacturer.id = :manufacturerId)
        AND (:fuelType IS NULL OR c.fuelType = :fuelType)
        AND (:maxRentalPrice IS NULL OR c.rentalPrice <= :maxRentalPrice)
    """)
    List<Car> findAvailableCarsWithFilters(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("manufacturerId") Integer manufacturerId,
            @Param("fuelType") FuelType fuelType,
            @Param("maxRentalPrice") Double maxRentalPrice
    );
}
