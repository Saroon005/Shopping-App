package com.group.shoppingapp.service;

import com.group.shoppingapp.dto.UserDTO;
import com.group.shoppingapp.entity.User;
import com.group.shoppingapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("john@example.com");
        testUser.setNumber(1234567890L);
        testUser.setRole("CUSTOMER");

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setNumber(1234567890L);
        userDTO.setRole("CUSTOMER");
    }

    @Test
    void testCreateUser_Success() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.createUser(userDTO);

        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void testGetAllUsers_Success() {
        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Jane");
        user2.setLastName("Smith");

        List<User> users = Arrays.asList(testUser, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetAllUsers_Empty() {
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        List<UserDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("john@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        UserDTO result = userService.getUserById(999L);

        assertNull(result);
        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    void testUpdateUser_Success() {
        UserDTO updateDTO = new UserDTO();
        updateDTO.setFirstName("Jane");
        updateDTO.setLastName("Smith");
        updateDTO.setEmail("jane@example.com");
        updateDTO.setNumber(9876543210L);
        updateDTO.setRole("ADMIN");

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.updateUser(1L, updateDTO);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        userService.updateUser(999L, userDTO);

        verify(userRepository, times(1)).findById(999L);
        verify(userRepository, never()).save(any(User.class));
    }

//    @Test
//    void testUpdateUser_NullDTO() {
//        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
//
//        userService.updateUser(1L, null);
//
//        verify(userRepository, times(1)).findById(1L);
//    }

//    @Test
//    void testDeleteUser_Success() {
//        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
//
//        userService.deleteUser(1L);
//
//        verify(userRepository, times(1)).deleteById(1L);
//    }

    @Test
    void testCreateUser_MultipleUsers() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        for (int i = 0; i < 5; i++) {
            userService.createUser(userDTO);
        }

        verify(userRepository, times(5)).save(any(User.class));
    }
}
