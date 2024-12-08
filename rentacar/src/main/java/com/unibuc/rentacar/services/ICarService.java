package com.unibuc.rentacar.services;


import com.unibuc.rentacar.entities.Car;

import java.util.List;

public interface ICarService{
    List<Car> getAllCars();

    Car getCarById(Integer id);

    int createCar(Car car);

    int updateCar(Integer id, Car car);

    void deleteCar(Integer id);
}
