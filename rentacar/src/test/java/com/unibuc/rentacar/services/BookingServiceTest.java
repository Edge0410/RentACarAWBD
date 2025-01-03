package com.unibuc.rentacar.services;

import com.unibuc.rentacar.entities.Booking;
import com.unibuc.rentacar.entities.Car;
import com.unibuc.rentacar.json.FuelType;
import com.unibuc.rentacar.repositories.BookingRepository;
import com.unibuc.rentacar.repositories.CarRepository;
import com.unibuc.rentacar.repositories.UserRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private BookingService bookingService;

    private Booking booking;
    private Car car1, car2;

    public BookingServiceTest() {
        MockitoAnnotations.openMocks(this);

        booking = new Booking();
        booking.setStartDate(LocalDate.of(2025, 8, 10));
        booking.setEndDate(LocalDate.of(2025, 8, 15));

        car1 = new Car();
        car1.setId(1);
        car1.setModel("Toyota Corolla");
        car1.setMileage(15000);
        car1.setFuelType(FuelType.PETROL);
        car1.setRentalPrice(20.0);

        car2 = new Car();
        car2.setId(2);
        car2.setModel("Honda Civic");
        car2.setMileage(12000);
        car2.setFuelType(FuelType.PETROL);
        car2.setRentalPrice(25.0);

        booking.setCars(List.of(car1, car2));
    }

    @Test
    void getBookingsByUserId_UserExists_Returns() {
        Integer userId = 2;

        when(userRepository.existsById(userId)).thenReturn(true);
        when(bookingRepository.findByUserId(userId)).thenReturn(List.of(new Booking()));

        List<Booking> result = bookingService.getBookingsByUserId(userId);

        // We check for the booking service to return the mocked booking, so the result should have exactly one booking

        assertThat(result).isNotEmpty().hasSize(1);
        verify(userRepository, times(1)).existsById(userId);
        verify(bookingRepository, times(1)).findByUserId(userId);
    }

    @Test
    void getBookingsByUserId_UserDoesNotExist_Exception() {
        Integer userId = 2;

        when(userRepository.existsById(userId)).thenReturn(false);

        // We mocked userId = 2 as non-existing, so whenever we get bookings by user id, error is thrown

        assertThatThrownBy(() -> bookingService.getBookingsByUserId(userId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No user found with id: " + userId);
    }

    @Test
    void createBooking_AvailableCars_Returns() {

        when(carRepository.findAvailableCars(booking.getStartDate(), booking.getEndDate()))
                .thenReturn(List.of(car1, car2));
        when(carRepository.findAllById(Set.of(1, 2)))
                .thenReturn(List.of(car1, car2));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking result = bookingService.createBooking(booking);

        // We mocked 2 cars and a booking object - the cars have all their invoked methods mocked so that they
        // appear to be available for booking, then the booking total price is calculated based on their individual
        // prices and a booking is returned containing those cars

        assertThat(result).isNotNull();
        assertThat(result.getTotalPrice()).isEqualTo(225.0); // Total price = (20 + 25) * 5 days
        verify(carRepository, times(1)).findAvailableCars(booking.getStartDate(), booking.getEndDate());
        verify(bookingRepository, times(1)).save(booking);
    }
}
