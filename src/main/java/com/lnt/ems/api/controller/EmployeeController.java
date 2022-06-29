package com.lnt.ems.api.controller;

import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    @PostMapping("/employee")
    public void addStudent(@RequestBody Employee employeeData) {
        employeeRepository.save(employeeData);
    }


}
