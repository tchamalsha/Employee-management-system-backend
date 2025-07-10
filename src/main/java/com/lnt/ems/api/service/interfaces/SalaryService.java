package com.lnt.ems.api.service.interfaces;

import com.lnt.ems.api.model.Salary;
import com.lnt.ems.api.model.SalaryData;
import com.lnt.ems.api.model.SalaryDetails;

import java.util.Date;
import java.util.List;

public interface SalaryService {
    void setSalaryData(SalaryDetails salaryDetails);
    Float getSalary(Integer id, Date date);
    List<Salary> getAllSalaries();
    List<Salary> getSalariesByEmployeeId(Integer employeeId);
    SalaryData getSalaryData(Integer id, Date date);
    Integer getBasicSalary(Integer id);
    Float getOtRate(Integer id);
    Float calculateSalary(Integer id, Date date);
    Float calculateSalary(String strategyType, Integer id, Date date);
    Salary calculateAndSaveSalary(Integer id, Date date);
    SalaryData addSalaryData(SalaryData salaryData);
    boolean salaryDataExists(Integer employeeId, Date date);
    boolean waitForSalaryData(Integer employeeId, Date date, int maxWaitSeconds);
} 