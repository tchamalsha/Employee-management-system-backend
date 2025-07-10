package com.lnt.ems.api.strategy;

import com.lnt.ems.api.model.SalaryData;
import com.lnt.ems.api.model.SalaryDetails;
import com.lnt.ems.api.repository.SalaryDataRepository;
import com.lnt.ems.api.repository.SalaryDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExecutiveSalaryStrategy implements SalaryCalculationStrategy {
    
    private final SalaryDetailsRepository salaryDetailsRepository;
    private final SalaryDataRepository salaryDataRepository;
    
    @Autowired
    public ExecutiveSalaryStrategy(SalaryDetailsRepository salaryDetailsRepository,
                                  SalaryDataRepository salaryDataRepository) {
        this.salaryDetailsRepository = salaryDetailsRepository;
        this.salaryDataRepository = salaryDataRepository;
    }
    
    @Override
    public Float calculateSalary(Integer employeeId, Date date) {
        // Get salary details
        Integer basicSalary = salaryDetailsRepository.getBasicSalary(employeeId);
        Float otRate = salaryDetailsRepository.getOtRate(employeeId);
        Integer specialAllowance = salaryDetailsRepository.getSpecialAllowance(employeeId);
        SalaryData salaryData = salaryDataRepository.getSalaryData(employeeId, date);
        
        // Use default values if salaryData is null
        Float noPayDays = (salaryData != null) ? salaryData.getNoPayDays() : 0.0f;
        Integer attendanceBonus = (salaryData != null) ? salaryData.getAttendanceBonus() : 0;
        Float overTimeHours = (salaryData != null) ? salaryData.getOverTimeHours() : 0.0f;
        
        // Executive calculation formula with higher bonuses and allowances
        Float baseSalary = basicSalary - (basicSalary / 25 * noPayDays);
        Float overtimePay = otRate * overTimeHours * 1.5f; // 50% higher OT rate for executives
        Float performanceBonus = (float) (attendanceBonus * 2); // Double attendance bonus
        Float executiveAllowance = specialAllowance * 1.2f; // 20% higher special allowance
        
        Float totalSalary = baseSalary + overtimePay + performanceBonus + executiveAllowance;
        
        return totalSalary;
    }
    
    @Override
    public String getStrategyName() {
        return "EXECUTIVE";
    }
} 