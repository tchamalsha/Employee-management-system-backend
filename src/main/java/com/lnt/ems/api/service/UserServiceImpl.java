package com.lnt.ems.api.service;

import com.lnt.ems.api.model.Admin;
import com.lnt.ems.api.model.Employee;
import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.model.User;
import com.lnt.ems.api.repository.AdminRepository;
import com.lnt.ems.api.repository.EmployeeRepository;
import com.lnt.ems.api.repository.PersonalDetailsRepository;
import com.lnt.ems.api.repository.UserRepository;
import com.lnt.ems.api.service.interfaces.UserService;
import com.lnt.ems.api.dto.UserSignupRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final PersonalDetailsRepository personalDetailsRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;

    public void addPersonalDetails(PersonalDetails personalDetails){
        personalDetailsRepository.save(personalDetails);
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public Boolean isLoginSuccess(Integer id, String password){
        try {
            // First, try to find in admins table
            Admin admin = adminRepository.findById(id).orElse(null);
            if (admin != null && admin.getPassword().equals(password)) {
                log.info("Admin login successful for user ID: {}", id);
                return true;
            }
            
            // Then, try to find in employees table
            Employee employee = employeeRepository.findById(id).orElse(null);
            if (employee != null && employee.getPassword().equals(password)) {
                log.info("Employee login successful for user ID: {}", id);
                return true;
            }
            
            // Finally, try the legacy users table
            String storedPassword = userRepository.getPassword(id);
            if (storedPassword != null && storedPassword.equals(password)) {
                log.info("Legacy user login successful for user ID: {}", id);
                return true;
            }
            
            log.warn("Login failed for user ID: {}", id);
            return false;
        } catch (Exception e) {
            log.error("Error during login for user ID: {}", id, e);
            return false;
        }
    }

    // Check if ID already exists in any table
    private boolean isIdAlreadyExists(Integer id) {
        return adminRepository.findById(id).isPresent() ||
               employeeRepository.findById(id).isPresent() ||
               userRepository.getPassword(id) != null;
    }

    // New method to register admin in admins table with validation
    public Admin registerAdmin(Admin admin) {
        if (admin.getId() == null) {
            throw new IllegalArgumentException("Admin ID cannot be null");
        }
        
        if (isIdAlreadyExists(admin.getId())) {
            log.warn("Registration failed: ID {} already exists in the system", admin.getId());
            throw new IllegalArgumentException("User with ID " + admin.getId() + " already exists in the system");
        }
        
        log.info("Registering new admin: {}", admin.getEmail());
        return adminRepository.save(admin);
    }

    // New method to register employee in employees table with validation
    public Employee registerEmployee(Employee employee) {
        if (employee.getId() == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        
        if (isIdAlreadyExists(employee.getId())) {
            log.warn("Registration failed: ID {} already exists in the system", employee.getId());
            throw new IllegalArgumentException("User with ID " + employee.getId() + " already exists in the system");
        }
        
        log.info("Registering new employee: {}", employee.getEmail());
        return employeeRepository.save(employee);
    }

    // Method to get user type (for frontend to know which table to use)
    public String getUserType(Integer id) {
        if (adminRepository.findById(id).isPresent()) {
            return "ADMIN";
        } else if (employeeRepository.findById(id).isPresent()) {
            return "EMPLOYEE";
        } else if (userRepository.getPassword(id) != null) {
            return "LEGACY_USER";
        }
        return "NOT_FOUND";
    }
    
    // New unified signup method that creates user in appropriate table based on role
    @Override
    public Object registerUser(UserSignupRequest request) {
        if (request.getId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
        if (isIdAlreadyExists(request.getId())) {
            log.warn("Registration failed: ID {} already exists in the system", request.getId());
            throw new IllegalArgumentException("User with ID " + request.getId() + " already exists in the system");
        }
        
        // Validate role
        if (!"ADMIN".equals(request.getRole()) && !"EMPLOYEE".equals(request.getRole())) {
            throw new IllegalArgumentException("Invalid role. Must be either ADMIN or EMPLOYEE");
        }
        
        // Create user based on role
        if ("ADMIN".equals(request.getRole())) {
            Admin admin = new Admin();
            admin.setId(request.getId());
            admin.setName(request.getName());
            admin.setEmail(request.getEmail());
            admin.setPassword(request.getPassword());
            admin.setPosition(request.getPosition());
            admin.setRole("ADMIN");
            
            log.info("Registering new admin: {} with email: {}", request.getName(), request.getEmail());
            return adminRepository.save(admin);
            
        } else if ("EMPLOYEE".equals(request.getRole())) {
            Employee employee = new Employee();
            employee.setId(request.getId());
            employee.setName(request.getName());
            employee.setEmail(request.getEmail());
            employee.setPassword(request.getPassword());
            employee.setPosition(request.getPosition());
            employee.setRole("EMPLOYEE");
            employee.setAdminId(request.getAdminId()); // Set the admin who created this employee
            
            log.info("Registering new employee: {} with email: {} under admin ID: {}", 
                    request.getName(), request.getEmail(), request.getAdminId());
            return employeeRepository.save(employee);
        }
        
        throw new IllegalArgumentException("Invalid role specified");
    }
}
