package com.lnt.ems.api.controller;

import com.lnt.ems.api.model.SalaryDetails;
import com.lnt.ems.api.model.SalaryData;
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

    @PostMapping("/signup/user/salaryDetails")
    public void addSalaryDetails(@RequestBody SalaryDetails salaryDetails){
        salaryService.setSalaryData(salaryDetails);
    }

    @PostMapping("/signup/user/salaryData")
    public void addSalaryData(@RequestBody SalaryData salaryData){
        salaryService.addSalaryData(salaryData);
    }

    @PostMapping("/calculate-salary")
    public Float calculateSalary(@RequestBody SalaryCalculationRequest request){
        return salaryService.calculateAndSaveSalary(request.getId(), request.getDate());
    }

    // Inner class for salary calculation request
    public static class SalaryCalculationRequest {
        private Integer id;
        private String date;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
