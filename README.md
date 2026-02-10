# 🎬 Movie Reservation System API

A Spring Boot RESTful backend application for managing movies, showtimes, cinema halls, and seat reservations.

This project focuses on clean layered architecture, secure authentication, and real-world cinema booking logic.

Designed as a **portfolio-ready backend project** demonstrating practical backend engineering skills.

---

## 🚀 Features

### 🎥 Movie Management
- Create, update, delete movies
- Pagination & sorting support
- Filter movies by genre
- Retrieve movie details

### 🕒 Showtime Management
- Add showtimes per movie & hall
- Update and delete showtimes
- Prevent hall schedule conflicts
- Query showtimes by date

### 🎟 Reservation System
- Create reservations
- Reserve multiple seats
- Prevent duplicate seat bookings
- Get user reservations
- Reservation status tracking

### 🪑 Seat System
- Cinema halls with structured seats
- Unique seat constraints per hall
- Query available seats per showtime

### 🔐 Authentication & Security
- JWT Authentication
- Refresh Token support
- Role-based authorization
- Spring Security integration

### 📄 API Documentation
- Swagger UI
- OpenAPI 3 documentation
- JWT secured API testing

### 🧱 Database & Migration
- PostgreSQL database
- Flyway migrations
- Constraint-based schema design

---

## 🏗 Architecture

Project follows layered architecture:

controller → service → repository → database


### Package Structure
- config
- controller
- dto
- exception
- jwt
- model
- repository
- service
- utils
- scheduling

### Architectural Highlights
- DTO-based API responses
- Global exception handling
- RootEntity generic API wrapper
- Pagination-ready endpoints
- Enum-based domain modeling
- Database constraints for integrity

---

## 🗄 Entity Model

### Core Entities
- Movie
- Hall
- Seat
- Showtime
- Reservation
- ReservationSeat
- User
- RefreshToken

### Key Relationships
- Movie → Showtime (OneToMany)
- Hall → Seat (OneToMany)
- Showtime → Hall (ManyToOne)
- Reservation → ReservationSeat (OneToMany)
- ReservationSeat → Seat (ManyToOne)
- User → Reservation (ManyToOne)

### Data Integrity Features
- Unique seat per hall row/number
- Unique showtime per hall/time
- Unique reservation-seat mapping

---

## 🛠 Tech Stack

| Technology        | Usage                 |
| ----------------- | --------------------- |
| Java 17           | Backend language      |
| Spring Boot 3     | Framework             |
| Spring Security   | Authentication        |
| JWT               | Token-based auth      |
| Spring Data JPA   | ORM                   |
| PostgreSQL        | Database              |
| Flyway            | DB Migration          |
| Swagger / OpenAPI | API Docs              |
| Lombok            | Boilerplate reduction |
| Maven             | Build tool            |

---

## ▶️ Running the Project

### 1️⃣ Clone Repository
git clone https://github.com/yusufguc/MovieReservationSystem

cd MovieReservationSystem


### 2️⃣ Configure Database
Update:

application.properties

application-local.properties


### 3️⃣ Run Flyway Migration
Runs automatically on startup.

### 4️⃣ Start Project
mvn spring-boot:run

---

## 📘 API Documentation

Swagger UI:
http://localhost:8080/swagger-ui/index.html



Authorize using JWT token from:

/auth/authenticate

---

## 🔐 Authentication Flow
1. Register user
2. Login via `/auth/authenticate`
3. Receive JWT
4. Use token in Swagger "Authorize"
5. Access secured endpoints

---

## 📌 Example Endpoints
🔐 Authentication Controller
-POST   /auth/register 

-POST   /auth/authenticate

-POST   /auth/refreshToken

🎬 Movie Controller

-POST   /api/v1/movies

-PUT    /api/v1/movies/{id}

-DELETE /api/v1/movies/{id}

-GET    /api/v1/movies/id/{id}

-GET    /api/v1/movies/pageable

-GET    /api/v1/movies/pageable/genre/{genre}

🕒 Showtime Controller

-POST   /api/v1/movies/{movieId}/showtimes

-PUT    /api/v1/movies/{movieId}/showtimes/{showTimeId}

-DELETE /api/v1/showtime/{showTimeId}

-GET    /api/v1/showtimes/by-date

🎟 Reservation Controller

-POST   /api/v1/reservation

-PUT    /api/v1/reservation/id/{reservationId}

-DELETE /api/v1/reservation/id/{reservationId}

-GET    /api/v1/reservation/pageable

-GET    /api/v1/reservation/my

🪑 Seat Controller

-GET /api/v1/showtimes/{showtimeId}/available-seats


---

## 🎯 Purpose of This Project
- Backend portfolio project
- Demonstrate REST API design
- Show layered architecture
- Practice secure authentication
- Implement real-world booking logic

---

## 👨‍💻 Author
**Yusuf Guc**  
Backend Developer (Java & Spring Boot)
