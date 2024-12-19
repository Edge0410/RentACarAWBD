package com.unibuc.rentacar.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String event;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // Constructors
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

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
