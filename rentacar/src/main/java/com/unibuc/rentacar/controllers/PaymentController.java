package com.unibuc.rentacar.controllers;

import com.unibuc.rentacar.entities.Payment;
import com.unibuc.rentacar.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Payment Controller")
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "Get all payments", description = "Retrieve a list of all payments.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of payments retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class)))
    })
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @Operation(summary = "Get payment by ID", description = "Retrieve a specific payment by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class))),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Integer id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get payments by booking ID", description = "Retrieve all payments associated with a specific booking.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of payments retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<Payment>> getPaymentsByBookingId(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(paymentService.getPaymentsByBookingId(bookingId));
    }

    @Operation(summary = "Create a payment", description = "Add a new payment for a booking.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found or payment syntax is invalid.")
    })
    @PostMapping
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody Payment payment) {
        Payment createdPayment = paymentService.createPayment(payment);
        return ResponseEntity.ok(createdPayment);
    }

    @Operation(summary = "Update a payment", description = "Update the details of an existing payment.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class))),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Integer id, @Valid @RequestBody Payment payment) {
        Payment updatedPayment = paymentService.updatePayment(id, payment);
        return ResponseEntity.ok(updatedPayment);
    }

    @Operation(summary = "Delete a payment", description = "Remove a payment by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Payment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
