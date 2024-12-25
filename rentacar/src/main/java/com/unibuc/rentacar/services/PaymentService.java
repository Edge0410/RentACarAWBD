package com.unibuc.rentacar.services;

import com.unibuc.rentacar.entities.Payment;
import com.unibuc.rentacar.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Integer id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> getPaymentsByBookingId(Integer bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Integer id, Payment paymentDetails) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setAmount(paymentDetails.getAmount());
        payment.setPaymentDate(paymentDetails.getPaymentDate());
        payment.setPaymentMethod(paymentDetails.getPaymentMethod());

        return paymentRepository.save(payment);
    }

    public void deletePayment(Integer id) {
        paymentRepository.deleteById(id);
    }
}
