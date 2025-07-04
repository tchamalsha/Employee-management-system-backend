package com.lnt.ems.api.repository;

import com.lnt.ems.api.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    
    // Find admin by email
    Admin findByEmail(String email);
    
    // Find admin by email and password (for login)
    Admin findByEmailAndPassword(String email, String password);
    
    // Check if admin exists by email
    boolean existsByEmail(String email);
}