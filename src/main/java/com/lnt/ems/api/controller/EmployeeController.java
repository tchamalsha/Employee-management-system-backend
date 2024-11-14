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

    private final EmployeeRepository employeeRepository;
    private EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/employee/register")
    public ResponseEntity<Employee> registerEmployee(@RequestBody Employee employeeData) {
        Employee savedEmployee = employeeRepository.save(employeeData);
        Integer cid = employeeService.generateCid(savedEmployee.getDepartment(),savedEmployee.getId());
        savedEmployee.setCid(cid);
        savedEmployee = employeeRepository.save(employeeData);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees(){
        return  employeeService.getAllEmployees();
    }

    @GetMapping("/login")
    public Boolean isLoginSuccess(@RequestBody Integer id,String password){
        return employeeService.isLoginSuccess(id,password);
    }


}
