package com.group.shoppingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.group.shoppingapp.entity.User;
import com.group.shoppingapp.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController 
{

    @Autowired
    private UserRepository userRepository;

    
    
    @PostMapping
    public User createUser(@RequestBody User user) 
    {
        return userRepository.save(user);
    }

   
    
}