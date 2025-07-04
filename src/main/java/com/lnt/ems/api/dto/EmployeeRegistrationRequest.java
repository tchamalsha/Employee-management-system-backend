package com.lnt.ems.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRegistrationRequest {

    // Employee details
    @NotNull(message = "Employee ID is required")
    private Integer id;
    
    @NotNull(message = "Employee name is required")
    private String name;
    
    @NotNull(message = "Employee email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotNull(message = "Employee password is required")
    private String password;
    
    @NotNull(message = "Employee position is required")
    private String position;
    
    // Salary details
    @NotNull(message = "Basic salary is required")
    @Positive(message = "Basic salary must be positive")
    private Double basicSalary;
    
    @NotNull(message = "OT rate is required")
    @Positive(message = "OT rate must be positive")
    private Double otRate;
    
    @NotNull(message = "Special allowance is required")
    @Positive(message = "Special allowance must be positive")
    private Integer specialAllowance;
    
    // Personal details (optional)
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
} 