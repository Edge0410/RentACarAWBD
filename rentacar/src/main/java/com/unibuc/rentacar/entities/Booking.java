package com.unibuc.rentacar.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unibuc.rentacar.json.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "booking_cars",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private List<Car> cars;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Payment> payments;

    @Column(name = "start_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "End date must be today or in the future")
    private LocalDate endDate;

    @Column(nullable = false)
    private BookingStatus status;

    @Column(name = "total_price", nullable = false)
    @PositiveOrZero
    private Double totalPrice;

    @AssertTrue(message = "Start date must be before end date")
    public boolean isStartDateBeforeEndDate() {
        if (startDate == null || endDate == null) {
            return true; // Skip validation if dates are null
        }
        return startDate.isBefore(endDate);
    }

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = BookingStatus.PENDING;
        }
    }
}
