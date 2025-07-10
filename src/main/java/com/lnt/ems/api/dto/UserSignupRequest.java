package com.lnt.ems.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupRequest {
    
    @NotNull(message = "User ID is required")
    private Integer id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    @NotBlank(message = "Position is required")
    private String position;
    
    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(ADMIN|EMPLOYEE)$", message = "Role must be either ADMIN or EMPLOYEE")
    private String role;
    
    // Optional field for employee registration (admin who created this employee)
    private Integer adminId;
} 