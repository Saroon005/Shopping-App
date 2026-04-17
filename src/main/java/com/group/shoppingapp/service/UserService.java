package com.group.shoppingapp.service;

import java.util.List;
import com.group.shoppingapp.dto.UserDTO;

public interface UserService 
{
    void createUser(UserDTO dto);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    void updateUser(Long id, UserDTO dto);

    void deleteUser(Long id);
}