Banking Management System
A desktop application developed in Java Swing for managing bank accounts, with data stored in a MySQL database via JDBC.

Features
Add new account holders
Update account details
Delete existing accounts
View balances and account status in a table
Test database connection directly from the GUI

Technologies Used
Java (Swing for GUI)

MySQL
JDBC (mysql-connector)

Setup Instructions
Clone or Download the Project
Copy all .java source files and supporting files into a folder (e.g., week7).
Setup MySQL Database
Create a MySQL database (e.g., bankcontent) and Accounts table:

sql
CREATE DATABASE bankcontent;
USE bankcontent;
CREATE TABLE Accounts (
    acc_no INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    balance DOUBLE NOT NULL DEFAULT 0,
    status VARCHAR(20) DEFAULT 'Active'
);

Adjust table schema if needed.
Download JDBC Driver
Download mysql-connector JAR and place it in the project directory.
Compile the Application
javac week8.java

Run the Application
java -cp ".;mysql-connector-j-9.3.0.jar" week8
On Mac/Linux, replace ; with : in the classpath.

Edit Database Credentials (if needed)

Update the constants at the top of week8.java to match your MySQL username, password, and database name:

java
private static final String DB_URL = "jdbc:mysql://localhost:3306/bankcontent";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";
Screenshots

<img width="1176" height="877" alt="Screenshot 2025-10-17 022521" src="https://github.com/user-attachments/assets/010ecda6-9645-4f4e-a7f8-9574176cbfb5" />

FILE STRUCTURE
week7/
├── week8.java
├── week8.class
├── mysql-connector-j-9.3.0.jar
└── ...
Author
Amrita Hariharan
