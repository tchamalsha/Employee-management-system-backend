package com.lnt.ems.api.controller;

import com.lnt.ems.api.dto.EmployeeRegistrationRequest;
import com.lnt.ems.api.model.Admin;
import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.service.AdminServiceImpl;
import com.lnt.ems.api.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Tag(name = "Admin Management", description = "APIs for managing admin users and operations")
public class AdminController {

    private AdminServiceImpl adminService;


    @Autowired
    public AdminController(AdminServiceImpl adminService){
        this.adminService=adminService;

    }

    @GetMapping("/admins")
    @Operation(
        summary = "Get all admins",
        description = "Retrieves a list of all admin users in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved admins",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Admin.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No admins found"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<List<Admin>> getAllAdmins(){
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @PostMapping("/admin/login")
    @Operation(
        summary = "Admin login",
        description = "Authenticates admin credentials and returns login status"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Login attempt processed",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Boolean.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid login credentials"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<Boolean> adminLogin(
        @Parameter(description = "Admin credentials", required = true)
        @RequestBody Admin admin
    ){
        Boolean loginSuccess = adminService.isAdminLoginSuccess(admin.getId(), admin.getPassword());
        return ResponseEntity.ok(loginSuccess);
    }

    @GetMapping("/admin/{adminId}/employees")
    @Operation(
        summary = "Get employees created by admin",
        description = "Retrieves a list of all employees created by a specific admin"
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
            responseCode = "401",
            description = "Unauthorized - Admin authentication required"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Admin not found"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<?> getEmployeesByAdmin(
        @Parameter(description = "Admin ID", required = true)
        @PathVariable Integer adminId,
        @Parameter(description = "Admin password for authentication", required = true)
        @RequestHeader("Admin-Password") String adminPassword
    ){
        // First, authenticate the admin
        Boolean isAdminAuthenticated = adminService.isAdminLoginSuccess(adminId, adminPassword);
        
        if (!isAdminAuthenticated) {
            return ResponseEntity.status(401)
                .body("Unauthorized: Admin authentication required");
        }
        
        try {
            List<Employee> employees = adminService.getEmployeesByAdmin(adminId);
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body("Error retrieving employees: " + e.getMessage());
        }
    }

    @PostMapping("/admin/{adminId}/register-employee")
    @Operation(
        summary = "Register new employee (Admin only)",
        description = "Creates a new employee account with salary details. Only authenticated admins can perform this operation. Admin ID is required in the URL path."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Employee registered successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Employee.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid employee data or admin not authenticated"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Admin authentication required"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Admin not found"
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
    public ResponseEntity<?> registerEmployee(
        @Parameter(description = "Admin ID from URL path", required = true)
        @PathVariable Integer adminId,
        @Parameter(description = "Admin password for authentication", required = true)
        @RequestHeader("Admin-Password") String adminPassword,
        @Parameter(description = "Employee registration data with salary details", required = true)
        @RequestBody EmployeeRegistrationRequest request
    ){
        // First, authenticate the admin using admin ID from URL
        Boolean isAdminAuthenticated = adminService.isAdminLoginSuccess(adminId, adminPassword);
        
        if (!isAdminAuthenticated) {
            return ResponseEntity.status(401)
                .body("Unauthorized: Admin authentication required");
        }
        
        try {
            // Pass admin ID to service for tracking who created the employee
            Employee employee = adminService.registerEmployee(request, adminId);
            return ResponseEntity.status(201).body(employee);
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("already exists")) {
                return ResponseEntity.status(409)
                    .body("Error: " + errorMessage);
            } else if (errorMessage.contains("not found")) {
                return ResponseEntity.status(404)
                    .body("Error: " + errorMessage);
            } else {
                return ResponseEntity.status(400)
                    .body("Error registering employee: " + errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body("Internal server error: " + e.getMessage());
        }
    }

    @PostMapping("/admin/create-default")
    @Operation(
        summary = "Create default admin (Development only)",
        description = "Creates a default admin user for testing purposes"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Default admin created successfully"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Admin already exists"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<String> createDefaultAdmin(){
        try {
            adminService.createDefaultAdmin();
            return ResponseEntity.status(201).body("Default admin created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(409).body("Admin already exists or error occurred");
        }
    }
}
