# Crowdfunding Web App

This repository demonstrates a basic setup of Spring Security, showcasing fundamental authentication and authorization mechanisms within a Spring Boot application.

## Installation

Clone the repository
```
git clone https://github.com/nikos-kaparos/Spring-Security-Example.git
```
<!--
## Build the project with Maven:
```
mvn clean package -DskipTests
```
-->
## Usage
Run the containers
```bash
docker compose up --build
```
Check the database:

Connect to container that host the database
```bash
docker exec -it my_postgres_db psql -U myuser -d mydatabase
```
Check the database
```bash
\dt
```
Get all users from users table 
```bash
SELECT * FROM users;
```
Login open browser to http://localhost:8080 login note that has RBAC (role based access control).

## Features

-   **Admin User**: An admin user is automatically created in the Docker container's database. The admin can view new users who sign up in the system.
-   **Unique Usernames**: The app does not allow users to register with the same username. If a username already exists, registration is prevented.
-   **User Enablement**: The admin can enable or disable users. Only enabled users can log in to the app.


## Technologies
-   Java 21
-   Maven 3.9.9
-   Docker 27.2.1
-   Docker Compose 1.29.2
-   PostgreSQL 16.3
