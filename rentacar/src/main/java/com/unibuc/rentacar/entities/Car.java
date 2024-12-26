package com.unibuc.rentacar.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unibuc.rentacar.json.FuelType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false)
    @JsonBackReference
    private Manufacturer manufacturer;

    @ManyToMany(mappedBy = "cars")
    @JsonIgnore
    private List<Booking> bookings;

    @Column(nullable = false)
    @Size(max = 50, message = "Model name must not exceed 50 characters")
    private String model;

    @Column(nullable = false)
    @Min(value = 0, message = "Mileage cannot be negative")
    private Integer mileage;

    @Column(nullable = false)
    private FuelType fuelType;

    @Column(nullable = false)
    @PositiveOrZero(message = "Rental price must be positive")
    private Double rentalPrice;
}