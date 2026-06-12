CREATE DATABASE IF NOT EXISTS vstread_db;

USE vstread_db;

CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50),
    full_name VARCHAR(100),
    email VARCHAR(100),
    balance DOUBLE
);

CREATE TABLE portfolio (
    username VARCHAR(50),
    stock_name VARCHAR(50),
    quantity INT,
    PRIMARY KEY(username, stock_name)
);

CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    type VARCHAR(20),
    stock_name VARCHAR(50),
    quantity INT,
    price DOUBLE,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE watchlist (
    username VARCHAR(50),
    stock_name VARCHAR(50)
);

INSERT INTO users VALUES(
'admin',
'admin123',
'Demo User',
'demo@example.com',
100000
);