package com.lnt.ems.api.repository;

import com.lnt.ems.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Employee findEmployeeById(Integer id);
    
    // Find all employees created by a specific admin
    List<Employee> findByAdminId(Integer adminId);

    @Query("SELECT e from Employee e")
    List<Employee> getAllEmployees();

}
