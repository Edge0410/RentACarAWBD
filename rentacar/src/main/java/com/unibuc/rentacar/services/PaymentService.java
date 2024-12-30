package com.unibuc.rentacar.services;

import com.unibuc.rentacar.entities.Booking;
import com.unibuc.rentacar.entities.Payment;
import com.unibuc.rentacar.json.BookingStatus;
import com.unibuc.rentacar.repositories.BookingRepository;
import com.unibuc.rentacar.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Integer id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        return Optional.ofNullable(payment);
    }

    public List<Payment> getPaymentsByBookingId(Integer bookingId) {
        List<Payment> payments = paymentRepository.findByBookingId(bookingId);
        if(payments.isEmpty()){
            throw new RuntimeException("Booking not found with id: " + bookingId);
        }

        return payments;
    }

    public Payment createPayment(Payment payment) {
        Integer bookingId = payment.getBooking().getId();
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
        Payment completedPayment = paymentRepository.save(payment);

        // logic to mark the booking as confirmed

        // fetch total price of booking

        double totalPrice = booking.getTotalPrice();

        List<Payment> payments = paymentRepository.findByBookingId(bookingId);
        double totalPaid = payments.stream()
                .mapToDouble(Payment::getAmount)
                .sum();

        if (totalPaid >= totalPrice) {
            booking.setStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);
        }

        return completedPayment;
    }

    public Payment updatePayment(Integer id, Payment paymentDetails) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Integer bookingId = payment.getBooking().getId();
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        payment.setAmount(paymentDetails.getAmount());
        payment.setPaymentMethod(paymentDetails.getPaymentMethod());
        Payment completedPayment = paymentRepository.save(payment);

        double totalPrice = booking.getTotalPrice();

        List<Payment> payments = paymentRepository.findByBookingId(bookingId);
        double totalPaid = payments.stream()
                .mapToDouble(Payment::getAmount)
                .sum();

        if (totalPaid >= totalPrice) {
            booking.setStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);
        }
        else{
            booking.setStatus(BookingStatus.PENDING);
            bookingRepository.save(booking);
        }

        return completedPayment;
    }

    public void deletePayment(Integer id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Integer bookingId = payment.getBooking().getId();
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        paymentRepository.deleteById(id);

        double totalPrice = booking.getTotalPrice();

        List<Payment> payments = paymentRepository.findByBookingId(bookingId);
        double totalPaid = payments.stream()
                .mapToDouble(Payment::getAmount)
                .sum();

        if (totalPaid >= totalPrice) {
            booking.setStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);
        }
        else{
            booking.setStatus(BookingStatus.PENDING);
            bookingRepository.save(booking);
        }
    }
}
