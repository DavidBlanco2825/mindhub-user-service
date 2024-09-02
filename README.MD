# User Service

**Author:** [David Fernando Blanco](https://github.com/DavidBlanco2825)

## Overview

**UserService** is a microservice within the **NoteList** application, dedicated to managing user-related operations. It provides a comprehensive RESTful API to perform Create, Read, Update, and Delete (CRUD) operations on users, serving as a crucial component in handling user-related data within the application.

TaskService is built using **Spring WebFlux**, embracing reactive programming principles. This enables the service to handle asynchronous data streams and provide non-blocking IO operations, making it highly scalable and capable of efficiently managing large volumes of concurrent requests with minimal resource consumption.

### Key Features

- **Full CRUD Functionality**: Seamless management of user lifecycle through a robust API.
- **Reactive Programming**: Powered by Spring WebFlux for building responsive, non-blocking, and event-driven applications.
- **High Scalability**: Designed to scale effortlessly with increasing loads, suitable for high-traffic environments.
- **Seamless Integration**: Designed to integrate effortlessly with other microservices within the NoteList application.
- **API Documentation**: Integrated Swagger documentation provides an interactive interface to explore the API.

As an integral part of the NoteList application, TaskService ensures a responsive, reliable, and efficient backend for managing tasks, enhancing the overall user experience.

## Technologies Used

- **Spring Boot**: The backbone framework for the application, ensuring a robust and streamlined development experience.
- **Spring WebFlux**: A reactive framework that facilitates the development of asynchronous and event-driven web applications.
- **Spring Data R2DBC**: Enables reactive interactions with relational databases, fully compatible with Spring WebFlux.
- **H2 Database**: An in-memory database used for development and testing, promoting quick and efficient prototyping.
- **PostgreSQL**: A robust, production-ready relational database supported by the service for scalable data management.
- **R2DBC H2 Driver**: Provides reactive database connectivity to the H2 database.
- **R2DBC PostgreSQL Driver**: Enables reactive interaction with PostgreSQL databases.
- **SpringDoc OpenAPI**: Automatically generates detailed API documentation, adhering to OpenAPI standards.
- **Reactor Test**: A toolkit for testing reactive streams within the Spring WebFlux environment.
- **Spring Boot Test**: Comprehensive testing support, including both unit and integration tests for Spring Boot applications.

## Getting Started

### Prerequisites

- **Java 17** or later
- **Maven 3.6** or later
- **Git** (for version control)
- **PostgreSQL** (for production setup)

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
    - The API is available at `http://localhost:8080/api/users`.
    - Swagger API documentation can be accessed at `http://localhost:8080/webjars/swagger-ui/index.html`.

## API Endpoints

- **`POST /api/users`**: Create a new user.
- **`GET /api/users/{id}`**: Retrieve a user by ID.
- **`GET /api/users`**: Retrieve all users.
- **`PUT /api/users/{id}`**: Update an existing user by ID.
- **`DELETE /api/users/{id}`**: Delete a user by ID.
