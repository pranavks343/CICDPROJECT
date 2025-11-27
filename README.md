# Healthcare Records Management System

A complete full-stack application for managing healthcare records with CI/CD pipeline, containerization, and Kubernetes deployment. Features separate dashboards for Admin, Doctor, and Patient roles.

## 🚀 Tech Stack

### Backend
- Spring Boot 3.2.0
- Java 17
- PostgreSQL
- Maven
- Spring Data JPA
- Swagger/OpenAPI

### Frontend
- React 18
- Vite
- React Router
- Axios
- Context API

### DevOps & Infrastructure
- **Docker** - Containerization
- **Docker Compose** - Local development
- **Kubernetes** - Container orchestration
- **GitHub Actions** - CI/CD pipeline
- **Ansible** - Configuration management
- **Prometheus & Grafana** - Monitoring
- **Nginx** - Reverse proxy

## 📁 Project Structure

```
CICDENDSEMHACKATHON/
├── backend/                     # Spring Boot backend
│   ├── src/
│   │   └── main/
│   │       ├── java/com/healthrecords/
│   │       │   ├── entity/             # JPA entities
│   │       │   ├── repository/         # Data repositories
│   │       │   ├── service/            # Business logic
│   │       │   ├── controller/         # REST controllers
│   │       │   ├── dto/                # Data Transfer Objects
│   │       │   ├── exception/          # Exception handling
│   │       │   └── config/             # Configuration
│   │       └── resources/
│   │           └── application.properties
│   ├── Dockerfile                      # Backend Docker image
│   └── pom.xml
│
├── frontend/                    # React frontend
│   ├── src/
│   │   ├── api/                # API client
│   │   ├── context/            # Auth context
│   │   ├── components/         # Reusable components
│   │   ├── pages/              # Page components
│   │   ├── App.jsx
│   │   └── main.jsx
│   ├── Dockerfile              # Frontend Docker image
│   ├── nginx.conf              # Nginx configuration
│   ├── package.json
│   └── vite.config.js
│
├── k8s/                        # Kubernetes manifests
│   ├── namespace.yaml
│   ├── configmap.yaml
│   ├── secrets.yaml
│   ├── postgres-deployment.yaml
│   ├── backend-deployment.yaml
│   ├── frontend-deployment.yaml
│   └── ingress.yaml
│
├── ansible/                    # Ansible automation
│   ├── site.yml                # Main playbook
│   ├── deploy.yml              # Quick deployment
│   ├── rollback.yml            # Rollback playbook
│   ├── inventory.yml           # Inventory file
│   └── roles/                  # Ansible roles
│       ├── common/
│       ├── kubernetes/
│       └── monitoring/
│
├── .github/
│   └── workflows/
│       └── ci-cd.yml           # GitHub Actions pipeline
│
├── docker-compose.yml          # Local development
├── Makefile                    # Automation commands
├── DEPLOYMENT.md               # Deployment guide
├── CICD-SETUP.md              # CI/CD setup guide
└── README.md
```

## 📋 Prerequisites

### For Local Development
- Java 17 or higher
- Maven 3.6+
- Node.js 18+ and npm
- PostgreSQL 15+
- Docker 20.10+
- Docker Compose 2.0+

### For Full Deployment
- Kubernetes cluster (1.28+)
- kubectl
- Helm 3.x
- Ansible 2.14+
- Docker Hub account
- GitHub account

## 🚀 Quick Start

### Option 1: Docker Compose (Recommended for Local Development)

```bash
# Start all services
docker-compose up -d

# Access the application
# Frontend: http://localhost
# Backend: http://localhost:8080
# Database: localhost:5432

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

### Option 2: Traditional Setup

#### 1. Database Setup

Create PostgreSQL database:
```sql
CREATE DATABASE healthrecords;
CREATE USER healthuser WITH PASSWORD 'healthpass123';
GRANT ALL PRIVILEGES ON DATABASE healthrecords TO healthuser;
```

Update database credentials in `backend/src/main/resources/application.properties` if needed.

#### 2. Backend Setup

```bash
cd backend
mvn spring-boot:run
```

Backend will start on `http://localhost:8080`

#### 3. Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

Frontend will start on `http://localhost:3000`

### Option 3: Using Makefile

```bash
# Install dependencies
make local-dev

# Start backend
make dev-backend

# Start frontend (in another terminal)
make dev-frontend

# Run tests
make test

# Build everything
make build
```

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

## 🐳 Docker Deployment

### Build Docker Images

```bash
# Build both images
make docker-build DOCKER_USERNAME=your_username

# Or manually
docker build -t your_username/healthcare-backend:latest ./backend
docker build -t your_username/healthcare-frontend:latest ./frontend
```

### Push to Docker Hub

```bash
docker login
make docker-push DOCKER_USERNAME=your_username
```

## ☸️ Kubernetes Deployment

### Deploy to Kubernetes

```bash
# Deploy all resources
make k8s-deploy

# Check status
make k8s-status

# View logs
make k8s-logs-backend
make k8s-logs-frontend

# Access application
kubectl port-forward -n healthcare svc/frontend-service 8080:80
```

### Using Ansible

```bash
# Full deployment
make ansible-deploy

# Quick update
make ansible-quick-deploy

# Rollback
make ansible-rollback
```

For detailed deployment instructions, see [DEPLOYMENT.md](DEPLOYMENT.md)

## 🔄 CI/CD Pipeline

This project includes a complete GitHub Actions CI/CD pipeline that:

1. ✅ Tests backend and frontend code
2. 🐳 Builds Docker images
3. 📤 Pushes images to Docker Hub
4. ☸️ Deploys to Kubernetes
5. 🤖 Runs Ansible automation

### Setup CI/CD

1. Fork/clone this repository
2. Add GitHub Secrets:
   - `DOCKER_HUB_USERNAME`
   - `DOCKER_HUB_TOKEN`
   - `KUBE_CONFIG` (base64 encoded)
3. Push to `main` branch to trigger pipeline

For detailed CI/CD setup, see [CICD-SETUP.md](CICD-SETUP.md)

## 📊 Monitoring

### Setup Monitoring Stack

```bash
make monitor-setup
```

### Access Monitoring Tools

```bash
# Port forward Grafana
make monitor-port-forward

# Access Grafana at http://localhost:3000
# Username: admin
# Password: admin123
```

## 🛠️ Available Make Commands

```bash
make help                    # Show all available commands
make local-dev              # Setup local development
make test                   # Run all tests
make build                  # Build backend and frontend
make docker-build           # Build Docker images
make docker-push            # Push images to Docker Hub
make k8s-deploy             # Deploy to Kubernetes
make k8s-status             # Check deployment status
make ansible-deploy         # Deploy using Ansible
make monitor-setup          # Setup monitoring
make clean                  # Clean build files
```

## 🔧 Development

### Running Tests

```bash
# Backend tests
make test-backend

# Frontend tests
make test-frontend

# All tests
make test
```

### Building for Production

```bash
# Build backend
make build-backend

# Build frontend
make build-frontend

# Build both
make build
```

## 📚 Documentation

- [Deployment Guide](DEPLOYMENT.md) - Complete deployment instructions
- [CI/CD Setup Guide](CICD-SETUP.md) - CI/CD configuration and setup
- [Makefile](Makefile) - All available automation commands

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📝 License

This project is for educational purposes as part of a CI/CD demonstration.

# Trigger new pipeline with fixed workflow
