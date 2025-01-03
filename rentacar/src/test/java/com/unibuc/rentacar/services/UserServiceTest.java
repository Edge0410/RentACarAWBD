package com.unibuc.rentacar.services;

import com.unibuc.rentacar.entities.User;
import com.unibuc.rentacar.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final User mockUser, mockUser2;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);

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
    void getAllUsers_Returns() {

        when(userRepository.findAll()).thenReturn(List.of(mockUser, mockUser2));

        List<User> result = userService.getAllUsers();

        // Mocking a list of 2 sample users to make sure they are retrieved from the service

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Test User 1");
        assertThat(result.get(1).getName()).isEqualTo("Test User 2");
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_Returns() {
        Integer userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        Optional<User> result = userService.getUserById(userId);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Test User 1");
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void createUser_Returns() {
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        User result = userService.createUser(mockUser);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test User 1");
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    void deleteUser_NoContext() {
        Integer userId = 1;

        doNothing().when(userRepository).deleteById(userId);

        // Just verify that the delete method is called once

        userService.deleteUser(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }
}
