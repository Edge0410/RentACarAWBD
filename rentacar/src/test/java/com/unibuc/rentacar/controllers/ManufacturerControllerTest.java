package com.unibuc.rentacar.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibuc.rentacar.entities.Manufacturer;
import com.unibuc.rentacar.services.ManufacturerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

public class ManufacturerControllerTest {

    @Mock
    private ManufacturerService manufacturerService;

    @InjectMocks
    private ManufacturerController manufacturerController;

    private Manufacturer mockManufacturer;
    private Manufacturer mockManufacturer2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockManufacturer = new Manufacturer();
        mockManufacturer.setId(1);
        mockManufacturer.setName("Toyota");
        mockManufacturer.setCountry("Japan");

        mockManufacturer2 = new Manufacturer();
        mockManufacturer2.setId(2);
        mockManufacturer2.setName("Ford");
        mockManufacturer2.setCountry("USA");
    }

    @Test
    void getAllManufacturers_Returns() {
        List<Manufacturer> manufacturers = Arrays.asList(mockManufacturer, mockManufacturer2);
        when(manufacturerService.getAllManufacturers()).thenReturn(manufacturers);

        ResponseEntity<List<Manufacturer>> response = manufacturerController.getAllManufacturers();

        assertEquals(OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(manufacturerService, times(1)).getAllManufacturers();
    }

    @Test
    void getManufacturerById_Returns() {
        when(manufacturerService.getManufacturerById(1)).thenReturn(Optional.of(mockManufacturer));

        ResponseEntity<Manufacturer> response = manufacturerController.getManufacturerById(1);

        assertEquals(OK, response.getStatusCode());
        assertEquals("Toyota", response.getBody().getName());
        verify(manufacturerService, times(1)).getManufacturerById(1);
    }

    @Test
    void getManufacturerById_InvalidId_NotFound() {
        when(manufacturerService.getManufacturerById(99)).thenReturn(Optional.empty());

        ResponseEntity<Manufacturer> response = manufacturerController.getManufacturerById(99);

        assertEquals(NOT_FOUND, response.getStatusCode());
        verify(manufacturerService, times(1)).getManufacturerById(99);
    }

    @Test
    void deleteManufacturer_InvalidId_RuntimeException() {
        doThrow(new RuntimeException("Manufacturer not found with id: 99")).when(manufacturerService).deleteManufacturer(99);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            manufacturerController.deleteManufacturer(99);
        });

        assertEquals("Manufacturer not found with id: 99", exception.getMessage());
        verify(manufacturerService, times(1)).deleteManufacturer(99);
    }

}
