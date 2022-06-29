package com.lnt.ems.api.controller;

import com.lnt.ems.api.model.Admin;
import com.lnt.ems.api.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    private AdminRepository adminRepository;

    @Autowired
    public AdminController(AdminRepository adminRepository){
        this.adminRepository =adminRepository;
    }

    @PostMapping("/admin")
    private void addAdmin(@RequestBody Admin adminData){
        adminData.setRole("ADMIN");
        adminRepository.save(adminData);
    }
}
