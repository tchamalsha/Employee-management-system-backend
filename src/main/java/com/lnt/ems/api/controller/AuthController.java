package com.lnt.ems.api.controller;

import com.lnt.ems.api.model.Admin;
import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.repository.AdminRepository;
import com.lnt.ems.api.repository.EmployeeRepository;
import com.lnt.ems.api.service.PersonalDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    public AuthController(PersonalDetailsServiceImpl personalDetailsService, AdminRepository adminRepository, EmployeeRepository employeeRepository) {
        this.personalDetailsService = personalDetailsService;
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
    }

    private PersonalDetailsServiceImpl personalDetailsService;
    private AdminRepository adminRepository;
    private EmployeeRepository employeeRepository;

    @PostMapping("/signup/personalDetails")
    public void addPersonalDetails(@RequestBody PersonalDetails personalDetails){
        personalDetailsService.addPersonalDetails(personalDetails);
    }

    @PostMapping("/signup/admin")
    public void addAdmin(@RequestBody Admin admin) {
        adminRepository.save(admin);
    }

    @PostMapping("/signup/employee")
    public void addEmployee(@RequestBody Employee employee) {
        employeeRepository.save(employee);
    }

    @GetMapping("/login/admin")
    public Boolean isAdminLoginSuccess(@RequestBody Admin admin){
        Admin foundAdmin = adminRepository.findAdminById(admin.getId());
        return foundAdmin != null && foundAdmin.getPassword().equals(admin.getPassword());
    }

    @GetMapping("/login/employee")
    public Boolean isEmployeeLoginSuccess(@RequestBody Employee employee){
        Employee foundEmployee = employeeRepository.findEmployeeById(employee.getId());
        return foundEmployee != null && foundEmployee.getPassword().equals(employee.getPassword());
    }
}
