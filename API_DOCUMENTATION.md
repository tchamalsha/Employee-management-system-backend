# Employee Management System API Documentation

## Base URL
```
http://localhost:8089
```

## Overview
This API provides endpoints for managing employees, admins, salaries, and personal details in an Employee Management System.

---

## Authentication Endpoints

### 1. Admin Registration
**POST** `/signup/admin`

Register a new admin user.

**Request Body:**
```json
{
  "id": 101,
  "name": "Admin User",
  "email": "admin@company.com",
  "password": "admin123",
  "position": "System Administrator"
}
```

**Response:** `200 OK` (No content)

---

### 2. Employee Registration
**POST** `/signup/employee`

Register a new employee.

**Request Body:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@company.com",
  "password": "password123",
  "position": "Software Developer",
  "adminId": 101
}
```

**Response:** `200 OK` (No content)

---

### 3. Admin Login
**GET** `/login/admin`

Authenticate an admin user.

**Request Body:**
```json
{
  "id": 101,
  "password": "admin123"
}
```

**Response:** `200 OK`
```json
true
```

---

### 4. Employee Login
**GET** `/login/employee`

Authenticate an employee user.

**Request Body:**
```json
{
  "id": 1,
  "password": "password123"
}
```

**Response:** `200 OK`
```json
true
```

---

## Personal Details Management

### 5. Add Personal Details
**POST** `/signup/personalDetails`

Add personal details for a user.

**Request Body:**
```json
{
  "id": 1,
  "telephone": 1234567890,
  "address": "123 Main Street, City, State",
  "postalCode": 12345
}
```

**Response:** `200 OK` (No content)

---

## Salary Management

### 6. Add Salary Details
**POST** `/signup/user/salaryDetails`

Add basic salary information for an employee.

**Request Body:**
```json
{
  "id": 1,
  "basicSalary": 50000,
  "otRate": 25.0,
  "specialAllowance": 2000
}
```

**Response:** `200 OK` (No content)

---

### 7. Add Salary Data
**POST** `/signup/user/salaryData`

Add monthly salary data (attendance, overtime, etc.).

**Request Body:**
```json
{
  "id": 1,
  "date": "2024-07",
  "noPayDays": 2.0,
  "overTimeHours": 10.5,
  "attendanceBonus": 500
}
```

**Response:** `200 OK` (No content)

---

### 8. Calculate and Save Salary
**POST** `/user/calculate-salary`

Calculate salary for an employee and save it to the database.

**Request Body:**
```json
{
  "id": 1,
  "date": "2024-07"
}
```

**Response:** `200 OK`
```json
52500.0
```

**Calculation Formula:**
```
Total Salary = (Basic Salary - (Basic Salary/25 × No Pay Days)) + Attendance Bonus + (OT Rate × Overtime Hours)
```

---

## Data Retrieval Endpoints

### 9. Get All Admins
**GET** `/admins`

Retrieve all admin users.

**Response:** `200 OK`
```json
[
  {
    "id": 101,
    "name": "Admin User",
    "email": "admin@company.com",
    "password": "admin123",
    "position": "System Administrator"
  }
]
```

---

### 10. Get All Employees
**GET** `/employees`

Retrieve all employees.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@company.com",
    "password": "password123",
    "position": "Software Developer",
    "adminId": 101
  }
]
```

---

## Data Models

### Admin Model
```json
{
  "id": "Integer (Primary Key)",
  "name": "String",
  "email": "String",
  "password": "String",
  "position": "String"
}
```

### Employee Model
```json
{
  "id": "Integer (Primary Key)",
  "name": "String",
  "email": "String",
  "password": "String",
  "position": "String",
  "adminId": "Integer (Foreign Key to Admin)"
}
```

### Personal Details Model
```json
{
  "id": "Integer (Primary Key)",
  "telephone": "Long",
  "address": "String",
  "postalCode": "Integer"
}
```

### Salary Details Model
```json
{
  "id": "Integer (Primary Key)",
  "basicSalary": "Integer",
  "otRate": "Float",
  "specialAllowance": "Integer"
}
```

### Salary Data Model
```json
{
  "id": "Integer (Primary Key)",
  "date": "String (YYYY-MM format)",
  "noPayDays": "Float",
  "overTimeHours": "Float",
  "attendanceBonus": "Integer"
}
```

### Salary Model
```json
{
  "id": "Integer (Primary Key)",
  "date": "String (YYYY-MM format)",
  "salaryAmount": "Float"
}
```

---

## Database Schema

### Tables
1. **admin** - Stores admin user information
2. **employee** - Stores employee information with admin_id reference
3. **personal_details** - Stores personal contact information
4. **salary_details** - Stores basic salary configuration
5. **salary_data** - Stores monthly attendance and overtime data
6. **salary** - Stores calculated monthly salaries

### Key Relationships
- `employee.admin_id` → `admin.id` (Many-to-One)
- `salary_details.id` → `employee.id` (One-to-One)
- `salary_data.id` → `employee.id` (One-to-Many)
- `salary.id` → `employee.id` (One-to-Many)

---

## Error Responses

### Common HTTP Status Codes
- `200 OK` - Request successful
- `400 Bad Request` - Invalid request data
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

### Error Response Format
```json
{
  "timestamp": "2024-07-10T16:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid input data"
}
```

---

## Usage Examples

### Complete Employee Registration Flow
1. **Register Admin:**
   ```bash
   POST /signup/admin
   {
     "id": 101,
     "name": "HR Manager",
     "email": "hr@company.com",
     "password": "hr123",
     "position": "HR Manager"
   }
   ```

2. **Register Employee:**
   ```bash
   POST /signup/employee
   {
     "id": 1,
     "name": "John Doe",
     "email": "john@company.com",
     "password": "john123",
     "position": "Developer",
     "adminId": 101
   }
   ```

3. **Add Personal Details:**
   ```bash
   POST /signup/personalDetails
   {
     "id": 1,
     "telephone": 1234567890,
     "address": "123 Main St",
     "postalCode": 12345
   }
   ```

4. **Add Salary Details:**
   ```bash
   POST /signup/user/salaryDetails
   {
     "id": 1,
     "basicSalary": 50000,
     "otRate": 25.0,
     "specialAllowance": 2000
   }
   ```

5. **Add Monthly Data:**
   ```bash
   POST /signup/user/salaryData
   {
     "id": 1,
     "date": "2024-07",
     "noPayDays": 2.0,
     "overTimeHours": 10.5,
     "attendanceBonus": 500
   }
   ```

6. **Calculate Salary:**
   ```bash
   POST /user/calculate-salary
   {
     "id": 1,
     "date": "2024-07"
   }
   ```

---

## Notes

- All dates in salary-related endpoints use `YYYY-MM` format
- Employee registration requires a valid `adminId`
- Salary calculation automatically saves results to the salary table
- The system uses MySQL database with automatic table creation
- CORS is enabled for cross-origin requests
- All endpoints return appropriate HTTP status codes

---

## Testing

You can test the API using tools like:
- Postman
- cURL
- Insomnia
- Any HTTP client

Make sure the application is running on `http://localhost:8089` before testing the endpoints. 