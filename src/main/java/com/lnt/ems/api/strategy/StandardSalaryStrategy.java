package com.lnt.ems.api.strategy;

import com.lnt.ems.api.model.SalaryData;
import com.lnt.ems.api.model.SalaryDetails;
import com.lnt.ems.api.repository.SalaryDataRepository;
import com.lnt.ems.api.repository.SalaryDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StandardSalaryStrategy implements SalaryCalculationStrategy {
    
    private final SalaryDetailsRepository salaryDetailsRepository;
    private final SalaryDataRepository salaryDataRepository;
    
    @Autowired
    public StandardSalaryStrategy(SalaryDetailsRepository salaryDetailsRepository,
                                 SalaryDataRepository salaryDataRepository) {
        this.salaryDetailsRepository = salaryDetailsRepository;
        this.salaryDataRepository = salaryDataRepository;
    }
    
    @Override
    public Float calculateSalary(Integer employeeId, Date date) {
        // Get salary details
        Integer basicSalary = salaryDetailsRepository.getBasicSalary(employeeId);
        Float otRate = salaryDetailsRepository.getOtRate(employeeId);
        SalaryData salaryData = salaryDataRepository.getSalaryData(employeeId, date);
        
        // Use default values if salaryData is null
        Float noPayDays = (salaryData != null) ? salaryData.getNoPayDays() : 0.0f;
        Integer attendanceBonus = (salaryData != null) ? salaryData.getAttendanceBonus() : 0;
        Float overTimeHours = (salaryData != null) ? salaryData.getOverTimeHours() : 0.0f;
        
        // Standard calculation formula
        Float totalSalary = (basicSalary - (basicSalary / 25 * noPayDays)) + 
                           attendanceBonus + (otRate * overTimeHours);
        
        return totalSalary;
    }
    
    @Override
    public String getStrategyName() {
        return "STANDARD";
    }
} 