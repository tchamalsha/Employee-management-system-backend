# 📋 Employee Management System - User Stories

## 🎯 **Overview**
This document contains comprehensive user stories for all features implemented in the Employee Management System (EMS) backend. Each story follows the format: **As a [user type], I want [feature] so that [benefit/value].**

---

## 🔐 **Authentication & Authorization**

### **Admin Authentication**
**As an** administrator  
**I want to** log into the system with my credentials  
**So that** I can access admin-only features and manage the system

**Acceptance Criteria:**
- Admin can login with ID and password
- System validates admin credentials against database
- Returns true/false based on authentication success
- Invalid credentials return appropriate error response

**API Endpoint:** `POST /admin/login`

---

### **User Authentication**
**As a** regular user/employee  
**I want to** log into the system with my credentials  
**So that** I can access my personal information and system features

**Acceptance Criteria:**
- User can login with ID and password
- System validates user credentials against database
- Returns true/false based on authentication success
- Invalid credentials return appropriate error response

**API Endpoint:** `POST /login`

---

## 👥 **User Management**

### **User Registration**
**As a** new user  
**I want to** register for an account in the system  
**So that** I can access the employee management system

**Acceptance Criteria:**
- User can provide basic information (ID, name, email, password, position)
- System validates all required fields
- User is assigned appropriate role (EMPLOYEE by default)
- Duplicate user IDs are rejected
- Success message is returned upon registration

**API Endpoint:** `POST /signup`

---

### **Personal Details Management**
**As a** user  
**I want to** add and manage my personal details  
**So that** the organization has my complete contact information

**Acceptance Criteria:**
- User can add personal details (address, phone number)
- System stores personal information separately from basic user data
- Personal details are linked to user ID
- Optional fields can be left empty
- Success message is returned upon addition

**API Endpoint:** `POST /signup/personalDetails`

---

## 👨‍💼 **Employee Management**

### **View All Employees**
**As an** administrator  
**I want to** view a list of all employees in the system  
**So that** I can get an overview of the workforce

**Acceptance Criteria:**
- System returns list of all employees
- Each employee record includes ID, name, email, position, role
- Empty list is returned if no employees exist
- Response includes proper HTTP status codes

**API Endpoint:** `GET /employees`

---

### **View Employee by ID**
**As an** administrator  
**I want to** view details of a specific employee by their ID  
**So that** I can access individual employee information

**Acceptance Criteria:**
- System accepts employee ID as parameter
- Returns complete employee details if found
- Returns 404 error if employee doesn't exist
- Response includes proper HTTP status codes

**API Endpoint:** `GET /employees/{id}`

---

### **Create Employee (Admin Only)**
**As an** administrator  
**I want to** create new employee accounts with complete information  
**So that** I can onboard new staff members with their salary and personal details

**Acceptance Criteria:**
- Only authenticated admins can create employees
- Admin credentials must be provided in request headers
- Employee data includes basic info, salary details, and personal details
- System validates all required fields
- Employee is automatically assigned "EMPLOYEE" role
- Salary and personal details are created automatically
- Returns 401 if admin authentication fails
- Returns 201 with employee details on success

**API Endpoint:** `POST /admin/register-employee`

**Required Headers:**
- `Admin-ID`: Admin's user ID
- `Admin-Password`: Admin's password

**Request Body:**
```json
{
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
}
```

---

### **Update Employee Information**
**As an** administrator  
**I want to** update existing employee information  
**So that** I can keep employee records current and accurate

**Acceptance Criteria:**
- System accepts employee ID and updated information
- Validates that employee exists before updating
- Updates employee details in database
- Returns updated employee information
- Returns 404 if employee doesn't exist

**API Endpoint:** `PUT /employees/{id}`

---

### **Delete Employee**
**As an** administrator  
**I want to** remove employees from the system  
**So that** I can manage staff departures and system cleanup

**Acceptance Criteria:**
- System accepts employee ID for deletion
- Validates that employee exists before deletion
- Removes employee from database
- Returns 204 No Content on successful deletion
- Returns 404 if employee doesn't exist

**API Endpoint:** `DELETE /employees/{id}`

---

## 💰 **Salary Management**

### **Add Salary Details**
**As a** user or administrator  
**I want to** add salary information for employees  
**So that** the organization can track compensation details

**Acceptance Criteria:**
- System accepts basic salary information
- Salary details are linked to employee ID
- Validates salary data (positive values)
- Success message is returned upon addition

**API Endpoint:** `POST /signup/salaryDetails`

---

### **View All Salaries**
**As an** administrator  
**I want to** view all salary records in the system  
**So that** I can get an overview of compensation across the organization

**Acceptance Criteria:**
- System returns list of all salary records
- Each record includes employee ID and salary details
- Empty list is returned if no salaries exist
- Response includes proper HTTP status codes

**API Endpoint:** `GET /salaries`

---

### **View Salary by Employee ID**
**As an** administrator or employee  
**I want to** view salary information for a specific employee  
**So that** I can access individual compensation details

**Acceptance Criteria:**
- System accepts employee ID as parameter
- Returns salary details if found
- Returns 404 error if salary record doesn't exist
- Response includes proper HTTP status codes

**API Endpoint:** `GET /salaries/{employeeId}`

---

## 👨‍💻 **Admin Management**

### **View All Admins**
**As an** administrator  
**I want to** view a list of all admin users in the system  
**So that** I can manage administrative access

**Acceptance Criteria:**
- System returns list of all admin users
- Each admin record includes ID, name, email, position, role
- Empty list is returned if no admins exist
- Response includes proper HTTP status codes

**API Endpoint:** `GET /admins`

---

### **Create Default Admin**
**As a** system administrator  
**I want to** create a default admin account for initial setup  
**So that** the system has an administrator to manage it

**Acceptance Criteria:**
- Creates admin with predefined credentials
- Admin ID: 1, Email: admin@ems.com, Password: admin123
- Role is set to "ADMIN"
- Only works if no admin with ID 1 exists
- Throws error if admin already exists

**API Endpoint:** `POST /admin/create-default`

---

## 📊 **Data Models & Relationships**

### **User Hierarchy**
**As a** system designer  
**I want to** implement a proper user hierarchy  
**So that** the system can distinguish between different user types

**Acceptance Criteria:**
- User is the base class with common fields
- Admin and Employee inherit from User
- Each user type has appropriate role assignment
- Single table inheritance is used for efficiency

**Data Structure:**
```
User (Base)
├── Admin (role: "ADMIN")
└── Employee (role: "EMPLOYEE")
```

---

### **Related Data Management**
**As a** system designer  
**I want to** manage related data efficiently  
**So that** employee information is properly organized

**Acceptance Criteria:**
- PersonalDetails linked to User by ID
- BasicSalary linked to User by ID
- SalaryData for additional salary information
- Proper foreign key relationships

**Data Relationships:**
```
User (1) ←→ (1) PersonalDetails
User (1) ←→ (1) BasicSalary
User (1) ←→ (1) SalaryData
```

---

## 🔧 **System Features**

### **API Documentation**
**As a** developer or API consumer  
**I want to** access comprehensive API documentation  
**So that** I can understand and use the system effectively

**Acceptance Criteria:**
- Swagger UI available at `/swagger-ui.html`
- OpenAPI JSON available at `/api-docs`
- All endpoints properly documented
- Request/response examples provided
- Interactive testing capability

**Access Points:**
- Swagger UI: `http://localhost:8089/swagger-ui.html`
- API Docs: `http://localhost:8089/api-docs`

---

### **Cross-Origin Resource Sharing (CORS)**
**As a** frontend developer  
**I want to** access the API from different origins  
**So that** I can build web applications that consume the API

**Acceptance Criteria:**
- CORS enabled for all endpoints
- All origins allowed for development
- Proper headers set for cross-origin requests

---

### **Database Integration**
**As a** system administrator  
**I want to** use MySQL database for data persistence  
**So that** the system can reliably store and retrieve information

**Acceptance Criteria:**
- MySQL 8.0 database connection
- Proper table creation and relationships
- Connection pooling with HikariCP
- Transaction management
- Data validation and constraints

---

## 🛡️ **Security Features**

### **Admin-Only Employee Registration**
**As a** system administrator  
**I want to** restrict employee registration to admins only  
**So that** only authorized personnel can add new employees

**Acceptance Criteria:**
- Employee registration requires admin authentication
- Admin credentials must be provided in request headers
- Unauthorized requests return 401 status
- Proper error messages for authentication failures

**Security Flow:**
1. Admin provides credentials in headers
2. System validates admin credentials
3. If valid, proceed with employee registration
4. If invalid, return 401 Unauthorized

---

### **Input Validation**
**As a** system administrator  
**I want to** validate all input data  
**So that** the system maintains data integrity and security

**Acceptance Criteria:**
- Email format validation
- Required field validation
- Positive number validation for salaries
- Proper error messages for invalid data
- SQL injection prevention

---

## 📈 **Business Value**

### **Operational Efficiency**
- **Reduced Manual Work:** Automated employee registration process
- **Data Accuracy:** Centralized employee information management
- **Quick Access:** Fast retrieval of employee and salary information
- **Audit Trail:** Complete record of all employee data

### **Security & Compliance**
- **Role-Based Access:** Proper separation of admin and employee functions
- **Data Protection:** Secure storage of sensitive employee information
- **Authentication:** Proper user verification before system access
- **Authorization:** Admin-only access to critical functions

### **Scalability**
- **Modular Design:** Easy to add new features and user types
- **Database Efficiency:** Optimized queries and relationships
- **API-First Approach:** Easy integration with frontend applications
- **Documentation:** Comprehensive API documentation for developers

---

## 🎯 **Success Metrics**

### **Functional Metrics**
- ✅ All user stories implemented and tested
- ✅ API endpoints return correct responses
- ✅ Database operations work correctly
- ✅ Authentication and authorization functioning
- ✅ Input validation working properly

### **Technical Metrics**
- ✅ Swagger documentation complete
- ✅ CORS configuration working
- ✅ Database relationships properly established
- ✅ Error handling implemented
- ✅ Security measures in place

### **User Experience Metrics**
- ✅ Intuitive API design
- ✅ Clear error messages
- ✅ Comprehensive documentation
- ✅ Interactive testing capability
- ✅ Fast response times

---

## 🚀 **Future Enhancements**

### **Planned Features**
1. **Password Hashing:** Implement BCrypt for secure password storage
2. **JWT Authentication:** Token-based authentication system
3. **Session Management:** Persistent user sessions
4. **Rate Limiting:** API rate limiting for security
5. **Audit Logging:** Complete audit trail of all actions
6. **Email Notifications:** Automated email notifications
7. **File Upload:** Profile picture and document upload
8. **Reporting:** Advanced reporting and analytics
9. **Mobile API:** Optimized endpoints for mobile applications
10. **Multi-tenancy:** Support for multiple organizations

This comprehensive set of user stories covers all the features currently implemented in the Employee Management System, providing a clear roadmap for understanding, testing, and enhancing the system. 