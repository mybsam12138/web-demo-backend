# Web Demo Backend

A comprehensive Spring Boot backend application demonstrating OAuth authentication integration with multiple providers. This project showcases modern Java development practices with clean architecture, database migrations, and production-ready configurations.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![Redis](https://img.shields.io/badge/Redis-7.0+-red.svg)](https://redis.io/)

## 🚀 Live Demo

**Test the application:** [www.niudiantask.cn](https://www.niudiantask.cn)

## ✨ Features

- **Multi-Provider OAuth Authentication**: Support for Google, Twitter, GitHub, and Gitee
- **Session Management**: Secure token-based authentication using Sa-Token
- **User Management**: Complete user lifecycle with profile management
- **Database Migrations**: Automated schema management with Flyway
- **RESTful API**: Clean REST endpoints with proper error handling
- **API Documentation**: Interactive Swagger/OpenAPI documentation
- **Redis Integration**: High-performance session storage
- **Production Ready**: Configured for development and production environments

## 🛠 Tech Stack

### Core Framework
- **Java 21** - Modern Java runtime
- **Spring Boot 3.3.6** - Production-ready framework
- **Spring Web** - REST API development

### Database & ORM
- **MySQL 8.0+** - Primary database
- **MyBatis-Flex** - Type-safe SQL builder and ORM
- **Flyway** - Database migration tool

### Authentication & Security
- **Sa-Token** - Lightweight authentication framework
- **JustAuth** - OAuth integration library
- **Redis** - Session storage

### Development Tools
- **Lombok** - Code generation
- **SpringDoc OpenAPI** - API documentation
- **Maven** - Build automation

## 📋 Prerequisites

- **Java 21** or higher
- **MySQL 8.0+**
- **Redis 7.0+**
- **Maven 3.6+**

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd web-demo-backend
```

### 2. Database Setup

Create a MySQL database and update the connection settings in `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database_name
    username: your_username
    password: your_password
```

### 3. Redis Configuration

Ensure Redis is running and update the connection settings if needed:

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
```

### 4. OAuth Configuration

Configure OAuth providers in `application.yml`:

```yaml
oauth:
  providers:
    oauth_google:
      client-id: your_google_client_id
      client-secret: your_google_client_secret
      redirect-uri: http://your-domain.com/api/oauth/callback/OAUTH_GOOGLE
    # Configure other providers similarly
```

### 5. Build and Run

```bash
# Build the application
mvn clean compile

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 🔧 Configuration

### Application Profiles

- **dev**: Development environment with detailed logging
- **prod**: Production environment with optimized settings

Switch profiles using:
```bash
java -jar target/web-demo-backend-1.0.0.jar --spring.profiles.active=prod
```

### Key Configuration Files

- `application.yml` - Main configuration
- `application-dev.yml` - Development overrides
- `application-prod.yml` - Production overrides

## 📚 API Documentation

Once the application is running, access the Swagger UI at:
**http://localhost:8080/swagger-ui.html**

### Main Endpoints

#### OAuth Authentication
```
GET /oauth/callback/{provider} - OAuth callback handler
```

#### User Management
```
GET /user/info    - Get current user information
GET /user/logout  - Logout current user
```

## 🗄 Database Schema

### Users Table

```sql
CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    uuid VARCHAR(255) NOT NULL COMMENT 'OAuth provider user ID',
    provider VARCHAR(50) NOT NULL COMMENT 'OAuth provider',
    username VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) DEFAULT NULL,
    avatar VARCHAR(500) DEFAULT NULL,
    email VARCHAR(255) DEFAULT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_uuid_provider (uuid, provider),
    KEY idx_provider (provider)
);
```

## 🏗 Project Structure

```
src/main/java/com/demo/
├── controller/          # REST controllers
│   ├── OAuthController.java
│   └── UserController.java
├── service/            # Business logic
│   └── UserService.java
├── repository/         # Data access layer
│   └── UserRepository.java
├── entity/             # Domain entities
│   └── User.java
├── vo/                 # Value objects
│   ├── UserVo.java
│   ├── OAuthUserDto.java
│   └── OAuthCallbackResponse.java
├── config/             # Configuration classes
│   ├── GlobalExceptionHandler.java
│   ├── RedisStartupChecker.java
│   └── SessionConfig.java
├── exception/          # Custom exceptions
│   └── ServiceException.java
└── common/             # Shared utilities
    └── vo/
        └── ErrorResponse.java
```

## 🔐 Security Features

- **Token-based Authentication**: JWT-like tokens with configurable expiration
- **Session Management**: Redis-backed sessions with concurrent login control
- **OAuth Integration**: Secure third-party authentication
- **CORS Configuration**: Properly configured cross-origin resource sharing
- **Global Exception Handling**: Centralized error management

## 🧪 Development

### Running Tests

```bash
mvn test
```

### Building for Production

```bash
mvn clean package -Dspring.profiles.active=prod
```

### Code Style

This project uses:
- Google Java Style Guide
- Lombok for boilerplate reduction
- Spring Boot best practices

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For questions or support, please open an issue in the GitHub repository.

## 🔗 Related Projects

- [web-demo-frontend](https://github.com/your-org/web-demo-frontend) - Frontend application
- [justauth-oauth-spring-boot-starter](https://github.com/your-org/justauth-oauth-spring-boot-starter) - OAuth starter library


