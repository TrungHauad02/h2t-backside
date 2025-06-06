<div align="center">

# 🌟 H2T English - Backend 🌟

<p align="center">
  <a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/Spring_Boot-3.4.1-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot"></a>
  <a href="https://www.java.com/"><img src="https://img.shields.io/badge/Java-18-007396?style=for-the-badge&logo=java&logoColor=white" alt="Java"></a>
  <a href="https://www.mysql.com/"><img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"></a>
  <a href="https://jwt.io/"><img src="https://img.shields.io/badge/JWT-Auth-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white" alt="JWT"></a>
  <a href="https://redis.io/"><img src="https://img.shields.io/badge/Redis-Caching-DC382D?style=for-the-badge&logo=redis&logoColor=white" alt="Redis"></a>
</p>

<p align="center">
  <em>A comprehensive RESTful API backend powering the H2T English learning platform with secure authentication, content management, and advanced assessment capabilities</em>
</p>

<img src="https://cdn-icons-png.flaticon.com/512/8661/8661468.png" alt="H2T English Backend" height="200px" width="200px" style="margin: 20px 0;">

</div>

## 📖 Table of Contents

- [🌟 H2T English - Backend 🌟](#-h2t-english---backend-)
    - [📖 Table of Contents](#-table-of-contents)
    - [🚀 Features](#-features)
        - [👤 User Management and Authentication](#-user-management-and-authentication)
        - [🔑 Security](#-security)
        - [📚 Educational Content Management](#-educational-content-management)
        - [🧠 Speech and Text Processing](#-speech-and-text-processing)
        - [📊 Assessment Engine](#-assessment-engine)
        - [📝 TOEIC Preparation](#-toeic-preparation)
        - [🛣️ Learning Route Management](#️-learning-route-management)
    - [💻 Technology Stack](#-technology-stack)
        - [Core Framework](#core-framework)
        - [Security](#security)
        - [Data Access](#data-access)
        - [Speech and Text Processing](#speech-and-text-processing)
        - [Documentation and Validation](#documentation-and-validation)
        - [Utilities](#utilities)
    - [🏁 API Endpoints](#-api-endpoints)
        - [Authentication and User Management](#authentication-and-user-management)
        - [Learning Content](#learning-content)
        - [Tests and Assessments](#tests-and-assessments)
        - [Speech and Text Processing](#speech-and-text-processing-1)
        - [Learning Routes](#learning-routes)
    - [📂 Project Structure](#-project-structure)
    - [🏛️ Database Schema](#️-database-schema)
        - [Core Entities](#core-entities)
        - [Educational Content](#educational-content)
        - [Assessment](#assessment)
        - [Routes and Progress](#routes-and-progress)
    - [🔒 Security Implementation](#-security-implementation)
        - [Authentication Flow](#authentication-flow)
        - [Role-Based Access Control](#role-based-access-control)
        - [Token Management](#token-management)
    - [⚙️ Error Handling](#️-error-handling)
        - [Global Exception Handling](#global-exception-handling)
        - [Standardized Response Format](#standardized-response-format)
        - [Error Logging](#error-logging)
    - [🏗️ Getting Started](#️-getting-started)
        - [Prerequisites](#prerequisites)
        - [Installation](#installation)
        - [Configuration](#configuration)
        - [Running the Application](#running-the-application)
    - [📚 API Documentation](#-api-documentation)
    - [👨‍🎓 Team](#-team)

## 🚀 Features

### 👤 User Management and Authentication

- **JWT-based Authentication**: Secure token-based authentication system
- **OAuth2 Integration**: Support for Google authentication
- **Refresh Token Mechanism**: Enhanced session management with secure token rotation
- **Password Reset**: Email-based password recovery with OTP verification
- **Profile Management**: Comprehensive user data handling with CRUD operations

### 🔑 Security

- **Role-Based Authorization**: Fine-grained access control for different user roles (Student, Teacher, Admin)
- **Secure Password Storage**: Password encryption using Spring Security's BCrypt encoder
- **Token Blacklisting**: Protection against compromised tokens via Redis-based blacklist
- **Input Validation**: Request validation using Jakarta Validation framework
- **Secure Headers**: Implementation of security headers for protection against common web vulnerabilities

### 📚 Educational Content Management

- **Diverse Learning Materials**: Support for various types of educational content:
    - Grammar lessons
    - Vocabulary topics
    - Reading materials
    - Listening exercises
    - Speaking practice
    - Writing activities
- **Lesson Questions and Answers**: Structured format for interactive learning materials
- **Preparation Activities**: Support for different types of preparation exercises:
    - Word-sentence matching
    - Word classification
    - Sentence construction

### 🧠 Speech and Text Processing

- **Text-to-Speech Conversion**: Convert text to natural-sounding speech
- **Speech-to-Text Processing**: Transcribe spoken language to text
- **Voice Variety**: Support for different voices and speech styles
- **Audio File Handling**: Process uploaded audio files for transcription

### 📊 Assessment Engine

- **Comprehensive Testing System**: Support for different types of assessments:
    - Mixed skill tests
    - Reading tests
    - Listening tests
    - Speaking tests
    - Writing tests
- **Question Management**: Flexible question and answer handling
- **Answer Submission**: Record and evaluate user test submissions
- **Performance Analytics**: Track and analyze user test performance

### 📝 TOEIC Preparation

- **Full TOEIC Structure**: Complete implementation of all TOEIC test parts:
    - Part 1: Photographs
    - Part 2: Question-Response
    - Parts 3 & 4: Conversations and Talks
    - Part 5: Incomplete Sentences
    - Part 6: Text Completion
    - Part 7: Reading Comprehension
- **Score Tracking**: Record and analyze TOEIC test attempts
- **Competitive Testing**: Timed competition tests with rankings

### 🛣️ Learning Route Management

- **Custom Learning Paths**: Create and manage educational routes
- **Route Nodes**: Organize different content types within learning routes
- **Sequenced Learning**: Structure content in progressive learning sequences

## 💻 Technology Stack

### Core Framework
- **Spring Boot 3.4.1**: Modern, production-ready framework for building stand-alone applications
- **Java 18**: Latest Java features for enhanced development
- **Spring MVC**: Web layer implementation with REST controllers
- **Spring Data**: Data access abstraction

### Security
- **Spring Security**: Comprehensive security framework
- **Nimbus JOSE JWT (9.37.2)**: JWT implementation for token-based authentication
- **OAuth2 Client**: Authentication with third-party providers (Google)
- **Spring Security Crypto**: Cryptographic utilities

### Data Access
- **Spring Data JPA**: JPA-based repositories
- **Spring Data JDBC**: Low-level database access
- **MySQL Connector (8.0.33)**: Database connectivity
- **Spring Data Redis**: Caching and token blacklisting

### Speech and Text Processing
- **Spring Mail**: Email service for OTP and notifications
- **Text-to-Speech API Integration**: Convert text to speech
- **Speech-to-Text API Integration**: Transcribe audio to text

### Documentation and Validation
- **Jakarta Validation**: Input validation framework
- **Spring Boot Starter Validation**: Validation infrastructure
- **MapStruct (1.6.3)**: Object mapping between DTOs and entities

### Utilities
- **Lombok (1.18.36)**: Reduce boilerplate code
- **Jackson Datatype JSR310 (2.18.2)**: Java 8 date/time serialization
- **Spring DevTools**: Development productivity tools

## 🏁 API Endpoints

The H2T English backend provides a comprehensive set of RESTful endpoints organized by functionality.

### Authentication and User Management

- **Authentication**:
    - `POST /api/auth/login`: Authenticate user and get access token
    - `POST /api/auth/login/google`: Login with Google OAuth
    - `POST /api/auth/logout`: Logout and invalidate token
    - `GET /api/auth/validate`: Validate access token
    - `POST /api/auth/refresh-token`: Refresh access token

- **User Management**:
    - `GET /api/users/{id}`: Get user by ID
    - `POST /api/users`: Create new user
    - `PUT /api/users/{id}`: Update user
    - `PATCH /api/users/{id}`: Partial update user
    - `DELETE /api/users/{id}`: Delete user
    - `GET /api/users`: Search users with filters

- **Password Management**:
    - `POST /api/users/send-otp`: Send OTP for password reset
    - `POST /api/users/verify-otp`: Verify OTP code
    - `POST /api/users/reset-password`: Reset password with verified OTP

### Learning Content

- **Grammar**:
    - `GET /api/grammars/{id}`: Get grammar by ID
    - `POST /api/grammars`: Create grammar
    - `PUT /api/grammars/{id}`: Update grammar
    - `DELETE /api/grammars/{id}`: Delete grammar
    - `GET /api/grammars`: Get grammars with filters
    - `GET /api/grammars/questions`: Get questions for a grammar

- **Vocabulary**:
    - `GET /api/vocabularies/{id}`: Get vocabulary by ID
    - `POST /api/vocabularies`: Create vocabulary
    - `PUT /api/vocabularies/{id}`: Update vocabulary
    - `DELETE /api/vocabularies/{id}`: Delete vocabulary
    - `GET /api/vocabularies`: Get vocabularies with filters by topic

- **Topics**:
    - `GET /api/topics/{id}`: Get topic by ID
    - `POST /api/topics`: Create topic
    - `PUT /api/topics/{id}`: Update topic
    - `DELETE /api/topics/{id}`: Delete topic
    - `GET /api/topics`: Get topics with filters
    - `GET /api/topics/questions`: Get questions for a topic

- **Reading**:
    - `GET /api/readings/{id}`: Get reading by ID
    - `POST /api/readings`: Create reading
    - `PUT /api/readings/{id}`: Update reading
    - `DELETE /api/readings/{id}`: Delete reading
    - `GET /api/readings`: Get readings with filters
    - `GET /api/readings/questions`: Get questions for a reading

- **Listening**:
    - `GET /api/listenings/{id}`: Get listening by ID
    - `POST /api/listenings`: Create listening
    - `PUT /api/listenings/{id}`: Update listening
    - `DELETE /api/listenings/{id}`: Delete listening
    - `GET /api/listenings`: Get listenings with filters
    - `GET /api/listenings/questions`: Get questions for a listening

- **Speaking**:
    - `GET /api/speakings/{id}`: Get speaking by ID
    - `POST /api/speakings`: Create speaking
    - `PUT /api/speakings/{id}`: Update speaking
    - `DELETE /api/speakings/{id}`: Delete speaking
    - `GET /api/speakings`: Get speakings with filters

- **Writing**:
    - `GET /api/writings/{id}`: Get writing by ID
    - `POST /api/writings`: Create writing
    - `PUT /api/writings/{id}`: Update writing
    - `DELETE /api/writings/{id}`: Delete writing
    - `GET /api/writings`: Get writings with filters

### Tests and Assessments

- **Regular Tests**:
    - `GET /api/tests/{id}`: Get test by ID
    - `POST /api/tests`: Create test
    - `PUT /api/tests/{id}`: Update test
    - `DELETE /api/tests/{id}`: Delete test
    - `GET /api/tests`: Get tests with filters

- **Competition Tests**:
    - `GET /api/competition-tests/{id}`: Get competition test by ID
    - `POST /api/competition-tests`: Create competition test
    - `PUT /api/competition-tests/{id}`: Update competition test
    - `DELETE /api/competition-tests/{id}`: Delete competition test
    - `GET /api/competition-tests`: Get competition tests with filters

- **TOEIC Tests**:
    - `GET /api/toeic/{id}`: Get TOEIC test by ID
    - `POST /api/toeic`: Create TOEIC test
    - `PUT /api/toeic/{id}`: Update TOEIC test
    - `DELETE /api/toeic/{id}`: Delete TOEIC test
    - `GET /api/toeic`: Get TOEIC tests with filters

- **Test Submissions**:
    - `POST /api/submit-tests`: Submit test
    - `GET /api/submit-tests/{id}`: Get test submission
    - `GET /api/submit-tests/stats`: Get test submission statistics

### Speech and Text Processing

- **Text-to-Speech**:
    - `GET /api/text-to-speech/convert`: Convert text to speech audio
    - `GET /api/text-to-speech/voices`: Get available voices

- **Speech-to-Text**:
    - `POST /api/speech-to-text/convert`: Convert speech audio to text
    - `POST /api/speech-to-text/convert-base64`: Convert base64 encoded audio to text

### Learning Routes

- **Routes**:
    - `GET /api/routes/{id}`: Get route by ID
    - `POST /api/routes`: Create route
    - `PUT /api/routes/{id}`: Update route
    - `DELETE /api/routes/{id}`: Delete route
    - `GET /api/routes`: Get routes by owner ID with filters

- **Route Nodes**:
    - `GET /api/routeNodes/{id}`: Get route node by ID
    - `POST /api/routeNodes`: Create route node
    - `PUT /api/routeNodes/{id}`: Update route node
    - `DELETE /api/routeNodes/{id}`: Delete route node

## 📂 Project Structure

The project follows a well-organized layered architecture based on Spring Boot best practices:

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── englishweb/
│   │           └── h2t_backside/
│   │               ├── config/                # Application configuration
│   │               ├── controller/            # REST controllers
│   │               │   ├── lesson/            # Controllers for educational content
│   │               │   └── test/              # Controllers for assessments
│   │               ├── dto/                   # Data transfer objects
│   │               │   ├── enumdto/           # Enum DTOs
│   │               │   ├── filter/            # Filter DTOs
│   │               │   ├── lesson/            # Lesson DTOs
│   │               │   ├── response/          # Response DTOs
│   │               │   ├── security/          # Security DTOs
│   │               │   └── test/              # Test DTOs
│   │               ├── exception/             # Custom exceptions
│   │               ├── mapper/                # MapStruct mappers
│   │               ├── model/                 # Domain models
│   │               │   ├── abstractmodel/     # Abstract base models
│   │               │   ├── enummodel/         # Enum models
│   │               │   ├── features/          # Special feature models
│   │               │   ├── interfacemodel/    # Model interfaces
│   │               │   ├── lesson/            # Educational content models
│   │               │   ├── log/               # Logging models
│   │               │   └── test/              # Assessment models
│   │               ├── repository/            # Data repositories
│   │               ├── security/              # Security configuration
│   │               ├── service/               # Business logic
│   │               │   ├── feature/           # Feature services
│   │               │   ├── impl/              # Service implementations
│   │               │   ├── lesson/            # Lesson services
│   │               │   └── test/              # Test services
│   │               ├── util/                  # Utility classes
│   │               └── H2tBacksideApplication.java  # Application entry point
│   └── resources/
│       ├── application.properties             # Application properties
│       ├── application-dev.properties         # Development properties
│       └── application-prod.properties        # Production properties
└── test/                                      # Test files
    └── java/
        └── com/
            └── englishweb/
                └── h2t_backside/               # Test classes
```

## 🏛️ Database Schema

The H2T English backend utilizes a comprehensive database schema designed to support all educational and assessment features.

### Core Entities

- **User**: Central entity for user management with role-based access control
    - Attributes: id, name, email, password, avatar, role, level, phoneNumber, dateOfBirth, refreshToken
    - Relations: One-to-many with Route, SubmitTest, SubmitToeic, SubmitCompetition

- **AbstractBaseEntity**: Base class for all entities
    - Attributes: id, status, createdAt, updatedAt

- **ErrorLog**: Records system errors for monitoring
    - Attributes: id, message, errorCode, timestamp

- **UpdateLog**: Tracks entity changes for auditing
    - Attributes: id, userId, targetId, targetTable, timestamp, action

### Educational Content

- **AbstractLessonEntity**: Base class for educational content
    - Attributes: title, image, description, views, routeNodeId

- **Grammar**: Grammar lessons with definitions and examples
    - Attributes: file, definition, example, tips, questions

- **Topic**: Vocabulary topic containers
    - Attributes: questions
    - Relations: One-to-many with Vocabulary

- **Vocabulary**: Individual vocabulary items
    - Attributes: example, image, word, meaning, phonetic, wordType, topicId

- **Reading**: Reading comprehension materials
    - Attributes: file, questions, preparationId

- **Listening**: Listening exercises with audio files
    - Attributes: audio, transcript, questions, preparationId

- **Speaking**: Speaking practice activities
    - Attributes: topic, duration, preparationId
    - Relations: One-to-many with SpeakingConversation

- **Writing**: Writing exercises
    - Attributes: topic, file, paragraph, preparationId, tips
    - Relations: One-to-many with WritingAnswer

- **Preparation**: Preparatory activities for lessons
    - Attributes: title, tip, questions, type

### Assessment

- **Test**: Base test entity
    - Attributes: title, description, duration, type, parts

- **CompetitionTest**: Competition-based assessments
    - Attributes: title, duration, startTime, endTime, parts

- **Toeic**: TOEIC test structure
    - Attributes: title, duration, questionsPart1-7

- **Question**: General question entity
    - Attributes: content, explanation
    - Relations: One-to-many with Answer

- **Answer**: Answer options for questions
    - Attributes: content, correct, questionId

- **SubmitTest**: Records test submissions
    - Attributes: score, userId, testId, comment

- **SubmitToeic**: Records TOEIC test submissions
    - Attributes: score, comment, toeicId, userId

- **SubmitCompetition**: Records competition submissions
    - Attributes: score, userId, testId

### Routes and Progress

- **Route**: Learning pathways
    - Attributes: title, image, description, ownerId
    - Relations: One-to-many with RouteNode

- **RouteNode**: Node in a learning route
    - Attributes: nodeId, type, serial, routeId

## 🔒 Security Implementation

The H2T English backend employs a robust, multi-layered security approach.

### Authentication Flow

1. **Initial Authentication**:
    - User submits credentials via `/api/auth/login` or OAuth via `/api/auth/login/google`
    - System validates credentials and generates access and refresh tokens
    - Tokens are returned to the client in `AuthenticateDTO`

2. **Access Token Usage**:
    - Client includes access token in `Authorization` header for secure endpoints
    - Token is validated via JwtUtil for each request
    - Token expiration is checked

3. **Token Refresh**:
    - When access token expires, client uses refresh token to request a new one
    - Refresh token is validated and a new access token is issued
    - Original refresh token can be maintained or rotated based on security policy

4. **Logout Process**:
    - Client sends refresh token to `/api/auth/logout`
    - Token is invalidated and added to blacklist in Redis
    - Any further attempts to use the token are rejected

### Role-Based Access Control

- **Student**: Basic access to learning materials and tests
- **Teacher**: Content creation and management capabilities
- **Teacher Admin**: Extended teaching capabilities and moderation
- **Admin**: Full system access and administration

### Token Management

- **Access Tokens**: Short-lived JWTs for API access
- **Refresh Tokens**: Longer-lived tokens stored in database for session management
- **Blacklisting**: Revoked tokens tracked in Redis to prevent reuse
- **Token Validation**: Comprehensive validation including signature, expiration, and blacklist check

## ⚙️ Error Handling

The H2T English backend implements a comprehensive error handling strategy to ensure smooth operation and debugging.

### Global Exception Handling

The system utilizes Spring's `@ControllerAdvice` and `@ExceptionHandler` mechanisms to centrally manage exceptions:

- **Custom Exceptions**: Domain-specific exceptions with meaningful messages
- **Validation Exceptions**: Specialized handling for input validation failures
- **Security Exceptions**: Authentication and authorization failure handling
- **Runtime Exceptions**: Graceful handling of unexpected errors

### Standardized Response Format

All API responses follow a consistent format using `ResponseDTO`:

```java
public class ResponseDTO<T> {
    private ResponseStatusEnum status;  // SUCCESS or FAIL
    private String message;             // Human-readable message
    private T data;                     // Response payload (when successful)
}
```

This ensures that clients receive consistent error information regardless of the error type.

### Error Logging

The system maintains detailed error logs for monitoring and debugging:

- **Error Log Entity**: Persists error details to database
- **Structured Logging**: Uses SLF4J with structured log formats
- **Log Levels**: Appropriate log levels for different severity errors
- **Contextual Information**: Includes request context for debugging

## 🏗️ Getting Started

### Prerequisites

- **Java Development Kit (JDK) 18** or higher
- **Maven 3.6** or higher
- **MySQL 8.0** or higher
- **Redis** server (for caching and token management)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-organization/h2t-backside.git
   cd h2t-backside
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

### Configuration

Configure your application in `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/api/v1

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/h2t_english
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Security Configuration
security.jwt.secret=your_jwt_secret_key
security.jwt.expiration=3600000
security.jwt.refresh.expiration=86400000

# OAuth2 Configuration
spring.security.oauth2.client.registration.google.client-id=your_google_client_id
spring.security.oauth2.client.registration.google.client-secret=your_google_client_secret

# Redis Configuration
spring.redis.host=localhost
spring.redis.port=6379

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### Running the Application

```bash
# Development mode
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Production mode
mvn spring-boot:run -Dspring-boot.run.profiles=prod

# OR using the JAR file
java -jar -Dspring.profiles.active=prod target/h2t-backside-0.0.1-SNAPSHOT.jar

# API Speech to text
docker run -d -p 9000:9000 -e ASR_MODEL=base -e ASR_ENGINE=openai_whisper onerahmet/openai-whisper-asr-webservice:latest

# MinIO Server - store data
docker run -p 9000:9000 -p 9001:9001 --name minio \
  -v /path/to/minio/data:/data \
  -e "MINIO_ROOT_USER=minioadmin" \
  -e "MINIO_ROOT_PASSWORD=minioadmin" \
  quay.io/minio/minio server /data --console-address ":9001"
```

# Vosk scoring speech

```bash
docker run -d -p 5000:5000 trunghauad02/speech-scoring-api
```

## 📚 API Documentation

API documentation can be generated and accessed using SpringDoc OpenAPI:

1. Add the SpringDoc dependency to `pom.xml`:
   ```xml
   <dependency>
       <groupId>org.springdoc</groupId>
       <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
       <version>2.3.0</version>
   </dependency>
   ```

2. Configure in `application.properties`:
   ```properties
   springdoc.api-docs.path=/api-docs
   springdoc.swagger-ui.path=/swagger-ui.html
   ```

3. Access the documentation at:
   ```
   http://localhost:8080/api/v1/swagger-ui.html
   ```

## 👨‍🎓 Team

<div align="center">
<img src="https://cdn-icons-png.flaticon.com/512/3898/3898082.png" width="100" alt="Team">

<h3>Developed by three students from K21 cohort at Ho Chi Minh City University of Technology and Education (HCMUTE)</h3>

 <table>
 <tr>
 <td align="center">
 <img src="https://avatars.githubusercontent.com/u/97101001?s=400&u=c2e995d2acff0cb120417bf042d6c1205bd4bbb4&v=4" width="100" alt="Developer Avatar">
 <br>
 <b>Nguyễn Trung Hậu</b>
 <br>
 <a href="https://github.com/TrungHauad02">GitHub</a>
 </td>
 <td align="center">
 <img src="https://avatars.githubusercontent.com/u/95125368" width="100" alt="Developer Avatar">
 <br>
 <b>Thái Thanh Hưng</b>
 <br>
 <a href="https://github.com/username2">GitHub</a>
 </td>
 <td align="center">
 <img src="https://avatars.githubusercontent.com/u/96189553" width="100" alt="Developer Avatar">
 <br>
 <b>Cáp Lê Hữu Tân</b>
 <br>
 <a href="https://github.com/username3">GitHub</a>
 </td>
 </tr>
 </table>
</div>

<div align="center">
 <hr>

 <p>
 <a href="#"><img src="https://img.shields.io/badge/H2T_English-Website-teal?style=for-the-badge" alt="Website"></a>
 <a href="mailto:contact@h2tenglish.com"><img src="https://img.shields.io/badge/Email-Contact-red?style=for-the-badge&logo=gmail" alt="Contact"></a>
 <a href="#"><img src="https://img.shields.io/badge/Report-Issues-yellow?style=for-the-badge&logo=github" alt="Issues"></a>
 </p>

 <p>© 2025 H2T English. All Rights Reserved.</p>

 <p>
 <img src="https://img.shields.io/badge/Made_with_❤️_in-Vietnam-red?style=flat-square&logo=vietnam" alt="Made in Vietnam">
 </p>
</div>