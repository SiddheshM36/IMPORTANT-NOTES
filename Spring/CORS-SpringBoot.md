Handling Cross-Origin Resource Sharing (CORS) in Spring Boot and configuring security for specific routes involves setting up CORS configurations at both the global and controller levels, as well as configuring security settings using Spring Security.


### CORS Configuration for Specific Controllers

To configure CORS for specific controllers or routes, you can use the `@CrossOrigin` annotation directly on the controller or method.

#### Example: Controller-Level CORS Configuration

```java
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyController {

    @CrossOrigin(origins = "http://example.com", allowedMethods = "GET")
    @GetMapping("/resource")
    public String getResource() {
        return "Resource content";
    }
}
```

### Spring Security Configuration for CORS

When using Spring Security, you need to ensure that CORS configuration is integrated with the security settings. This often involves configuring a `CorsConfigurationSource` bean and applying it to the security filter chain.

#### Example: Spring Security Configuration

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .authorizeRequests()
                .antMatchers("/public/**").permitAll() // Public routes
                .anyRequest().authenticated() // Secure other routes
            .and()
            .httpBasic(); // Basic authentication for simplicity
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://example.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
```

### Summary

1. **Global CORS Configuration**: Configure global CORS settings using a `WebMvcConfigurer` bean.
2. **Controller-Level CORS Configuration**: Use the `@CrossOrigin` annotation for specific controllers or methods.
3. **Spring Security Integration**: Ensure CORS settings are integrated with Spring Security by defining a `CorsConfigurationSource` bean and applying it in the security configuration.

These steps allow you to handle CORS requests appropriately, ensuring secure and controlled cross-origin access to your Spring Boot application.
