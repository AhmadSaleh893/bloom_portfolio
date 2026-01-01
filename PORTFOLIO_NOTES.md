# Portfolio Project Notes

## Project Overview

This is a simplified, portfolio-ready version of the Bloom booking/reservation API system. The code has been cleaned, sanitized, and simplified to showcase key architectural patterns and best practices while removing proprietary business logic and sensitive information.

## What's Included

### Core Features
- ✅ **JWT Authentication** - Complete authentication flow with access/refresh tokens
- ✅ **User Management** - Registration, login, profile management
- ✅ **Service Management** - CRUD operations for services catalog
- ✅ **Role-Based Access Control** - ADMIN and USER roles
- ✅ **MongoDB Integration** - Spring Data MongoDB with custom queries
- ✅ **RESTful API** - Clean REST endpoints with proper HTTP semantics
- ✅ **Input Validation** - Jakarta Bean Validation
- ✅ **Error Handling** - Global exception handler
- ✅ **API Documentation** - Swagger/OpenAPI integration
- ✅ **Soft Deletes** - Deleted flag instead of hard deletes
- ✅ **Base Entity Pattern** - Common fields for all entities

### Architecture Patterns Demonstrated

1. **Clean Architecture**
   - Clear separation: Controllers → Services → Repositories
   - Domain-driven design
   - Dependency injection

2. **Design Patterns**
   - Repository Pattern
   - Service Layer Pattern
   - DTO Pattern
   - Factory Pattern (for entity conversions)

3. **Security**
   - JWT token-based authentication
   - Spring Security integration
   - Password encryption (BCrypt)
   - Method-level security

4. **Best Practices**
   - Proper error handling
   - Input validation
   - Transaction management
   - Code documentation
   - Consistent naming conventions

## What's Been Removed/Simplified

For portfolio purposes, the following have been removed or simplified:

- ❌ Complex business logic (reservations, payments, etc.)
- ❌ Third-party integrations (AWS S3, Firebase, OpenAI, etc.)
- ❌ Complex domain models (venues, packages, reviews, etc.)
- ❌ Advanced features (LLM chat, notifications, etc.)
- ❌ Proprietary business rules
- ❌ Sensitive configuration (API keys, database credentials)
- ❌ Migration scripts
- ❌ Custom validators (kept simple)

## Key Files to Review

### Architecture
- `config/SecurityConfiguration.java` - Security setup
- `config/JwtService.java` - JWT token handling
- `common/BaseEntity.java` - Base entity pattern

### Domain Models
- `domain/model/user/User.java` - User entity with UserDetails
- `domain/model/service/Service.java` - Service entity

### Services
- `domain/service/UserServiceImpl.java` - User business logic
- `domain/service/ServiceImpl.java` - Service business logic
- `security/AuthenticationService.java` - Authentication logic

### Controllers
- `controller/AuthenticationController.java` - Auth endpoints
- `controller/UserController.java` - User endpoints
- `controller/ServiceController.java` - Service endpoints

## Customization for Your Portfolio

1. **Update README.md** - Add your personal information and project details
2. **Update package names** - Change `com.portfolio.bloom` to your preferred package
3. **Add your GitHub link** - Update repository URL in documentation
4. **Add tests** - Consider adding unit/integration tests
5. **Add more features** - Expand with additional endpoints if desired
6. **Update dependencies** - Keep dependencies up to date

## Next Steps for Publishing

1. ✅ Code is ready for GitHub
2. ✅ Sensitive data removed
3. ✅ Documentation complete
4. ⬜ Add comprehensive tests (optional but recommended)
5. ⬜ Add CI/CD pipeline (optional)
6. ⬜ Create demo environment (optional)

## Notes for Interviews

This project demonstrates:
- **Spring Boot expertise** - Modern Spring Boot 3.x with Java 17
- **Security knowledge** - JWT, Spring Security, password encryption
- **Database skills** - MongoDB, Spring Data, custom queries
- **API design** - RESTful principles, proper HTTP status codes
- **Code quality** - Clean code, proper patterns, documentation
- **Architecture** - Layered architecture, separation of concerns

Be prepared to discuss:
- Why you chose MongoDB
- JWT token management approach
- Security considerations
- How you would scale this system
- Testing strategies
- Deployment considerations
