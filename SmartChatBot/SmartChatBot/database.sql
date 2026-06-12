CREATE DATABASE chatbot_db;

USE chatbot_db;

CREATE TABLE faq(
id INT AUTO_INCREMENT PRIMARY KEY,
keywords VARCHAR(255),
response TEXT
);

CREATE TABLE learned_data(
question VARCHAR(255) PRIMARY KEY,
answer TEXT
);

CREATE TABLE chat_history(
id INT AUTO_INCREMENT PRIMARY KEY,
question TEXT,
answer TEXT
);