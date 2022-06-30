package com.lnt.ems.api.service;

import com.lnt.ems.api.model.SalaryData;
import com.lnt.ems.api.repository.BasicSalaryRepository;
import com.lnt.ems.api.repository.SalaryDataRepository;
import com.lnt.ems.api.repository.SalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SalaryServiceImpl  {

    private final SalaryRepository salaryRepository;
    private final BasicSalaryRepository basicSalaryRepository;
    private final SalaryDataRepository salaryDataRepository;

    //get salary of an employee
    public Float getSalary(Integer id, Date date){
        return salaryRepository.getEmployeeSalary(date,id);
    }

    //get employee salary data
    public SalaryData getSalaryData(Integer id,Date date){
        return salaryDataRepository.getSalaryData(id,date);
    }

    //get basic salary
    public Integer getBasicSalary(Integer id){
        return  basicSalaryRepository.getBasicSalary(id);
    }

}
