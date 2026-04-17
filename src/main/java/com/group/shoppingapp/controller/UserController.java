
package com.group.shoppingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.group.shoppingapp.dto.UserDTO;
import com.group.shoppingapp.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController 
{

	@Autowired
	private UserService service;

	@PostMapping("/create")
	public String createUser(@RequestBody UserDTO dto) 
	{
		service.createUser(dto);
		return "User created successfully!";
	}


	@GetMapping("/get")
	public List<UserDTO>  getAllUsers()
	{
		return service.getAllUsers();
	}



	@GetMapping("/get/{id}")
	public UserDTO getUserById(@PathVariable Long id) 
	{
		return service.getUserById(id);
	}

	@PutMapping("/update/{id}")
	public String updateUser(@PathVariable Long id, @RequestBody UserDTO dto)
	{
		service.updateUser(id, dto);
		return "User updated successfully";
	}

	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable Long id) 
	{
		service.deleteUser(id);
		return "User deleted successfully!";
	}
}