<h2> SPRING BOOT </h2>

To implement a fallback mechanism using Resilience4j for handling communication failures between microservices, you can leverage its `Fallback` functionality along with the Circuit Breaker pattern. Resilience4j provides a powerful and flexible way to manage resilience in your applications.

Here's how you can implement it:

### Step-by-Step Implementation

1. **Add Dependencies**:
   Add the required dependencies for Resilience4j to your `pom.xml`:

   ```xml
   <dependency>
       <groupId>io.github.resilience4j</groupId>
       <artifactId>resilience4j-spring-boot2</artifactId>
       <version>1.7.0</version>
   </dependency>
   ```

2. **Enable Resilience4j**:
   Resilience4j integrates nicely with Spring Boot, and you just need to enable it by adding the `@EnableResilience4j` annotation to your main application class:

   ```java
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
   import org.springframework.cloud.client.loadbalancer.LoadBalanced;
   import org.springframework.context.annotation.Bean;
   import org.springframework.web.client.RestTemplate;

   @SpringBootApplication
   @EnableResilience4j
   public class Application {
       public static void main(String[] args) {
           SpringApplication.run(Application.class, args);
       }

       @Bean
       @LoadBalanced
       public RestTemplate restTemplate() {
           return new RestTemplate();
       }
   }
   ```

3. **Create a Fallback Method**:
   Implement a fallback method that will be called when the main method fails due to a communication error:

   ```java
   import org.springframework.stereotype.Component;

   @Component
   public class RatingFallback {

       public String ratingHotelFallback(String hotelId, Throwable throwable) {
           return "Rating service is currently unavailable. Please try again later.";
       }
   }
   ```

4. **Configure Fallback in Your Service**:
   Use Resilience4j annotations to configure the circuit breaker with the fallback method:

   ```java
   import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   import org.springframework.web.client.RestTemplate;

   @Service
   public class RatingService {

       @Autowired
       private RestTemplate restTemplate;

       @Autowired
       private RatingFallback ratingFallback;

       @CircuitBreaker(name = "ratingService", fallbackMethod = "ratingHotelFallback")
       public String getRatingForHotel(String hotelId) {
           String url = "http://rating-service/ratings/" + hotelId;
           return restTemplate.getForObject(url, String.class);
       }

       public String ratingHotelFallback(String hotelId, Throwable throwable) {
           return ratingFallback.ratingHotelFallback(hotelId, throwable);
       }
   }
   ```

### Explanation

1. **Dependencies**:
   We include the `resilience4j-spring-boot2` dependency to integrate Resilience4j with Spring Boot.

2. **Enable Resilience4j**:
   Annotate the main application class with `@EnableResilience4j` to enable Resilience4j's integration with Spring Boot.

3. **Fallback Method**:
   Create a fallback method in a separate component (e.g., `RatingFallback`) that will handle communication failures with the rating service.

4. **Configure Fallback**:
   Use the `@CircuitBreaker` annotation to configure Resilience4j's circuit breaker. Specify the name of the circuit breaker (`name = "ratingService"`) and the fallback method (`fallbackMethod = "ratingHotelFallback"`).

### Testing the Implementation

1. **Start Your Services**:
   Start both the hotel service and the rating service.

2. **Simulate Failure**:
   Stop the rating service to simulate a failure.

3. **Test the Endpoint**:
   Access the `/hotels/{hotelId}/rating` endpoint. The fallback method should return a message indicating that the rating service is currently unavailable.

By using Resilience4j's Circuit Breaker with a fallback method, you can handle communication failures between microservices gracefully, ensuring a better user experience and improved system resilience.

<h2> ANGULAR </h2>

To display the fallback message on an Angular front end when a microservice is down, you can handle the error response from the Angular service call and show a message to the user. Here's how you can do it:

### 1. Update Angular Service to Handle Errors

Update your Angular service to handle errors returned from the backend service call. When an error occurs, return a fallback message instead of the actual data.

```typescript
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RatingService {

  private apiUrl = 'http://backend-api-url/ratings/';

  constructor(private http: HttpClient) { }

  getRatingForHotel(hotelId: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}${hotelId}`).pipe(
      catchError((error: HttpErrorResponse) => {
        // Handle the error here
        console.error('Error:', error);
        return throwError('Rating service is currently unavailable. Please try again later.');
      })
    );
  }
}
```

### 2. Update Angular Component to Display Fallback Message

In your Angular component, subscribe to the service method and handle both successful responses and errors. Display the fallback message when an error occurs.

```typescript
import { Component, OnInit } from '@angular/core';
import { RatingService } from 'path/to/rating.service';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class RatingComponent implements OnInit {

  hotelId = '123';
  rating: string;

  constructor(private ratingService: RatingService) { }

  ngOnInit(): void {
    this.getRating();
  }

  getRating(): void {
    this.ratingService.getRatingForHotel(this.hotelId)
      .subscribe(
        (data: any) => {
          // Handle successful response
          this.rating = data.rating;
        },
        (error: any) => {
          // Handle error response
          this.rating = error;
        }
      );
  }
}
```

### 3. Update Angular Component Template

In your component template (`rating.component.html`), display the rating or the fallback message based on whether the rating is available or not.

```html
<div *ngIf="rating; else fallback">
  Rating: {{ rating }}
</div>
<ng-template #fallback>
  <div>{{ rating }}</div>
</ng-template>
```

### Summary

By updating your Angular service to handle errors returned from the backend service call and updating your component to display the fallback message when an error occurs, you can gracefully handle scenarios where a microservice is down. This ensures a better user experience by providing informative messages to the user when data is unavailable.
