package com.unibuc.rentacar.entities;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class Car {
    private Integer id;

    @NotNull(message = "Manufacturer ID is required")
    private Integer manufacturerId;

    @NotBlank(message = "Model name cannot be blank")
    @Size(max = 50, message = "Model name must not exceed 50 characters")
    private String model;

    @Min(value = 0, message = "Mileage cannot be negative")
    private Integer mileage;

    @NotBlank(message = "Fuel type is required")
    @Pattern(regexp = "Petrol|Diesel|Electric|Hybrid", message = "Fuel type must be one of: Petrol, Diesel, Electric, or Hybrid")
    private String fuelType;

    @NotNull(message = "Rental price is required")
    @Positive(message = "Rental price must be positive")
    private Double rentalPrice;
}