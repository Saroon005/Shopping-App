package com.group.shoppingapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.shoppingapp.dto.UserDTO;
import com.group.shoppingapp.entity.User;
import com.group.shoppingapp.repository.UserRepository;

@Service
public class UserService 
{
	@Autowired
	private UserRepository repository;


	public void createUser(UserDTO dto) 
	{
		User user = new User();
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setNumber(dto.getNumber());
		user.setRole(dto.getRole());

		repository.save(user);
	}


	public List<UserDTO> getAllUsers() 
	{
		return repository.findAll().stream().map(user -> 
		{
			UserDTO dto = new UserDTO();
			dto.setId(user.getId());
			dto.setFirstName(user.getFirstName());
			dto.setLastName(user.getLastName());
			dto.setEmail(user.getEmail());
			dto.setNumber(user.getNumber());
			dto.setRole(user.getRole());
			return dto;
		}).collect(Collectors.toList());
	}


	public UserDTO getUserById(Long id) 
	{
		User user = repository.findById(id).orElse(null);

		if (user == null) return null;

		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getEmail());
		dto.setNumber(user.getNumber());
		dto.setRole(user.getRole());

		return dto;
	}


	public void updateUser(Long id, UserDTO dto) 
	{
		repository.findById(id).ifPresent(user -> {
			user.setFirstName(dto.getFirstName());
			user.setLastName(dto.getLastName());
			user.setEmail(dto.getEmail());
			user.setNumber(dto.getNumber());
			user.setRole(dto.getRole());

			repository.save(user);
		});
	}


	public void deleteUser(Long id)
	{
		repository.deleteById(id);
	}
}