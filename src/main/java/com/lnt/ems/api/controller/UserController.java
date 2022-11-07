package com.lnt.ems.api.controller;

import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


}
