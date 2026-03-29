# RoommateFinder - Backend

Intelligent Apartment & Roommate Matching Platform built with Spring Boot.

## Features

- ✅ User registration and authentication
- ✅ Lifestyle compatibility quiz
- ✅ Apartment and roommate listings
- ✅ Advanced search with filters
- ✅ Compatibility algorithm (0-100% scoring)
- ✅ Express interest system
- ✅ Email notifications
- ✅ Favorites management
- ✅ User dashboard

## Tech Stack

- **Framework:** Spring Boot 3.2.0
- **Language:** Java 17
- **Database:** MySQL 8.0
- **Build Tool:** Maven
- **Email:** Spring Mail (SMTP)

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.9+

## Setup Instructions

### 1. Clone the repository
```bash
git clone <repository-url>
cd roommate-finder-backend
```

### 2. Create MySQL database
```sql
CREATE DATABASE roommate_finder_db;
```

### 3. Configure application.yml

Update `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/roommate_finder_db
    username: root
    password: your_password
  
  mail:
    username: your-email@gmail.com
    password: your-app-password
```

**For Gmail:**
1. Enable 2-factor authentication
2. Generate app password: https://myaccount.google.com/apppasswords
3. Use app password in application.yml

### 4. Run the application
```bash
mvn spring-boot:run
```

Application will start on http://localhost:8080

### 5. Test the APIs

Use Postman or the provided `test-api.http` file to test endpoints.

## API Endpoints

### Authentication
- POST `/api/auth/register` - Register new user
- POST `/api/auth/login` - User login

### Users
- GET `/api/users/{id}` - Get user profile
- PUT `/api/users/{id}` - Update user profile

### Quiz
- POST `/api/quiz` - Submit quiz
- GET `/api/quiz/user/{userId}` - Get user's quiz
- PUT `/api/quiz/{id}` - Update quiz

### Apartments
- POST `/api/apartments` - Create listing
- GET `/api/apartments` - Search apartments
- GET `/api/apartments/{id}` - Get single listing
- PUT `/api/apartments/{id}` - Update listing
- DELETE `/api/apartments/{id}` - Delete listing

### Roommates
- POST `/api/roommates` - Create listing
- GET `/api/roommates` - Search roommates
- GET `/api/roommates/{id}` - Get single listing
- PUT `/api/roommates/{id}` - Update listing
- DELETE `/api/roommates/{id}` - Delete listing

### Compatibility
- GET `/api/compatibility?user1={id}&user2={id}` - Calculate compatibility

### Interest
- POST `/api/interest` - Express interest
- GET `/api/interest/received?userId={id}` - Get received interests
- GET `/api/interest/sent?userId={id}` - Get sent interests

### Favorites
- POST `/api/favorites` - Add favorite
- GET `/api/favorites/user/{userId}` - Get user's favorites
- DELETE `/api/favorites/{id}` - Remove favorite

### Dashboard
- GET `/api/dashboard/user/{userId}` - Get dashboard stats

## Project Structure
```
src/main/java/com/roommatefinder/
├── config/               # Configuration classes
├── controller/           # REST Controllers
├── dto/                  # Data Transfer Objects
│   ├── request/
│   └── response/
├── exception/            # Custom exceptions
├── model/                # Entity classes
│   └── enums/
├── repository/           # JPA Repositories
├── service/              # Business logic
└── util/                 # Utility classes
```

## Compatibility Algorithm

The platform uses a weighted scoring system:

- **Dealbreakers (30%):** Smoking, Pets, Drinking
- **Daily Habits (40%):** Sleep schedule, Cleanliness, Noise tolerance
- **Lifestyle (30%):** Social level, Guest frequency, Cooking habits

**Score Interpretation:**
- 85-100%: Excellent match
- 70-84%: Good match
- 50-69%: Moderate match
- 0-49%: Low compatibility

## Email Configuration

The application sends emails for:
- Welcome message on registration
- Interest notifications to listing owners
- Confirmation to users who expressed interest

## Future Enhancements

- JWT token-based authentication
- Password encryption (BCrypt)
- File upload for photos (AWS S3)
- Real-time messaging (WebSockets)
- Review & rating system
- Payment integration

## License

MIT License

## Contributors

Rohit Rohilla