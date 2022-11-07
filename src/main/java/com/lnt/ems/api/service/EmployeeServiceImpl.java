package com.lnt.ems.api.service;

import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.repository.EmployeeRepository;
import com.lnt.ems.api.repository.PersonalDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EmployeeServiceImpl {

    private final EmployeeRepository employeeRepository;
    private final PersonalDetailsRepository personalDetailsRepository;


    //save an employee
    public void addEmployee(Employee employee){
        employee.setRole("EMPLOYEE");
        employeeRepository.save(employee);
    }

    //get all employees
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    //get personal details
    public PersonalDetails getPersonalDetails(Integer id){
        return personalDetailsRepository.getPersonalDetails(id);
    }

    public Boolean isLoginSuccess(Integer id,String password){
        return (employeeRepository.getPassword(id)==password);
    }
}
