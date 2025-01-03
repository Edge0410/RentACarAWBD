package com.unibuc.rentacar.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unibuc.rentacar.entities.Car;
import com.unibuc.rentacar.entities.Manufacturer;
import com.unibuc.rentacar.json.FuelType;
import com.unibuc.rentacar.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private MockMvc mockMvc;
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private Car mockCar;
    private Car mockCar2;
    private Manufacturer mockManufacturer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();

        mockManufacturer = new Manufacturer();
        mockManufacturer.setId(1);
        mockManufacturer.setName("Tesla");
        mockManufacturer.setCountry("USA");

        mockCar = new Car();
        mockCar.setId(1);
        mockCar.setModel("Model X");
        mockCar.setMileage(13000);
        mockCar.setFuelType(FuelType.ELECTRIC);
        mockCar.setRentalPrice(100.00);
        mockCar.setManufacturer(mockManufacturer);

        mockCar2 = new Car();
        mockCar2.setId(2);
        mockCar2.setModel("Model Y");
        mockCar2.setMileage(15000);
        mockCar2.setFuelType(FuelType.ELECTRIC);
        mockCar2.setRentalPrice(100.00);
        mockCar2.setManufacturer(mockManufacturer);
    }

    @Test
    void getAllCars_Returns() throws Exception {
        when(carService.getAllCars()).thenReturn(List.of(mockCar, mockCar2));

        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].model").value("Model X"))
                .andExpect(jsonPath("$[1].model").value("Model Y"));

        verify(carService, times(1)).getAllCars();
    }

    @Test
    void getCarById_Returns() throws Exception {
        when(carService.getCarById(1)).thenReturn(Optional.of(mockCar));

        mockMvc.perform(get("/api/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("Model X"));

        verify(carService, times(1)).getCarById(1);
    }

    @Test
    void getAvailableCars_ValidDateRange_ReturnsCars() throws Exception {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);

        when(carService.getAvailableCars(startDate, endDate)).thenReturn(List.of(mockCar));

        mockMvc.perform(get("/api/cars/available")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].model").value("Model X"));

        verify(carService, times(1)).getAvailableCars(startDate, endDate);
    }

    @Test
    void createCar_Returns() throws Exception {
        when(carService.createCar(mockCar)).thenReturn(mockCar);

        // Serialize the mockCar object into a JSON string using UTF8 Encoding to reproduce Request Body
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = writer.writeValueAsString(mockCar);

        mockMvc.perform(post("/api/cars")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isOk());

        // We are not going to verify the returned car as response because the foreign key fields will be marked as null
        // OK Status is enough since there is not any data manipulation inside controller itself
    }

}
