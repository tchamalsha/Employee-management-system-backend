package com.lnt.ems.api.repository;

import com.lnt.ems.api.model.SalaryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryDetailsRepository extends JpaRepository<SalaryDetails,Integer> {

    @Query("SELECT bs.basicSalary FROM SalaryDetails bs WHERE bs.id=?1")
    Integer getBasicSalary(Integer id);

    @Query("SELECT bs.otRate FROM SalaryDetails bs WHERE bs.id=?1")
    Float getOtRate(Integer id);

    @Query("SELECT bs.specialAllowance FROM SalaryDetails bs WHERE bs.id=?1")
    Float getSpecialAllowance(Integer id);

}
