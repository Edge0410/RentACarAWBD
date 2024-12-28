package com.unibuc.rentacar.services;

import com.unibuc.rentacar.entities.Car;
import com.unibuc.rentacar.exception.NoAvailableCarsException;
import com.unibuc.rentacar.json.FuelType;
import com.unibuc.rentacar.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarById(Integer id) {
        return carRepository.findById(id);
    }

    public List<Car> getAvailableCars(LocalDate startDate, LocalDate endDate) {
        List<Car> availableCars = carRepository.findAvailableCars(startDate, endDate);

        if (availableCars.isEmpty()) {
            throw new NoAvailableCarsException("No cars available for the selected date range.");
        }

        return availableCars;
    }

    public List<Car> getAvailableCarsWithFilters(
            LocalDate startDate,
            LocalDate endDate,
            Integer manufacturerId,
            FuelType fuelType,
            Double maxRentalPrice) {

        List<Car> availableCars =  carRepository.findAvailableCarsWithFilters(startDate, endDate, manufacturerId, fuelType, maxRentalPrice);

        if (availableCars.isEmpty()) {
            throw new NoAvailableCarsException("No cars available for the selected date range.");
        }

        return availableCars;
    }


    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public Car updateCar(Integer id, Car carDetails) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        car.setModel(carDetails.getModel());
        car.setMileage(carDetails.getMileage());
        car.setFuelType(carDetails.getFuelType());
        car.setRentalPrice(carDetails.getRentalPrice());
        car.setManufacturer(carDetails.getManufacturer());

        return carRepository.save(car);
    }

    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }
}
