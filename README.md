# MoneyMap

Navigate your finances with ease. Track expenses, set budgets, and visualize your financial journey with MoneyMap.

# 🚀 Tech Stack

- Java  
- Spring Boot  
- Spring Security  
- JWT (JSON Web Tokens)  
- JPA / Hibernate  
- PostgreSQL  
- Docker  
- RESTful API design  

# 🔐 Authentication & Security

- Stateless authentication using JWT  
- Custom JWT filter integrated with Spring Security  
- Secured endpoints with role-based access control  
- Password encryption using BCrypt  
- Asymmetric key authentication using public/private key pair  

# 📊 Core Features

## 🧾 Transactions
- Create, update, and delete transactions  
- Associate transactions with categories  
- Store timestamped financial records  

## 🗂 Categories
- Create and manage custom transaction categories  
- Organize expenses for better financial tracking  

## 💵 Budgets
- Set spending limits  
- Track expenses against defined budgets  
- Monitor financial activity over time  

# 🏗 Architecture

The application follows a **microservices architecture**, where services are independently structured and containerized using Docker for scalability and separation of concerns.  

Each service exposes RESTful endpoints and communicates securely.

# 🐳 Running the Application

1. Clone the repository.  
2. Email **vincentwong5609@gmail.com** to request the required public keys for JWT authentication.  
3. After adding the keys to the appropriate configuration location, run:

```
docker compose up -d --build
```

This will start all services along with the PostgreSQL database.

# 🔎 Example API Endpoints

- **Register:** `POST /auth/register`  
- **Login:** `POST /auth/login`  
- **Create Transaction:** `POST /transactions` (Requires Authorization: Bearer `<JWT_TOKEN>`)  

# 🎯 Future Improvements

- Frontend integration (React)  
- Cloud deployment (AWS or GCP)  
- API documentation with Swagger / OpenAPI  
- Financial analytics dashboard  

# 📌 Purpose

This project was built to demonstrate:  

- Secure backend development  
- Microservice architecture design  
- Stateless authentication using JWT  
- Containerized deployment  
- Proper relational data modeling with JPA/Hibernate

