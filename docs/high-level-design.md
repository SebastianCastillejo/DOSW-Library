# High Level Design — DOSW-Library

## 1. Descripción General

DOSW-Library es un sistema de gestión de biblioteca que permite administrar libros, usuarios y préstamos. Está construido con Spring Boot 4.0.3 y Java 21, siguiendo una arquitectura en capas con soporte para persistencia dual (PostgreSQL y MongoDB Atlas).

## 2. Arquitectura General

El sistema sigue el patrón **MVC (Model-View-Controller)** con una separación clara en capas:

```
Cliente (Swagger / Frontend)
        ↓
[Capa de Seguridad - JWT]
        ↓
[Capa de Controladores - Controllers]
        ↓
[Capa de Servicios - Services]
        ↓
[Capa de Persistencia - Repository Port]
        ↓
[MongoDB Atlas] o [PostgreSQL]
```

## 3. Componentes Principales

### 3.1 Controladores (controller/)
Reciben las peticiones HTTP y devuelven respuestas en formato JSON. Cada entidad tiene su propio controlador:

- `BookController` — gestión de libros (CRUD)
- `UserController` — gestión de usuarios
- `LoanController` — gestión de préstamos
- `AuthController` — registro y login

Los controladores usan **DTOs** para recibir y enviar datos, y **Mappers** para convertir entre DTOs y modelos de dominio.

### 3.2 Servicios (core/service/)
Contienen la lógica de negocio del sistema:

- `BookService` — validaciones de copias disponibles, búsqueda por autor
- `UserService` — gestión de usuarios
- `LoanService` — creación de préstamos, devoluciones, validación de disponibilidad

Los servicios dependen de interfaces (`RepositoryPort`) y no de implementaciones concretas.

### 3.3 Modelos de Dominio (core/model/)
Representan las entidades del negocio usando Lombok:

- `Book` — título, autor, ISBN, copias totales y disponibles
- `User` — username, password, email, rol (USER / LIBRARIAN)
- `Loan` — bookId, userId, fecha de préstamo, fecha de devolución, estado (ACTIVE / RETURNED)

### 3.4 Excepciones (core/exception/)
Manejo centralizado de errores con `GlobalExceptionHandler` y excepciones personalizadas:

- `BookNotFoundException`
- `BookNotAvailableException`
- `LoanNotFoundException`
- `UserNotFoundException`

## 4. Persistencia Dual

El sistema soporta dos bases de datos mediante el patrón **Repository Port**:

```
BookService
    ↓ inyecta
BookRepositoryPort (interfaz)
    ├── @Profile("relational") → BookRepositoryJpaImpl → PostgreSQL
    └── @Profile("mongo")      → BookRepositoryMongoImpl → MongoDB Atlas
```

El perfil activo se configura en `application.yaml` o mediante la variable de entorno `SPRING_PROFILES_ACTIVE`.

### 4.1 Persistencia Relacional (PostgreSQL)
- Entidades JPA: `BookEntity`, `UserEntity`, `LoanEntity`
- Repositorios: extienden `JpaRepository`
- Mappers: convierten entre modelo de dominio y entidad JPA

### 4.2 Persistencia No Relacional (MongoDB Atlas)
- Documentos: `BookDocument`, `UserDocument`, `LoanDocument`
- Repositorios: extienden `MongoRepository`
- Mappers: convierten entre modelo de dominio y documento MongoDB

## 5. Seguridad

El sistema usa **JWT (JSON Web Tokens)** para autenticación y autorización:

- `AuthController` — endpoints `/auth/register` y `/auth/login`
- `JwtService` — generación y validación de tokens
- `JwtAuthenticationFilter` — intercepta cada petición y valida el token
- `SecurityConfiguration` — configura las rutas públicas y protegidas

### Roles
- `LIBRARIAN` — puede crear/eliminar libros y ver todos los préstamos
- `USER` — puede crear préstamos y devolver libros

## 6. CI/CD

### 6.1 GitHub Actions
Pipeline configurado en `.github/workflows/ci.yml` con 4 jobs:

```
build → test → analysis → deploy
```

- **build**: compila el proyecto con Maven
- **test**: ejecuta los tests con JUnit y Mockito
- **analysis**: análisis estático con Checkstyle y JaCoCo
- **deploy**: despliega el JAR a Azure App Service

Se dispara automáticamente en cada `pull_request` hacia `main`.

### 6.2 Azure App Service
- Plataforma: Linux, Java 21 SE
- URL: `https://librebia-e4f9fmhpcqcpctga.canadacentral-01.azurewebsites.net`
- Variables de entorno configuradas: `SERVER_PORT`, `SSL_ENABLED`, `SPRING_PROFILES_ACTIVE`, `SPRING_DATA_MONGODB_URI`

## 7. Diagrama de Arquitectura

```
┌─────────────────────────────────────────────────────────┐
│                      Cliente                            │
│              (Swagger UI / Frontend)                    │
└───────────────────────┬─────────────────────────────────┘
                        │ HTTP/HTTPS
┌───────────────────────▼─────────────────────────────────┐
│              Capa de Seguridad (JWT)                    │
│         JwtAuthenticationFilter + SecurityConfig        │
└───────────────────────┬─────────────────────────────────┘
                        │
┌───────────────────────▼─────────────────────────────────┐
│              Capa de Controladores                      │
│    BookController | UserController | LoanController     │
│              AuthController                             │
└───────────────────────┬─────────────────────────────────┘
                        │ DTOs + Mappers
┌───────────────────────▼─────────────────────────────────┐
│               Capa de Servicios                         │
│      BookService | UserService | LoanService            │
└───────────────────────┬─────────────────────────────────┘
                        │ Repository Port (interfaz)
┌───────────────────────▼─────────────────────────────────┐
│             Capa de Persistencia                        │
│  ┌──────────────────┐      ┌──────────────────────┐    │
│  │  JpaImpl         │      │  MongoImpl            │    │
│  │ @Profile("rel")  │      │ @Profile("mongo")     │    │
│  └────────┬─────────┘      └──────────┬────────────┘    │
└───────────┼────────────────────────────┼─────────────────┘
            │                            │
┌───────────▼──────────┐    ┌────────────▼───────────────┐
│     PostgreSQL        │    │      MongoDB Atlas          │
│   (localhost:5433)    │    │  (cluster0.0cw5yye...)     │
└──────────────────────┘    └────────────────────────────┘
```

## 8. Tecnologías Utilizadas

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Java | 21 | Lenguaje principal |
| Spring Boot | 4.0.3 | Framework principal |
| Spring Security | 7.0.3 | Autenticación y autorización |
| Spring Data JPA | 4.0.3 | Persistencia relacional |
| Spring Data MongoDB | 5.0.3 | Persistencia no relacional |
| PostgreSQL | 42.7.10 | Base de datos relacional |
| MongoDB Atlas | 5.6.3 | Base de datos no relacional |
| JWT (JJWT) | 0.11.5 | Tokens de autenticación |
| Lombok | - | Reducción de boilerplate |
| MapStruct | 1.5.5 | Mapeo entre objetos |
| SpringDoc OpenAPI | 2.8.5 | Documentación Swagger |
| JUnit 5 | - | Pruebas unitarias |
| Mockito | - | Mocking en pruebas |
| GitHub Actions | - | CI/CD |
| Azure App Service | - | Despliegue en la nube |