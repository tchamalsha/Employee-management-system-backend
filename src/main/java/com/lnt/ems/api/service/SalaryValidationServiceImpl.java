package com.lnt.ems.api.service;

import com.lnt.ems.api.model.SalaryData;
import com.lnt.ems.api.model.SalaryDetails;
import com.lnt.ems.api.repository.EmployeeRepository;
import com.lnt.ems.api.service.interfaces.SalaryValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SalaryValidationServiceImpl implements SalaryValidationService {
    
    private final EmployeeRepository employeeRepository;
    
    @Autowired
    public SalaryValidationServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
    @Override
    public boolean validateSalaryData(SalaryData data) {
        if (data == null) {
            return false;
        }
        
        // Validate required fields
        if (data.getId() == null || data.getDate() == null) {
            return false;
        }
        
        // Validate numeric fields
        if (data.getNoPayDays() < 0 || data.getAttendanceBonus() < 0 || data.getOverTimeHours() < 0) {
            return false;
        }
        
        // Validate business rules
        if (data.getNoPayDays() > 31) { // Maximum days in a month
            return false;
        }
        
        if (data.getOverTimeHours() > 200) { // Maximum reasonable overtime hours
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean validateEmployeeExists(Integer employeeId) {
        if (employeeId == null) {
            return false;
        }
        return employeeRepository.findById(employeeId).isPresent();
    }
    
    @Override
    public boolean validateSalaryDetails(SalaryDetails details) {
        if (details == null) {
            return false;
        }
        
        // Validate required fields
        if (details.getId() == null) {
            return false;
        }
        
        // Validate numeric fields
        if (details.getBasicSalary() == null || details.getBasicSalary() <= 0) {
            return false;
        }
        
        if (details.getOtRate() == null || details.getOtRate() <= 0) {
            return false;
        }
        
        if (details.getSpecialAllowance() == null || details.getSpecialAllowance() < 0) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean validateDate(Date date) {
        if (date == null) {
            return false;
        }
        
        // Check if date is not in the future
        Date currentDate = new Date();
        return !date.after(currentDate);
    }
    
    @Override
    public boolean validateSalaryAmount(Float amount) {
        if (amount == null) {
            return false;
        }
        
        // Validate salary amount is positive and reasonable
        return amount > 0 && amount <= 1000000; // Maximum reasonable salary
    }
} 