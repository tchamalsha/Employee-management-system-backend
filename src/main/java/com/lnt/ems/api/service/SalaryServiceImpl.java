package com.lnt.ems.api.service;

import com.lnt.ems.api.model.SalaryDetails;
import com.lnt.ems.api.model.SalaryData;
import com.lnt.ems.api.model.Salary;
import com.lnt.ems.api.repository.SalaryDetailsRepository;
import com.lnt.ems.api.repository.SalaryDataRepository;
import com.lnt.ems.api.repository.SalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SalaryServiceImpl  {

    private final SalaryRepository salaryRepository;
    private final SalaryDetailsRepository salaryDetailsRepository;
    private final SalaryDataRepository salaryDataRepository;

    //add salary
    public void setSalaryData(SalaryDetails salaryDetails){
        salaryDetailsRepository.save(salaryDetails);
    }
    //get salary of an employee
    public Float getSalary(Integer id, Date date){
        return salaryRepository.getEmployeeSalary(date,id);
    }

    //get all salaries
    public java.util.List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }

    //get all salaries for a specific employee
    public java.util.List<Salary> getSalariesByEmployeeId(Integer employeeId) {
        return salaryRepository.findAllById(employeeId);
    }

    //get employee salary data
    public SalaryData getSalaryData(Integer id,Date date){
        return salaryDataRepository.getSalaryData(id,date);
    }

    //get basic salary
    public Integer getBasicSalary(Integer id){
        return  salaryDetailsRepository.getBasicSalary(id);
    }

    //get OT rate
    public Float getOtRate(Integer id){
        return salaryDetailsRepository.getOtRate(id);
    }

   

    //calculate salary
    public Float calculateSalary(Integer id,Date date){
        Integer basicSalary = getBasicSalary(id);
        Float otRate = getOtRate(id);
        SalaryData salaryData = getSalaryData(id,date);

        // Use default values if salaryData is null
        Float noPayDays = (salaryData != null) ? salaryData.getNoPayDays() : 0.0f;
        Integer attendanceBonus = (salaryData != null) ? salaryData.getAttendanceBonus() : 0;
        Float overTimeHours = (salaryData != null) ? salaryData.getOverTimeHours() : 0.0f;

        Float totalSalary = (basicSalary-(basicSalary/25 * noPayDays)) + attendanceBonus +
                (otRate*overTimeHours);

        return totalSalary;
    }

    //calculate and save salary
    public Salary calculateAndSaveSalary(Integer id, Date date){
        Float calculatedSalary = calculateSalary(id, date);
        Salary salary = new Salary();
        salary.setId(id);
        salary.setDate(date);
        salary.setSalaryAmount(calculatedSalary);
        return salaryRepository.save(salary);
    }

    //add salary data
    public SalaryData addSalaryData(SalaryData salaryData){
        // Save the salary data first
        SalaryData savedSalaryData = salaryDataRepository.save(salaryData);
        if(salaryDataExists(salaryData.getId(), salaryData.getDate())){
            log.info("Salary data found for employee {} on date {}", 
                    salaryData.getId(), salaryData.getDate());
        }
        
        // Automatically calculate and save the salary using the date from salary data
        try {
            calculateAndSaveSalary(salaryData.getId(), salaryData.getDate());
            log.info("Salary calculation completed for employee {} on date {}", 
                    salaryData.getId(), salaryData.getDate());
        } catch (Exception e) {
            // Log the error but don't fail the salary data save operation
            log.error("Error calculating salary for employee {} on date {}: {}", 
                     salaryData.getId(), salaryData.getDate(), e.getMessage());
        }
        
        return savedSalaryData;
    }

    // Check if salary data exists for given employee and date
    public boolean salaryDataExists(Integer employeeId, Date date) {
        try {
            SalaryData data = salaryDataRepository.getSalaryData(employeeId, date);
            return data != null;
        } catch (Exception e) {
            log.error("Error checking salary data existence for employee {} on date {}: {}", 
                     employeeId, date, e.getMessage());
            return false;
        }
    }

    // Wait for salary data to be available
    public boolean waitForSalaryData(Integer employeeId, Date date, int maxWaitSeconds) {
        int waitCount = 0;
        while (waitCount < maxWaitSeconds) {
            if (salaryDataExists(employeeId, date)) {
                log.info("Salary data found for employee {} on date {} after {} seconds", 
                        employeeId, date, waitCount);
                return true;
            }
            try {
                Thread.sleep(1000); // Wait 1 second
                waitCount++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Thread interrupted while waiting for salary data");
                break;
            }
        }
        log.warn("Salary data not found for employee {} on date {} after {} seconds", 
                employeeId, date, maxWaitSeconds);
        return false;
    }
}
