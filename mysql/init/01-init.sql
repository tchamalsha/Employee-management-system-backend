-- Initialize EMS Database with proper schema matching Java models
USE ems;

-- Drop existing tables if they exist (clean slate)
DROP TABLE IF EXISTS salary_data;
DROP TABLE IF EXISTS salary;
DROP TABLE IF EXISTS salary_details;
DROP TABLE IF EXISTS personal_details;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS users;

-- Create admins table
CREATE TABLE admins (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    position VARCHAR(255),
    role VARCHAR(50) DEFAULT 'ADMIN'
);

-- Create employees table
CREATE TABLE employees (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    position VARCHAR(255),
    role VARCHAR(50) DEFAULT 'EMPLOYEE',
    admin_id INT,
    FOREIGN KEY (admin_id) REFERENCES admins(id)
);

-- Create personal_details table (separate from salary)
CREATE TABLE personal_details (
    id INT PRIMARY KEY,
    telephone BIGINT,
    address VARCHAR(255),
    postal_code INT
);

-- Create salary_details table
CREATE TABLE salary_details (
    id INT PRIMARY KEY,
    basic_salary INT,
    ot_rate FLOAT,
    special_allowance INT
);

-- Create salary table
CREATE TABLE salary (
    id INT,
    date DATE,
    salary_amount FLOAT,
    PRIMARY KEY (id, date)
);

-- Create salary_data table
CREATE TABLE salary_data (
    id INT,
    date DATE,
    no_pay_days FLOAT,
    over_time_hours FLOAT,
    attendance_bonus INT,
    PRIMARY KEY (id, date)
);

-- Create indexes for better performance
CREATE INDEX idx_employees_admin_id ON employees(admin_id);
CREATE INDEX idx_employees_email ON employees(email);
CREATE INDEX idx_admins_email ON admins(email);

-- Insert default admin
INSERT INTO admins (id, name, email, password, position, role)
VALUES (1, 'System Administrator', 'admin@ems.com', 'admin123', 'System Administrator', 'ADMIN');

-- Sample employee data
INSERT INTO employees (id, name, email, password, position, role, admin_id)
VALUES (2, 'John Doe', 'john.doe@company.com', 'password123', 'Software Engineer', 'EMPLOYEE', 1);

-- Sample personal details
INSERT INTO personal_details (id, telephone, address, postal_code)
VALUES (2, 1234567890, '123 Main St, City, State', 12345);

-- Sample salary details data
INSERT INTO salary_details (id, basic_salary, ot_rate, special_allowance)
VALUES (2, 50000, 1.5, 5000); 