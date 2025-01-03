package com.unibuc.rentacar.services;

import com.unibuc.rentacar.entities.Car;
import com.unibuc.rentacar.entities.Manufacturer;
import com.unibuc.rentacar.exception.NoAvailableCarsException;
import com.unibuc.rentacar.json.FuelType;
import com.unibuc.rentacar.repositories.CarRepository;
import com.unibuc.rentacar.repositories.ManufacturerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private CarService carService;

    private Car mockCar;
    private Car mockCar2;
    private Manufacturer mockManufacturer;

    public CarServiceTest() {
        MockitoAnnotations.openMocks(this);
        mockManufacturer = new Manufacturer();
        mockManufacturer.setId(1);
        mockManufacturer.setName("Tesla");
        mockManufacturer.setCountry("USA");

        mockCar = new Car();
        mockCar.setId(1);
        mockCar.setModel("Model X");
        mockCar.setMileage(13000);
        mockCar.setFuelType(FuelType.ELECTRIC);
        mockCar.setRentalPrice(100.00);
        mockCar.setManufacturer(mockManufacturer);

        mockCar2 = new Car();
        mockCar2.setId(2);
        mockCar2.setModel("Model Y");
        mockCar2.setMileage(15000);
        mockCar2.setFuelType(FuelType.ELECTRIC);
        mockCar2.setRentalPrice(100.00);
        mockCar2.setManufacturer(mockManufacturer);
    }

    @Test
    void getAllCars_Returns() {
        when(carRepository.findAll()).thenReturn(List.of(mockCar, mockCar2));

        List<Car> result = carService.getAllCars();

        // Mocked cars are expected to pe asserted successfully

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Model X", result.get(0).getModel());
        assertEquals("Model Y", result.get(1).getModel());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void getCarById_Returns() {
        Integer carId = 1;
        when(carRepository.findById(carId)).thenReturn(Optional.of(mockCar));

        Optional<Car> result = carService.getCarById(carId);

        assertTrue(result.isPresent());
        assertEquals("Model X", result.get().getModel());
        verify(carRepository, times(1)).findById(carId);
    }

    @Test
    void getAvailableCars_ValidDate_Returns() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);
        when(carRepository.findAvailableCars(startDate, endDate)).thenReturn(List.of(mockCar));

        List<Car> result = carService.getAvailableCars(startDate, endDate);

        // We simulate a list of cars that would have been returned if the range is valid

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Model X", result.get(0).getModel());
        verify(carRepository, times(1)).findAvailableCars(startDate, endDate);
    }

    @Test
    void getAvailableCars_InvalidDate_NoAvailableCarsException() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);
        when(carRepository.findAvailableCars(startDate, endDate)).thenReturn(List.of());

        NoAvailableCarsException exception = assertThrows(NoAvailableCarsException.class, () -> carService.getAvailableCars(startDate, endDate));

        // We do the same here but return an empty list of available cars

        assertEquals("No cars available for the selected date range.", exception.getMessage());
        verify(carRepository, times(1)).findAvailableCars(startDate, endDate);
    }

    @Test
    void createCar_Returns() {
        when(manufacturerRepository.findById(anyInt())).thenReturn(Optional.of(mockManufacturer));
        when(carRepository.save(any(Car.class))).thenReturn(mockCar);

        Car result = carService.createCar(mockCar);

        assertNotNull(result);
        assertEquals("Model X", result.getModel());
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void deleteCar_NoContext() {
        Integer carId = 1;

        carService.deleteCar(carId);

        verify(carRepository, times(1)).deleteById(carId);
    }
}
