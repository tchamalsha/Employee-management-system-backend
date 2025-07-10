package com.lnt.ems.api.service.interfaces;

import com.lnt.ems.api.dto.EmployeeRegistrationRequest;
import com.lnt.ems.api.model.Admin;
import com.lnt.ems.api.model.Employee;

import java.util.List;

public interface AdminService {
    List<Admin> getAllAdmins();
    Boolean isAdminLoginSuccess(Integer id, String password);
    Employee registerEmployee(EmployeeRegistrationRequest request, Integer adminId);
    List<Employee> getEmployeesByAdmin(Integer adminId);
    void createDefaultAdmin();
} 