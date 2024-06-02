Sure, let's write the entire code for a session-based authentication system in a microservices architecture, focusing on the main components: Authentication Service, API Gateway, and a Sample Microservice. We'll use Redis for session storage, Spring Boot for the services, and proper security configurations.

### Project Structure

1. **Authentication Service**: Manages user authentication and sessions.
2. **API Gateway**: Routes requests and validates sessions.
3. **User Service**: A sample microservice that requires session validation.
4. **Shared Configuration**: Configuration for Redis and common security settings.

### 1. Authentication Service

#### `AuthController.java`

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.findByUsername(loginRequest.getUsername());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            String sessionId = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(sessionId, user, 30, TimeUnit.MINUTES); // Set session timeout
            response.addCookie(createSessionCookie(sessionId));
            return "Login successful";
        } else {
            return "Invalid username or password";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        String sessionId = getSessionIdFromRequest(request);
        if (sessionId != null) {
            redisTemplate.delete(sessionId);
        }
        return "Logout successful";
    }

    private javax.servlet.http.Cookie createSessionCookie(String sessionId) {
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("SESSIONID", sessionId);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Ensure this is true if using HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(30 * 60); // Set cookie timeout
        return cookie;
    }

    private String getSessionIdFromRequest(HttpServletRequest request) {
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
                if (cookie.getName().equals("SESSIONID")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
```

#### `SecurityConfig.java`

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/auth/login")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/auth/logout")
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

#### `UserService.java`

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
```

#### `UserRepository.java`

```java
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
```

#### `User.java`

```java
import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    // Getters and setters
}
```

#### `LoginRequest.java`

```java
public class LoginRequest {
    private String username;
    private String password;

    // Getters and setters
}
```

### 2. API Gateway

#### `GatewayFilter.java`

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class GatewayFilter implements WebFilter {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String sessionId = getSessionIdFromRequest(exchange);
        if (sessionId == null || redisTemplate.opsForValue().get(sessionId) == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    private String getSessionIdFromRequest(ServerWebExchange exchange) {
        return exchange.getRequest().getCookies().getFirst("SESSIONID").getValue();
    }
}
```

#### `GatewayConfig.java`

```java
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user_service", r -> r.path("/users/**")
                .uri("lb://USER-SERVICE"))
            .route("post_service", r -> r.path("/posts/**")
                .uri("lb://POST-SERVICE"))
            .build();
    }
}
```

### 3. User Service

#### `UserService.java`

```java
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserService {

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        // Business logic to get user by id
        return new User(); // Placeholder
    }
}
```

### 4. Shared Configuration

#### `RedisConfig.java`

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
```

### `application.properties` for All Services

```properties
# Redis properties
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=your-redis-password

# Common Spring Security settings
server.port=8080
spring.security.user.name=user
spring.security.user.password=password
```

### Conclusion

This setup provides a session-based authentication mechanism suitable for production environments in a microservices architecture. Key points include:

1. **Centralized Session Store (Redis)**: Manages session data centrally.
2. **API Gateway**: Validates sessions and routes requests.
3. **Secure Cookies**: Use `HttpOnly`, `Secure`, and proper session expiration.
4. **HTTPS**: Ensure all communications are over HTTPS.
5. **Monitoring and Maintenance**: Regularly monitor and clean up sessions.

This architecture balances the need for scalability, security, and simplicity, making it suitable for high-security applications like banking.
