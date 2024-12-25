package com.unibuc.rentacar.services;

import com.unibuc.rentacar.entities.Booking;
import com.unibuc.rentacar.entities.Car;
import com.unibuc.rentacar.repositories.BookingRepository;
import com.unibuc.rentacar.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CarRepository carRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Integer id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }

    public List<Booking> getBookingsByUserId(Integer userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Transactional
    public Booking createBooking(Booking booking) {
        // Fetch available cars for the given date range
        List<Car> availableCars = carRepository.findAvailableCars(
                booking.getStartDate(),
                booking.getEndDate()
        );

        Set<Integer> availableCarIds = availableCars.stream()
                .map(Car::getId)
                .collect(Collectors.toSet());

        Set<Integer> bookingCarIds = booking.getCars().stream()
                .map(Car::getId)
                .collect(Collectors.toSet());

        System.out.println(availableCarIds);
        System.out.println(bookingCarIds);

        // Validate that each car in the booking is available
        boolean allCarsAvailable = availableCarIds.containsAll(bookingCarIds);

        if (!allCarsAvailable) {
            throw new RuntimeException("One or more cars are unavailable for the selected date range.");
        }

        // Calculate price section

        List<Car> cars = carRepository.findAllById(bookingCarIds);
        if (cars.size() != bookingCarIds.size()) {
            throw new IllegalArgumentException("One or more cars are invalid or unavailable.");
        }

        // Calculate total price based on rental prices of selected cars

        long days = booking.getEndDate().toEpochDay() - booking.getStartDate().toEpochDay();
        double totalPrice = cars.stream()
                .mapToDouble(Car::getRentalPrice)
                .sum() * days;

        // Set the total price in the booking
        booking.setTotalPrice(totalPrice);

        // Save the booking if validation passes
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Integer id, Booking bookingDetails) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStartDate(bookingDetails.getStartDate());
        booking.setEndDate(bookingDetails.getEndDate());
        booking.setStatus(bookingDetails.getStatus());
        booking.setTotalPrice(bookingDetails.getTotalPrice());
        booking.setCars(bookingDetails.getCars());

        List<Car> availableCars = carRepository.findAvailableCars(
                booking.getStartDate(),
                booking.getEndDate()
        );

        Set<Integer> availableCarIds = availableCars.stream()
                .map(Car::getId)
                .collect(Collectors.toSet());

        Set<Integer> bookingCarIds = booking.getCars().stream()
                .map(Car::getId)
                .collect(Collectors.toSet());

        System.out.println(availableCarIds);
        System.out.println(bookingCarIds);

        // Validate that each car in the booking is available
        boolean allCarsAvailable = availableCarIds.containsAll(bookingCarIds);

        if (!allCarsAvailable) {
            throw new RuntimeException("One or more cars are unavailable for the selected date range.");
        }
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Integer id) {
        bookingRepository.deleteById(id);
    }
}
