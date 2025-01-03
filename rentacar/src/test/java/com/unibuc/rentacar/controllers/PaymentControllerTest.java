package com.unibuc.rentacar.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unibuc.rentacar.entities.Booking;
import com.unibuc.rentacar.entities.Payment;
import com.unibuc.rentacar.json.BookingStatus;
import com.unibuc.rentacar.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private MockMvc mockMvc;
    private Payment mockPayment;
    private Payment mockPayment2;
    private Booking mockBooking;
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

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
    void getAllPayments_Returns() throws Exception {
        List<Payment> payments = Arrays.asList(mockPayment, mockPayment2);
        when(paymentService.getAllPayments()).thenReturn(payments);

        mockMvc.perform(get("/api/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(paymentService, times(1)).getAllPayments();
    }

    @Test
    void getPaymentById_Returns() throws Exception {
        when(paymentService.getPaymentById(1)).thenReturn(Optional.of(mockPayment));

        mockMvc.perform(get("/api/payments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(250.0));

        verify(paymentService, times(1)).getPaymentById(1);
    }

    @Test
    void getPaymentsByBookingId_Returns() throws Exception {
        List<Payment> payments = Arrays.asList(mockPayment, mockPayment2);
        when(paymentService.getPaymentsByBookingId(1)).thenReturn(payments);

        mockMvc.perform(get("/api/payments/booking/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(paymentService, times(1)).getPaymentsByBookingId(1);
    }

    @Test
    void createPayment_Returns() throws Exception {
        when(paymentService.createPayment(any(Payment.class))).thenReturn(mockPayment);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = writer.writeValueAsString(mockPayment);

        mockMvc.perform(post("/api/payments")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isOk());

        verify(paymentService, times(1)).createPayment(any(Payment.class));
    }

    @Test
    void deletePayment_Returns() throws Exception {
        doNothing().when(paymentService).deletePayment(1);

        mockMvc.perform(delete("/api/payments/1"))
                .andExpect(status().isNoContent());

        verify(paymentService, times(1)).deletePayment(1);
    }

}
