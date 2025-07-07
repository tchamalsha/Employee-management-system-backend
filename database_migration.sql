-- Database Migration Script for Employee Management System
-- This script creates the proper table structure matching Java models

-- Step 1: Create admins table
CREATE TABLE IF NOT EXISTS admins (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    position VARCHAR(255),
    role VARCHAR(50) DEFAULT 'ADMIN'
);

-- Step 2: Create employees table with admin tracking
CREATE TABLE IF NOT EXISTS employees (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    position VARCHAR(255),
    role VARCHAR(50) DEFAULT 'EMPLOYEE',
    admin_id INT,
    FOREIGN KEY (admin_id) REFERENCES admins(id)
);

-- Step 3: Create personal_details table (separate from salary)
CREATE TABLE IF NOT EXISTS personal_details (
    id INT PRIMARY KEY,
    telephone BIGINT,
    address VARCHAR(255),
    postal_code INT
);

-- Step 4: Create salary_details table
CREATE TABLE IF NOT EXISTS salary_details (
    id INT PRIMARY KEY,
    basic_salary INT,
    ot_rate FLOAT,
    special_allowance INT
);

-- Step 5: Create salary table
CREATE TABLE IF NOT EXISTS salary (
    id INT,
    date DATE,
    salary_amount FLOAT,
    PRIMARY KEY (id, date)
);

-- Step 6: Create salary_data table
CREATE TABLE IF NOT EXISTS salary_data (
    id INT,
    date DATE,
    no_pay_days FLOAT,
    over_time_hours FLOAT,
    attendance_bonus INT,
    PRIMARY KEY (id, date)
);

-- Step 7: Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_employees_admin_id ON employees(admin_id);
CREATE INDEX IF NOT EXISTS idx_employees_email ON employees(email);
CREATE INDEX IF NOT EXISTS idx_admins_email ON admins(email);

-- Step 8: Insert default admin if not exists
INSERT INTO admins (id, name, email, password, position, role)
VALUES (1, 'System Administrator', 'admin@ems.com', 'admin123', 'System Administrator', 'ADMIN')
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    email = VALUES(email),
    password = VALUES(password),
    position = VALUES(position),
    role = VALUES(role);

-- Verification queries
SELECT 'Admins count:' as info, COUNT(*) as count FROM admins;
SELECT 'Employees count:' as info, COUNT(*) as count FROM employees;
SELECT 'Personal details count:' as info, COUNT(*) as count FROM personal_details;
SELECT 'Salary details count:' as info, COUNT(*) as count FROM salary_details;
SELECT 'Salary records count:' as info, COUNT(*) as count FROM salary;
SELECT 'Salary data count:' as info, COUNT(*) as count FROM salary_data; 