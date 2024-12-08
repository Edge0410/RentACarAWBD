package com.unibuc.rentacar.services;

import com.unibuc.rentacar.entities.Car;
import com.unibuc.rentacar.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService implements ICarService{

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // Method to find a car by ID
    @Override
    public Car getCarById(Integer id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with ID: " + id));
    }

    // Method to save a car (insert or update)
    public int createCar(Car car) {
        return carRepository.save(car);
    }

    // Method to update a car by ID
    public int updateCar(Integer id, Car car) {
        return carRepository.update(id, car);
    }

    // Method to delete a car by ID
    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }
}
