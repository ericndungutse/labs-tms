# Task Management System (TMS)

A robust Task Management System built with Java Servlets and PostgreSQL, providing RESTful API endpoints and web interface for managing tasks.

## Features

### 1. Task Management

- Create new tasks
- View all tasks
- View detailed information for a specific task
- Update existing tasks
- Delete tasks

### 2. API Endpoints

#### REST API

- `GET /api/tasks` - Retrieve all tasks
- `GET /api/tasks/{id}` - Get a specific task by ID
- `POST /api/tasks` - Create a new task
- `PUT /api/tasks/{id}` - Update an existing task
- `DELETE /api/tasks/{id}` - Delete a task

#### Web Servlets

- `/tasks` - View all tasks
- `/task/details` - View task details
- `/task/add` - Add a new task
- `/task/update` - Update task
- `/task/delete` - Delete task

## Technology Stack

- **Java 21**
- **Jakarta Servlet 6.1.0**
- **PostgreSQL 42.7.5**
- **Jackson 2.15.2** for JSON processing
- **JSTL 3.0.0** for JSP support
- **SLF4J/Logback** for logging
- **JUnit 5.11.0** for testing

## Prerequisites

- Java Development Kit (JDK) 21
- Maven
- PostgreSQL database
- Java EE compatible application server (e.g., Tomcat)

## Setup and Installation

1. Clone the repository
2. Configure PostgreSQL database
3. Update database connection settings in your application configuration
4. Build the project:
   ```bash
   mvn clean install
   ```
5. Deploy the generated WAR file to your application server

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/ndungutse/tms/
│   │       ├── controller/    # Servlets and API controllers
│   │       ├── service/       # Business logic
│   │       ├─ repository/    # Data access layer
│   │       ├── exception/     # Custom exceptions
│   │       ├── dto/          # Data Transfer Objects
│   │       └── utils/        # Utility classes
│   ├── resources/            # Configuration files
│   └── webapp/              # Web resources and JSP pages
└── test/                    # Test cases
```


## Logging

The application uses SLF4J with Logback for logging. Configuration can be modified in the Logback configuration file.

## Architecture
![Amalitech-Bank Managment System-Design-TSM - Architecture drawio](https://github.com/user-attachments/assets/7b05d326-d2d4-4c81-9eb3-ab6fa3c71a0b)

## Class Diagram
![Amalitech-Bank Managment System-Design-TMS Class Diagram drawio](https://github.com/user-attachments/assets/645f2008-77b1-4786-9127-d1da28bb1e8d)



