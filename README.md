<div align="center">

# 🌸 Bloom API - Portfolio Demo

**Enterprise Spring Boot REST API | JWT Authentication | MongoDB | Clean Architecture**

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-7.0-green.svg)](https://www.mongodb.com/)
[![License](https://img.shields.io/badge/License-Portfolio-blue.svg)](LICENSE)

---

</div>

## 📋 Project Overview

A production-ready **Spring Boot REST API** demonstrating enterprise Java development with clean architecture, security best practices, and modern design patterns. This portfolio project showcases a booking system API with user management, venue operations, and offer handling.

### ✨ Highlighted Features

| Feature | Description |
|---------|-------------|
| 🔐 **JWT Authentication** | Secure token-based authentication with Spring Security & refresh tokens |
| 🏗️ **Clean Architecture** | Layered design (Controllers → Services → Repositories → MongoDB) |
| 🌍 **[Multi-Language Support](#translation-method)** | Built-in translation system for EN, AR, HE languages |
| 🔒 **[Ownership Validation](#ownership-validation)** | Generic `isOwner()` function ensures data integrity - users can only modify their own resources |
| 🛡️ **Global Exception Handling** | Centralized error management with standardized error responses |
| 📊 **MongoDB Integration** | Spring Data MongoDB with custom queries & soft deletes |
| ✅ **Input Validation** | 	Jakarta Bean Validation for request validation and a custom `@EnumTypeExists` validator |
| 📖 **API Documentation** | Swagger/OpenAPI integration for interactive API docs |
| 🔄 **Domain Models** | User, Venue, and Offer management with DTO patterns |
| 🔥 **Firebase Integration** | Firebase services integration for enhanced functionality |
| 🤖 **OpenAI Integration** | AI-powered chatbot that provides personalized suggestions based on user preferences |

---

## 🏗️ Architecture

```
┌─────────────────────────────────────┐
│         Controllers Layer           │  ← REST API endpoints
│  (Auth, User, Venue, Offer)         │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│         Service Layer               │  ← Business logic
│  (UserService, VenueService, etc.)  │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      Repository Layer               │  ← Data access
│  (UserRepository, VenueRepository)  │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│           MongoDB                   │  ← Database
└─────────────────────────────────────┘
```

## 📚 API Documentation

Once the application is running, access Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

## 🔐 Authentication

### Register a User

```bash
POST /api/v1/auth/register
Content-Type: application/json

{
  "firstname": "John",
  "lastname": "Doe",
  "email": "john.doe@example.com",
  "password": "SecurePass123!",
  "phone": {
    "phoneNumber": "+1234567890",
    "countryCode": "US"
  }
}
```

### Login

```bash
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "john.doe@example.com",
  "password": "SecurePass123!"
}
```

Response includes JWT token:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "BEARER",
  "user": { ... }
}
```

### Using the Token

Include the token in subsequent requests:
```bash
Authorization: Bearer <accessToken>
```

## 📋 API Endpoints

### Authentication
- `POST /api/v1/auth/register` - Register new user
- `POST /api/v1/auth/login` - Login user
- `POST /api/v1/auth/refresh-token` - Refresh access token

### Users (Protected)
- `GET /api/v1/users/me` - Get current user profile
- `PUT /api/v1/users/me` - Update current user profile

### Venues (Public/Protected)
- `GET /api/v1/venues` - List all venues (paginated, multi-language)
- `GET /api/v1/venues/{id}` - Get venue by ID
- `GET /api/v1/venues/my-venues` - Get current user's venues (Protected)
- `POST /api/v1/venues` - Create new venue (Protected)
- `PUT /api/v1/venues/{id}` - Update venue (Protected, owner only)
- `DELETE /api/v1/venues/{id}` - Delete venue (Protected, owner only)

### Offers (Public/Protected)
- `GET /api/v1/offers` - List all offers (paginated)
- `GET /api/v1/offers/{id}` - Get offer by ID
- `GET /api/v1/offers/venue/{venueId}` - Get offers by venue
- `POST /api/v1/offers` - Create new offer (Protected)
- `PUT /api/v1/offers/{id}` - Update offer (Protected)
- `DELETE /api/v1/offers/{id}` - Delete offer (Protected)

## 🏛️ Domain Model

This portfolio demonstrates core domain models following clean architecture principles. Additional models implementing the same patterns are available in the full codebase but are not included here to maintain focus on key architectural concepts.

### User
- Authentication (email/password, JWT tokens)
- Profile information (name, phone, address)
- Role-based access control (ADMIN, USER, OWNER, CUSTOMER)

### Venue
- Venue management with multi-language support
- Categorization by type, capacity, and pricing
- Offer association for promotions

### Offer
- Time-based promotions with discount percentages
- Date range validation and active status
- Linked to venues and users

## 🔒 Security Features

- **JWT Authentication**: Stateless token-based authentication
- **Password Encryption**: BCrypt password hashing
- **Role-Based Access Control**: ADMIN, USER roles
- **Method Security**: `@PreAuthorize` annotations
- **Ownership Validation** <a id="ownership-validation"></a>: Generic `isOwner()` function for data integrity
  - **Universal Support**: Works with any entity implementing the `Ownable` interface
  - **Method-Level Security**: Used in `@PreAuthorize` annotations to ensure users can only modify their own resources
  - **Automatic Validation**: Validates ownership before allowing update/delete operations
  - **Usage Example**: `@PreAuthorize("hasRole('ADMIN') or @ownableSecurity.isOwner(#id, 'venue')")` - **`@ownableSecurity.isOwner(#id, 'venue')`** is the key function
- **CORS Configuration**: Configurable CORS policies
- **Token Management**: Access and refresh token support

## 🛠️ Key Patterns & Practices

### Base Entity Pattern
All domain entities extend `BaseEntity` which provides:
- Unique ID generation
- Created/Updated timestamps
- Soft delete flag
- Audit trail (action history)
- Translation support

### DTO Pattern
Separate DTOs for:
- Request payloads (`UserDto`, `VenueDto`, `OfferDto`)
- Response payloads (`UserResponseDto`)
- Entity-DTO conversion methods

### Service Layer Pattern
Business logic encapsulated in service classes:
- Transaction management
- Validation
- Entity-DTO conversions
- Error handling

### Repository Pattern
Spring Data MongoDB repositories:
- Custom query methods
- Projections for optimized queries
- Soft delete filtering

### Translation Method
The project implements a **database-driven translation system** for multi-language support:

- **Translation Storage**: Translations are stored in MongoDB as nested maps: `Map<String, Map<String, String>>`
  - Outer key: Field name (e.g., "venueName", "description")
  - Inner key: Language code (e.g., "en", "ar", "he")
  - Value: Translated text

- **Inheritance Model**: All entities extend `BaseEntity` which extends `Translation`, providing translation support automatically

- **Language Resolution & Translation Process**: Language via `Accept-Language` header, validated by `LangList.resolveLanguage()` (supports "en-US", "ar-SA", "ar,en;q=0.9", defaults to "en"). Flow: Entity stores translations → Service converts to DTO → DTO's `translate()` method applies translations → Response returns localized content

- **DTO Translation Functions**: Each DTO has its own translation function (e.g., `translateVenue()`, `translateOffer()`) that handles field-specific translation logic and validation

- **Hierarchical Translation Pattern**: Parent models can call sub-model translation functions (e.g., `translateVenue()` calls `translateOffer()` for nested objects), enabling complete translation of complex nested structures

- **Supported Languages**: English (EN - default), Arabic (AR), Hebrew (HE). Extensible by extending `LANGUAGE_TYPE` enum

#### Benefits Over Column-Based Approach

- **No Schema Changes**: Add new languages without database migrations
- **Scalable**: Support unlimited languages without modifying schema
- **Flexible**: Easy to add/remove languages without impacting existing data
- **MongoDB-Optimized**: Leverages document structure for nested data
- **Maintainable**: All translations stored together, easier to manage

Example structure:
```json
{
  "venueName": "Grand Hotel",
  "description": "A luxurious hotel",
  "translations": {
    "venueName": {
      "en": "Grand Hotel",
      "ar": "فندق جراند",
      "he": "מלון גרנד"
    },
    "description": {
      "en": "A luxurious hotel",
      "ar": "فندق فاخر",
      "he": "מלון מפואר"
    }
  }
}
```

### Input Validation
Comprehensive input validation using Jakarta Bean Validation:
- **Standard Annotations**: `@NotNull`, `@NotBlank`, `@Size`, `@Min`, `@Max`, `@Email`, etc.
- **Custom Validators**: 
  - `@EnumTypeExists` - Generic validator that works with any enum type
    - Validates enum values with case-insensitive support
    - Supports both enum instances and string representations
    - Configurable case sensitivity via `ignoreCase` parameter
    - Can be used with any enum class (e.g., `VenueType`, `PeopleType`, `PriceType`, etc.)
- **Validation Groups**: Support for different validation contexts
- **Error Messages**: Customizable validation error messages

Example usage:
```java
@NotNull(message = "Venue type is required")
@EnumTypeExists(message = "Venue type is not valid", enumClass = VenueType.class)
private VenueType venueType;
```

## 🧪 Testing

Run tests:
```bash
mvn test
```

[⬆ Back to Top](#-table-of-contents)

## 📦 Technologies Used

- **Spring Boot 3.4.2** - Application framework
- **Spring Security** - Authentication & authorization
- **Spring Data MongoDB** - Database integration
- **JWT (JJWT)** - Token generation/validation
- **Firebase** - Firebase services integration
- **OpenAI** - AI-powered chatbot with personalized recommendations
- **Lombok** - Boilerplate reduction
- **Swagger/OpenAPI** - API documentation
- **Jakarta Bean Validation** - Input validation
- **Maven** - Dependency management

[⬆ Back to Top](#-table-of-contents)

## 🎓 Learning Highlights

This project demonstrates:

1. **Clean Code**: Well-structured, maintainable codebase
2. **Best Practices**: Following Spring Boot and Java conventions
3. **Security**: Production-ready authentication implementation
4. **Architecture**: Layered architecture with clear separation of concerns
5. **API Design**: RESTful principles and proper HTTP semantics
6. **Database Design**: MongoDB document modeling
7. **Error Handling**: Comprehensive error responses
8. **Documentation**: API documentation with Swagger

## 📝 Notes

- This is a simplified portfolio version for demonstration purposes
- Sensitive configurations and proprietary business logic have been removed
- The code focuses on showcasing architectural patterns and best practices
- Production deployments should include additional security measures (HTTPS, rate limiting, etc.)

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB (local or Atlas connection string)

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd bloom-portfolio
```

2. Configure MongoDB connection in `application.properties`:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/bloom-demo
```

3. Update JWT secret key (generate a new one for production):
```properties
application.security.jwt.secret-key=YOUR_SECRET_KEY_HERE
```

4. Build the project:
```bash
mvn clean install
```

5. Run the application:
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

## 📄 License

This project is for portfolio/demonstration purposes.

## 👤 Author

Created as a portfolio demonstration project showcasing enterprise Java development skills.

---

**Note**: This is a demo version. For production use, ensure proper security configurations, environment-specific settings, and comprehensive testing.
