package com.lnt.ems.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDetailsDTO {
    private Integer id;
    private Long telephone;
    private String address;
    private Integer postalCode;
} 