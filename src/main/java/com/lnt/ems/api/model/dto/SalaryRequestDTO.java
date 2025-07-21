package com.lnt.ems.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for salary request operations (e.g., creating or updating salary records).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryRequestDTO {
    private Integer employeeId;
    private String date; // Format: YYYY-MM
    private Float salaryAmount;
} 