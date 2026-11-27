# Task Management API

A Spring Boot REST API for creating tasks, listing them, updating their status, and deleting them. Task data is stored in MySQL running in Docker.

## Stack

- Java (Maven target 17)
- Spring Boot, Spring Web MVC, Spring Data JPA, Hibernate
- MySQL 8.4, Docker Compose
- Maven Wrapper, Spring Boot DevTools, Postman

## Run locally (Windows PowerShell)

Install a compatible JDK (the project was developed with Java 25), start Docker Desktop, and open the repository directory.

Choose local passwords and set them in the same terminal before launching Docker and Spring Boot:

```powershell
$env:DB_PASSWORD = 'choose-a-taskapp-password'
$env:MYSQL_ROOT_PASSWORD = 'choose-a-root-password'
docker compose up -d
docker compose logs -f mysql
```

Wait until MySQL reports that it is ready on port 3306, then press Ctrl+C to exit the log view.

```powershell
.\mvnw.cmd spring-boot:run
```

The API listens on port 8080, and MySQL is published on localhost port 3308. Hibernate creates or updates the tasks table. If starting from IntelliJ instead, configure the matching DB_PASSWORD environment variable in the Application run configuration. MySQL initializes passwords only for a fresh data volume; existing volumes require the previously configured credentials.

## API

| Method | Endpoint | Success |
| --- | --- | --- |
| GET | /api/tasks | 200 |
| GET | /api/tasks/{id} | 200 |
| POST | /api/tasks | 201 |
| PATCH | /api/tasks/{id}/status | 200 |
| DELETE | /api/tasks/{id} | 204 |

Example POST to http://localhost:8080/api/tasks with JSON:

```json
{"title":"Practice array reversal"}
```

Use the returned ID in a PATCH request to /api/tasks/{id}/status:

```json
{"status":"DONE"}
```

Valid statuses are TODO, IN_PROGRESS, and DONE. Blank or overly long titles and invalid statuses return 400. Missing task IDs return 404.

## Structure

- Task: JPA entity
- TaskRepository: database operations through Spring Data JPA
- TaskService: task lookup, listing, and creation logic
- TaskController: HTTP endpoints, including status updates and deletion

The project is a local backend learning project, tested manually using Postman. The included context-load test requires the configured MySQL instance and database password.
