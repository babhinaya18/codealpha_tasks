# 📈 Stock Trading Platform

## Overview

Stock Trading Platform is a Java Swing desktop application that simulates stock market trading. Users can buy and sell stocks, manage portfolios, maintain watchlists, track transactions, view stock charts, and monitor wallet balances.

This project was developed as part of the CodeAlpha Java Programming Internship.

---

## Features

### User Authentication
- Login System
- User Profile Management

### Market Dashboard
- Live Stock Price Simulation
- Real-Time Market Updates
- Stock Trend Visualization

### Portfolio Management
- Buy Stocks
- Sell Stocks
- Portfolio Tracking
- Holdings Management

### Watchlist
- Add Stocks to Watchlist
- Monitor Favorite Stocks

### Wallet Management
- Deposit Funds
- Balance Tracking
- Profit and Loss Analysis

### Transaction History
- Complete Buy/Sell Logs
- Activity Tracking

### Data Persistence
- MySQL Database Integration
- File-Based Backup Storage

---

## Technologies Used

- Java
- Java Swing
- JDBC
- MySQL
- OOP Concepts
- Collections Framework
- File Handling

---

## Project Structure

```text
StockTradingPlatform
│
├── src
│   └── com
│       └── stocktrading
│           ├── DatabaseManager.java
│           ├── FileManager.java
│           ├── LoginFrame.java
│           ├── Main.java
│           ├── Portfolio.java
│           ├── Stock.java
│           ├── StockChartPanel.java
│           ├── TradingGUI.java
│           └── User.java
│
├── screenshots
├── database
├── README.md
└── .gitignore
```

---

## Database Setup

Create database:

```sql
CREATE DATABASE vstread_db;
```

Import the SQL file from:

```text
database/stocktrading.sql
```

Update database credentials inside:

```java
DatabaseManager.java
```

```java
private static final String USER = "root";
private static final String PASSWORD = "your_mysql_password";
```

---

## How to Run

1. Clone Repository

```bash
git clone https://github.com/yourusername/CodeAlpha_TaskA.git
```

2. Open in IntelliJ IDEA

3. Configure MySQL Database

4. Run:

```java
Main.java
```

---

## Screenshots

### Login Screen

login.png

### home

home.png

### Portfolio

portfolio.png

### Watchlist

watchlist.png

### Wallet

wallet.png

### BUY/SELL

stockbuy.png/stocksell.png

### Profile

profile.png

---

## Future Enhancements

- Real Stock Market API Integration
- Candlestick Charts
- Password Encryption
- User Registration
- Admin Dashboard
- Portfolio Analytics
- Export Reports

---

## Security Note

Passwords are stored in plain text for educational purposes.

In production systems, secure password hashing techniques such as BCrypt should be implemented.

---

## Author

Abhinaya Bandari

CodeAlpha Java Programming Internship