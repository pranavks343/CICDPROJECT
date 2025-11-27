# Health Records Management System

A complete full-stack college project for managing health records with separate dashboards for Admin, Doctor, and Patient roles.

## Tech Stack

### Backend
- Spring Boot 3.2.0
- Java 17
- MySQL
- Maven
- Spring Data JPA
- Swagger/OpenAPI

### Frontend
- React 18
- Vite
- React Router
- Axios
- Context API

## Project Structure

```
CICDENDSEMHACKATHON/
├── backend/                 # Spring Boot backend
│   ├── src/
│   │   └── main/
│   │       ├── java/com/healthrecords/
│   │       │   ├── entity/         # JPA entities
│   │       │   ├── repository/     # Data repositories
│   │       │   ├── service/        # Business logic
│   │       │   ├── controller/     # REST controllers
│   │       │   ├── dto/            # Data Transfer Objects
│   │       │   ├── exception/      # Exception handling
│   │       │   └── config/         # Configuration
│   │       └── resources/
│   │           └── application.properties
│   └── pom.xml
│
└── frontend/                # React frontend
    ├── src/
    │   ├── api/            # API client
    │   ├── context/        # Auth context
    │   ├── components/     # Reusable components
    │   ├── pages/          # Page components
    │   ├── App.jsx
    │   └── main.jsx
    ├── package.json
    └── vite.config.js
```

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Node.js 16+ and npm
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Setup Instructions

### 1. Database Setup

1. Create MySQL database:
```sql
CREATE DATABASE health_records_db;
```

2. Update database credentials in `backend/src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=your_password
```

### 2. Backend Setup

1. Navigate to backend directory:
```bash
cd backend
```

2. Run the Spring Boot application:
```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8081`

3. Access Swagger UI at:
```
http://localhost:8081/swagger-ui/index.html
```

### 3. Frontend Setup

1. Navigate to frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

The frontend will start on `http://localhost:3001`

## Default Login Credentials

The system automatically creates default users on first startup:

### Admin
- **Email:** admin@health.com
- **Password:** admin123
- **Role:** ADMIN

### Doctor
- **Email:** doctor@health.com
- **Password:** doctor123
- **Role:** DOCTOR

### Patient
- **Email:** patient@health.com
- **Password:** patient123
- **Role:** PATIENT

## Features

### Admin Dashboard
- View system statistics (total users, doctors, patients, visits)
- Manage doctors (create, view, delete)
- Manage patients (create, view, delete)
- View recent doctors and patients

### Doctor Dashboard
- View all visits assigned to the doctor
- Create new visit records
- View visit details
- Add patient information, diagnosis, prescriptions, and notes

### Patient Dashboard
- View personal profile information
- View all visit history
- View visit details including diagnosis and prescriptions

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login

### User Management (Admin)
- `POST /api/users` - Create user (doctor/patient)
- `GET /api/users` - Get all users
- `GET /api/users?role=DOCTOR` - Get users by role
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Visits
- `POST /api/visits` - Create visit
- `GET /api/visits/{id}` - Get visit by ID
- `GET /api/visits/patient/{patientId}` - Get visits by patient
- `GET /api/visits/doctor/{doctorId}` - Get visits by doctor
- `GET /api/visits` - Get all visits
- `DELETE /api/visits/{id}` - Delete visit

### Admin Stats
- `GET /api/admin/stats` - Get system statistics

## Ports

- **Backend:** 8081
- **Frontend:** 3001

## Notes

- Passwords are stored in plain text for simplicity (not recommended for production)
- CORS is configured to allow requests from `http://localhost:3001`
- Database schema is auto-generated using Hibernate (`spring.jpa.hibernate.ddl-auto=update`)
- Default users are created automatically on application startup

## Troubleshooting

### Backend Issues
- Ensure MySQL is running and database exists
- Check database credentials in `application.properties`
- Verify Java 17 is installed: `java -version`
- Check port 8081 is not in use

### Frontend Issues
- Clear node_modules and reinstall: `rm -rf node_modules && npm install`
- Ensure backend is running before starting frontend
- Check browser console for CORS errors

## Development

### Running Backend Tests
```bash
cd backend
mvn test
```

### Building Frontend for Production
```bash
cd frontend
npm run build
```

## License

This is a college project for educational purposes.

