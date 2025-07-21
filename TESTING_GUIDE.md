# Employee Management System - Testing Guide

## Overview

This guide explains the testing strategy implemented for the Employee Management System, including unit tests, integration tests, and how to run them.

## Testing Strategy

### 1. Unit Tests
Unit tests focus on testing individual components in isolation using mocks for dependencies.

**Location**: `src/test/java/com/lnt/ems/api/service/`
**Examples**: 
- `SalaryServiceImplTest.java`
- `EmployeeServiceImplTest.java`

**Key Features**:
- Uses Mockito for mocking dependencies
- Tests individual methods in isolation
- Fast execution
- High code coverage

### 2. Integration Tests
Integration tests test the interaction between components and the complete flow.

**Location**: `src/test/java/com/lnt/ems/api/controller/` and `src/test/java/com/lnt/ems/api/integration/`
**Examples**:
- `SalaryControllerIntegrationTest.java` (Controller layer with mocked services)
- `SalaryIntegrationTest.java` (Full integration with database)

**Key Features**:
- Tests complete API endpoints
- Uses H2 in-memory database for testing
- Tests real business logic flows
- Validates data persistence

## Test Configuration

### Test Properties
File: `src/test/resources/application-test.properties`

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

### Test Utilities
File: `src/test/java/com/lnt/ems/api/util/TestDataBuilder.java`

Provides reusable test data creation methods for consistent test data across all test classes.

## Running Tests

### 1. Run All Tests
```bash
./mvnw test
```

### 2. Run Specific Test Class
```bash
./mvnw test -Dtest=SalaryServiceImplTest
```

### 3. Run Tests with Coverage
```bash
./mvnw test jacoco:report
```

### 4. Run Integration Tests Only
```bash
./mvnw test -Dtest="*IntegrationTest"
```

### 5. Run Unit Tests Only
```bash
./mvnw test -Dtest="*Test" -Dtest="!*IntegrationTest"
```

## Test Categories

### Service Layer Tests
**Purpose**: Test business logic in isolation
**Coverage**:
- Salary calculation logic
- Data validation
- Business rules
- Error handling

**Example Test**:
```java
@Test
void testCalculateSalary() {
    // Given
    when(salaryDetailsRepository.getBasicSalary(101)).thenReturn(50000);
    when(salaryDetailsRepository.getOtRate(101)).thenReturn(500.0f);
    when(salaryDataRepository.getSalaryData(101, "2024-01")).thenReturn(salaryData);

    // When
    Float result = salaryService.calculateSalary(101, "2024-01");

    // Then
    assertEquals(51500.0f, result, 0.01f);
}
```

### Controller Layer Tests
**Purpose**: Test API endpoints with mocked services
**Coverage**:
- HTTP request/response handling
- JSON serialization/deserialization
- Status codes
- Response content

**Example Test**:
```java
@Test
void testGetSalary() throws Exception {
    // Given
    when(salaryService.getSalary(101, "2024-01")).thenReturn(52000.0f);

    // When & Then
    mockMvc.perform(post("/user/salary")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":101,\"date\":\"2024-01\"}"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Salary for ID 101")));
}
```

### Full Integration Tests
**Purpose**: Test complete application flow
**Coverage**:
- End-to-end API calls
- Database operations
- Complete business processes
- Data persistence validation

**Example Test**:
```java
@Test
void testCompleteSalaryFlow() throws Exception {
    // Step 1: Add salary details
    mockMvc.perform(post("/signup/user/salaryDetails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(salaryDetailsJson))
            .andExpect(status().isOk());

    // Step 2: Add salary data
    mockMvc.perform(post("/signup/user/salaryData")
            .contentType(MediaType.APPLICATION_JSON)
            .content(salaryDataJson))
            .andExpect(status().isOk());

    // Step 3: Calculate salary
    mockMvc.perform(post("/user/calculate-salary")
            .contentType(MediaType.APPLICATION_JSON)
            .content(calculateRequestJson))
            .andExpect(status().isOk());

    // Verify data persistence
    Salary savedSalary = salaryRepository.findById(salaryId).orElse(null);
    assertNotNull(savedSalary);
}
```

## Test Data Management

### Using TestDataBuilder
```java
// Create standard test data
SalaryDetails salaryDetails = TestDataBuilder.createSalaryDetails();

// Create test data with specific ID
Employee employee = TestDataBuilder.createEmployeeWithId(201);

// Create test data with specific parameters
SalaryData salaryData = TestDataBuilder.createSalaryDataWithIdAndDate(201, "2024-02");
```

### Test Data Cleanup
- Integration tests use `@Transactional` annotation
- Tests automatically rollback after completion
- No manual cleanup required
- Each test starts with clean database state

## Best Practices

### 1. Test Naming
Use descriptive test names that explain the scenario:
```java
@Test
void testCalculateSalary_WithOvertimeAndAttendanceBonus_ReturnsCorrectAmount()
```

### 2. Arrange-Act-Assert Pattern
Structure tests with clear sections:
```java
@Test
void testMethod() {
    // Arrange (Given)
    when(mock.method()).thenReturn(value);

    // Act (When)
    Result result = service.method();

    // Assert (Then)
    assertEquals(expected, result);
}
```

### 3. Test Isolation
- Each test should be independent
- Use `@BeforeEach` for common setup
- Avoid test dependencies

### 4. Mock Verification
Verify that mocks are called correctly:
```java
verify(salaryRepository, times(1)).save(any(Salary.class));
```

## Coverage Goals

### Target Coverage
- **Unit Tests**: 90%+ line coverage
- **Integration Tests**: 80%+ API endpoint coverage
- **Business Logic**: 100% critical path coverage

### Areas of Focus
1. **Salary Calculation Logic**: Critical business logic
2. **Data Validation**: Input validation and error handling
3. **API Endpoints**: All REST endpoints
4. **Database Operations**: CRUD operations
5. **Error Scenarios**: Edge cases and error conditions

## Continuous Integration

### GitHub Actions (Recommended)
Create `.github/workflows/test.yml`:
```yaml
name: Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          java-version: '17'
      - name: Run tests
        run: ./mvnw test
      - name: Generate coverage report
        run: ./mvnw test jacoco:report
```

## Troubleshooting

### Common Issues

1. **Test Database Connection**
   - Ensure H2 dependency is included
   - Check test properties configuration
   - Verify `@ActiveProfiles("test")` annotation

2. **Mock Setup Issues**
   - Verify mock annotations (`@Mock`, `@InjectMocks`)
   - Check mock behavior setup in `@BeforeEach`
   - Ensure proper argument matching

3. **Transaction Issues**
   - Use `@Transactional` for integration tests
   - Ensure proper test isolation
   - Check for transaction rollback

### Debugging Tests
1. Add logging to test methods
2. Use debugger breakpoints
3. Check test output for detailed error messages
4. Verify test data setup

## Performance Testing (Future Enhancement)

### Load Testing
Consider adding performance tests using:
- **JMeter**: For API load testing
- **Gatling**: For Scala-based performance testing
- **Spring Boot Test**: For application performance testing

### Example Performance Test Structure
```java
@Test
void testSalaryCalculationPerformance() {
    // Test salary calculation with large dataset
    // Measure response times
    // Validate performance requirements
}
```

## Conclusion

This testing strategy ensures:
- **Reliability**: Comprehensive test coverage
- **Maintainability**: Well-structured, readable tests
- **Confidence**: Automated validation of functionality
- **Quality**: Early detection of issues

Follow this guide to maintain high code quality and ensure the system works correctly across all scenarios. 