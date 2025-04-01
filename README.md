# Pathfinder AI

A Spring Boot application that uses OpenAI to help users search hotel data using natural language.

## Overview

Pathfinder AI allows users to search through hotel data using simple, conversational language. Instead of filtering through complex search parameters, users can simply type what they're looking for, and the OpenAI integration will understand and find relevant results.

## Features

- **Natural Language Search**: Ask for hotels in plain English
- **Secure Authentication**: Login system with JWT and Spring Security
- **User-Friendly Interface**: Built with Thymeleaf templates
- **Personalized Results**: Save preferences and get tailored recommendations

## Prerequisites

- Java 17+
- Maven or Gradle
- OpenAI API key

## Dependencies

```xml
<!-- Spring Boot Starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>

<!-- Thymeleaf -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<!-- OpenAI Client -->
<dependency>
    <groupId>com.theokanning.openai-gpt3-java</groupId>
    <artifactId>service</artifactId>
    <version>0.14.0</version>
</dependency>
```

## Configuration

Add your OpenAI API key to `application.properties`:

```properties
# OpenAI Configuration (NEVER commit your actual API key)
openai.api.key=${OPENAI_API_KEY}

# JWT Configuration
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000

# Database Configuration
spring.datasource.url=jdbc:h2:mem:hoteldb
```

## How It Works

1. Users log in through a secure authentication system
2. They enter natural language queries like "I need a pet-friendly hotel in Miami with a pool"
3. The application sends the query to OpenAI to interpret the user's requirements
4. Results are displayed in an easy-to-browse format using Thymeleaf templates
