### README

## Redis-based User Management Application

This Spring Boot application leverages Redis for managing user data. It provides configurations for Redis connections, CRUD operations for user data, and RESTful endpoints to interact with user information.

### Table of Contents

- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Components](#components)
  - [RedisConfig](#redisconfig)
  - [UserController](#usercontroller)
  - [UserDao](#userdao)
  - [User](#user)
- [API Endpoints](#api-endpoints)
- [Dependencies](#dependencies)

### Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yashivijayvargiya/SpringBoot_Redis/
   cd com.redis.example
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

### Configuration

The application uses Lettuce for Redis connection management and Spring Data Redis for data access. The `RedisConfig` class configures the connection factory and the Redis template.

### Components

#### RedisConfig

Located in `com.api.redis.config`, this class is annotated with `@Configuration` to indicate that it contains bean definitions.


#### UserController

Located in `com.api.redis.controller`, this class is annotated with `@RestController` and `@RequestMapping("/users")` to define REST endpoints for user operations.

- **`createUser`**: Generates a UUID for the user and saves the user.
- **`getUser`**: Retrieves a user by their ID.
- **`getAll`**: Retrieves all users.
- **`delete`**: Deletes a user by their ID.

#### UserDao

Located in `com.api.redis.dao`, this class is annotated with `@Repository` to indicate it is a DAO component.

- **`save`**: Saves a user in the Redis hash .
- **`get`**: Retrieves a user from the Redis hash by ID.
- **`findAll`**: Retrieves all users from the Redis hash.
- **`delete`**: Deletes a user from the Redis hash by ID.

#### User

Located in `com.api.redis.models`, this class represents the user entity and implements `Serializable`.

- Fields: `userId`, `name`, `phone`, `email`.
- Implements getter and setter methods, constructors, and a `toString` method.

### API Endpoints

- **Create User**: `POST /users`
  - Request Body: JSON object containing `name`, `phone`, and `email`.

- **Retrieve User**: `GET /users/{userId}`
  - Path Variable: `userId`

- **Retrieve All Users**: `GET /users`

- **Delete User**: `DELETE /users/{userId}`
  - Path Variable: `userId`

### Dependencies

Ensure you have the given dependencies in your `pom.xml`

This setup provides a robust foundation for managing user data with Redis in a Spring Boot application.
