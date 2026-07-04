# ☕ Portfolio Backend

A production-ready REST API for my personal portfolio website, built with **Spring Boot**, **Spring Security (JWT)**, **MySQL/PostgreSQL**, and **Resend Email API**. The application provides secure authentication, contact management, asynchronous email notifications, rate limiting, caching, and comprehensive API documentation with Swagger UI.

---

# 🏛 Architecture

The project follows a clean layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

---

# 🛠 Tech Stack

* ☕ Java 21
* 🚀 Spring Boot 3.3.5
* 🔐 Spring Security
* 🔑 JWT Authentication & Authorization
* 🗄 Spring Data JPA (Hibernate)
* 🐬 MySQL (Development)
* 🐘 PostgreSQL (Production)
* 📧 Resend Email API
* ⚡ Spring Cache
* 🔄 Async Processing (`@Async`)
* 🧪 Jakarta Validation
* 📖 Swagger / Springdoc OpenAPI
* 🌐 RESTful APIs
* 🐳 Docker
* ☁️ Render Deployment

---

# ✨ Features

* 🔐 JWT-based Authentication & Authorization
* 👤 Secure Admin Registration & Login
* 🔒 Role-Based Access Control
* 📬 Public Contact Form API
* 🗄 Contact Message Management
* 📧 Asynchronous Email Notifications using Resend API
* ⚡ Spring Cache Support
* ⏱ Built-in Rate Limiting
* 🚫 Duplicate Message Detection
* 📝 DTO-based Request & Response Handling
* ⚠️ Global Exception Handling with Custom Exceptions
* ✅ Input Validation using Jakarta Validation
* 🌐 CORS Configuration
* 📖 Interactive API Documentation with Swagger UI
* 🏛 Layered Architecture (Controller → Service → Repository)
* ⚙️ Environment-specific Configuration (Development / Test / Production)
* 📝 Structured Logging

---

# 🔒 Security

* JWT Authentication
* BCrypt Password Encryption
* Protected REST APIs
* Stateless Authentication
* Custom JWT Filter
* Spring Security Configuration

---

# 📖 API Documentation

After starting the application, Swagger UI is available at:

```text
http://localhost:8089/swagger-ui/index.html
```

---

# 🔗 REST API

## Authentication

| Method | Endpoint             | Description                               |
| ------ | -------------------- | ----------------------------------------- |
| POST   | `/api/auth/register` | Register a new admin                      |
| POST   | `/api/auth/login`    | Authenticate admin and generate JWT token |

## Contact

| Method | Endpoint        | Description              |
| ------ | --------------- | ------------------------ |
| POST   | `/api/contacts` | Submit a contact message |

## Admin

| Method | Endpoint                         | Description                          |
| ------ | -------------------------------- | ------------------------------------ |
| GET    | `/api/admin/contacts`            | Retrieve all contact messages        |
| GET    | `/api/admin/contacts/{id}`       | Retrieve a contact message by ID     |
| PATCH  | `/api/admin/contacts/{id}/read`  | Mark a contact message as read       |
| POST   | `/api/admin/contacts/{id}/reply` | Reply to a contact message via email |
| DELETE | `/api/admin/contacts/{id}`       | Delete a contact message             |


---

# ⚙️ Environment Variables

Create a `.env` file or configure the following environment variables:

```properties
DB_URL=
DB_USERNAME=
DB_PASSWORD=

JWT_SECRET=

RESEND_API_KEY=

PORT=
```

---

# 🚀 Run Locally

```bash
# Clone the repository
git clone <repository-url>

# Navigate to project directory
cd Portfolio-Backend-Java

# Configure environment variables

# Run the application
mvn spring-boot:run
```

---

## 📂 Project Structure

```text
src
└── main
    ├── java
    │   └── com.prabhat.portfolio
    │       ├── config          # Application & CORS configuration
    │       ├── constants       # Application constants
    │       ├── controller      # REST API controllers
    │       ├── dto             # Request & Response DTOs
    │       ├── entity          # JPA entities
    │       ├── enums           # Role & Contact status enums
    │       ├── exception       # Custom exceptions & global handler
    │       ├── repository      # Spring Data JPA repositories
    │       ├── security        # JWT, Spring Security & authentication
    │       ├── service
    │       │   ├── impl        # Business logic implementations
    │       │   └── interfaces  # Service interfaces
    │       ├── util            # Utility classes (Rate Limiter, etc.)
    │       └── PortfolioBackendApplication.java
    │
    └── resources
        ├── application.properties
        ├── application-dev.properties
        ├── application-test.properties
        ├── application-prod.properties
        ├── static
        └── templates
```


---

# 👨‍💻 Author

**Prabhat Prajapati**

* GitHub: https://github.com/prabhatpra
* LinkedIn: https://www.linkedin.com/in/prabhat-prajapati-01p6/
* Email: [prabhatprajapati01@gmail.com](mailto:prabhatprajapati01@gmail.com)
