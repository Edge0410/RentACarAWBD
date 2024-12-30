package com.unibuc.rentacar.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "audit")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank
    private String event;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Audit() {
    }

    public Audit(String event, LocalDateTime timestamp) {
        this.event = event;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
