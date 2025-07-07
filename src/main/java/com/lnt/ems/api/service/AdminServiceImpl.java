package com.lnt.ems.api.service;

import com.lnt.ems.api.dto.EmployeeRegistrationRequest;
import com.lnt.ems.api.model.Admin;
import com.lnt.ems.api.model.SalaryDetails;
import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.repository.AdminRepository;
import com.lnt.ems.api.repository.SalaryDetailsRepository;
import com.lnt.ems.api.repository.EmployeeRepository;
import com.lnt.ems.api.repository.PersonalDetailsRepository;
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
    private final EmployeeRepository employeeRepository;
    private final SalaryDetailsRepository salaryDetailsRepository;
    private final PersonalDetailsRepository personalDetailsRepository;

    //get all admins
    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    //admin login
    public Boolean isAdminLoginSuccess(Integer id, String password){
        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin != null && admin.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    //register employee with salary details
    public Employee registerEmployee(EmployeeRegistrationRequest request, Integer adminId){
        // Create employee
        Employee employee = new Employee();
        employee.setId(request.getId());
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPassword(request.getPassword());
        employee.setPosition(request.getPosition());
        employee.setRole("EMPLOYEE");
        employee.setAdminId(adminId); // Track which admin created this employee
        
        // Save employee
        Employee savedEmployee = employeeRepository.save(employee);
        
        // Create and save salary details
        SalaryDetails salaryDetails = new SalaryDetails();
        salaryDetails.setId(request.getId());
        salaryDetails.setBasicSalary(request.getBasicSalary().intValue());
        salaryDetails.setOtRate(request.getOtRate().floatValue());
        salaryDetails.setSpecialAllowance(request.getSpecialAllowance().intValue());
        salaryDetailsRepository.save(salaryDetails);
        
        // Create and save personal details if provided
        if (request.getAddress() != null && !request.getAddress().isEmpty()) {
            PersonalDetails personalDetails = new PersonalDetails();
            personalDetails.setId(request.getId());
            personalDetails.setAddress(request.getAddress());
            if (request.getPhoneNumber() != null) {
                try {
                    personalDetails.setTelephone(Long.parseLong(request.getPhoneNumber().replaceAll("[^0-9]", "")));
                } catch (NumberFormatException e) {
                    log.warn("Invalid phone number format for employee {}", request.getId());
                }
            }
            personalDetailsRepository.save(personalDetails);
        }
        
        return savedEmployee;
    }

    //get employees created by specific admin
    public List<Employee> getEmployeesByAdmin(Integer adminId){
        return employeeRepository.findByAdminId(adminId);
    }

    //create default admin for testing
    public void createDefaultAdmin(){
        // Check if admin already exists
        if (adminRepository.findById(1).isPresent()) {
            throw new RuntimeException("Admin already exists");
        }
        
        Admin admin = new Admin();
        admin.setId(1);
        admin.setName("System Administrator");
        admin.setEmail("admin@ems.com");
        admin.setPassword("admin123");
        admin.setPosition("System Administrator");
        admin.setRole("ADMIN");
        
        adminRepository.save(admin);
    }
}
