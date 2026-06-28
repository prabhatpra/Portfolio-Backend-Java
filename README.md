# ☕ Portfolio Backend

A production-ready backend for my personal portfolio website, built with **Spring Boot**, **Spring Security (JWT)**, **MySQL/PostgreSQL**, and **Resend Email API**. It provides secure authentication, contact management, rate limiting, caching, and email notifications.

---

# 🛠 Tech Stack

* ☕ Java 21
* 🚀 Spring Boot 3.3.5
* 🔐 Spring Security + JWT Authentication
* 🗄 Spring Data JPA (Hibernate)
* 🐬 MySQL (Development)
* 🐘 PostgreSQL (Production)
* 📧 Resend Email API
* ⚡ Spring Cache
* 🔄 Async Processing
* 🧪 Jakarta Validation
* 🐳 Docker
* ☁️ Render Deployment

---

# ✨ Features

* 🔐 JWT-based Authentication
* 👤 Admin Registration & Login
* 📬 Contact Form API
* 📧 Email Notification to Admin
* 🗄 Contact Message Management
* ⚡ Spring Cache Support
* ⏱ Rate Limiting
* 🚫 Duplicate Message Detection
* ⚠️ Global Exception Handling
* ✅ Input Validation
* 🌐 CORS Configuration
* 📝 Structured Logging

---

# 🔗 REST API

## Authentication

| Method | Endpoint             | Description          |
| ------ | -------------------- | -------------------- |
| POST   | `/api/auth/register` | Register Admin       |
| POST   | `/api/auth/login`    | Login & Generate JWT |

## Contacts

| Method | Endpoint                    | Description           |
| ------ | --------------------------- | --------------------- |
| POST   | `/api/contacts`             | Submit Contact Form   |
| GET    | `/api/contacts`             | Get All Contacts      |
| GET    | `/api/contacts/{id}`        | Get Contact By ID     |
| PATCH  | `/api/contacts/{id}/status` | Update Contact Status |
| DELETE | `/api/contacts/{id}`        | Delete Contact        |

---

# ⚙️ Environment Variables

```properties
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
RESEND_API_KEY
PORT
```

---

# 🚀 Run Locally

```bash
git clone <repository-url>
cd Portfolio-Backend-Java

mvn spring-boot:run
```

---

# 👨‍💻 Author

**Prabhat Prajapati**

* GitHub: https://github.com/prabhatpra
* LinkedIn: https://www.linkedin.com/in/prabhat-prajapati-01p6/
* Email: [prabhatprajapati01@gmail.com](mailto:prabhatprajapati01@gmail.com)
