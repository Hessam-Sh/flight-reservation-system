# ✈️ Flight Reservation System

A full-stack **Flight Reservation System** built with **Java** and **Spring Boot**.  
The application allows users to search flights, make reservations, and download their airline ticket as a **PDF** with a unique **QR code**.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![MySQL](https://img.shields.io/badge/MySQL-8-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 📌 Overview

Flight Reservation System is a web-based application designed to simulate the core workflow of an airline booking platform.  
Users can register, log in securely, browse available flights, complete a reservation, and receive a downloadable ticket containing flight and passenger details.

The project follows the **MVC architecture** and demonstrates practical usage of Spring Boot, Spring MVC, Spring Security, Hibernate, Thymeleaf, and MySQL.

## ✨ Features

- User registration and login
- Role-based access control with Spring Security
- Flight search and listing
- Flight reservation management
- Automatic ticket generation in PDF format
- QR code generation for each booked ticket
- Responsive user interface built with Thymeleaf
- Data persistence using Hibernate and MySQL
- Input validation and centralized error handling
- Clean MVC-based project structure

## 🛠️ Technologies

| Layer | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot |
| Web Layer | Spring MVC |
| Security | Spring Security |
| ORM | Hibernate / JPA |
| Template Engine | Thymeleaf |
| Database | MySQL |
| Build Tool | Maven |
| PDF Generation | iText / OpenPDF |
| QR Code | ZXing |

## 🏗️ Architecture

The application follows the **Model-View-Controller (MVC)** pattern:

```text
Controller
   ↓
Service Layer
   ↓
Repository / DAO Layer
   ↓
MySQL Database
```

- **Controller:** Handles HTTP requests and returns Thymeleaf views.
- **Service:** Contains business logic such as reservation processing and ticket generation.
- **Repository:** Communicates with the database through Spring Data JPA.
- **View:** Thymeleaf templates render dynamic HTML pages.

## 🚀 Getting Started

### Prerequisites

Make sure you have the following installed:

- Java 17 or later
- Maven 3.8+
- MySQL 8+
- IntelliJ IDEA

### Clone the Repository

```bash
git clone [https://github.com/YOUR-USERNAME/flight-reservation-system.git](https://github.com/YOUR-USERNAME/flight-reservation-system.git)
cd flight-reservation-system
```

### Configure the Database

Create a MySQL database:

```sql
CREATE DATABASE flight_reservation_db;
```

Update your database credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/flight_reservation_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> ⚠️ Do not commit real database passwords or API keys to a public repository.

### Run the Application

```bash
mvn spring-boot:run
```

Then open the application in your browser:

```text
http://localhost:8080
```

## 🎫 Ticket Generation

After a successful reservation, the system generates a downloadable PDF ticket.

The ticket includes:

- Passenger information
- Flight details
- Reservation ID
- Departure and arrival information
- A unique QR code for validation

The QR code can be scanned to verify the reservation details.

## 📁 Project Structure

```text
src/
 ├── main/
 │   ├── java/
 │   │   └── com/example/flightreservation/
 │   │       ├── controller/
 │   │       ├── service/
 │   │       ├── repository/
 │   │       ├── model/
 │   │       ├── config/
 │   │       └── util/
 │   └── resources/
 │       ├── templates/
 │       ├── static/
 │       └── application.properties
 └── test/
```

## 🔐 Security

The application uses Spring Security to protect authenticated areas and manage user sessions.

Security features include:

- Password encoding
- Login and logout handling
- Access control for protected pages

- Protection against unauthorized access

## 🧪 Future Improvements

- Payment gateway integration
- Email confirmation for bookings
- Admin dashboard for flight management
- Flight cancellation and refund workflow
- REST API endpoints for mobile clients
- Unit and integration tests
- Docker support

## 🤝 Contributing

Contributions are welcome.  
If you would like to improve the project, please fork the repository, create a new branch, and submit a pull request.

## 📄 License

This project is licensed under the MIT License.

## 👤 Author

GitHub: [@Hessam Sheykhzadeh](https://github.com/Hessam-Sh)