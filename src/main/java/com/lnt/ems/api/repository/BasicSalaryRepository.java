package com.lnt.ems.api.repository;

import com.lnt.ems.api.model.BasicSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicSalaryRepository extends JpaRepository<BasicSalary,Integer> {

    @Query("SELECT bs.basicSalary FROM BasicSalary bs WHERE bs.id=?1")
    Integer getBasicSalary(Integer id);

    @Query("SELECT bs.otRate FROM BasicSalary bs WHERE bs.id=?1")
    Float getOtRate(Integer id);


}
