-- Migration Script: Remove salary_date column from salary_details table
-- Run this script to remove the date column

USE ems;

-- Remove the salary_date column from salary_details table
ALTER TABLE salary_details DROP COLUMN salary_date;

-- Verify the column was removed successfully
DESCRIBE salary_details;

-- Show sample data from the updated table
SELECT * FROM salary_details LIMIT 5; 