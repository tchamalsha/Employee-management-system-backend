package com.lnt.ems.api.service;

import com.lnt.ems.api.model.SalaryDetails;
import com.lnt.ems.api.model.SalaryData;
import com.lnt.ems.api.model.Salary;
import com.lnt.ems.api.observer.SalaryCalculationObserver;
import com.lnt.ems.api.repository.SalaryDetailsRepository;
import com.lnt.ems.api.repository.SalaryDataRepository;
import com.lnt.ems.api.repository.SalaryRepository;
import com.lnt.ems.api.service.interfaces.SalaryService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class SalaryServiceImpl implements SalaryService {

    private static final Logger log = LoggerFactory.getLogger(SalaryServiceImpl.class);

    private final SalaryRepository salaryRepository;
    private final SalaryDetailsRepository salaryDetailsRepository;
    private final SalaryDataRepository salaryDataRepository;
    private final SalaryCalculationStrategyService strategyService;
    private final List<SalaryCalculationObserver> observers;

    @Autowired
    public SalaryServiceImpl(SalaryRepository salaryRepository,
                           SalaryDetailsRepository salaryDetailsRepository,
                           SalaryDataRepository salaryDataRepository,
                           SalaryCalculationStrategyService strategyService,
                           List<SalaryCalculationObserver> observers) {
        this.salaryRepository = salaryRepository;
        this.salaryDetailsRepository = salaryDetailsRepository;
        this.salaryDataRepository = salaryDataRepository;
        this.strategyService = strategyService;
        this.observers = observers;
    }

    //add salary
    public void setSalaryData(SalaryDetails salaryDetails){
        salaryDetailsRepository.save(salaryDetails);
    }
    
    //get salary of an employee
    public Float getSalary(Integer id, Date date){
        return salaryRepository.getEmployeeSalary(date,id);
    }

    //get all salaries
    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }

    //get all salaries for a specific employee
    public List<Salary> getSalariesByEmployeeId(Integer employeeId) {
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

    //calculate salary using strategy pattern
    public Float calculateSalary(Integer id, Date date){
        return strategyService.calculateSalary("STANDARD", id, date);
    }
    
    //calculate salary with specific strategy
    public Float calculateSalary(String strategyType, Integer id, Date date){
        return strategyService.calculateSalary(strategyType, id, date);
    }

    //calculate and save salary with observer pattern
    public Salary calculateAndSaveSalary(Integer id, Date date){
        Float calculatedSalary = calculateSalary(id, date);
        Salary salary = new Salary();
        salary.setId(id);
        salary.setDate(date);
        salary.setSalaryAmount(calculatedSalary);
        
        Salary savedSalary = salaryRepository.save(salary);
        
        // Notify observers
        notifyObservers(savedSalary);
        
        return savedSalary;
    }
    
    // Notify all observers
    private void notifyObservers(Salary salary) {
        for (SalaryCalculationObserver observer : observers) {
            try {
                observer.onSalaryCalculated(salary);
            } catch (Exception e) {
                log.error("Error notifying observer {}: {}", observer.getClass().getSimpleName(), e.getMessage());
            }
        }
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
