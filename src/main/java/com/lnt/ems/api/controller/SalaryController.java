package com.lnt.ems.api.controller;

import com.lnt.ems.api.model.BasicSalary;
import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.service.SalaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class SalaryController {

    private SalaryServiceImpl salaryService;

    @Autowired
    public SalaryController(SalaryServiceImpl salaryService) {
        this.salaryService = salaryService;
    }

    @PostMapping("/signup/salaryDetails")
    public void addPersonalDetails(@RequestBody BasicSalary basicSalary){
        salaryService.setSalaryData(basicSalary);
    }
}
