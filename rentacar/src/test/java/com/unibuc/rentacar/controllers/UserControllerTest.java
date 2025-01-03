package com.unibuc.rentacar.controllers;

import com.unibuc.rentacar.entities.User;
import com.unibuc.rentacar.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private User mockUser, mockUser2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // Mocking a sample user in order to make sure he is returned by mocked repository methods

        mockUser = new User();
        mockUser.setId(1);
        mockUser.setName("Test User 1");
        mockUser.setEmail("test@gmail.com");
        mockUser.setPhone("0723456789");

        mockUser2 = new User();
        mockUser2.setId(2);
        mockUser2.setName("Test User 2");
        mockUser2.setEmail("test@yahoo.com");
        mockUser2.setPhone("0723456788");
    }

    @Test
    void getAllUsers_Returns() throws Exception {

        when(userService.getAllUsers()).thenReturn(List.of(mockUser, mockUser2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test User 1"))
                .andExpect(jsonPath("$[1].name").value("Test User 2"));

        // We mock a list of 2 users as the return object of the getAllUsers method, and we check for discrepancies

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserById_Returns() throws Exception {
        Integer userId = 1;
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User 1"));

        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void getUserById_NotFound() throws Exception {
        Integer userId = 99;
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isNotFound());

        // Mock optional empty from getUserById

        verify(userService, times(1)).getUserById(userId);
    }
}
