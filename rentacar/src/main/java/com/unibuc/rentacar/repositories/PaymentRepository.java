package com.unibuc.rentacar.repositories;

import com.unibuc.rentacar.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByBookingId(Integer bookingId);
}
