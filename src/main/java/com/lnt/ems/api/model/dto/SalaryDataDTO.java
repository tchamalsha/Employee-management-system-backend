package com.lnt.ems.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDataDTO {
    private Integer id;
    private String date;
    private Float noPayDays;
    private Float overTimeHours;
    private Integer attendanceBonus;
} 