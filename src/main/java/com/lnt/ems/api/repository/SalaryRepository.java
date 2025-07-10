package com.lnt.ems.api.repository;

import com.lnt.ems.api.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends JpaRepository<Salary,Integer> {

    @Query("SELECT sl.salaryAmount FROM Salary sl WHERE sl.date=?1 AND sl.id=?2")
    Float getEmployeeSalary(String date, Integer employeeId);

}
