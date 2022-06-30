package com.lnt.ems.api.controller;

import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.repository.EmployeeRepository;
import com.lnt.ems.api.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService){
        this.employeeService=employeeService;
    }

    @PostMapping("/employee")
    public void addStudent(@RequestBody Employee employeeData) {
        employeeService.addEmployee(employeeData);
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees(){
        return  employeeService.getAllEmployees();
    }


}
