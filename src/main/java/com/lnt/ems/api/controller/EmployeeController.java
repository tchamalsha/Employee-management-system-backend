package com.lnt.ems.api.controller;

import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.repository.EmployeeRepository;
import com.lnt.ems.api.service.EmployeeServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class EmployeeController {

    private EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }



    @GetMapping("/employees")
    public List<Employee> getEmployees(){
        return  employeeService.getAllEmployees();
    }

    @GetMapping("/employee/{id}/personal-details")
    public PersonalDetails getPersonalDetails(@PathVariable Integer id) {
        return employeeService.getPersonalDetails(id);
    }

    @PutMapping("/employee/{id}/personal-details")
    public PersonalDetails updatePersonalDetails(@PathVariable Integer id, @RequestBody PersonalDetails details) {
        return employeeService.updatePersonalDetails(id, details);
    }

    @GetMapping("/employee/{id}/salary-details")
    public Double getSalaryDetails(@PathVariable Integer id) {
        return employeeService.getSalaryDetails(id);
    }




}
