package com.lnt.ems.api.service;

import com.lnt.ems.api.model.Admin;
import com.lnt.ems.api.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminServiceImpl implements UserServiceImpl {

    private final AdminRepository adminRepository;

    //get all admins
    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    //add admin
    public void addAdmin(Admin admin){
        admin.setRole("ADMIN");
        adminRepository.save(admin);
    }

}