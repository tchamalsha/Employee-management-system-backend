package com.lnt.ems.api.service;

import com.lnt.ems.api.model.Admin;
import com.lnt.ems.api.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class AdminServiceImpl  {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    //get all admins
    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

}
