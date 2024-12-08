package com.unibuc.rentacar.entities;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Manufacturer {
    private Integer id;
    @Size(max = 15, message = "Manufacturer name is too long")
    private String name;
    private String country;
}
