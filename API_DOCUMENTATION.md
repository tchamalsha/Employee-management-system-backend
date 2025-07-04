# Employee Management System API Documentation

## Overview

The Employee Management System (EMS) API provides RESTful endpoints for managing employees, users, admins, and salary information. The API is built with Spring Boot and uses MySQL as the database.

## Base URL

- **Development:** `http://localhost:8089`
- **Production:** `https://api.ems.com`

## Authentication

Currently, the API uses basic authentication. All endpoints require valid user credentials.

## API Endpoints

### 1. Employee Management

#### Get All Employees
```http
GET /employees
```

**Description:** Retrieves a list of all employees in the system.

**Response:**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@company.com",
    "position": "Software Engineer",
    "role": "EMPLOYEE"
  }
]
```

#### Get Employee by ID
```http
GET /employees/{id}
```

**Parameters:**
- `id` (path, required): Employee ID

**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@company.com",
  "position": "Software Engineer",
  "role": "EMPLOYEE"
}
```

#### Create Employee
```http
POST /employees
```

**Request Body:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@company.com",
  "password": "password123",
  "position": "Software Engineer",
  "role": "EMPLOYEE"
}
```

**Response:** `201 Created`

#### Update Employee
```http
PUT /employees/{id}
```

**Parameters:**
- `id` (path, required): Employee ID

**Request Body:**
```json
{
  "name": "John Doe Updated",
  "email": "john.updated@company.com",
  "position": "Senior Software Engineer",
  "role": "EMPLOYEE"
}
```

#### Delete Employee
```http
DELETE /employees/{id}
```

**Parameters:**
- `id` (path, required): Employee ID

**Response:** `204 No Content`

### 2. User Management

#### User Registration
```http
POST /signup
```

**Request Body:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@company.com",
  "password": "password123",
  "position": "Software Engineer",
  "role": "EMPLOYEE"
}
```

**Response:** `200 OK` with message "User registered successfully"

#### Add Personal Details
```http
POST /signup/personalDetails
```

**Request Body:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "dateOfBirth": "1990-01-01",
  "phoneNumber": "+1234567890",
  "address": "123 Main St, City, State 12345"
}
```

**Response:** `200 OK` with message "Personal details added successfully"

#### User Login
```http
POST /login
```

**Request Body:**
```json
{
  "id": 1,
  "password": "password123"
}
```

**Response:**
```json
true
```

### 3. Admin Management

#### Get All Admins
```http
GET /admins
```

**Description:** Retrieves a list of all admin users in the system.

**Response:**
```json
[
  {
    "id": 1,
    "name": "Admin User",
    "email": "admin@company.com",
    "position": "System Administrator",
    "role": "ADMIN"
  }
]
```

### 4. Salary Management

#### Add Salary Details
```http
POST /signup/salaryDetails
```

**Request Body:**
```json
{
  "id": 1,
  "basicSalary": 50000.00,
  "allowances": 5000.00,
  "bonus": 2000.00,
  "effectiveDate": "2024-01-01"
}
```

**Response:** `200 OK` with message "Salary details added successfully"

#### Get All Salaries
```http
GET /salaries
```

**Description:** Retrieves a list of all salary records in the system.

**Response:** `200 OK` with message "Salary list retrieved"

#### Get Salary by Employee ID
```http
GET /salaries/{employeeId}
```

**Parameters:**
- `employeeId` (path, required): Employee ID

**Response:**
```json
{
  "id": 1,
  "basicSalary": 50000.00,
  "allowances": 5000.00,
  "bonus": 2000.00,
  "effectiveDate": "2024-01-01"
}
```

## Data Models

### User
```json
{
  "id": "integer",
  "name": "string",
  "email": "string",
  "password": "string",
  "position": "string",
  "role": "string"
}
```

### Employee
```json
{
  "id": "integer",
  "name": "string",
  "email": "string",
  "password": "string",
  "position": "string",
  "role": "string"
}
```

### Admin
```json
{
  "id": "integer",
  "name": "string",
  "email": "string",
  "password": "string",
  "position": "string",
  "role": "string"
}
```

### PersonalDetails
```json
{
  "id": "integer",
  "firstName": "string",
  "lastName": "string",
  "dateOfBirth": "date",
  "phoneNumber": "string",
  "address": "string"
}
```

### BasicSalary
```json
{
  "id": "integer",
  "basicSalary": "decimal",
  "allowances": "decimal",
  "bonus": "decimal",
  "effectiveDate": "date"
}
```

## HTTP Status Codes

- `200 OK` - Request successful
- `201 Created` - Resource created successfully
- `204 No Content` - Request successful, no content to return
- `400 Bad Request` - Invalid request data
- `404 Not Found` - Resource not found
- `409 Conflict` - Resource already exists
- `500 Internal Server Error` - Server error

## Error Responses

When an error occurs, the API returns an appropriate HTTP status code with an error message:

```json
{
  "timestamp": "2024-01-01T12:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid request data",
  "path": "/api/employees"
}
```

## Rate Limiting

Currently, there are no rate limits implemented. This may be added in future versions.

## CORS

The API supports Cross-Origin Resource Sharing (CORS) for all origins.

## Swagger UI

Interactive API documentation is available at:
- **Development:** `http://localhost:8089/swagger-ui.html`
- **API Docs JSON:** `http://localhost:8089/api-docs`

## Examples

### Complete User Registration Flow

1. **Register User:**
```bash
curl -X POST http://localhost:8089/signup \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@company.com",
    "password": "password123",
    "position": "Software Engineer",
    "role": "EMPLOYEE"
  }'
```

2. **Add Personal Details:**
```bash
curl -X POST http://localhost:8089/signup/personalDetails \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "dateOfBirth": "1990-01-01",
    "phoneNumber": "+1234567890",
    "address": "123 Main St, City, State 12345"
  }'
```

3. **Add Salary Details:**
```bash
curl -X POST http://localhost:8089/signup/salaryDetails \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "basicSalary": 50000.00,
    "allowances": 5000.00,
    "bonus": 2000.00,
    "effectiveDate": "2024-01-01"
  }'
```

4. **Login:**
```bash
curl -X POST http://localhost:8089/login \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "password": "password123"
  }'
```

## Support

For API support, please contact:
- Email: support@ems.com
- Documentation: https://github.com/your-repo
- Issues: https://github.com/your-repo/issues 