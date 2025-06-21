package com.unibuc.rentacar.controllers;

import com.unibuc.rentacar.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingViewController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/bookings/view")
    public String viewBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "bookings"; // looks for bookings.html in templates/
    }

    @GetMapping("/bookings/new")
    public String showBookingForm() {
        return "create-booking"; // This matches create-booking.html in templates
    }
}
