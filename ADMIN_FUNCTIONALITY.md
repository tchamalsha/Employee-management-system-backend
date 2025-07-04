# Admin Functionality Documentation

## Overview

The Employee Management System now includes secure admin authentication and employee registration functionality. Only authenticated admins can register new employees with their salary details.

## Admin Authentication

### Default Admin Credentials
- **ID:** 1
- **Email:** admin@ems.com
- **Password:** admin123
- **Role:** ADMIN

### Admin Login Endpoint

```http
POST /admin/login
```

**Request Body:**
```json
{
  "id": 1,
  "password": "admin123"
}
```

**Response:**
```json
true
```

**Example using curl:**
```bash
curl -X POST http://localhost:8089/admin/login \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "password": "admin123"
  }'
```

## Employee Registration (Admin Only)

### Register New Employee Endpoint

```http
POST /admin/register-employee
```

**Request Body:**
```json
{
  "id": 3,
  "name": "Jane Smith",
  "email": "jane.smith@company.com",
  "password": "password123",
  "position": "Senior Developer",
  "basicSalary": 65000.00,
  "otRate": 1.5,
  "specialAllowance": 8000.00,
  "firstName": "Jane",
  "lastName": "Smith",
  "phoneNumber": "+1234567890",
  "address": "456 Oak Street, City, State 12345"
}
```

**Response (201 Created):**
```json
{
  "id": 3,
  "name": "Jane Smith",
  "email": "jane.smith@company.com",
  "password": "password123",
  "position": "Senior Developer",
  "role": "EMPLOYEE"
}
```

**Example using curl:**
```bash
curl -X POST http://localhost:8089/admin/register-employee \
  -H "Content-Type: application/json" \
  -d '{
    "id": 3,
    "name": "Jane Smith",
    "email": "jane.smith@company.com",
    "password": "password123",
    "position": "Senior Developer",
    "basicSalary": 65000.00,
    "otRate": 1.5,
    "specialAllowance": 8000.00,
    "phoneNumber": "+1234567890",
    "address": "456 Oak Street, City, State 12345"
  }'
```

## Data Validation

### Required Fields
- `id` - Employee ID (must be unique)
- `name` - Employee full name
- `email` - Valid email format
- `password` - Employee password
- `position` - Job position/title
- `basicSalary` - Basic salary amount (positive number)
- `otRate` - Overtime rate (positive number)
- `specialAllowance` - Special allowance amount (positive number)

### Optional Fields
- `firstName` - First name
- `lastName` - Last name
- `phoneNumber` - Contact phone number
- `address` - Residential address

## Business Rules

1. **Admin Authentication Required:** Only authenticated admins can register employees
2. **Unique Employee IDs:** Each employee must have a unique ID
3. **Salary Validation:** All salary components must be positive numbers
4. **Email Validation:** Email addresses must be in valid format
5. **Automatic Role Assignment:** All registered employees are automatically assigned the "EMPLOYEE" role

## Database Schema

### Users Table (Inheritance)
- `id` - Primary key
- `name` - Employee/Admin name
- `email` - Email address
- `password` - Password
- `position` - Job position
- `role` - User role (ADMIN/EMPLOYEE)
- `dtype` - Discriminator for inheritance (Admin/Employee)

### Basic Salary Table
- `id` - Foreign key to users table
- `basic_salary` - Basic salary amount
- `ot_rate` - Overtime rate multiplier
- `special_allowance` - Special allowance amount

### Personal Details Table
- `id` - Foreign key to users table
- `telephone` - Phone number
- `address` - Residential address
- `postal_code` - Postal code

## Error Handling

### Common Error Responses

**400 Bad Request - Invalid Data:**
```json
{
  "timestamp": "2025-07-04T14:58:30.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid employee data",
  "path": "/admin/register-employee"
}
```

**401 Unauthorized - Admin Not Authenticated:**
```json
{
  "timestamp": "2025-07-04T14:58:30.000+00:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Admin authentication required",
  "path": "/admin/register-employee"
}
```

**409 Conflict - Employee Already Exists:**
```json
{
  "timestamp": "2025-07-04T14:58:30.000+00:00",
  "status": 409,
  "error": "Conflict",
  "message": "Employee with ID already exists",
  "path": "/admin/register-employee"
}
```

## Security Considerations

1. **Password Storage:** Passwords are currently stored in plain text (should be hashed in production)
2. **Authentication:** Basic authentication is used (should implement JWT tokens in production)
3. **Authorization:** Role-based access control is implemented
4. **Input Validation:** All inputs are validated using Bean Validation annotations

## Testing the Functionality

### Step 1: Admin Login
```bash
curl -X POST http://localhost:8089/admin/login \
  -H "Content-Type: application/json" \
  -d '{"id": 1, "password": "admin123"}'
```

### Step 2: Register Employee
```bash
curl -X POST http://localhost:8089/admin/register-employee \
  -H "Content-Type: application/json" \
  -d '{
    "id": 3,
    "name": "Test Employee",
    "email": "test@company.com",
    "password": "password123",
    "position": "Developer",
    "basicSalary": 50000.00,
    "otRate": 1.5,
    "specialAllowance": 5000.00,
    "phoneNumber": "+1234567890",
    "address": "123 Test Street"
  }'
```

### Step 3: Verify Employee Registration
```bash
curl -X GET http://localhost:8089/employees
```

## Swagger Documentation

The complete API documentation is available at:
- **Swagger UI:** `http://localhost:8089/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8089/api-docs`

Navigate to the "Admin Management" section to see all available admin endpoints. 