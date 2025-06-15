package com.unibuc.rentacar.controllers;

import com.unibuc.rentacar.entities.Manufacturer;
import com.unibuc.rentacar.services.ManufacturerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Vehicle Manufacturers Controller")
@RequestMapping("/api/manufacturers")
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    @Operation(summary = "Get all manufacturers", description = "Retrieve a list of all manufacturers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of manufacturers retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Manufacturer.class)))
    })
    @GetMapping
    public ResponseEntity<List<Manufacturer>> getAllManufacturers() {
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        return ResponseEntity.ok(manufacturers);
    }

    @Operation(summary = "Get manufacturer by ID", description = "Retrieve a specific manufacturer by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Manufacturer retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Manufacturer.class))),
            @ApiResponse(responseCode = "404", description = "Manufacturer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable Integer id) {
        Optional<Manufacturer> manufacturer = manufacturerService.getManufacturerById(id);
        return manufacturer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new manufacturer", description = "Add a new manufacturer to the system")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Manufacturer created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Manufacturer.class)))
    })
    @PostMapping
    public ResponseEntity<Manufacturer> createManufacturer(@Valid @RequestBody Manufacturer manufacturer) {
        Manufacturer savedManufacturer = manufacturerService.createManufacturer(manufacturer);
        return ResponseEntity.ok(savedManufacturer);
    }

    @Operation(summary = "Update a manufacturer", description = "Update an existing manufacturer details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Manufacturer updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Manufacturer.class))),
            @ApiResponse(responseCode = "404", description = "Manufacturer not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Manufacturer> updateManufacturer(@PathVariable Integer id, @Valid @RequestBody Manufacturer manufacturerDetails) {
        Manufacturer updatedManufacturer = manufacturerService.updateManufacturer(id, manufacturerDetails);
        return ResponseEntity.ok(updatedManufacturer);
    }

    @Operation(summary = "Delete a manufacturer", description = "Remove a manufacturer by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Manufacturer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Manufacturer not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Integer id) {
        manufacturerService.deleteManufacturer(id);
        return ResponseEntity.noContent().build();
    }
}
