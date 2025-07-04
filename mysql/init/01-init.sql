-- Initialize EMS Database
USE ems;

-- Create tables if they don't exist (Spring Boot will handle this with JPA)
-- This file can be used for any initial data setup

-- Create default admin user
-- Note: This will be executed after Spring Boot creates the tables
-- The admin user will have ID=1, password=admin123
INSERT IGNORE INTO users (id, name, email, password, position, role, dtype) 
VALUES (1, 'System Administrator', 'admin@ems.com', 'admin123', 'System Administrator', 'ADMIN', 'Admin');

-- Sample employee data
INSERT IGNORE INTO users (id, name, email, password, position, role, dtype) 
VALUES (2, 'John Doe', 'john.doe@company.com', 'password123', 'Software Engineer', 'EMPLOYEE', 'Employee');

-- Sample salary data
INSERT IGNORE INTO salary_details (id, basic_salary, ot_rate, special_allowance, salary_date) 
VALUES (2, 50000, 1.5, 5000, '2024-01-01'); 