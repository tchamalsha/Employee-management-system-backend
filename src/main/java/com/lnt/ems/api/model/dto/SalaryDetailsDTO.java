package com.lnt.ems.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDetailsDTO {
    private Integer id;
    private Integer basicSalary;
    private Float otRate;
    private Integer specialAllowance;
} 