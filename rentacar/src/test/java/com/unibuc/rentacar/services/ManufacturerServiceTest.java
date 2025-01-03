package com.unibuc.rentacar.services;

import com.unibuc.rentacar.entities.Manufacturer;
import com.unibuc.rentacar.repositories.ManufacturerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ManufacturerServiceTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private ManufacturerService manufacturerService;
    private Manufacturer mockManufacturer;
    private Manufacturer mockManufacturer2;

    public ManufacturerServiceTest() {
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
        when(manufacturerRepository.findAll()).thenReturn(manufacturers);

        List<Manufacturer> result = manufacturerService.getAllManufacturers();

        assertEquals(2, result.size());
        verify(manufacturerRepository, times(1)).findAll();
    }

    @Test
    void getManufacturerById_Returns() {
        when(manufacturerRepository.findById(1)).thenReturn(Optional.of(mockManufacturer));

        Optional<Manufacturer> result = manufacturerService.getManufacturerById(1);

        assertTrue(result.isPresent());
        assertEquals("Toyota", result.get().getName());
        verify(manufacturerRepository, times(1)).findById(1);
    }

    @Test
    void getManufacturerById_InvalidId_RuntimeException() {
        when(manufacturerRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            manufacturerService.getManufacturerById(99);
        });

        assertEquals("Manufacturer not found with id: 99", exception.getMessage());
        verify(manufacturerRepository, times(1)).findById(99);
    }

    @Test
    void createManufacturer_Returns() {
        when(manufacturerRepository.save(any(Manufacturer.class))).thenReturn(mockManufacturer);

        Manufacturer result = manufacturerService.createManufacturer(mockManufacturer);

        assertEquals("Toyota", result.getName());
        verify(manufacturerRepository, times(1)).save(any(Manufacturer.class));
    }

    @Test
    void updateManufacturer_Returns() {
        Manufacturer updatedManufacturer = new Manufacturer();
        updatedManufacturer.setName("Toyota Updated");
        updatedManufacturer.setCountry("Japan");

        when(manufacturerRepository.findById(1)).thenReturn(Optional.of(mockManufacturer));
        when(manufacturerRepository.save(any(Manufacturer.class))).thenReturn(updatedManufacturer);

        Manufacturer result = manufacturerService.updateManufacturer(1, updatedManufacturer);

        assertEquals("Toyota Updated", result.getName());
        verify(manufacturerRepository, times(1)).findById(1);
        verify(manufacturerRepository, times(1)).save(any(Manufacturer.class));
    }

    @Test
    void deleteManufacturer_NoContext() {
        doNothing().when(manufacturerRepository).deleteById(1);

        manufacturerService.deleteManufacturer(1);

        verify(manufacturerRepository, times(1)).deleteById(1);
    }

}
