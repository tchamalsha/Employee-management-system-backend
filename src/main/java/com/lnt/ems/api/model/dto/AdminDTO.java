package com.lnt.ems.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String position;
} 