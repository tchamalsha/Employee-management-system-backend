package com.lnt.ems.api.repository;

import com.lnt.ems.api.model.SalaryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryDetailsRepository extends JpaRepository<SalaryDetails,Integer> {

    @Query("SELECT sd.basicSalary FROM SalaryDetails sd WHERE sd.id=?1")
    Integer getBasicSalary(Integer id);

    @Query("SELECT sd.otRate FROM SalaryDetails sd WHERE sd.id=?1")
    Float getOtRate(Integer id);

}
