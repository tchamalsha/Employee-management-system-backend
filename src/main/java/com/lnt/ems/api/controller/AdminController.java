package com.lnt.ems.api.controller;

import com.lnt.ems.api.model.Admin;
import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.service.AdminServiceImpl;
import com.lnt.ems.api.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class AdminController {

    private AdminServiceImpl adminService;


    @Autowired
    public AdminController(AdminServiceImpl adminService){
        this.adminService=adminService;

    }

    @PostMapping("/admin/signup")
    private void addAdmin(@RequestBody Admin adminData){
        adminService.addAdmin(adminData);
    }

    @GetMapping("/admins")
    private List<Admin> getAllAdmins(){
        return adminService.getAllAdmins();
    }

}
