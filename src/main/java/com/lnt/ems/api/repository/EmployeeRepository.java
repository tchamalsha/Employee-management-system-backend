package com.lnt.ems.api.repository;

import com.lnt.ems.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    
    // Find employee by email
    Employee findByEmail(String email);
    
    // Find employee by email and password (for login)
    Employee findByEmailAndPassword(String email, String password);
    
    // Check if employee exists by email
    boolean existsByEmail(String email);
    
    // Find all employees created by a specific admin
    @Query("SELECT e FROM Employee e WHERE e.adminId = ?1")
    List<Employee> findByAdminId(Integer adminId);

    @Query("SELECT e from Employee e")
    List<Employee> getAllEmployees();

}
