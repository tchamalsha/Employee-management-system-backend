-- Migration Script: Rename basic_salary table to salary_details
-- Run this script to update the table name

USE ems;

-- Check if basic_salary table exists before renaming
SELECT COUNT(*) as table_exists FROM information_schema.tables 
WHERE table_schema = 'ems' AND table_name = 'basic_salary';

-- Rename the table from basic_salary to salary_details
-- Note: This will fail if basic_salary table doesn't exist
RENAME TABLE basic_salary TO salary_details;

-- Verify the table was renamed successfully
SHOW TABLES LIKE 'salary_details';

-- Show the structure of the renamed table
DESCRIBE salary_details;

-- Show sample data from the renamed table
SELECT * FROM salary_details LIMIT 5; 