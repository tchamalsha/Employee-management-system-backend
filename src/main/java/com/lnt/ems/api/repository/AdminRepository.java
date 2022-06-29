package com.lnt.ems.api.repository;

import com.lnt.ems.api.model.Admin;
import com.lnt.ems.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Employee,Integer> {

    Admin findAdminByEmail(String email);
}