package com.lnt.ems.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String position;
    private Integer adminId;
} 