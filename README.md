# 📚 BookVerse

BookVerse is a Spring Boot RESTful API for managing books, authors, reviews, and book metadata. It demonstrates advanced Spring Boot concepts like JPA relationships, Flyway migrations, Docker deployment, integration with external APIs, and CI/CD with Jenkins.
## Project Theme Recap
We’re building a Bookverse API to manage:

📚 Books

✍️ Authors

🧑‍💻 Users

💬 Reviews

🌐 External API call to fetch e.g., book cover info or ISBN metadata

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

🔁 Relationships we'll demonstrate:
Relationship Type	Example
One-to-Many	Author → Books
Many-to-One	Book → Author
One-to-One	Book → CoverMetadata (external API info)
Many-to-Many (optional)	Book ↔️ User (e.g., favorites or purchases)
One-to-Many	Book → Reviews, User → Reviews


Recommended Order for Clean Domain-Driven Development
Entities + Relationships (Core data model)

Repositories (CRUD interfaces for entities)

DTOs + Mappers (Decouple domain from external representation):
Your mapper builds basic entity structure from DTO.
The service layer handles fetching relationships from the database.
This keeps responsibilities clear and avoids partial/inconsistent objects.

Services (Business logic)

Controllers (Exposing API)

Unit Tests (Service logic, mapping, utility)

Integration Tests (Controller → Service → DB using Testcontainers)

External API Calls (e.g., fetch book cover)

Dockerize + Jenkins Integration

Flyway Migrations

Profiles (dev, prod)








