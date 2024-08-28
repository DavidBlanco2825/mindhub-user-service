# User Service

**Author:** [David Fernando Blanco](https://github.com/DavidBlanco2825)

## Overview

This project is a simple implementation of a basic CRUD (Create, Read, Update, Delete) application using Spring Data R2DBC and H2 as the in-memory database. It is designed to demonstrate how to efficiently manage data in a reactive environment with Spring Boot. The project includes a fully functional API for managing items, which can be easily extended or integrated into larger applications.

This application leverages Spring Data R2DBC to provide non-blocking database access, ensuring high performance and scalability, especially in environments where handling large numbers of concurrent requests is essential.

With built-in API documentation via Swagger, you can easily explore and test the endpoints, making it an excellent starting point for both learning and real-world application development.

## Features

- **User Management**: Create, view, update, and delete users.
- **API Documentation**: Comprehensive API documentation with Swagger.

## Technologies Used

- **Spring Boot**: The foundation for the application, providing a robust and easy-to-use framework for developing Java-based applications.

- **Spring WebFlux**: A fully non-blocking reactive framework used for building asynchronous, event-driven web applications.

- **Spring Data R2DBC**: Provides reactive database connectivity and easy integration with relational databases using R2DBC.

- **H2 Database**: An in-memory database used for development and testing, allowing for rapid prototyping without the need for an external database.

- **R2DBC H2**: The R2DBC driver for H2, enabling reactive interaction with the H2 database.

- **SpringDoc OpenAPI**: Automatically generates comprehensive API documentation for the WebFlux application using OpenAPI standards.

- **Reactor Test**: A set of utilities and classes for testing reactive streams in a Spring WebFlux environment.

- **Spring Boot Test**: Provides essential testing utilities and support for testing Spring Boot applications, including integration and unit testing capabilities.


## Getting Started

### Prerequisites

- **Java 17** or later
- **Maven 3.6** or later
- **Git** (for version control)

### Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/DavidBlanco2825/mindhub-user-service.git
    cd mindhub-user-service
    ```

2. **Build the project**:
    ```bash
    mvn clean install
    ```

3. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

4. **Access the application**:
   - The API will be available at `http://localhost:8080/users`.
   - Swagger API documentation can be accessed at `http://localhost:8080/webjars/swagger-ui/index.html`.

## API Endpoints

- **`GET /api/users`**: Retrieve all users.
- **`GET /api/users/{id}`**: Retrieve an user by ID.
- **`POST /api/users`**: Create a new user.
- **`PUT /api/users/{id}`**: Update an user.
- **`DELETE /api/users/{id}`**: Delete an user.
