# Email Service

This project is a Spring Boot application that provides an email sending service.

## Project Structure

README.md
```
com.backend.srv.email 
├── controller 
│ └── EmailController.java 
├── dto 
│ └── EmailRequest.java 
├── service 
│ └── EmailService.java 
└── EmailApplication.java
```

## Features

- RESTful API for sending emails
- Uses Spring Boot and JavaMailSender for email functionality

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven

### Running the application

1. Clone the repository
2. Navigate to the project directory
3. Run `mvn spring-boot:run`

The application will start and be available at `http://localhost:8080`

## API Endpoints

### Send Email

POST /api/email/send

Request body:

```json
{
  "to": "recipient@example.com",
  "subject": "Email Subject",
  "text": "Email Body"
}
```

### Configuration
Ensure you have set up the appropriate SMTP settings in your application.properties or application.yml file.

### Testing
Run mvn test to execute the unit tests.

### Built With
- Spring Boot
- JavaMail API

## Docker

### Build/Run

#### Network
```
docker network create images-network
```

#### Images
```
Repository: [images]()
```

#### Service
```
docker compose up --build -d
```

## Swagger
The Swagger documentation is available at http://localhost:8080/swagger-ui/index.html