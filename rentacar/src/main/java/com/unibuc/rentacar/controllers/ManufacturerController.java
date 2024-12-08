package com.unibuc.rentacar.controllers;

import com.unibuc.rentacar.entities.Manufacturer;
import com.unibuc.rentacar.services.ManufacturerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerController {
    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerService.getAllManufacturers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable Integer id) {
        return ResponseEntity.ok(manufacturerService.getManufacturerById(id));
    }

    @PostMapping
    public ResponseEntity<String> createManufacturer(@Valid @RequestBody Manufacturer manufacturer) {
        manufacturerService.createManufacturer(manufacturer);
        return ResponseEntity.ok("Manufacturer created successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateManufacturer(@PathVariable Integer id, @Valid @RequestBody Manufacturer manufacturer) {
        manufacturerService.updateManufacturer(id, manufacturer);
        return ResponseEntity.ok("Manufacturer updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Integer id) {
        manufacturerService.deleteManufacturer(id);
        return ResponseEntity.noContent().build();
    }
}