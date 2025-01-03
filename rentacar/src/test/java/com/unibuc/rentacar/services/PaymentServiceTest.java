package com.unibuc.rentacar.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibuc.rentacar.entities.Booking;
import com.unibuc.rentacar.entities.Payment;
import com.unibuc.rentacar.json.BookingStatus;
import com.unibuc.rentacar.repositories.BookingRepository;
import com.unibuc.rentacar.repositories.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Payment mockPayment;
    private Payment mockPayment2;
    private Booking mockBooking;

    public PaymentServiceTest() {
        MockitoAnnotations.openMocks(this);

        mockBooking = new Booking();
        mockBooking.setId(1);
        mockBooking.setStatus(BookingStatus.PENDING);
        mockBooking.setTotalPrice(500.0);

        mockPayment = new Payment();
        mockPayment.setId(1);
        mockPayment.setAmount(250.0);
        mockPayment.setPaymentMethod("Credit Card");
        mockPayment.setBooking(mockBooking);

        mockPayment2 = new Payment();
        mockPayment2.setId(2);
        mockPayment2.setAmount(250.0);
        mockPayment2.setPaymentMethod("Debit Card");
        mockPayment2.setBooking(mockBooking);
    }

    @Test
    void getAllPayments_Returns() {
        when(paymentRepository.findAll()).thenReturn(Arrays.asList(mockPayment, mockPayment2));

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void getPaymentById_InvalidPayment_RuntimeException() {
        when(paymentRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            paymentService.getPaymentById(99);
        });

        assertEquals("Payment not found with id: 99", exception.getMessage());
        verify(paymentRepository, times(1)).findById(99);
    }

    @Test
    void getPaymentsByBookingId_Returns() {
        when(paymentRepository.findByBookingId(1)).thenReturn(Arrays.asList(mockPayment, mockPayment2));

        List<Payment> result = paymentService.getPaymentsByBookingId(1);

        assertEquals(2, result.size());
        verify(paymentRepository, times(1)).findByBookingId(1);
    }

    @Test
    void createPayment_Returns() {
        when(bookingRepository.findById(1)).thenReturn(Optional.of(mockBooking));
        when(paymentRepository.findByBookingId(1)).thenReturn(List.of(mockPayment));
        when(paymentRepository.save(mockPayment2)).thenReturn(mockPayment2);

        Payment result = paymentService.createPayment(mockPayment2);

        assertEquals(250.0, result.getAmount());
        verify(paymentRepository, times(1)).save(mockPayment2);
        verify(paymentRepository, times(1)).findByBookingId(1);
        verify(bookingRepository, times(1)).findById(1);
    }

}
