# 🗂️ Task Manager API

![CI](https://github.com/Luis-Morenoo/task-manager-api/actions/workflows/ci.yml/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Luis-Morenoo_task-manager-api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Luis-Morenoo_task-manager-api)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Luis-Morenoo_task-manager-api&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Luis-Morenoo_task-manager-api)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Luis-Morenoo_task-manager-api&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=Luis-Morenoo_task-manager-api)

I have professional experience with Java Spring Boot in enterprise environments, but I wanted to challenge myself to build something completely from the ground up — no existing codebase to reference, no senior dev to ask. Just me, the docs, and a blank IntelliJ window. 😅

This is a production-ready Task Manager REST API that covers the complete lifecycle of a backend service. Built, tested, secured, containerized, deployed, and monitored. The kind of thing you'd see in enterprise environments, just without the 47 approval tickets to deploy it. 😂

🌐 **Live API:** https://lm-task-manager-api.up.railway.app  
📖 **Swagger UI:** https://lm-task-manager-api.up.railway.app/swagger-ui/index.html  
❤️ **Health Check:** https://lm-task-manager-api.up.railway.app/actuator/health 

📖 **Technical Document:** https://docs.google.com/document/d/1GVApxweT1_Df0EEk2T8ut4kvhGUatpz-WFF-VNHtlmQ/edit?usp=sharing


---

## 🛠️ Tech Stack

| Category | Technology |
|----------|-----------|
| Language & Framework | Java 21, Spring Boot 3.5.13 |
| Database | MongoDB Atlas (cloud-hosted NoSQL) |
| Caching | Redis (Railway-hosted, in-memory) |
| Build Tool | Gradle |
| Containerization | Docker (multi-stage build) |
| Deployment | Railway (cloud platform) |
| CI/CD | GitHub Actions |
| Code Quality | SonarCloud (All A grades — yes, really) |
| API Docs | Swagger UI / OpenAPI 3.0 |
| Testing | JUnit 5, Mockito, Spring Boot Test |
| Logging | SLF4J with Logback |
| Monitoring | Spring Boot Actuator, Railway Observability |

---

## 🏗️ Architecture

The API follows a clean layered architecture — the same pattern used in enterprise Java development, except this time I actually understand every layer because I built it myself. 😄

```
HTTP Request → Controller → Service → Repository → MongoDB Atlas
                                ↕
                            Redis Cache
```

- **Controller:** Handles incoming HTTP requests and response formatting
- **Service:** Contains business logic and cache management
- **Repository:** Abstracts all database operations using Spring Data MongoDB
- **Redis:** Caches frequently accessed data so MongoDB isn't getting hammered on every request

---

## 📡 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/tasks | Get all tasks |
| GET | /api/tasks/{id} | Get task by ID |
| POST | /api/tasks | Create a new task |
| PUT | /api/tasks/{id} | Update a task |
| DELETE | /api/tasks/{id} | Delete a task |
| GET | /actuator/health | Health check |

You can test every endpoint interactively through the [Swagger UI](https://lm-task-manager-api.up.railway.app/swagger-ui/index.html) without needing Postman. Pretty neat.

---

## 🔒 Security

SonarCloud flagged several real vulnerabilities during development, all of which were fixed. This is exactly what enterprise security scanning tools do on every pull request — turns out those flags are there for good reason. 😅

**DTO Pattern:** Prevents mass assignment attacks where a clever client could send an ID in the request body and overwrite someone else's data.

**Log Injection Prevention:** User input is sanitized before logging. Without this, someone could inject fake log entries by hiding newline characters in their request. Yeah, people actually do that.

**PATH Hijacking Prevention:** System commands use absolute paths instead of relying on the `PATH` variable. Another one I learned the hard way via SonarCloud.

**Environment Variables:** No secrets hardcoded anywhere. MongoDB URI and Redis URL live in environment variables, consistent with how secrets are managed in production environments.

---

## ⚙️ CI/CD Pipeline

Every push to `main` triggers the full pipeline automatically. No manual deploys, no "works on my machine" moments.

```
Push to main
    → GitHub Actions: build + test + JaCoCo coverage report
    → SonarCloud: quality and security analysis (All A grades)
    → Railway: automatic deployment via Dockerfile
```

Same concept as enterprise CI/CD pipelines — automated quality gates before anything reaches production. 💪

---

## 🚀 Running Locally

**Prerequisites:** Java 21, Docker, MongoDB Atlas account

**1. Clone the repository:**
```bash
git clone https://github.com/Luis-Morenoo/task-manager-api.git
cd task-manager-api
```

**2. Copy the example config and fill in your credentials:**
```bash
cp src/main/resources/application.yaml.example src/main/resources/application.yaml
```

**3. Run with Gradle:**
```bash
./gradlew bootRun
```

**4. Or run with Docker:**
```bash
docker build -t task-manager .
docker run -p 8080:8080 -e MONGODB_URI=your_uri_here task-manager
```

Swagger UI opens automatically when the service starts. No hunting for the URL.

---

## 🧪 Testing

Two layers of tests — because one layer is not enough and zero layers is how you find out about bugs in production. 😅

**Unit Tests:** Mockito mocks out the database entirely. Tests run in milliseconds and verify every service method in isolation using the Arrange-Act-Assert pattern.

**Integration Tests:** MockMvc fires real HTTP requests through the full stack against a live MongoDB Atlas instance. Includes a security test that proves the API ignores client-provided IDs even when someone tries to sneak one in.

```bash
# Run all tests
./gradlew test

# Run with coverage report
./gradlew test jacocoTestReport
```

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/luis/taskmanager/
│   │   ├── controller/          # HTTP request handlers
│   │   ├── service/             # Business logic + caching
│   │   ├── repository/          # MongoDB data access
│   │   ├── model/               # Database entity (Task)
│   │   ├── dto/                 # Data Transfer Objects (TaskRequest)
│   │   ├── OpenApiConfig.java   # Swagger UI configuration
│   │   └── TaskManagerApplication.java
│   └── resources/
│       └── application.yaml.example
└── test/
    └── java/com/luis/taskmanager/
        ├── TaskServiceTest.java               # Unit tests
        └── TaskControllerIntegrationTest.java  # Integration tests
```

---

## 💡 What I Learned

Working in enterprise Java environments gives you a lot of exposure — reading production code, understanding distributed systems, working within established pipelines. But building something completely from scratch is a different challenge entirely.

This project forced me to understand every layer deeply. Writing tests from scratch gave me a new appreciation for automated test suites I had previously just run and hoped for green. 😄 SonarCloud flagged real security vulnerabilities I wouldn't have caught on my own. Docker finally made the containerization concepts I had seen in production click. And deploying with a CI/CD pipeline I built myself made the whole end-to-end picture come together.

Building something real and owning every decision is one of the best ways to grow as an engineer. 💪

---

## 👤 Author

**Luis Moreno**  
[GitHub](https://github.com/Luis-Morenoo) | [LinkedIn](https://www.linkedin.com/in/luis-moreno11/)
