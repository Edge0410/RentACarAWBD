package com.unibuc.rentacar.controllers;

import com.unibuc.rentacar.entities.Manufacturer;
import com.unibuc.rentacar.services.ManufacturerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    // ✅ Retrieve all manufacturers
    @GetMapping
    public ResponseEntity<List<Manufacturer>> getAllManufacturers() {
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        return ResponseEntity.ok(manufacturers);
    }

    // ✅ Retrieve a manufacturer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable Integer id) {
        Optional<Manufacturer> manufacturer = manufacturerService.getManufacturerById(id);
        return manufacturer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Create a new manufacturer
    @PostMapping
    public ResponseEntity<Manufacturer> createManufacturer(@Valid @RequestBody Manufacturer manufacturer) {
        Manufacturer savedManufacturer = manufacturerService.saveManufacturer(manufacturer);
        return ResponseEntity.ok(savedManufacturer);
    }

    // ✅ Update an existing manufacturer
    @PutMapping("/{id}")
    public ResponseEntity<Manufacturer> updateManufacturer(@PathVariable Integer id, @Valid @RequestBody Manufacturer manufacturerDetails) {
        Manufacturer updatedManufacturer = manufacturerService.updateManufacturer(id, manufacturerDetails);
        return ResponseEntity.ok(updatedManufacturer);
    }

    // ✅ Delete a manufacturer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Integer id) {
        manufacturerService.deleteManufacturer(id);
        return ResponseEntity.noContent().build();
    }
}
