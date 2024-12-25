package com.unibuc.rentacar.controllers;

import com.unibuc.rentacar.entities.Car;
import com.unibuc.rentacar.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Integer id) {
        return ResponseEntity.ok(carService.getCarById(id).orElseThrow());
    }

    @GetMapping("/available")
    public ResponseEntity<List<Car>> getAvailableCars(
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<Car> availableCars = carService.getAvailableCars(start, end);
        return ResponseEntity.ok(availableCars);
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return ResponseEntity.ok(carService.saveCar(car));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Integer id, @RequestBody Car car) {
        return ResponseEntity.ok(carService.updateCar(id, car));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
