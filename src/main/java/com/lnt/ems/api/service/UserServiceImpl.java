package com.lnt.ems.api.service;

import com.lnt.ems.api.model.User;
import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.repository.UserRepository;
import com.lnt.ems.api.repository.PersonalDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {

    private UserRepository userRepository;
    private PersonalDetailsRepository personalDetailsRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PersonalDetailsRepository personalDetailsRepository) {
        this.userRepository = userRepository;
        this.personalDetailsRepository = personalDetailsRepository;
    }

    // Add a new user
    public User addUser(User user) {
        // Check if user ID already exists
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new RuntimeException("User with ID " + user.getId() + " already exists. Please use a different ID.");
        }
        
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists. Please use a different email.");
        }
        
        return userRepository.save(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    // Get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Check if user exists by email
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Login user
    public User loginUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    // Check if login is successful
    public Boolean isLoginSuccess(Integer id, String password) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    // Add personal details
    public PersonalDetails addPersonalDetails(PersonalDetails personalDetails) {
        return personalDetailsRepository.save(personalDetails);
    }
}
