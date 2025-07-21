package com.lnt.ems.api.repository;

import com.lnt.ems.api.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends JpaRepository<Salary,Salary.SalaryId> {

    @Query("SELECT sl.salaryAmount FROM Salary sl WHERE sl.date=?1 AND sl.id=?2")
    Float getEmployeeSalary(String date, Integer employeeId);

    @Query("SELECT sl FROM Salary sl WHERE sl.id=?1")
    java.util.List<Salary> findAllByEmployeeId(Integer employeeId);

    @Query("SELECT sl FROM Salary sl WHERE sl.date=?1")
    java.util.List<Salary> findAllByDate(String date);

}
