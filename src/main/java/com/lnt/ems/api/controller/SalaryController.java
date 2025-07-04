package com.lnt.ems.api.controller;

import com.lnt.ems.api.model.BasicSalary;
import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.service.SalaryServiceImpl;
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

@RestController
@CrossOrigin
@Tag(name = "Salary Management", description = "APIs for managing employee salary information")
public class SalaryController {

    private SalaryServiceImpl salaryService;

    @Autowired
    public SalaryController(SalaryServiceImpl salaryService) {
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
        @Parameter(description = "Basic salary object", required = true)
        @RequestBody BasicSalary basicSalary
    ){
        salaryService.setSalaryData(basicSalary);
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
            description = "Successfully retrieved salaries",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = BasicSalary.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No salaries found"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<String> getAllSalaries(){
        // This would need to be implemented in the service
        return ResponseEntity.ok("Salary list retrieved");
    }

    @GetMapping("/salaries/{employeeId}")
    @Operation(
        summary = "Get salary by employee ID",
        description = "Retrieves salary information for a specific employee"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved salary",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = BasicSalary.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Salary not found for employee"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<BasicSalary> getSalaryByEmployeeId(
        @Parameter(description = "Employee ID", required = true)
        @PathVariable Integer employeeId
    ){
        // This would need to be implemented in the service
        return ResponseEntity.ok().build();
    }
}
