package com.lnt.ems.api.controller;

import com.lnt.ems.api.model.SalaryDetails;
import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.model.Salary;
import com.lnt.ems.api.model.SalaryData;
import com.lnt.ems.api.service.interfaces.SalaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@Tag(name = "Salary Management", description = "APIs for managing employee salary information")
public class SalaryController {

    private SalaryService salaryService;

    @Autowired
    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @PostMapping("/signup/salaryDetails")
    @Operation(
        summary = "Add salary details",
        description = "Adds basic salary information for an employee during registration"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Salary details added successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid salary data"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<String> addSalaryDetails(
        @Parameter(description = "Salary details object", required = true)
        @RequestBody SalaryDetails salaryDetails
    ){
        salaryService.setSalaryData(salaryDetails);
        return ResponseEntity.ok("Salary details added successfully");
    }

    @GetMapping("/salaries")
    @Operation(
        summary = "Get all salaries",
        description = "Retrieves a list of all salary records in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Salary list retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Salary.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<List<Salary>> getAllSalaries(){
        List<Salary> salaries = salaryService.getAllSalaries();
        return ResponseEntity.ok(salaries);
    }

    @GetMapping("/salaries/{employeeId}")
    @Operation(
        summary = "Get salary by employee ID",
        description = "Retrieves salary information for a specific employee"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Salary information retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Salary.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Employee not found"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<List<Salary>> getSalariesByEmployeeId(
        @Parameter(description = "Employee ID", required = true)
        @PathVariable Integer employeeId
    ){
        List<Salary> salaries = salaryService.getSalariesByEmployeeId(employeeId);
        if (salaries == null || salaries.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(salaries);
    }

    @PostMapping("/calculate-salary/{employeeId}/{date}")
    @Operation(
        summary = "Calculate and save salary",
        description = "Calculates salary for an employee based on their details and saves it to the database"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Salary calculated and saved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Salary.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid employee data or missing salary information"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Employee not found"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<Salary> calculateAndSaveSalary(
        @Parameter(description = "Employee ID", required = true)
        @PathVariable Integer employeeId,
        @Parameter(description = "Date for salary calculation", required = true)
        @PathVariable String date
    ){
        try {
            // Parse date string to Date object
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = sdf.parse(date);
            Salary savedSalary = salaryService.calculateAndSaveSalary(employeeId, parsedDate);
            return ResponseEntity.ok(savedSalary);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/calculate-salary/{strategy}/{employeeId}/{date}")
    @Operation(
        summary = "Calculate and save salary with strategy",
        description = "Calculates salary for an employee using a specific calculation strategy"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Salary calculated and saved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Salary.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid employee data or missing salary information"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Employee not found"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<Salary> calculateAndSaveSalaryWithStrategy(
        @Parameter(description = "Calculation strategy (STANDARD, EXECUTIVE)", required = true)
        @PathVariable String strategy,
        @Parameter(description = "Employee ID", required = true)
        @PathVariable Integer employeeId,
        @Parameter(description = "Date for salary calculation", required = true)
        @PathVariable String date
    ){
        try {
            // Parse date string to Date object
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = sdf.parse(date);
            
            // Calculate salary using strategy
            Float calculatedSalary = salaryService.calculateSalary(strategy, employeeId, parsedDate);
            
            // Create and save salary
            Salary salary = new Salary();
            salary.setId(employeeId);
            salary.setDate(parsedDate);
            salary.setSalaryAmount(calculatedSalary);
            
            // Use service method to save
            Salary savedSalary = salaryService.calculateAndSaveSalary(employeeId, parsedDate);
            return ResponseEntity.ok(savedSalary);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/salarydata")
    @Operation(
        summary = "Add salary data",
        description = "Adds attendance and overtime data for salary calculation"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Salary data added successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SalaryData.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid salary data"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<SalaryData> addSalaryData(
        @Parameter(description = "Salary data object", required = true)
        @RequestBody SalaryData salaryData
    ){
        try {
            SalaryData savedSalaryData = salaryService.addSalaryData(salaryData);
            return ResponseEntity.ok(savedSalaryData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
