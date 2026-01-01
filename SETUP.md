# Setup Guide

## Quick Start

### 1. Prerequisites
- Java 17 or higher
- Maven 3.6+
- MongoDB (local installation or Atlas account)

### 2. Configure MongoDB

**Option A: Local MongoDB**
- Install MongoDB locally
- Ensure MongoDB is running on default port 27017
- Update `application.properties` if needed:
  ```properties
  spring.data.mongodb.uri=mongodb://localhost:27017/bloom-demo
  ```

**Option B: MongoDB Atlas (Cloud)**
- Create a free cluster at https://www.mongodb.com/cloud/atlas
- Get your connection string
- Update `application.properties`:
  ```properties
  spring.data.mongodb.uri=mongodb+srv://username:password@cluster.mongodb.net/bloom-demo
  ```

### 3. Generate JWT Secret Key (Optional but Recommended)

For production use, generate a secure secret key:
```bash
# On Linux/Mac
openssl rand -base64 64

# On Windows (PowerShell)
[Convert]::ToBase64String((1..64 | ForEach-Object { Get-Random -Maximum 256 }))
```

Update `application.properties`:
```properties
application.security.jwt.secret-key=YOUR_GENERATED_KEY_HERE
```

### 4. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

### 5. Access API Documentation

Once running, visit:
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs (JSON): http://localhost:8080/v3/api-docs

## Testing the API

### 1. Register a User

```bash
POST http://localhost:8080/api/v1/auth/register
Content-Type: application/json

{
  "firstname": "John",
  "lastname": "Doe",
  "email": "john.doe@example.com",
  "password": "SecurePass123!",
  "phone": {
    "phoneNumber": "1234567890",
    "countryCode": "+1"
  }
}
```

### 2. Login

```bash
POST http://localhost:8080/api/v1/auth/login
Content-Type: application/json

{
  "username": "john.doe@example.com",
  "password": "SecurePass123!"
}
```

### 3. Access Protected Endpoints

Use the `access_token` from the login response:

```bash
GET http://localhost:8080/api/v1/users/me
Authorization: Bearer <your_access_token>
```

### 4. Create a Service (Admin only)

First, register/login as admin (or modify user role in database), then:

```bash
POST http://localhost:8080/api/v1/services
Authorization: Bearer <your_access_token>
Content-Type: application/json

{
  "serviceName": "Wi-Fi",
  "serviceType": "TECHNICAL",
  "icon": "wifi-icon"
}
```

## Project Structure

```
bloom-portfolio/
├── src/
│   ├── main/
│   │   ├── java/com/portfolio/bloom/
│   │   │   ├── common/          # Base classes (BaseEntity, Translation)
│   │   │   ├── config/          # Configuration (Security, JWT, Application)
│   │   │   ├── controller/      # REST Controllers
│   │   │   ├── domain/
│   │   │   │   ├── dto/         # Data Transfer Objects
│   │   │   │   ├── model/       # Domain entities
│   │   │   │   ├── repository/  # MongoDB repositories
│   │   │   │   └── service/     # Business logic services
│   │   │   ├── error/           # Exception handlers
│   │   │   └── security/        # Authentication service
│   │   └── resources/
│   │       └── application.properties
│   └── test/                    # Test classes
├── pom.xml
├── README.md
└── SETUP.md
```

## Key Features Demonstrated

- ✅ JWT Authentication
- ✅ Role-Based Access Control
- ✅ RESTful API Design
- ✅ MongoDB Integration
- ✅ Input Validation
- ✅ Error Handling
- ✅ Clean Architecture
- ✅ DTO Pattern
- ✅ Service Layer Pattern
- ✅ Repository Pattern
- ✅ Soft Deletes
- ✅ API Documentation (Swagger)

## Troubleshooting

### MongoDB Connection Issues
- Ensure MongoDB is running
- Check connection string format
- Verify network/firewall settings

### Port Already in Use
- Change port in `application.properties`: `server.port=8081`

### JWT Token Issues
- Ensure secret key is properly configured
- Check token expiration settings
- Verify token is included in Authorization header

## Next Steps

- Add unit tests
- Add integration tests
- Implement refresh token endpoint
- Add request/response logging
- Add rate limiting
- Add API versioning
- Configure CORS for frontend integration
