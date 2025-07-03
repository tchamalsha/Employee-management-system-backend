package com.lnt.ems.api.service;

import com.lnt.ems.api.model.Admin;
import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.model.User;
import com.lnt.ems.api.model.BasicSalary;
import com.lnt.ems.api.model.Salary;
import com.lnt.ems.api.model.SalaryData;
import com.lnt.ems.api.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminServiceImpl  {

    private final AdminRepository adminRepository;

    //get all admins
    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    public Employee createEmployee(Employee employee) {
        // Implement employee creation logic
        return null;
    }

    public User assignRole(Integer id, String role) {
        // Implement role assignment logic
        return null;
    }

    public Employee updateEmployee(Integer id, Employee employee) {
        // Implement employee update logic
        return null;
    }

    public void removeEmployee(Integer id) {
        // Implement employee removal logic
    }

    public void updateSalary(Integer id, Double salary) {
        // Implement salary update logic
    }

    public Employee createEmployeeWithSalary(Employee employee, BasicSalary basicSalary) {
        // Implement employee creation with salary logic
        return null;
    }

    public BasicSalary updateSalaryDetails(Integer id, BasicSalary basicSalary) {
        // Implement salary details update logic
        return null;
    }

    public Salary calculateSalary(Integer id, SalaryData salaryData) {
        // Implement salary calculation logic
        return null;
    }

}
