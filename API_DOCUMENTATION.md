# Employee Management System API Documentation

## Authentication & Authorization
- **Roles:** `ADMIN`, `EMPLOYEE`
- **Authentication:** HTTP Basic Auth or session (Spring Security)

---

## User Endpoints

### Login
- **POST** `/login`
- **Description:** User login (admin or employee)
- **Request Body:**
```json
{
  "id": 1,
  "password": "string"
}
```
- **Response:**
```json
true | false
```
- **Access:** Public

### Password Reset
- **POST** `/password-reset`
- **Description:** Reset user password
- **Request Params:**
  - `email` (string)
  - `newPassword` (string)
- **Access:** Public

---

## Admin Endpoints

### Create Employee with Salary
- **POST** `/admin/employees`
- **Description:** Create a new employee with salary details
- **Request Body:**
```json
{
  "employee": {
    "id": 2,
    "name": "John Doe",
    "email": "john@example.com",
    "password": "string",
    "position": "Developer",
    "role": "EMPLOYEE"
  },
  "basicSalary": {
    "basicSalary": 50000,
    "otRate": 200.0,
    "specialAllowance": 5000
  }
}
```
- **Response:** Employee object
- **Access:** ADMIN only

### Update Salary Details
- **PUT** `/admin/employees/{id}/salary-details`
- **Description:** Update salary details for an employee
- **Request Body:**
```json
{
  "basicSalary": 55000,
  "otRate": 220.0,
  "specialAllowance": 6000
}
```
- **Response:** BasicSalary object
- **Access:** ADMIN only

### Calculate Salary
- **POST** `/admin/employees/{id}/calculate-salary`
- **Description:** Calculate and store salary for an employee for a given month. The calculation uses the employee's basic salary, OT rate, special allowance (fetched automatically), and the provided salary data (noPayDays, overTimeHours, attendanceBonus).
- **Request Body:**
```json
{
  "date": "2024-06-01",
  "noPayDays": 2,
  "overTimeHours": 10,
  "attendanceBonus": 1000
}
```
- **Response:**
```json
{
  "id": 2,
  "date": "2024-06-01",
  "salaryAmount": 60000.0
}
```
- **Access:** ADMIN only
- **Note:** The special allowance is retrieved automatically from the employee's salary details using the `getSpecialAllowance` method in the backend.

### Assign Role
- **PUT** `/admin/employees/{id}/role?role=ADMIN|EMPLOYEE`
- **Description:** Assign a role to a user
- **Response:** User object
- **Access:** ADMIN only

### Update Employee
- **PUT** `/admin/employees/{id}`
- **Description:** Update employee details
- **Request Body:** Employee object
- **Response:** Employee object
- **Access:** ADMIN only

### Remove Employee
- **DELETE** `/admin/employees/{id}`
- **Description:** Remove an employee
- **Access:** ADMIN only

### View All Employees
- **GET** `/employees`
- **Description:** Get a list of all employees
- **Response:** Array of Employee objects
- **Access:** ADMIN only

---

## Employee Endpoints

### View Personal Details
- **GET** `/employee/{id}/personal-details`
- **Description:** View personal details
- **Response:** PersonalDetails object
- **Access:** EMPLOYEE (self)

### Update Personal Details
- **PUT** `/employee/{id}/personal-details`
- **Description:** Update personal details
- **Request Body:** PersonalDetails object
- **Response:** PersonalDetails object
- **Access:** EMPLOYEE (self)

### View Salary Details
- **GET** `/employee/{id}/salary-details`
- **Description:** View salary details
- **Response:**
```json
1000.0
```
- **Access:** EMPLOYEE (self)

---

## Models

### User
```json
{
  "id": 1,
  "name": "string",
  "email": "string",
  "password": "string",
  "position": "string",
  "role": "ADMIN|EMPLOYEE"
}
```

### Employee
- Inherits from User

### BasicSalary
```json
{
  "id": 2,
  "basicSalary": 50000,
  "otRate": 200.0,
  "specialAllowance": 5000
}
```

### SalaryData
```json
{
  "id": 2,
  "date": "2024-06-01",
  "noPayDays": 2,
  "overTimeHours": 10,
  "attendanceBonus": 1000
}
```

### Salary
```json
{
  "id": 2,
  "date": "2024-06-01",
  "salaryAmount": 60000.0
}
```

### PersonalDetails
```json
{
  "id": 1,
  "telephone": 1234567890,
  "address": "string",
  "postalCode": 12345
}
```

---

## Notes
- All endpoints (except `/login` and `/password-reset`) require authentication.
- Only admins can create, update, or remove employees and assign roles.
- Employees can only view/update their own details and view their own salary.
- Use the correct role when authenticating to access protected endpoints.
- The backend automatically retrieves the special allowance for salary calculation using the employee's ID. 