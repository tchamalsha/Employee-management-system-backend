# Employee Management System Backend

A Spring Boot-based REST API for managing employees, salaries, and administrative functions in an Employee Management System.

## 🚀 Features

- **Employee Management**: Register and manage employee information
- **Admin Management**: Admin registration and authentication
- **Salary Management**: Calculate and manage employee salaries
- **Personal Details**: Store and manage employee personal information
- **PDF Generation**: Generate salary reports in PDF format
- **RESTful API**: Complete REST API with proper HTTP methods
- **Database Integration**: MySQL database with JPA/Hibernate
- **Testing**: Comprehensive unit and integration tests

## 📋 Prerequisites

Before running this application, make sure you have the following installed:

- **Java 17** or higher
- **Maven 3.6** or higher
- **MySQL 8.0** or higher
- **Git** (for cloning the repository)

## 🛠️ Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd Employee-management-system-backend
```

### 2. Database Setup

1. **Start MySQL Server**
   ```bash
   # On macOS/Linux
   sudo service mysql start
   # or
   brew services start mysql
   
   # On Windows
   net start mysql
   ```

2. **Create Database**
   ```sql
   mysql -u root -p
   CREATE DATABASE ems;
   ```

3. **Configure Database Connection**
   
   Update `src/main/resources/application.properties` with your MySQL credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ems
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### 3. Build the Project

```bash
# Clean and compile
./mvnw clean compile

# Or using Maven directly
mvn clean compile
```

### 4. Run the Application

```bash
# Using Maven wrapper
./mvnw spring-boot:run

# Or using Maven directly
mvn spring-boot:run
```

The application will start on `http://localhost:8089`

## 🧪 Running Tests

### Prerequisites for Testing

The project uses H2 in-memory database for testing, so no additional database setup is required for running tests.

### Test Commands

1. **Run All Tests**
   ```bash
   ./mvnw test
   ```

2. **Run Specific Test Class**
   ```bash
   ./mvnw test -Dtest=SalaryServiceImplTest
   ```

3. **Run Tests with Coverage Report**
   ```bash
   ./mvnw test jacoco:report
   ```

4. **Run Integration Tests Only**
   ```bash
   ./mvnw test -Dtest="*IntegrationTest"
   ```

5. **Run Unit Tests Only**
   ```bash
   ./mvnw test -Dtest="*Test" -Dtest="!*IntegrationTest"
   ```

6. **Run Tests with Detailed Output**
   ```bash
   ./mvnw test -Dspring.profiles.active=test
   ```

### Test Configuration

The test configuration is defined in `src/test/resources/application-test.properties`:

```properties
# H2 In-memory database for testing
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Disable security for testing
security.basic.enable=false
```

## 📚 API Documentation

### Base URL
```
http://localhost:8089
```

### Key Endpoints

#### Authentication
- `POST /signup/admin` - Register admin
- `POST /signup/employee` - Register employee
- `POST /login` - Login (admin/employee)

#### Employee Management
- `GET /employees` - Get all employees
- `POST /signup/user/personalDetails` - Add personal details

#### Salary Management
- `POST /signup/user/salaryDetails` - Add salary details
- `POST /signup/user/salaryData` - Add salary data
- `POST /user/calculate-salary` - Calculate salary
- `POST /user/salary` - Get salary for specific date
- `POST /user/salary/all` - Get all salaries for employee

#### PDF Generation
- `GET /pdf/salary-report/{month}` - Generate salary report
- `GET /pdf/employee-salary-report/{employeeId}/{month}` - Generate employee-specific report

For detailed API documentation, see [API_DOCUMENTATION.md](API_DOCUMENTATION.md)

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/lnt/ems/api/
│   │   ├── controller/          # REST controllers
│   │   ├── model/              # Entity classes
│   │   │   └── dto/            # Data Transfer Objects
│   │   ├── repository/         # Data access layer
│   │   └── service/            # Business logic layer
│   └── resources/
│       └── application.properties
└── test/
    ├── java/com/lnt/ems/api/
    │   ├── controller/         # Controller tests
    │   ├── service/            # Service tests
    │   └── integration/        # Integration tests
    └── resources/
        └── application-test.properties
```

## 🔧 Configuration

### Application Properties

Key configuration options in `application.properties`:

```properties
# Server Configuration
server.port=8089

# Database Configuration
spring.jpa.hibernate.ddl-auto=create
spring.datasource.url=jdbc:mysql://localhost:3306/ems
spring.datasource.username=root
spring.datasource.password=root

# JPA Configuration
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create

# Security (disabled for development)
security.basic.enable=false
```

## 🚀 Deployment

### Production Build

```bash
# Create JAR file
./mvnw clean package

# Run JAR file
java -jar target/api-0.0.1-SNAPSHOT.jar
```

### Docker Deployment (Optional)

```bash
# Build Docker image
docker build -t ems-backend .

# Run Docker container
docker run -p 8089:8089 ems-backend
```

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Ensure MySQL is running
   - Verify database credentials in `application.properties`
   - Check if database `ems` exists

2. **Port Already in Use**
   - Change port in `application.properties`
   - Kill process using port 8089

3. **Test Failures**
   - Ensure H2 dependency is included
   - Check test configuration in `application-test.properties`

4. **Build Failures**
   - Ensure Java 17 is installed and set as JAVA_HOME
   - Run `./mvnw clean` before building

### Logs

Check application logs for detailed error information:
```bash
# View logs in real-time
tail -f logs/application.log
```

## 📝 Additional Documentation

- [API Documentation](API_DOCUMENTATION.md) - Detailed API endpoints and usage
- [Testing Guide](TESTING_GUIDE.md) - Comprehensive testing documentation
- [Report](report.md) - Project report and analysis

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Run all tests to ensure they pass
6. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the documentation files

---

**Note**: This is a development version. For production deployment, ensure proper security configurations and environment-specific settings. 