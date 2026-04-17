

package com.group.shoppingapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDTO {


	
    private Long id;
    
    @NotBlank(message = "First name cannot be empty")  
    private String firstName;
    
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;
    
    @NotBlank(message = "E- mail cannot be empty")
    private String email;
    
    @NotNull(message = "Number cannot be null")
    private Long number;
    
    @NotNull(message = "Role cannot be null")
    private String role;

    public UserDTO() {}

    public UserDTO(Long id, String firstName, String lastName, String email, Long number, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}