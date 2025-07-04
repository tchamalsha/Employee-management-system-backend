-- Database Migration Script for Employee Management System
-- This script handles the transition from single table inheritance to separate tables

-- Step 1: Create new admin table
CREATE TABLE IF NOT EXISTS admins (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    position VARCHAR(255),
    role VARCHAR(50) DEFAULT 'ADMIN'
);

-- Step 2: Create new employee table with admin tracking
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

-- Step 3: Migrate existing admin data
INSERT IGNORE INTO admins (id, name, email, password, position, role)
SELECT id, name, email, password, position, role
FROM users
WHERE role = 'ADMIN';

-- Step 4: Migrate existing employee data
INSERT IGNORE INTO employees (id, name, email, password, position, role)
SELECT id, name, email, password, position, role
FROM users
WHERE role = 'EMPLOYEE';

-- Step 5: Create salary_details table if it doesn't exist
CREATE TABLE IF NOT EXISTS salary_details (
    id INT PRIMARY KEY,
    basic_salary INT,
    ot_rate FLOAT,
    special_allowance INT
);

-- Step 6: Create personal_details table if it doesn't exist
CREATE TABLE IF NOT EXISTS personal_details (
    id INT PRIMARY KEY,
    address TEXT,
    telephone BIGINT
);

-- Step 7: Create salary_data table if it doesn't exist
CREATE TABLE IF NOT EXISTS salary_data (
    id INT,
    date DATE,
    no_pay_days INT,
    attendance_bonus FLOAT,
    over_time_hours FLOAT,
    PRIMARY KEY (id, date)
);

-- Step 8: Create indexes for better performance
CREATE INDEX idx_employees_admin_id ON employees(admin_id);
CREATE INDEX idx_employees_email ON employees(email);
CREATE INDEX idx_admins_email ON admins(email);

-- Step 9: Optional: Drop the old users table (uncomment when ready)
-- DROP TABLE IF EXISTS users;

-- Step 10: Insert default admin if not exists
INSERT INTO admins (id, name, email, password, position, role)
VALUES (1, 'System Administrator', 'admin@ems.com', 'admin123', 'System Administrator', 'ADMIN')
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    email = VALUES(email),
    password = VALUES(password),
    position = VALUES(position),
    role = VALUES(role);

-- Step 11: Rename basic_salary table to salary_details
RENAME TABLE IF EXISTS basic_salary TO salary_details;

-- Verification queries
SELECT 'Admins count:' as info, COUNT(*) as count FROM admins;
SELECT 'Employees count:' as info, COUNT(*) as count FROM employees;
SELECT 'Employees by admin:' as info, admin_id, COUNT(*) as count FROM employees GROUP BY admin_id; 