REDIS -> EMAIL VERIFICATION WITHOUT STORING IN DB

Awesome — here’s a **plug-and-play Spring Boot example** for “email-first verification” where you **don’t store the user until their email is verified**. It uses **Redis** for the temporary token store (with TTL) and **Spring Mail** to send the verification link.

---

# 1) Dependencies (Maven)

```xml
<!-- pom.xml -->
<dependencies>
  <!-- Web + Validation -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
  </dependency>

  <!-- JPA (for saving the verified user) + your DB driver -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
  <dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
  </dependency>
  <!-- Or use MySQL driver instead of H2 -->

  <!-- Redis (temp token store) -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
  </dependency>

  <!-- Email -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
  </dependency>
</dependencies>
```

---

# 2) Configuration (YAML)

```yaml
# application.yml
spring:
  datasource:
    url: jdbc:h2:mem:demo;DB_CLOSE_DELAY=-1
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

  data:
    redis:
      host: localhost
      port: 6379

  mail:
    host: smtp.example.com
    port: 587
    username: your-smtp-user
    password: your-smtp-pass
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true

app:
  verification:
    base-url: http://localhost:8080 # used to build verify link
    token-ttl-minutes: 15
```

> Replace SMTP config with your provider (Gmail/SES/SendGrid/etc.).
> For Redis: make sure `redis-server` is running locally (or point to your instance).

---

# 3) Domain + DTOs

```java
// src/main/java/com/example/auth/user/User.java
package com.example.auth.user;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String email;

  private String name;

  private Instant verifiedAt;

  // getters/setters
}
```

```java
// src/main/java/com/example/auth/user/UserRepository.java
package com.example.auth.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);
}
```

```java
// src/main/java/com/example/auth/api/RegisterRequest.java
package com.example.auth.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
  @NotBlank @Email
  private String email;

  private String name; // optional

  // getters/setters
}
```

---

# 4) Redis Config

```java
// src/main/java/com/example/auth/config/RedisConfig.java
package com.example.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {
  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(); // uses spring.data.redis.* properties
  }

  @Bean
  public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory cf) {
    return new StringRedisTemplate(cf);
  }
}
```

---

# 5) Email Service

```java
// src/main/java/com/example/auth/email/EmailService.java
package com.example.auth.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailService {
  private final JavaMailSender mailSender;

  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendVerificationEmail(String to, String verificationUrl) {
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setTo(to);
    msg.setSubject("Verify your email");
    msg.setText("Click the link to verify your email: " + verificationUrl);
    mailSender.send(msg);
  }
}
```

---

# 6) Registration / Verification Service

```java
// src/main/java/com/example/auth/service/RegistrationService.java
package com.example.auth.service;

import com.example.auth.api.RegisterRequest;
import com.example.auth.email.EmailService;
import com.example.auth.user.User;
import com.example.auth.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class RegistrationService {

  private final StringRedisTemplate redis;
  private final EmailService emailService;
  private final UserRepository userRepository;

  private final String baseUrl;
  private final long tokenTtlMinutes;

  public RegistrationService(
      StringRedisTemplate redis,
      EmailService emailService,
      UserRepository userRepository,
      @Value("${app.verification.base-url}") String baseUrl,
      @Value("${app.verification.token-ttl-minutes}") long tokenTtlMinutes
  ) {
    this.redis = redis;
    this.emailService = emailService;
    this.userRepository = userRepository;
    this.baseUrl = baseUrl;
    this.tokenTtlMinutes = tokenTtlMinutes;
  }

  public void startRegistration(RegisterRequest req) {
    final String email = req.getEmail().trim().toLowerCase();

    // Optional: prevent re-registering an already verified user
    if (userRepository.existsByEmail(email)) {
      // You can either silently succeed or throw an error
      // Here we just send link again (idempotent feel)
    }

    // Generate token
    String token = UUID.randomUUID().toString();

    // Store token -> email mapping in Redis with TTL
    String key = tokenKey(token);
    redis.opsForValue().set(key, email, Duration.ofMinutes(tokenTtlMinutes));

    // Build verification link
    String verifyUrl = String.format("%s/auth/verify?token=%s", baseUrl, token);

    // Send email
    emailService.sendVerificationEmail(email, verifyUrl);
  }

  public String verifyAndCreateUser(String token, String optionalName) {
    String key = tokenKey(token);
    String email = redis.opsForValue().get(key);

    if (email == null) {
      return "Invalid or expired token";
    }

    // Create user only now (email verified)
    User user = userRepository.findByEmail(email)
        .orElseGet(() -> {
          User u = new User();
          u.setEmail(email);
          return u;
        });

    if (optionalName != null && !optionalName.isBlank()) {
      user.setName(optionalName);
    }
    user.setVerifiedAt(Instant.now());

    userRepository.save(user);

    // Invalidate token
    redis.delete(key);

    return "Email verified and user created/updated successfully";
  }

  private String tokenKey(String token) {
    return "verify:email:" + token;
  }
}
```

---

# 7) Controller

```java
// src/main/java/com/example/auth/api/AuthController.java
package com.example.auth.api;

import com.example.auth.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final RegistrationService registrationService;

  public AuthController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  // Step 1: Accept email (no DB write)
  @PostMapping("/register")
  public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
    registrationService.startRegistration(request);
    return ResponseEntity.ok("Verification link sent to your email.");
  }

  // Step 2: Verify token (now write to DB)
  @GetMapping("/verify")
  public ResponseEntity<String> verify(@RequestParam String token,
                                       @RequestParam(required = false) String name) {
    String msg = registrationService.verifyAndCreateUser(token, name);
    if (msg.startsWith("Invalid")) {
      return ResponseEntity.badRequest().body(msg);
    }
    return ResponseEntity.ok(msg);
  }
}
```

---

# 8) Quick Test (manual)

1. **Request verification link**

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","name":"Siddhesh"}'
```

2. Check your mailbox for a link like:

```
http://localhost:8080/auth/verify?token=...UUID...
```

3. **Open the link** (or curl it):

```bash
curl "http://localhost:8080/auth/verify?token=<TOKEN_FROM_EMAIL>&name=Siddhesh"
```

---

## Notes & Options

* **OTP instead of link**: store `email -> otp` in Redis and ask user to POST `/auth/verify-otp` with `email + otp`.
* **Replay protection**: we delete the token after successful verification (`redis.delete(key)`).
* **Rate limiting / resend**: you can throttle resends per email (use an extra Redis key).
* **Security**: Always validate/normalize email; consider signed tokens (JWT) if you want stateless verification instead of Redis.

---

If you want, I can also give you a **variant using OTP input** (no link) or a **JWT-signed link** (no Redis).
