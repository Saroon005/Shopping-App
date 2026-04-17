package com.group.shoppingapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.group.shoppingapp.dto.UserDTO;

@Service
public interface UserService 
{
    void createUser(UserDTO dto);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    void updateUser(Long id, UserDTO dto);

    void deleteUser(Long id);
}