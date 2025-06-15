package com.unibuc.rentacar.controllers;

import com.unibuc.rentacar.entities.Booking;
import com.unibuc.rentacar.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Booking Management Controller")
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @Operation(summary = "Get all bookings", description = "Retrieve a list of all bookings")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of bookings retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)))
    })
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @Operation(summary = "Get booking by ID", description = "Retrieve a specific booking by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @Operation(summary = "Get bookings by user ID", description = "Retrieve all bookings associated with a specific user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "404", description = "No bookings found for this user")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable Integer userId) {
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }

    @Operation(summary = "Create a booking", description = "Create a new booking with the provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Booking created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "404", description = "Cars are already booked or invalid booking details syntax"),
    })
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @Operation(summary = "Update a booking", description = "Update an existing booking details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "404", description = "Cars are already booked or invalid booking details syntax"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Integer id, @RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.updateBooking(id, booking));
    }

    @Operation(summary = "Delete a booking", description = "Delete a booking by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Booking deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
