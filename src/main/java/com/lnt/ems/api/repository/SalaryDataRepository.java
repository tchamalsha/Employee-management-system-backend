package com.lnt.ems.api.repository;

import com.lnt.ems.api.model.SalaryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface SalaryDataRepository extends JpaRepository<SalaryData,Integer> {

    @Query("SELECT sd FROM SalaryData sd where sd.id=?1 AND sd.date=?2")
    SalaryData getSalaryData(Integer id, Date date);

}
