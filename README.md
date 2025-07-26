# Match Management API

A Spring Boot project for managing sports matches and associated odds.

## Features

- CRUD operations for Matches
- CRUD operations for Match Odds
- REST API with Swagger UI documentation
- Docker support for easy deployment

## Technologies

- Java 17
- Spring Boot
- Spring Data JPA
- H2 (in-memory DB)
- OpenAPI (Swagger)
- Docker

## How to Run (IntelliJ)

1. Clone the repository.
2. Open the project with IntelliJ.
3. Run `MatchManagementApiApplication.java`.
4. Access Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## How to Run (Docker)

1. Make sure Docker is installed and running.
2. From the project root, build and run:

```bash
docker-compose up --build
```

3. API will be available at `http://localhost:8080`

## Endpoints

- `/matches` - Manage matches
- `/odds` - Manage match odds

## DB

- if you run the app locally, you need to create a DB called matchdb
