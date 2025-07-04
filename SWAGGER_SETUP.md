# Swagger API Documentation Setup

## ✅ What's Been Implemented

1. **Swagger/OpenAPI Dependencies** - Added to `pom.xml`
2. **Swagger Configuration** - Created `SwaggerConfig.java`
3. **API Annotations** - Enhanced all controllers with comprehensive documentation
4. **Interactive Documentation** - Available via Swagger UI

## 🚀 Accessing the API Documentation

### Swagger UI (Interactive)
- **URL:** `http://localhost:8089/swagger-ui.html`
- **Features:** 
  - Interactive API testing
  - Request/response examples
  - Schema documentation
  - Try-it-out functionality

### OpenAPI JSON
- **URL:** `http://localhost:8089/api-docs`
- **Use:** For programmatic access or importing into other tools

## 📋 Available API Endpoints

### Employee Management
- `GET /employees` - Get all employees
- `GET /employees/{id}` - Get employee by ID
- `POST /employees` - Create new employee
- `PUT /employees/{id}` - Update employee
- `DELETE /employees/{id}` - Delete employee

### User Management
- `POST /signup` - Register new user
- `POST /signup/personalDetails` - Add personal details
- `POST /login` - User authentication

### Admin Management
- `GET /admins` - Get all admins

### Salary Management
- `POST /signup/salaryDetails` - Add salary details
- `GET /salaries` - Get all salaries
- `GET /salaries/{employeeId}` - Get salary by employee ID

## 🔧 Configuration

The Swagger configuration is in `src/main/java/com/lnt/ems/api/config/SwaggerConfig.java`:

```java
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Management System API")
                        .description("RESTful API for managing employees, users, admins, and salary information")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("EMS Development Team")
                                .email("support@ems.com"))
                        .license(new License()
                                .name("MIT License")))
                .servers(List.of(
                        new Server().url("http://localhost:8089").description("Development Server"),
                        new Server().url("https://api.ems.com").description("Production Server")
                ));
    }
}
```

## 📖 Documentation Features

### Annotations Used
- `@Tag` - Groups endpoints by functionality
- `@Operation` - Describes what each endpoint does
- `@Parameter` - Documents request parameters
- `@ApiResponses` - Documents all possible responses
- `@Schema` - Describes data models

### Example Annotation
```java
@GetMapping("/employees")
@Operation(
    summary = "Get all employees",
    description = "Retrieves a list of all employees in the system"
)
@ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved employees",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Employee.class)
        )
    )
})
public ResponseEntity<List<Employee>> getEmployees() {
    // Implementation
}
```

## 🎯 Benefits

1. **Interactive Testing** - Test APIs directly from the browser
2. **Auto-generated Documentation** - Always up-to-date with code
3. **Client Code Generation** - Can generate client SDKs
4. **API Discovery** - Easy to understand available endpoints
5. **Request/Response Examples** - Clear examples for each endpoint

## 🔄 Maintenance

- Documentation automatically updates when you modify controllers
- Add new annotations to enhance documentation
- Update `SwaggerConfig.java` for global changes
- Modify `application.properties` for Swagger UI settings

## 📚 Additional Resources

- **Complete API Documentation:** `API_DOCUMENTATION.md`
- **Database Setup:** `README.md`
- **Docker Management:** `db-manager.sh`

## 🚀 Quick Start

1. **Start the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Open Swagger UI:**
   ```
   http://localhost:8089/swagger-ui.html
   ```

3. **Explore and test APIs!** 