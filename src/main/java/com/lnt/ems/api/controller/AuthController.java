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

    @PostMapping("/signup/user/personalDetails")
    public String addPersonalDetails(@RequestBody PersonalDetails personalDetails){
        PersonalDetails savedDetails = personalDetailsService.addPersonalDetails(personalDetails);
        return "Personal details successfully saved for ID: " + savedDetails.getId() + 
               ", Address: " + savedDetails.getAddress() + 
               ", Telephone: " + savedDetails.getTelephone() + 
               ", Postal Code: " + savedDetails.getPostalCode();
    }

    @PostMapping("/signup/admin")
    public String addAdmin(@RequestBody Admin admin) {
       Admin existingAdmin = adminRepository.findAdminById(admin.getId());
       if (existingAdmin != null) {
           return "Admin with ID " + admin.getId() + " already registered";
       }
       adminRepository.save(admin);
       return "Admin successfully registered";
    }

    @PostMapping("/signup/employee")
    public String addEmployee(@RequestBody Employee employee) {
        Employee existingEmployee = employeeRepository.findEmployeeById(employee.getId());
        if (existingEmployee != null) {
            return "Employee with ID " + employee.getId() + " already registered";
        }
        employeeRepository.save(employee);
        return "Employee successfully registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){
        // Try admin login first
        Admin foundAdmin = adminRepository.findAdminById(request.getId());
        if (foundAdmin != null && foundAdmin.getPassword().equals(request.getPassword())) {
            return "Admin login successful";
        }
        
        // Try employee login
        Employee foundEmployee = employeeRepository.findEmployeeById(request.getId());
        if (foundEmployee != null && foundEmployee.getPassword().equals(request.getPassword())) {
            return "Employee login successful";
        }
        
        return "Login failed - Invalid credentials";
    }

    // Inner class for login request
    public static class LoginRequest {
        private Integer id;
        private String password;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
