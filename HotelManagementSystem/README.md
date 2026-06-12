# 🏨 Hotel Management System

A complete Hotel Management System developed using Java Swing, JDBC, and MySQL.

This desktop application automates hotel operations including guest registration, room booking, employee management, reception management, online reservation handling, room inventory tracking, and administrative controls.

---

# 📌 Project Overview

The Hotel Management System is designed to simplify and automate hotel operations through a centralized desktop application.

The system provides:

- Guest Portal
- Reception Portal
- Administrative Portal
- Online Reservation System
- Room Management System
- Employee Management System
- Driver Management System

The application uses Java Swing for GUI development and MySQL for persistent data storage.

---

# 🚀 Features

## Guest Module

- Guest Registration
- Guest Login
- Online Room Booking
- Room Browsing
- Room Filtering
- Booking History
- Reservation Management

## Reception Module

- Customer Check-In
- Customer Check-Out
- Customer Information Management
- Room Status Updates
- Pickup Service Management
- Department Information
- Employee Information
- Manager Information
- Advanced Room Search

## Administrator Module

- Add Employees
- Add Rooms
- Add Drivers
- Administrative Dashboard

## Room Management

- Room Availability Tracking
- Room Classification
- AC / Non-AC Rooms
- Single Bed / Double Bed Rooms
- Standard Rooms
- Deluxe Rooms
- Suite Rooms

## Database Management

- JDBC Connectivity
- MySQL Integration
- Dynamic Data Retrieval
- Real-Time Data Updates

---

# 🛠 Technologies Used

| Technology | Purpose |
|------------|----------|
| Java | Core Programming |
| Java Swing | GUI Development |
| JDBC | Database Connectivity |
| MySQL | Database Management |
| AWT | GUI Components |
| IntelliJ IDEA | Development Environment |
| OOP | Software Design |

---

# 📂 Project Structure

```text
Hotel Management System
│
├── src
│   └── Hotel
│       └── Management
│           └── System
│
├── icon
│
├── screenshots
│
├── database.sql
│
├── README.md
│
└── .gitignore
```

---

# 🖼 Application Screenshots

## Splash Screen

![Splash Screen](screenshots/Splash%20Screen.png)

---

## Portal Selection

![Portal Selection](screenshots/Portal%20Selection.png)

---

## Guest Login

![Guest Login](screenshots/Guest%20Login.png)

---

## Guest Registration

![Guest Registration](screenshots/Register%20Guest.png)

---

## Guest Dashboard

![Guest Dashboard](screenshots/Guest%20Dashboard.png)

---

## Staff Login

![Staff Login](screenshots/Staff%20Login.png)

---

## Staff Dashboard

![Staff Dashboard](screenshots/Staff%20Dashboard.png)

---

## Reception Desk

![Reception Desk](screenshots/Staff%20Operation%20Desk.png)

---

## Admin Login

![Admin Login](screenshots/Admin%20Login.png)

---

## Admin Dashboard

![Admin Dashboard](screenshots/Admin%20dashboard.png)

---

# ⚙ Database Setup

## Step 1

Install:

- MySQL Server
- MySQL Workbench

---

## Step 2

Create Database

```sql
CREATE DATABASE hotelMS;
```

---

## Step 3

Import SQL File

Import:

```text
database.sql
```

into MySQL Workbench.

---

## Step 4

Update Database Credentials

Open:

```java
DatabaseConnection.java
```

Update:

```java
connection = DriverManager.getConnection(
"jdbc:mysql://localhost:3306/hotelMS",
"root",
"your_password"
);
```

---

## Step 5

Run Project

Run:

```java
Splash.java
```

---

# 🏗 System Architecture

```text
Splash Screen
       │
       ▼
Portal Selection
       │
 ┌─────┴─────┐
 │           │
 ▼           ▼
Guest      Staff
Portal     Portal
 │           │
 ▼           ▼
Guest      Reception/Admin
Dashboard  Dashboard
```

---

# 📊 Main Database Tables

### customer

Stores customer information and booking records.

### employee

Stores employee details.

### room

Stores room information and availability.

### driver

Stores driver information.

### hotel_users

Stores guest login credentials.

### login

Stores staff login credentials.

### department

Stores department information.

---

# 🎯 Future Enhancements

- Online Payment Gateway
- Password Encryption
- Email Notifications
- SMS Notifications
- Mobile Application
- QR-Based Check-In
- Analytics Dashboard
- Cloud Database Integration

---

# 📚 Learning Outcomes

This project helped in learning:

- Java Swing GUI Development
- JDBC Database Connectivity
- MySQL Database Design
- Event Handling
- Object-Oriented Programming
- Desktop Application Development
- User Authentication Systems
- Multi-Module Software Design

---

# 👨‍💻 Author

**Abhinaya Bandari**

B.Tech Computer Science Engineering

CodeAlpha Internship Project

---

# 📄 License

This project is developed for educational and internship purposes.

Copyright © 2026 Abhinaya Bandari

---

# ⭐ Project Highlights

✔ Java Swing GUI

✔ MySQL Database Integration

✔ JDBC Connectivity

✔ Guest Booking System

✔ Online Reservation System

✔ Reception Management

✔ Room Management

✔ Employee Management

✔ Driver Management

✔ Administrative Controls

✔ Object-Oriented Design

✔ Multi-User Access System