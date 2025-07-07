package com.lnt.ems.api.controller;

import com.lnt.ems.api.model.Admin;
import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.model.User;
import com.lnt.ems.api.repository.UserRepository;
import com.lnt.ems.api.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Tag(name = "User Management", description = "APIs for user registration, authentication, and personal details")
public class UserController {

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    private UserServiceImpl userService;

    @PostMapping("/signup/personalDetails")
    @Operation(
        summary = "Add personal details",
        description = "Adds personal details for a user during registration"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Personal details added successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid personal details data"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<String> addPersonalDetails(
        @Parameter(description = "Personal details object", required = true)
        @RequestBody PersonalDetails personalDetails
    ){
        userService.addPersonalDetails(personalDetails);
        return ResponseEntity.ok("Personal details added successfully");
    }

    @PostMapping("/signup/admin")
    @Operation(
        summary = "Register new admin",
        description = "Creates a new admin account in the admins table"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Admin registered successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid admin data or admin already exists"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "User ID already exists in the system"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<String> registerAdmin(
        @Parameter(description = "Admin object to register", required = true)
        @RequestBody Admin admin
    ) {
        try {
            userService.registerAdmin(admin);
            return ResponseEntity.ok("Admin registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Registration failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error: " + e.getMessage());
        }
    }

    @PostMapping("/signup/employee")
    @Operation(
        summary = "Register new employee",
        description = "Creates a new employee account in the employees table"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Employee registered successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid employee data or employee already exists"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "User ID already exists in the system"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<String> registerEmployee(
        @Parameter(description = "Employee object to register", required = true)
        @RequestBody Employee employee
    ) {
        try {
            userService.registerEmployee(employee);
            return ResponseEntity.ok("Employee registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Registration failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error: " + e.getMessage());
        }
    }

    @PostMapping("/signup")
    @Operation(
        summary = "Register new user (legacy)",
        description = "Creates a new user account in the legacy users table"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User registered successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid user data or user already exists"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<String> addUser(
        @Parameter(description = "User object to register", required = true)
        @RequestBody User user
    ) {
        userService.addUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    @Operation(
        summary = "User login",
        description = "Authenticates user credentials from admins, employees, or legacy users table"
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
    public ResponseEntity<Boolean> isLoginSuccess(
        @Parameter(description = "User credentials", required = true)
        @RequestBody User user
    ){
        Boolean loginSuccess = userService.isLoginSuccess(user.getId(), user.getPassword());
        return ResponseEntity.ok(loginSuccess);
    }

    @GetMapping("/user/{id}/type")
    @Operation(
        summary = "Get user type",
        description = "Returns the type of user (ADMIN, EMPLOYEE, LEGACY_USER, or NOT_FOUND)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User type retrieved successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "User not found"
        )
    })
    public ResponseEntity<String> getUserType(
        @Parameter(description = "User ID", required = true)
        @PathVariable Integer id
    ) {
        String userType = userService.getUserType(id);
        return ResponseEntity.ok(userType);
    }
}
