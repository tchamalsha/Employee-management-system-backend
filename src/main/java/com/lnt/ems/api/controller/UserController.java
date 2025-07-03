package com.lnt.ems.api.controller;

import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.model.User;
import com.lnt.ems.api.repository.UserRepository;
import com.lnt.ems.api.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    private UserServiceImpl userService;

    @PostMapping("/signup/personalDetails")
    public void addPersonalDetails(@RequestBody PersonalDetails personalDetails){
        userService.addPersonalDetails(personalDetails);
    }

    @PostMapping("/signup")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PostMapping("/login")
    public Boolean login(@RequestBody User user){
        return userService.isLoginSuccess(user.getId(),user.getPassword());
    }

    @PostMapping("/password-reset")
    public void resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        userService.resetPassword(email, newPassword);
    }

}
