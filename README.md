# 🏥 GesundKlinik – Work in progress 🚧
![Java](https://img.shields.io/badge/Java-17+-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen?logo=spring)
![Status](https://img.shields.io/badge/status-in%20development-yellow)
<a href="https://gesundklinik-springboot-api.onrender.com/swagger-ui/index.html" target="_blank"><img src="https://img.shields.io/badge/Swagger%20UI-Online-brightgreen?logo=swagger" /></a>


GesundKlinik is a clinical appointment scheduling system built with Java and Spring Boot, applying Clean Architecture and Domain-Driven Design (DDD) principles to ensure a modular, testable, and maintainable codebase.

The application provides endpoints for managing doctors, patients, and appointments through a RESTful API. Business rules are enforced with robust validation logic using the Chain of Responsibility (CoR) pattern. It also follows the Command Query Separation (CQS) principle, cleanly separating write operations (commands) from read operations (queries), promoting a clean and organized service structure.

Its layered and modular design ensures a clear separation of concerns, while security is handled via JWT and Spring Security. Full API documentation is available through Swagger (SpringDoc OpenAPI).

📘 [View API Documentation (Swagger UI)](https://gesundklinik-springboot-api.onrender.com/swagger-ui/index.html)

---

## :clipboard: Features

- Register, update, and manage doctors and patients

- Schedule and cancel appointments

- Business rule validation using chained validators

- Modular architecture with Command/Query separation

- API documentation with Swagger (SpringDoc OpenAPI)

- JWT-based authentication and authorization via Spring Security


## 📂 Modular Structure:

- `modules/doctor` – Doctor module
- `modules/patient` – Patient module
- `modules/appointment` – Appointment module
- `shared/` – Shared components (DTOs, exceptions, security, config)


## ⚙️ Tech Stack

```Java 17+```

```Spring Boot```

```Spring Data JPA```

```MySQL```

```MapStruct```

```Spring Security```

```Swagger (SpringDoc OpenAPI)```


## 🛠️ Tools

```Maven``` – Dependency management

```Flyway``` – Database migrations

```Insomnia / Postman``` – API testing

```Lombok``` – Boilerplate code reduction


## 🧪 Testing

```JUnit 5``` – Testing framework used for unit, integration, and behavioral tests

```Mockito``` – Mocking dependencies for isolation and test control


## 🔄 Appointment Scheduling Flow

The diagram below illustrates the complete flow of scheduling an appointment, from receiving a request to saving it in the database and returning a response:

``` mermaid
graph TD
    A[Receive AppointmentScheduleRequest DTO]
    B[Controller handles request]
    C[Mapper converts to Appointment entity]
    D[Call AppointmentService schedule method]
    E[Validators execute business rules]
    F{Doctor ID is present?}
    G[Choose doctor by speciality]
    H[Save Appointment in database]
    I[Mapper returns AppointmentDetailsResponse DTO]

    A --> B
    B --> C
    C --> D
    D --> E
    E --> F
    F -- Yes --> H
    F -- No --> G
    G --> H
    H --> I

```
## 📐 Domain Class Diagram (UML)
The diagram below provides an overview of the main domain entities in the system and their relationships. It highlights how Appointments are associated with Doctors and Pacients, and how both entities embed the Address object for contact and location information.

This structural representation helps clarify the design of the application's core domain model:

``` mermaid
classDiagram
    class Doctor {
        Long id
        String name
        String email
        String crm
        LocalDate birthDate
        String phone
        Speciality speciality
        Address address
        Boolean active
    }

    class Patient {
        Long id
        String name
        String email
        String cpf
        LocalDate birthDate
        String phone
        Address address
        Boolean active
    }

    class Appointment {
        Long id
        LocalDateTime date
        Speciality speciality
        CancellationReason cancellationReason
    }

    class Address {
        String street
        String number
        String city
        String state
        String zipCode
    }

    Doctor --> Address : embeds
    Patient --> Address : embeds
    Appointment --> Doctor : references
    Appointment --> Patient : references

```
