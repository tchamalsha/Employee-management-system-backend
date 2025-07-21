package com.lnt.ems.api.service;

import com.lnt.ems.api.model.SalaryDetails;
import com.lnt.ems.api.model.SalaryData;
import com.lnt.ems.api.model.Salary;
import com.lnt.ems.api.repository.SalaryDetailsRepository;
import com.lnt.ems.api.repository.SalaryDataRepository;
import com.lnt.ems.api.repository.SalaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class SalaryServiceImpl  {

    private final SalaryRepository salaryRepository;
    private final SalaryDetailsRepository salaryDetailsRepository;
    private final SalaryDataRepository salaryDataRepository;

    public SalaryServiceImpl(SalaryRepository salaryRepository, SalaryDetailsRepository salaryDetailsRepository, SalaryDataRepository salaryDataRepository) {
        this.salaryRepository = salaryRepository;
        this.salaryDetailsRepository = salaryDetailsRepository;
        this.salaryDataRepository = salaryDataRepository;
    }

    //add salary details
    public SalaryDetails setSalaryData(SalaryDetails salaryDetails){
        return salaryDetailsRepository.save(salaryDetails);
    }
    
    //add salary data
    public SalaryData addSalaryData(SalaryData salaryData){
        return salaryDataRepository.save(salaryData);
    }
    
    //get salary of an employee
    public Float getSalary(Integer id, String date){
        return salaryRepository.getEmployeeSalary(date,id);
    }

    //get employee salary data
    public SalaryData getSalaryData(Integer id, String date){
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
    public Float calculateSalary(Integer id, String date){
        Integer basicSalary = getBasicSalary(id);
        Float otRate = getOtRate(id);
        SalaryData salaryData = getSalaryData(id,date);

        Float totalSalary = (basicSalary-(basicSalary/25 * salaryData.getNoPayDays())) + salaryData.getAttendanceBonus() +
                (otRate*salaryData.getOverTimeHours());

        return totalSalary;
    }
    
    //calculate and save salary
    public Float calculateAndSaveSalary(Integer id, String date){
        Float calculatedSalary = calculateSalary(id, date);
        
        // Create and save salary record
        Salary salary = new Salary();
        salary.setId(id);
        salary.setDate(date);
        salary.setSalaryAmount(calculatedSalary);
        salaryRepository.save(salary);
        
        return calculatedSalary;
    }

    //get all salaries for an employee
    public java.util.List<Salary> getAllSalaries(Integer id) {
        return salaryRepository.findAllByEmployeeId(id);
    }

    //get all salaries for a given date
    public java.util.List<Salary> getAllSalariesByDate(String date) {
        return salaryRepository.findAllByDate(date);
    }
}
