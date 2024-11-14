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
public class EmployeeServiceImpl implements UserServiceImpl {

    private final EmployeeRepository employeeRepository;
    private final PersonalDetailsRepository personalDetailsRepository;


    public Integer generateCid(String department, Integer id) {
        String departmentCode;

        // Determine the department code based on the department
        switch (department) {
            case "Cutting Department":
                departmentCode = "100";
                break;
            case "Packing Department":
                departmentCode = "200";
                break;
            case "Stitching Department":
                departmentCode = "300";
                break;
            default:
                throw new IllegalArgumentException("Unknown department: " + department);
        }

        // Format the ID to be three digits (e.g., 001 for 1)
        String idFormatted = String.format("%03d", id);

        // Combine the department code and the formatted ID
        return Integer.parseInt(departmentCode + idFormatted);
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
