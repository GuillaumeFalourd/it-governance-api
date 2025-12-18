# IT Governance API

A RESTful API built with Kotlin and Spring Boot for managing IT governance, including users, accesses, permissions, accounts, and names. The API integrates with Github, AWS, and company email systems.

## Features

- **MVC Architecture**: Clean separation of concerns with Controllers, Services, Entities, DTOs, and Repositories.
- **Full-Stack Web Application**: REST API with integrated web interface using Thymeleaf and Bootstrap.
- **CRUD Operations**: Complete Create, Read, Update, Delete for all entities with proper validation.
- **Advanced Relationships**: Many-to-many relationships between users, accounts, and permissions.
- **Database Integration**: H2 in-memory database with JPA/Hibernate ORM.
- **Data Validation**: Comprehensive input validation using Bean Validation.
- **Testing**: Extensive unit tests with JUnit 5, Mockito, and Spring Boot Test (17+ tests).
- **API Documentation**: Auto-generated OpenAPI/Swagger documentation with interactive UI.
- **Database Console**: Built-in H2 web console for database inspection.
- **Modern UI**: Responsive web interface with dynamic forms and multi-select capabilities.
- **Extensibility**: Modular design ready for future integrations and automations.

## Tech Stack

- **Language**: Kotlin 2.2.0
- **Framework**: Spring Boot 3.4.0
- **Database**: H2 (in-memory database for development and testing)
- **Build Tool**: Maven
- **Java Version**: 25
- **Testing**: JUnit 5, Mockito, Spring Boot Test
- **Documentation**: SpringDoc OpenAPI 2.6.0 (Swagger UI)
- **Web Framework**: Spring MVC with Thymeleaf templates

## Entities

- **User**: Manages user information with company email and GitHub account. Users can be associated with multiple accounts and permissions.
- **Account**: Represents cloud/service accounts with types (AWS, GITHUB, STACKSPOT) and identifiers.
- **Permission**: Defines permission types specific to each account. Permissions are linked to specific accounts rather than account types.

## Relationships

- **User ↔ Account**: Many-to-many relationship (users can access multiple accounts)
- **User ↔ Permission**: Many-to-many relationship (users can have multiple permissions)
- **Account → Permission**: One-to-many relationship (each permission belongs to one account)

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
- `POST /permissions` - Create a new permission (requires accountId)
- `PUT /permissions/{id}` - Update a permission
- `DELETE /permissions/{id}` - Delete a permission

## Setup and Installation

### Prerequisites
- Java 25 or higher
- Maven 3.6+ (or use the included Maven wrapper)

### Database Setup
The application uses H2 in-memory database by default, so no additional database setup is required. The database is automatically created and populated when the application starts.

For production deployment, you can configure PostgreSQL by updating the `application.yml` file.

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

3. Access the web interface:
   - **Dashboard**: `http://localhost:8080`
   - **API Documentation**: `http://localhost:8080/swagger-ui.html`
   - **H2 Database Console**: `http://localhost:8080/h2-console`
   - **OpenAPI JSON**: `http://localhost:8080/api-docs`

### Web Interface

The application includes a comprehensive web interface for managing IT governance:

- **Dashboard**: Overview of users, accounts, and permissions with statistics
- **Account Management**: Create and manage cloud/service accounts
- **Permission Management**: Define permissions linked to specific accounts
- **User Management**: Manage users with multi-select account and permission assignment

The web interface uses Thymeleaf templates with Bootstrap for responsive design.

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
    url: jdbc:h2:mem:testdb
    username: sa
    password:
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.H2Dialect
    defer-datasource-initialization: true
  h2:
    console:
      enabled: true
      path: /h2-console
      settings:
        web-allow-others: true
        web-admin-password: admin
  sql:
    init:
      mode: never
  web:
    resources:
      static-locations: classpath:/static/
  thymeleaf:
    cache: false

server:
  port: 8080
  error:
    whitelabel:
      enabled: false

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
    enabled: true
```

### Test Configuration
Test-specific configuration in `src/test/resources/application-test.yml` uses H2 database with test-specific settings.

## Project Structure

```
src/
├── main/
│   ├── kotlin/com/example/itgovernanceapi/
│   │   ├── controller/     # REST controllers and web controllers
│   │   ├── dto/           # Data Transfer Objects
│   │   ├── entity/        # JPA entities
│   │   ├── repository/    # Data repositories
│   │   ├── service/       # Business logic services
│   │   └── ItGovernanceApiApplication.kt
│   └── resources/
│       ├── static/        # Static web assets (CSS, JS, images)
│       ├── templates/     # Thymeleaf HTML templates
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
5. Create REST controller in `controller/` package
6. Create web controller methods for UI integration
7. Create Thymeleaf templates in `resources/templates/`
8. Add comprehensive unit tests

### Web Development
- Use Thymeleaf for server-side templating
- Bootstrap 5 for responsive UI components
- Font Awesome for icons
- JavaScript for dynamic form interactions (multi-select dropdowns)
- REST API integration for AJAX calls

### Code Style
- Follow Kotlin coding conventions
- Use Spring Boot best practices
- Include proper validation and error handling
- Add Swagger/OpenAPI documentation for all endpoints

## Future Enhancements

- Authentication/Authorization (OAuth2, JWT)
- Audit logging and change tracking
- Automation for onboarding/offboarding workflows
- Additional cloud integrations (Azure, GCP, etc.)
- Role-based access control and permission hierarchies
- API rate limiting and security hardening
- Caching layer (Redis) for performance
- Message queuing (RabbitMQ/Kafka) for async processing
- Database migration scripts for production deployment
- Containerization with Docker
- CI/CD pipeline configuration
