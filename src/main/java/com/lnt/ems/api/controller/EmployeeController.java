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




}
