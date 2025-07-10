package com.lnt.ems.api.dto;

import java.util.Date;

public class SalaryResponseDTO {
    private Integer employeeId;
    private Date date;
    private Float amount;
    private String calculationStrategy;
    
    public SalaryResponseDTO() {}
    
    public SalaryResponseDTO(Integer employeeId, Date date, Float amount, String calculationStrategy) {
        this.employeeId = employeeId;
        this.date = date;
        this.amount = amount;
        this.calculationStrategy = calculationStrategy;
    }
    
    // Getters and Setters
    public Integer getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public Float getAmount() {
        return amount;
    }
    
    public void setAmount(Float amount) {
        this.amount = amount;
    }
    
    public String getCalculationStrategy() {
        return calculationStrategy;
    }
    
    public void setCalculationStrategy(String calculationStrategy) {
        this.calculationStrategy = calculationStrategy;
    }
} 