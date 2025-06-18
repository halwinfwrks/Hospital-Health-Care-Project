# 🏥 Hospital Health Care Project - Backend

This is a **Spring Boot** application for a **Hospital Health Care system** that provides user authentication, role-based access control, and various healthcare-related functionality.

---

## 🚀 Project Overview

The backend is built with:

- **Spring Boot** 3.5.0  
- **Java** 17  
- **MongoDB** for data storage  
- **Spring Security** with **JWT authentication**  
- **RESTful API** architecture  

---

## ✨ Features

- RESTful API designed for a comprehensive **Hospital Health Care System**
- User authentication using **JWT tokens**
- **Role-based access control** with roles: `USER`, `ADMIN`
- User account management: registration, login, and profile updates
- Support for multiple user types: **Patients** and **Doctors**
- Secure API access using **Spring Security**
- Functionality to manage appointments, medical records, and health services
- Well-structured API endpoints, clearly separated into public and protected routes


---

## 📁 Project Structure

Typical Spring Boot structure with separated packages for controllers, services, repositories, models, configurations, etc.

---

## ✅ Prerequisites

- Java 17 or higher  
- Maven  
- MongoDB (Cloud Atlas or local instance)

---

## ▶️ Running the Application

1. **Clone the repository**:

   ```bash
   git clone <repository-url>
   cd hospital-healthcare-backend
   ```

2. **Configure MongoDB connection**:

   Edit `application.properties` and update the MongoDB URI if needed.

3. **Build and run the application**:

   For Windows:

   ```bash
   ./mvnw spring-boot:run
   ```

   The application will start on **port 8989** (as configured).

---


## 📄 License

This project is licensed under the **MIT License** – see the `LICENSE` file for details.