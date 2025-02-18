# Crowdfunding Web App

This repository contains a **Crowdfunding Web Application** built with **Spring Boot** and **Vue**, designed to facilitate project fundraising through user contributions. The platform enables creators to launch campaigns and backers to support projects securely.

This project follows **RESTful API** principles, providing secure and structured endpoints for managing users, projects, and donations.For detailed API documentation, refer to the Swagger UI at: http://localhost:8080/swagger-ui/index.html  

## Installation

Clone the repository
```
git clone https://github.com/nikos-kaparos/Spring-Security-Example.git
```
## Usage
Run the containers
```bash
docker compose up --build
```
Check if the containers ruing
```bash
docker ps
``` 
**To run the app**

1. First open **postman** in and send a post request to http://localhost:8080/api/auth/signup
or you can also use the **swagger**

    **note**: Make sure that you send an json look like this and **the role is admin**   
```json
{
    "username": "your_username",
    "password": "your_password",
    "email": "xxx@example.com",
    "role": "admin"
}
```
2. Open a browser to http://localhost:3000 and navigate to the system

**Note** The web app include JTW athentication and BRAC (based role access control )

## To manage the database 

Connect to container that host the database
```bash
docker exec -it my_postgres_db psql -U myuser -d mydatabase
```
View all the table in database database
```bash
\dt
```
Get all users from users table 
```bash
SELECT * FROM users;
```
## Features

- **Admin User**: The admin can view new users who sign up in the system and approves the registration. Admin can view all project that creators post and approves them. Only the approved project is displayed. Also can delete an user **only** when the project that he/she is related is completed.

- **Unique Usernames**: The app does not allow users to register with the same username. If a username already exists, registration is prevented. Only enabled users can log in to the app.
- **Creators**: Can post, delete a project and edit it the same time or in second time.

- **Supporter** : Can donate money in the projects one time in the same project.  

## Technologies
- Java 21
- Maven 3.9.9
- Docker 27.2.1 

- Docker Compose 1.29.2
- PostgreSQL 16.3
- Vue cli 5.0.8
- Node.js 20
- Nginx last stable version                                                                                                                                                                                                                                                                                                                                 
