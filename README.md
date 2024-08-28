# Bank System Project

## Overview
A simple banking system application built in Java that allows users to create accounts, log in, and manage their finances. This app utilizes object-oriented programming, JDBC for MySQL connectivity, and a basic user interface through the console.

## Features
- **User Registration**: Users can create new accounts with an email, full name, and password. This will be added to the local MySQL database.
- **User Login**: Registered users can login using their email and password.
- **Account Management**: Users can view their account balance, full name, and operate their account. These operations include: deposit, withdraw and a simple loan simulator. 
- **Data Storage**: User and account information is securely stored in local MySQL database.

## Technologies Used
- **Java**: The primary programming language for the application.
- **JDBC**: Java Database Connectivity for interacting with the MySQL database.
- **MySQL**: Relational database management system for data storage.


### Installation

1. Clone the repository:
   git clone https://github.com/coderfreeze/banking_system.git
   cd banking_system
2. Import the project into your favorite IDE (e.g., IntelliJ IDEA).

3. Set up your MySQL database:

   Create a new database.
   Create users and accounts tables using the provided SQL table structure.

4. Add the MySQL Connector dependency:
   Go to Project Structure > Libraries and add the JDBC driver JAR file.
