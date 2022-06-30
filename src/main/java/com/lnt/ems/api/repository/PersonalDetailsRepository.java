package com.lnt.ems.api.repository;

import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.model.SalaryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails,Integer> {

    @Query("SELECT pd FROM PersonalDetails pd where pd.id=?1")
    PersonalDetails getPersonalDetails(Integer id);

}