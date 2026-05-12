# ZapGo ⚡

ZapGo is a backend-based EV charging slot booking and management system developed using Java and Spring Boot.  
The project allows users to manage charging stations, book charging slots, track station occupancy, and analyze booking activity efficiently.

---

# Features

- EV charging station management
- Charging slot booking system
- Real-time station occupancy tracking
- Analytics and booking insights
- Layered backend architecture
- RESTful API design
- Exception handling
- AOP-based logging system
- DTO implementation for request/response handling

---

# Tech Stack

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Maven
- AOP (Aspect-Oriented Programming)
- REST APIs

---

# Project Structure

```bash
src/main/java
├── aspect
├── controller
├── dao
├── dto
├── exception
├── model
├── runner
└── service
```

---

# API Modules

- Booking Management
- Station Management
- Analytics Module

---

# Logging & Exception Handling

The project uses AOP-based centralized logging to track API activity and application behavior.  
Custom exception handling is implemented using a global exception handler for cleaner error responses.

---

# How to Run

1. Clone the repository

```bash
git clone <your-repo-url>
```

2. Open the project in STS / IntelliJ IDEA

3. Configure database settings in:

```bash
src/main/resources/application.properties
```

4. Run the application

```bash
mvn spring-boot:run
```

---

# Future Improvements

- JWT Authentication
- Role-Based Access Control
- Frontend Integration
- Real-time slot updates
- Payment Gateway Integration

---

# Author

Abhishek Kumar
