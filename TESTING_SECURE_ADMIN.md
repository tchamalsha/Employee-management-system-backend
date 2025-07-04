# 🧪 Testing Guide for Secure Admin Implementation

## 🎯 **Overview**
This guide provides step-by-step testing instructions for the new secure admin implementation with separate tables and admin tracking.

---

## 🚀 **Prerequisites**

### **1. Database Setup**
```bash
# Run the migration script
mysql -u root -p ems_db < database_migration.sql
```

### **2. Application Startup**
```bash
# Start the application
./mvnw spring-boot:run
```

### **3. Verify Default Admin**
The migration script creates a default admin:
- **ID:** 1
- **Email:** admin@ems.com
- **Password:** admin123

---

## 🧪 **Test Cases**

### **Test Case 1: Admin Authentication**

#### **1.1 Valid Admin Login**
```bash
curl -X POST "http://localhost:8089/admin/login" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "password": "admin123"
  }'
```

**Expected Response:**
```json
true
```

#### **1.2 Invalid Admin Login**
```bash
curl -X POST "http://localhost:8089/admin/login" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "password": "wrongpassword"
  }'
```

**Expected Response:**
```json
false
```

---

### **Test Case 2: Employee Registration with Admin Tracking**

#### **2.1 Register Employee (Admin ID 1)**
```bash
curl -X POST "http://localhost:8089/admin/1/register-employee" \
  -H "Content-Type: application/json" \
  -H "Admin-Password: admin123" \
  -d '{
    "id": 1001,
    "name": "John Doe",
    "email": "john.doe@company.com",
    "password": "password123",
    "position": "Software Engineer",
    "basicSalary": 50000.0,
    "otRate": 25.0,
    "specialAllowance": 5000,
    "address": "123 Main St, City",
    "phoneNumber": "+1-555-0123"
  }'
```

**Expected Response (201 Created):**
```json
{
  "id": 1001,
  "name": "John Doe",
  "email": "john.doe@company.com",
  "password": "password123",
  "position": "Software Engineer",
  "role": "EMPLOYEE",
  "adminId": 1
}
```

#### **2.2 Register Another Employee (Admin ID 1)**
```bash
curl -X POST "http://localhost:8089/admin/1/register-employee" \
  -H "Content-Type: application/json" \
  -H "Admin-Password: admin123" \
  -d '{
    "id": 1002,
    "name": "Jane Smith",
    "email": "jane.smith@company.com",
    "password": "password456",
    "position": "Senior Developer",
    "basicSalary": 65000.0,
    "otRate": 30.0,
    "specialAllowance": 8000,
    "address": "456 Oak St, City",
    "phoneNumber": "+1-555-0456"
  }'
```

**Expected Response (201 Created):**
```json
{
  "id": 1002,
  "name": "Jane Smith",
  "email": "jane.smith@company.com",
  "password": "password456",
  "position": "Senior Developer",
  "role": "EMPLOYEE",
  "adminId": 1
}
```

---

### **Test Case 3: Admin-Specific Employee Queries**

#### **3.1 Get Employees Created by Admin ID 1**
```bash
curl -X GET "http://localhost:8089/admin/1/employees" \
  -H "Admin-Password: admin123"
```

**Expected Response (200 OK):**
```json
[
  {
    "id": 1001,
    "name": "John Doe",
    "email": "john.doe@company.com",
    "position": "Software Engineer",
    "role": "EMPLOYEE",
    "adminId": 1
  },
  {
    "id": 1002,
    "name": "Jane Smith",
    "email": "jane.smith@company.com",
    "position": "Senior Developer",
    "role": "EMPLOYEE",
    "adminId": 1
  }
]
```

---

### **Test Case 4: Security Tests**

#### **4.1 Unauthorized Access (Wrong Password)**
```bash
curl -X POST "http://localhost:8089/admin/1/register-employee" \
  -H "Content-Type: application/json" \
  -H "Admin-Password: wrongpassword" \
  -d '{
    "id": 1003,
    "name": "Bob Wilson",
    "email": "bob.wilson@company.com",
    "password": "password789",
    "position": "QA Engineer",
    "basicSalary": 45000.0,
    "otRate": 20.0,
    "specialAllowance": 3000,
    "address": "789 Pine St, City",
    "phoneNumber": "+1-555-0789"
  }'
```

**Expected Response (401 Unauthorized):**
```json
"Unauthorized: Admin authentication required"
```

#### **4.2 Missing Password Header**
```bash
curl -X POST "http://localhost:8089/admin/1/register-employee" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1003,
    "name": "Bob Wilson",
    "email": "bob.wilson@company.com",
    "password": "password789",
    "position": "QA Engineer",
    "basicSalary": 45000.0,
    "otRate": 20.0,
    "specialAllowance": 3000,
    "address": "789 Pine St, City",
    "phoneNumber": "+1-555-0789"
  }'
```

**Expected Response (400 Bad Request):**
```json
"Required request header 'Admin-Password' for method parameter of type String is not present"
```

#### **4.3 Non-existent Admin**
```bash
curl -X POST "http://localhost:8089/admin/999/register-employee" \
  -H "Content-Type: application/json" \
  -H "Admin-Password: admin123" \
  -d '{
    "id": 1003,
    "name": "Bob Wilson",
    "email": "bob.wilson@company.com",
    "password": "password789",
    "position": "QA Engineer",
    "basicSalary": 45000.0,
    "otRate": 20.0,
    "specialAllowance": 3000,
    "address": "789 Pine St, City",
    "phoneNumber": "+1-555-0789"
  }'
```

**Expected Response (401 Unauthorized):**
```json
"Unauthorized: Admin authentication required"
```

---

### **Test Case 5: Multiple Admins**

#### **5.1 Create Second Admin**
```bash
curl -X POST "http://localhost:8089/admin/create-default" \
  -H "Content-Type: application/json"
```

**Note:** You'll need to modify the createDefaultAdmin method to create admin ID 2, or manually insert:
```sql
INSERT INTO admins (id, name, email, password, position, role)
VALUES (2, 'HR Manager', 'hr@ems.com', 'hr123', 'HR Manager', 'ADMIN');
```

#### **5.2 Register Employee with Admin ID 2**
```bash
curl -X POST "http://localhost:8089/admin/2/register-employee" \
  -H "Content-Type: application/json" \
  -H "Admin-Password: hr123" \
  -d '{
    "id": 1003,
    "name": "Bob Wilson",
    "email": "bob.wilson@company.com",
    "password": "password789",
    "position": "QA Engineer",
    "basicSalary": 45000.0,
    "otRate": 20.0,
    "specialAllowance": 3000,
    "address": "789 Pine St, City",
    "phoneNumber": "+1-555-0789"
  }'
```

**Expected Response (201 Created):**
```json
{
  "id": 1003,
  "name": "Bob Wilson",
  "email": "bob.wilson@company.com",
  "password": "password789",
  "position": "QA Engineer",
  "role": "EMPLOYEE",
  "adminId": 2
}
```

#### **5.3 Verify Admin Isolation**
```bash
# Get employees for Admin 1
curl -X GET "http://localhost:8089/admin/1/employees" \
  -H "Admin-Password: admin123"

# Get employees for Admin 2
curl -X GET "http://localhost:8089/admin/2/employees" \
  -H "Admin-Password: hr123"
```

**Expected:** Each admin should only see their own employees.

---

### **Test Case 6: Database Verification**

#### **6.1 Check Admin Table**
```sql
SELECT * FROM admins;
```

**Expected Output:**
```
+----+---------------------+----------------+----------+---------------------+-------+
| id | name                | email          | password | position            | role  |
+----+---------------------+----------------+----------+---------------------+-------+
|  1 | System Administrator| admin@ems.com  | admin123 | System Administrator| ADMIN |
|  2 | HR Manager          | hr@ems.com     | hr123    | HR Manager          | ADMIN |
+----+---------------------+----------------+----------+---------------------+-------+
```

#### **6.2 Check Employee Table with Admin Tracking**
```sql
SELECT e.id, e.name, e.email, e.position, e.admin_id, a.name as created_by
FROM employees e
LEFT JOIN admins a ON e.admin_id = a.id;
```

**Expected Output:**
```
+------+------------+------------------------+---------------------+----------+---------------------+
| id   | name       | email                 | position            | admin_id | created_by          |
+------+------------+------------------------+---------------------+----------+---------------------+
| 1001 | John Doe   | john.doe@company.com  | Software Engineer   |        1 | System Administrator|
| 1002 | Jane Smith | jane.smith@company.com| Senior Developer    |        1 | System Administrator|
| 1003 | Bob Wilson | bob.wilson@company.com| QA Engineer         |        2 | HR Manager          |
+------+------------+------------------------+---------------------+----------+---------------------+
```

---

## ✅ **Test Results Checklist**

### **Authentication Tests**
- [ ] Valid admin credentials work
- [ ] Invalid admin credentials return 401
- [ ] Non-existent admin returns 401
- [ ] Missing password header returns 400

### **Employee Registration Tests**
- [ ] Valid employee data creates employee
- [ ] Admin ID is properly stored in employee record
- [ ] Duplicate employee ID returns 409
- [ ] Invalid data returns 400

### **Admin Tracking Tests**
- [ ] Admin can see only their own employees
- [ ] Employee records show correct admin ID
- [ ] Foreign key constraints work properly
- [ ] Database queries return correct data

### **Security Tests**
- [ ] URL-based admin authentication works
- [ ] Admin isolation is enforced
- [ ] Unauthorized access is blocked
- [ ] Data integrity is maintained

---

## 🚨 **Troubleshooting**

### **Common Issues**

#### **1. Migration Errors**
```bash
# If migration fails, check database connection
mysql -u root -p -e "USE ems_db; SHOW TABLES;"
```

#### **2. Application Startup Issues**
```bash
# Check if port 8089 is available
lsof -i :8089
# Kill process if needed
kill -9 <PID>
```

#### **3. Database Connection Issues**
```bash
# Verify database is running
docker ps | grep mysql
# Check application logs
tail -f logs/application.log
```

#### **4. Foreign Key Constraint Errors**
```sql
-- Check if admin exists before creating employee
SELECT * FROM admins WHERE id = 1;
```

---

## 🎯 **Success Criteria**

All tests should pass with:
- ✅ **Authentication working** - admins can login
- ✅ **Employee registration working** - employees created with admin tracking
- ✅ **Admin isolation working** - admins see only their employees
- ✅ **Security enforced** - unauthorized access blocked
- ✅ **Data integrity** - foreign key constraints working
- ✅ **API responses correct** - proper HTTP status codes and JSON responses

This testing guide ensures the secure admin implementation is working correctly with proper admin tracking and security measures. 