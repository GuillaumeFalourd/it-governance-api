# IT Governance API

A RESTful API built with Kotlin and Spring Boot for managing IT governance, including users, accesses, permissions, accounts, and names. The API integrates with Github, AWS, and company email systems.

## Features

- **MVC Architecture**: Clean separation of concerns with Controllers, Services, Entities, DTOs, and Repositories.
- **CRUD Operations**: Full Create, Read, Update, Delete for all entities.
- **Validation**: Input validation using Spring Boot Validation.
- **Database**: PostgreSQL with JPA/Hibernate.
- **Testing**: Comprehensive unit tests with JUnit 5, Mockito, and H2 in-memory database.
- **Documentation**: Auto-generated OpenAPI/Swagger documentation.
- **Extensibility**: Designed for future integrations and automations.

## Tech Stack

- **Language**: Kotlin 2.0.21
- **Framework**: Spring Boot 3.2.0
- **Database**: PostgreSQL (production), H2 (testing)
- **Build Tool**: Maven
- **Java Version**: 17
- **Testing**: JUnit 5, Mockito, Spring Boot Test

## Entities

- **User**: Manages user information across Github, AWS, and company email.
- **Account**: Represents accounts with types (AWS, GITHUB, STACKSPOT) and identifiers.
- **Permission**: Defines permission types specific to each account type (AWS permissions, GITHUB permissions, STACKSPOT permissions).

## API Endpoints

### User Management
- `GET /users` - Get all users
- `GET /users/{id}` - Get user by ID
- `POST /users` - Create a new user
- `PUT /users/{id}` - Update a user
- `DELETE /users/{id}` - Delete a user

### Account Management
- `GET /accounts` - Get all accounts
- `GET /accounts/{id}` - Get account by ID
- `POST /accounts` - Create a new account
- `PUT /accounts/{id}` - Update an account
- `DELETE /accounts/{id}` - Delete an account

### Permission Management
- `GET /permissions` - Get all permissions
- `GET /permissions/{id}` - Get permission by ID
- `GET /permissions/account-type/{accountType}` - Get permissions by account type (AWS, GITHUB, STACKSPOT)
- `POST /permissions` - Create a new permission
- `PUT /permissions/{id}` - Update a permission
- `DELETE /permissions/{id}` - Delete a permission

## Setup and Installation

### Prerequisites
- Java 17 or higher
- PostgreSQL database
- Maven 3.6+ (or use the included Maven wrapper)

### Database Setup
1. Install and start PostgreSQL
2. Create a database named `itgovernance`:
   ```sql
   CREATE DATABASE itgovernance;
   ```
3. Set environment variables for database credentials:
   ```bash
   export DB_USERNAME=your_db_username
   export DB_PASSWORD=your_db_password
   ```
   Or update `src/main/resources/application.yml` with your database credentials.

### Building the Application
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd it-governance-api
   ```

2. Build the application:
   ```bash
   mvn clean compile
   ```

### Running the Application
1. Start the application:
   ```bash
   mvn spring-boot:run
   ```

2. The application will start on `http://localhost:8080`

3. Access the API documentation:
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - OpenAPI JSON: `http://localhost:8080/api-docs`

### User Interface

![Dashboard](/img/dashboard.png)

![Accounts](/img/accounts.png)

![Permissions](/img/permissions.png)

![Users](/img/users.png)

## Testing

### Running Unit Tests
Execute all tests:
```bash
mvn test
```

The project includes comprehensive unit tests for all service classes:
- **UserServiceTest**: 1 test
- **AccountServiceTest**: 7 tests
- **PermissionServiceTest**: 8 tests

**Total**: 17 tests covering all CRUD operations and edge cases.

### Test Configuration
- Tests use H2 in-memory database for isolation
- Spring Boot Test framework with `@SpringBootTest`
- Mockito for mocking dependencies
- JUnit 5 for test execution

## Configuration

### Application Configuration
The main configuration is in `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/itgovernance
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:password}
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

server:
  port: 8080

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
```

### Test Configuration
Test-specific configuration in `src/test/resources/application-test.yml` uses H2 database.

## Project Structure

```
src/
├── main/
│   ├── kotlin/com/example/itgovernanceapi/
│   │   ├── controller/     # REST controllers
│   │   ├── dto/           # Data Transfer Objects
│   │   ├── entity/        # JPA entities
│   │   ├── repository/    # Data repositories
│   │   ├── service/       # Business logic services
│   │   └── ItGovernanceApiApplication.kt
│   └── resources/
│       └── application.yml
└── test/
    ├── kotlin/com/example/itgovernanceapi/
    │   └── service/       # Unit tests for services
    └── resources/
        └── application-test.yml
```

## Development

### Adding New Features
1. Create entity in `entity/` package
2. Create repository interface in `repository/` package
3. Create service class in `service/` package
4. Create DTOs in `dto/` package
5. Create controller in `controller/` package
6. Add comprehensive unit tests

### Code Style
- Follow Kotlin coding conventions
- Use Spring Boot best practices
- Include proper validation and error handling
- Add Swagger/OpenAPI documentation for all endpoints

## Future Enhancements

- Authentication/Authorization (OAuth2, JWT)
- Audit logging
- Automation for onboarding/offboarding
- Additional integrations (Azure, GCP, etc.)
- Role-based access control
- API rate limiting
- Caching layer (Redis)
- Message queuing (RabbitMQ/Kafka)
