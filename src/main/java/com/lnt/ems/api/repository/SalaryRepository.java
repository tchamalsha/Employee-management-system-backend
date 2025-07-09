package com.lnt.ems.api.repository;

import com.lnt.ems.api.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary,Integer> {

    @Query("SELECT sl.salaryAmount FROM Salary sl WHERE sl.date=?1 AND sl.id=?2")
    Float getEmployeeSalary(Date date,Integer employeeId);

    // Fetch all salaries for a specific employee
    List<Salary> findAllById(Integer employeeId);

    // JpaRepository already provides findAll() for all salaries
}
