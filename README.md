# 📚 BookVerse

BookVerse is a Spring Boot RESTful API for managing books, authors, reviews, and book metadata. It demonstrates advanced Spring Boot concepts like JPA relationships, Flyway migrations, Docker deployment, integration with external APIs, and CI/CD with Jenkins.

## 🚀 How to Run

### 1. Prerequisites

- Java 17
- Maven
- Docker + Docker Compose

### 2. Start MySQL with Docker Compose
docker-compose up -d
### 3. Run application

-- bash

./mvnw spring-boot:run

--or build and run as a docker container

./mvnw clean package
docker build -t bookverse .
docker run -p 8080:8080 bookverse

### 4. API Docs
   Once running, visit:

http://localhost:8080/api/books
🧪 Testing
Run tests with:

./mvnw test
### 5. ⚙️ Project Features
JPA: One-to-One, One-to-Many, Many-to-One

Flyway: Version-controlled migrations

External API integration (e.g., Open Library)

Dockerized app and database

CI/CD with Jenkins

### 6. 📦 Deployment
Use Jenkins to build and deploy via Jenkinsfile.








