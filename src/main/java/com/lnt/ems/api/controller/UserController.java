package com.lnt.ems.api.controller;

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

    @PostMapping("/signup")
    @Operation(
        summary = "Register new user",
        description = "Creates a new user account in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User registered successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid user data"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "User already exists"
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
        try {
            userService.addUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("already exists")) {
                return ResponseEntity.status(409)
                    .body("Error: " + errorMessage);
            } else {
                return ResponseEntity.status(400)
                    .body("Error registering user: " + errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body("Internal server error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(
        summary = "User login",
        description = "Authenticates user credentials and returns login status"
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

}
