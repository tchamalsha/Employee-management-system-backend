package com.lnt.ems.api.service;

import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.repository.EmployeeRepository;
import com.lnt.ems.api.repository.PersonalDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class EmployeeServiceImpl {

    private final EmployeeRepository employeeRepository;
    private final PersonalDetailsRepository personalDetailsRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PersonalDetailsRepository personalDetailsRepository) {
        this.employeeRepository = employeeRepository;
        this.personalDetailsRepository = personalDetailsRepository;
    }

    //get all employees
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    //get personal details
    public PersonalDetails getPersonalDetails(Integer id){
        return personalDetailsRepository.getPersonalDetails(id);
    }

}
