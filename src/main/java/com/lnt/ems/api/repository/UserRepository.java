package com.lnt.ems.api.repository;

import com.lnt.ems.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    // Find user by email
    User findByEmail(String email);
    
    // Find user by email and password (for login)
    User findByEmailAndPassword(String email, String password);
    
    // Check if user exists by email
    boolean existsByEmail(String email);
}
