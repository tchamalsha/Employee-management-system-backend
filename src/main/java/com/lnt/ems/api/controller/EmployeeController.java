package com.lnt.ems.api.controller;

import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.repository.EmployeeRepository;
import com.lnt.ems.api.service.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Tag(name = "Employee Management", description = "APIs for managing employee information")
public class EmployeeController {

    private EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }



    @GetMapping("/employees")
    @Operation(
        summary = "Get all employees",
        description = "Retrieves a list of all employees in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved employees",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Employee.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No employees found"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<List<Employee>> getEmployees(){
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees/{id}")
    @Operation(
        summary = "Get employee by ID",
        description = "Retrieves a specific employee by their ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved employee",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Employee.class)
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
    public ResponseEntity<Employee> getEmployeeById(
        @Parameter(description = "Employee ID", required = true)
        @PathVariable Integer id
    ){
        // This would need to be implemented in the service
        return ResponseEntity.ok().build();
    }

    @PostMapping("/employees")
    @Operation(
        summary = "Create new employee",
        description = "Creates a new employee in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Employee created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Employee.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid employee data"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Employee already exists"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<Employee> createEmployee(
        @Parameter(description = "Employee object to create", required = true)
        @RequestBody Employee employee
    ){
        // This would need to be implemented in the service
        return ResponseEntity.status(201).body(employee);
    }

    @PutMapping("/employees/{id}")
    @Operation(
        summary = "Update employee",
        description = "Updates an existing employee's information"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Employee updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Employee.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Employee not found"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid employee data"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<Employee> updateEmployee(
        @Parameter(description = "Employee ID", required = true)
        @PathVariable Integer id,
        @Parameter(description = "Updated employee object", required = true)
        @RequestBody Employee employee
    ){
        // This would need to be implemented in the service
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/employees/{id}")
    @Operation(
        summary = "Delete employee",
        description = "Deletes an employee from the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Employee deleted successfully"
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
    public ResponseEntity<Void> deleteEmployee(
        @Parameter(description = "Employee ID", required = true)
        @PathVariable Integer id
    ){
        // This would need to be implemented in the service
        return ResponseEntity.noContent().build();
    }
}
