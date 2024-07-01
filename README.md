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
   git clone https://github.com/yourusername/redis-user-management.git
   cd redis-user-management
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

```java
package com.api.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
```

- **`connectionFactory`**: Configures a Redis connection using Lettuce.
- **`redisTemplate`**: Configures a Redis template with key and value serializers.

#### UserController

Located in `com.api.redis.controller`, this class is annotated with `@RestController` and `@RequestMapping("/users")` to define REST endpoints for user operations.

```java
package com.api.redis.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.api.redis.dao.UserDao;
import com.api.redis.models.User;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDao userDao;

    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setUserId(UUID.randomUUID().toString());
        return userDao.save(user);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") String userId) {
        return userDao.get(userId);
    }

    @GetMapping
    public Map<Object, Object> getAll() {
        return userDao.findAll();
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") String userId) {
        userDao.delete(userId);
    }
}
```

- **`createUser`**: Generates a UUID for the user and saves the user.
- **`getUser`**: Retrieves a user by their ID.
- **`getAll`**: Retrieves all users.
- **`delete`**: Deletes a user by their ID.

#### UserDao

Located in `com.api.redis.dao`, this class is annotated with `@Repository` to indicate it is a DAO component.

```java
package com.api.redis.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.api.redis.models.User;

@Repository
public class UserDao {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String KEY = "User";

    public User save(User user) {
        redisTemplate.opsForHash().put(KEY, user.getUserId(), user);
        return user;
    }

    public User get(String userId) {
        return (User) redisTemplate.opsForHash().get(KEY, userId);
    }

    public Map<Object, Object> findAll() {
        return redisTemplate.opsForHash().entries(KEY);
    }

    public void delete(String userId) {
        redisTemplate.opsForHash().delete(KEY, userId);
    }
}
```

- **`save`**: Saves a user in the Redis hash.
- **`get`**: Retrieves a user from the Redis hash by ID.
- **`findAll`**: Retrieves all users from the Redis hash.
- **`delete`**: Deletes a user from the Redis hash by ID.

#### User

Located in `com.api.redis.models`, this class represents the user entity and implements `Serializable`.

```java
package com.api.redis.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class User implements Serializable {

    private String userId;
    private String name;
    private String phone;
    private String email;

    public User() {
        super();
    }

    public User(String userId, String name, String phone, String email) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

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

Ensure you have the following dependencies in your `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>io.lettuce.core</groupId>
        <artifactId>lettuce-core</artifactId>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

This setup provides a robust foundation for managing user data with Redis in a Spring Boot application.
