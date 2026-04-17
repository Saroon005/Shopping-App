package com.group.shoppingapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.group.shoppingapp.dto.UserDTO;

@Service
public class UserService 
{
    public void createUser(UserDTO dto) {};

    public List<UserDTO> getAllUsers(){};

    public UserDTO getUserById(Long id) {};

    public void updateUser(Long id, UserDTO dto) {};

    public void deleteUser(Long id) {};
}