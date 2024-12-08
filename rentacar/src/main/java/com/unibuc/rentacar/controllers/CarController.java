package com.unibuc.rentacar.controllers;

import com.unibuc.rentacar.entities.Car;
import com.unibuc.rentacar.services.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    // Get all cars
    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    // Get a car by ID
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Integer id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    // Create a new car
    @PostMapping
    public ResponseEntity<String> createCar(@Valid @RequestBody Car car) {
        carService.createCar(car);
        return ResponseEntity.ok("Car created successfully.");
    }

    // Update an existing car
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCar(@PathVariable Integer id, @Valid @RequestBody Car car) {
        carService.updateCar(id, car);
        return ResponseEntity.ok("Car updated successfully.");
    }

    // Delete a car by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
