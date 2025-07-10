package com.lnt.ems.api.controller;

import com.lnt.ems.api.model.SalaryDetails;
import com.lnt.ems.api.model.SalaryData;
import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.model.Salary;
import com.lnt.ems.api.service.SalaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String addSalaryDetails(@RequestBody SalaryDetails salaryDetails){
        SalaryDetails savedDetails = salaryService.setSalaryData(salaryDetails);
        return "Salary details successfully saved for ID: " + savedDetails.getId() + 
               ", Basic Salary: " + savedDetails.getBasicSalary() + 
               ", OT Rate: " + savedDetails.getOtRate() + 
               ", Special Allowance: " + savedDetails.getSpecialAllowance();
    }

    @PostMapping("/signup/user/salaryData")
    public String addSalaryData(@RequestBody SalaryData salaryData){
        SalaryData savedData = salaryService.addSalaryData(salaryData);
        return "Salary data successfully saved for ID: " + savedData.getId() + 
               ", Date: " + savedData.getDate() + 
               ", No Pay Days: " + savedData.getNoPayDays() + 
               ", Overtime Hours: " + savedData.getOverTimeHours() + 
               ", Attendance Bonus: " + savedData.getAttendanceBonus();
    }

    @PostMapping("/user/calculate-salary")
    public Float calculateSalary(@RequestBody SalaryCalculationRequest request){
        return salaryService.calculateAndSaveSalary(request.getId(), request.getDate());
    }

    @PostMapping("/user/salary")
    public String getSalary(@RequestBody SalaryRequest request) {
        if (request.getId() == null || request.getDate() == null) {
            return "Error: Both 'id' and 'date' fields are required in the request body. Example: {\"id\":123,\"date\":\"2024-01\"}";
        }
        Float salary = salaryService.getSalary(request.getId(), request.getDate());
        if (salary != null) {
            return "Salary for ID " + request.getId() + " on " + request.getDate() + ": " + salary;
        } else {
            return "No salary found for ID " + request.getId() + " on " + request.getDate();
        }
    }

    @PostMapping("/user/salaries")
    public java.util.List<Salary> getAllSalaries(@RequestBody SalaryIdRequest request) {
        if (request.getId() == null) {
            return java.util.Collections.emptyList();
        }
        return salaryService.getAllSalaries(request.getId());
    }

    @PostMapping("/salaries/date")
    public java.util.List<Salary> getAllSalariesByDate(@RequestBody SalaryDateRequest request) {
        if (request.getDate() == null) {
            return java.util.Collections.emptyList();
        }
        return salaryService.getAllSalariesByDate(request.getDate());
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

    // Inner class for salary request
    public static class SalaryRequest {
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

    // Inner class for salary id request
    public static class SalaryIdRequest {
        private Integer id;
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
    }

    // Inner class for salary date request
    public static class SalaryDateRequest {
        private String date;
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
    }
}
