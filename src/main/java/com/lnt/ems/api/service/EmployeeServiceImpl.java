package com.lnt.ems.api.service;

import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EmployeeServiceImpl implements UserServiceImpl {

    private final EmployeeRepository employeeRepository;

    //get all employees
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

}
