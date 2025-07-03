package com.lnt.ems.api.controller;

import com.lnt.ems.api.model.Admin;
import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.model.BasicSalary;
import com.lnt.ems.api.model.Salary;
import com.lnt.ems.api.model.SalaryData;
import com.lnt.ems.api.service.AdminServiceImpl;
import com.lnt.ems.api.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class AdminController {

    private AdminServiceImpl adminService;


    @Autowired
    public AdminController(AdminServiceImpl adminService){
        this.adminService=adminService;

    }

    @GetMapping("/admins")
    private List<Admin> getAllAdmins(){
        return adminService.getAllAdmins();
    }

    @PostMapping("/admin/employees")
    public Employee createEmployeeWithSalary(@RequestBody Employee employee, @RequestBody BasicSalary basicSalary) {
        return adminService.createEmployeeWithSalary(employee, basicSalary);
    }

    @PutMapping("/admin/employees/{id}/role")
    public User assignRole(@PathVariable Integer id, @RequestParam String role) {
        return adminService.assignRole(id, role);
    }

    @PutMapping("/admin/employees/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        return adminService.updateEmployee(id, employee);
    }

    @DeleteMapping("/admin/employees/{id}")
    public void removeEmployee(@PathVariable Integer id) {
        adminService.removeEmployee(id);
    }

    @PutMapping("/admin/employees/{id}/salary-details")
    public BasicSalary updateSalaryDetails(@PathVariable Integer id, @RequestBody BasicSalary basicSalary) {
        return adminService.updateSalaryDetails(id, basicSalary);
    }

    @PostMapping("/admin/employees/{id}/calculate-salary")
    public Salary calculateSalary(@PathVariable Integer id, @RequestBody SalaryData salaryData) {
        return adminService.calculateSalary(id, salaryData);
    }
}
