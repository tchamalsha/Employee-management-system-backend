package com.lnt.ems.api.service.interfaces;

import com.lnt.ems.api.model.Admin;
import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.model.User;
import com.lnt.ems.api.dto.UserSignupRequest;

public interface UserService {
    void addPersonalDetails(PersonalDetails personalDetails);
    void addUser(User user);
    Boolean isLoginSuccess(Integer id, String password);
    Admin registerAdmin(Admin admin);
    Employee registerEmployee(Employee employee);
    String getUserType(Integer id);
    
    // New unified signup method
    Object registerUser(UserSignupRequest request);
} 