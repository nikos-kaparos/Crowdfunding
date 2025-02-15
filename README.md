# Crowdfunding Web App

The repository showcases a basic Crowdfunding web application built with Spring Boot. It includes fundamental authentication and authorization features, ensuring secure access and user management.

## Installation

Clone the repository
```
git clone https://github.com/nikos-kaparos/Crowdfunding.git
```

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
Insert admin user

- **Open Postman** and send a **POST** request in http://localhost:8080/api/auth/signup with this body 
```json
{
    "username": "your_username",
    "password": "your_password",
    "email": "your_email",
    "role": "admin"
}
```
After that, open browser in http://localhost:3000 login note that has RBAC (role based access control) and Jwt Authentication.

Also you can view the logs of the container by this.

Run 
```bash
docker logs <container-name>
``` 

## Features

-   **Admin User**: The admin can view new users who sign up in the system and approves the registration, he can delete them when the project that they are connected is finished. Also admin can view all project that users post and approves them. Only the approved project is displayed.

NEED UPDATE!!!!!!!!!
-   **Unique Usernames**: The app does not allow users to register with the same username. If a username already exists, registration is prevented.
-   **User Enablement**: The admin can enable or disable users. Only enabled users can log in to the app.


## Technologies
-   Java 21
-   Maven 3.9.9
-   Docker 27.2.1
-   Docker Compose 1.29.2
-   PostgreSQL 16.3
