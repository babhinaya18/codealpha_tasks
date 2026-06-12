-- Disable safe updates temporarily so our configuration changes apply smoothly
SET FOREIGN_KEY_CHECKS = 0;
SET SQL_SAFE_UPDATES = 0;

-- 1. Database Initialization
DROP DATABASE IF EXISTS hotelMS;
CREATE DATABASE hotelMS;
USE hotelMS;

-- 2. Authentication Tables
CREATE TABLE login (
    username VARCHAR(25) NOT NULL,
    password VARCHAR(25) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE login2 (
    username VARCHAR(25) NOT NULL,
    password VARCHAR(25) NOT NULL,
    PRIMARY KEY (username)
);

-- Seed initial staff and administrator authorization keys
INSERT INTO login VALUES ('student', '123456789');
INSERT INTO login2 VALUES ('Abhi', '456789');


-- 3. Guest Portal Registration Table
CREATE TABLE hotel_users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    PRIMARY KEY (username)
);


-- 4. Unified Room Inventory Table (Upgraded with Class and AC attributes)
CREATE TABLE room (
    roomnumber VARCHAR(20) NOT NULL,
    availability VARCHAR(20) NOT NULL,
    cleaning_status VARCHAR(20) NOT NULL,
    price VARCHAR(20) NOT NULL,
    bed_type VARCHAR(30) NOT NULL,      -- 'Single Bed' or 'Double Bed'
    room_class VARCHAR(30) NOT NULL,    -- 'Standard', 'Deluxe', 'Suite'
    ac_status VARCHAR(20) NOT NULL,     -- 'AC', 'Non-AC'
    PRIMARY KEY (roomnumber)
);

-- Seed comprehensive test room deployment matrix
INSERT INTO room VALUES ('101', 'Available', 'Cleaned', '1500', 'Single Bed', 'Standard', 'AC');
INSERT INTO room VALUES ('102', 'Available', 'Cleaned', '1200', 'Single Bed', 'Standard', 'Non-AC');
INSERT INTO room VALUES ('201', 'Available', 'Cleaned', '3500', 'Double Bed', 'Deluxe', 'AC');
INSERT INTO room VALUES ('202', 'Available', 'Cleaned', '3000', 'Double Bed', 'Deluxe', 'Non-AC');
INSERT INTO room VALUES ('301', 'Available', 'Cleaned', '7500', 'Double Bed', 'Suite', 'AC');
INSERT INTO room VALUES ('302', 'Available', 'Cleaned', '6500', 'Double Bed', 'Suite', 'AC');


-- 5. Operational Staff Matrix Table
CREATE TABLE employee (
    name VARCHAR(40) NOT NULL,
    age VARCHAR(20) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    job VARCHAR(40) NOT NULL,
    salary VARCHAR(20) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL,
    aadhar VARCHAR(20) NOT NULL,
    PRIMARY KEY (aadhar)
);


-- 6. Logistics & Fleet Drivers Table (All spelling typos resolved)
CREATE TABLE driver (
    name VARCHAR(40) NOT NULL,
    age VARCHAR(20) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    company VARCHAR(40) NOT NULL,
    carname VARCHAR(40) NOT NULL,
    available VARCHAR(20) NOT NULL,
    location VARCHAR(60) NOT NULL,
    PRIMARY KEY (carname)
);


-- 7. Corporate Department Budget Table
CREATE TABLE department (
    department VARCHAR(80) NOT NULL,
    budget VARCHAR(50) NOT NULL,
    PRIMARY KEY (department)
);

INSERT INTO department VALUES ('Office Staff', '50000');
INSERT INTO department VALUES ('Housekeeping', '40000');
INSERT INTO department VALUES ('Kitchen & Food', '80000');


-- 8. Customer Transaction & Central Ledger Table
CREATE TABLE customer (
    id VARCHAR(30) NOT NULL,             -- Document Type (e.g., 'Aadhar' or 'ONLINE')
    number VARCHAR(50) NOT NULL,         -- Document / Receipt Tracking ID
    name VARCHAR(50) NOT NULL,           -- Guest profile name
    gender VARCHAR(20) NOT NULL,
    country VARCHAR(40) NOT NULL,
    room VARCHAR(20) NOT NULL,           -- Allocated room context link
    checkintime VARCHAR(100) NOT NULL,   -- Timestamp index entry
    deposit VARCHAR(20) NOT NULL,        -- Collected financial statement balance
    PRIMARY KEY (number)
);

-- Re-enable safety configurations for everyday operations
SET FOREIGN_KEY_CHECKS = 1;
SET SQL_SAFE_UPDATES = 1;

USE hotelMS;

-- 1. Insert an initial Manager staff profile so that ManagerInfo matches parameters
INSERT INTO employee (name, age, gender, job, salary, phone, email, aadhar)
VALUES ('Abhinaya B', '21', 'Female', 'Manager', '95000', '9876543210', 'abhi@vsa.com', '123456789012')
ON DUPLICATE KEY UPDATE job='Manager';

-- 2. Insert standard staff personnel members 
INSERT INTO employee (name, age, gender, job, salary, phone, email, aadhar)
VALUES ('Rahul Kumar', '24', 'Male', 'Front Desk', '35000', '9123456780', 'rahul@vsa.com', '987654321098')
ON DUPLICATE KEY UPDATE name=name;

-- 3. Seed fleet vehicle profiles for pickup selection engines
INSERT INTO driver (name, age, gender, company, carname, available, location)
VALUES ('Vikram Singh', '32', 'Male', 'Toyota', 'Innova Crysta', 'YES', 'Hyderabad Airport')
ON DUPLICATE KEY UPDATE available='YES';

INSERT INTO driver (name, age, gender, company, carname, available, location)
VALUES ('Suresh Raina', '29', 'Male', 'BMW', 'BMW S-Class', 'YES', 'Hotel Entrance')
ON DUPLICATE KEY UPDATE available='YES';