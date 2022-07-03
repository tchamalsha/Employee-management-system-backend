package com.lnt.ems.api.repository;

import com.lnt.ems.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Employee findEmployeeById(Integer id);

    @Query("SELECT em.password from Employee em where em.id=?1")
    String getPassword(Integer id);


}
