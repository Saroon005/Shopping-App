package com.group.shoppingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.group.shoppingapp.dto.UserDTO;
import com.group.shoppingapp.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController 
{

    @Autowired
    private UserService service;

 
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO dto) 
    {
        service.createUser(dto);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }


    @GetMapping("/get")
    public ResponseEntity<List<UserDTO>> getAllUsers() 
    {
        List<UserDTO> users = service.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    
    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) 
    {
        UserDTO user = service.getUserById(id);

        if (user == null) 
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO dto) 
    {
        service.updateUser(id, dto);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) 
    {
        service.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}