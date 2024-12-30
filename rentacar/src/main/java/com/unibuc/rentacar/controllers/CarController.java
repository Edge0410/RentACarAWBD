package com.unibuc.rentacar.controllers;

import com.unibuc.rentacar.entities.Car;
import com.unibuc.rentacar.json.FuelType;
import com.unibuc.rentacar.services.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Tag(name = "Vehicle Management and Filtering Controller")
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Operation(summary = "Get all cars", description = "Retrieve a list of all cars.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of cars retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class)))
    })
    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @Operation(summary = "Get car by ID", description = "Retrieve a specific car by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Integer id) {
        return ResponseEntity.ok(carService.getCarById(id).orElseThrow());
    }

    @Operation(summary = "Get available cars by date range", description = "Retrieve cars available between start and end dates.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of available cars retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class)))
    })
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

    @Operation(summary = "Get available cars with filters", description = "Retrieve cars available between start and end dates, with optional filters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of filtered available cars retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class)))
    })
    @GetMapping("/availableFiltered")
    public ResponseEntity<List<Car>> getAvailableCarsWithFilters(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) Integer manufacturerId,
            @RequestParam(required = false) FuelType fuelType,
            @RequestParam(required = false) Double maxRentalPrice) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<Car> availableCars = carService.getAvailableCarsWithFilters(start, end, manufacturerId, fuelType, maxRentalPrice);
        return ResponseEntity.ok(availableCars);
    }

    @Operation(summary = "Create a new car", description = "Add a new car to the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "404", description = "Wrong car syntax")
    })
    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return ResponseEntity.ok(carService.createCar(car));
    }

    @Operation(summary = "Update a car", description = "Update an existing car's details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Integer id, @RequestBody Car car) {
        return ResponseEntity.ok(carService.updateCar(id, car));
    }

    @Operation(summary = "Delete a car", description = "Remove a car by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Car deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
