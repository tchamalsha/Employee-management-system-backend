-- Cleanup Migration Script for User Inheritance
-- This script removes separate tables and ensures proper inheritance structure

USE ems;

-- Step 1: Drop the separate admin and employee tables if they exist
DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS employees;

-- Step 2: Ensure the users table has the correct structure for inheritance
-- Add admin_id column to users table (will fail if already exists, which is fine)
ALTER TABLE users ADD COLUMN admin_id INT;

-- Step 3: Update existing data to have proper dtype values
-- Update existing admin records
UPDATE users SET dtype = 'Admin' WHERE role = 'ADMIN' AND dtype IS NULL;

-- Update existing employee records  
UPDATE users SET dtype = 'Employee' WHERE role = 'EMPLOYEE' AND dtype IS NULL;

-- Step 4: Set admin_id for existing employees (assuming admin ID 1)
UPDATE users SET admin_id = 1 WHERE role = 'EMPLOYEE' AND admin_id IS NULL;

-- Step 5: Verify the structure
DESCRIBE users;

-- Step 6: Show sample data
SELECT id, name, email, role, dtype, admin_id FROM users LIMIT 10; 