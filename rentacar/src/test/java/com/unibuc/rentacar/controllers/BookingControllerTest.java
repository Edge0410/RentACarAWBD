package com.unibuc.rentacar.controllers;

import com.unibuc.rentacar.entities.Booking;
import com.unibuc.rentacar.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.List;


public class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    void getAllBookings_Returns() throws Exception {
        when(bookingService.getAllBookings()).thenReturn(List.of(new Booking()));

        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // We mock the fact that the service returns exactly one booking, so this is what we expect from fetching

        verify(bookingService, times(1)).getAllBookings();
    }

    @Test
    void getBookingById_Returns() throws Exception {
        Integer bookingId = 1;
        Booking booking = new Booking();
        when(bookingService.getBookingById(bookingId)).thenReturn(booking);

        mockMvc.perform(get("/api/bookings/{id}", bookingId))
                .andExpect(status().isOk());

        verify(bookingService, times(1)).getBookingById(bookingId);
    }

}
