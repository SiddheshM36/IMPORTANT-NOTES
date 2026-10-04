🔥 Spring / Spring Boot Interview Questions
1. Spring Core — Must Know
Basic
1. What is Spring Framework?
2. What are the advantages of Spring?
3. What is IoC?
4. What is Dependency Injection?
5. Constructor injection vs Setter injection?
6. Why is constructor injection preferred?
7. What is a Spring Bean?
8. How does Spring create a Bean?
9. What is ApplicationContext?
10. BeanFactory vs ApplicationContext?
11. What is component scanning?
12. @Component vs @Service vs @Repository?
13. What is @Configuration?
14. What is @Bean?
15. @Component vs @Bean?
16. What happens internally when we use @Autowired?
17. What happens if multiple beans of the same type exist?
18. @Primary vs @Qualifier?
19. What is circular dependency?
20. How do you resolve circular dependency?
2. ⭐ Bean Lifecycle — Very Important
21. Explain Spring Bean lifecycle.
22. What happens from:
SpringApplication.run()
        ↓
Bean creation
        ↓
Dependency injection
        ↓
@PostConstruct
        ↓
Bean ready

23. What is BeanPostProcessor?
24. What is @PostConstruct?
25. What is @PreDestroy?
26. Singleton vs Prototype bean?
27. What happens if a prototype bean is injected into a singleton bean?
28. What are lazy beans?
29. @Lazy ka use kya hai?
30. When exactly does Spring create singleton beans?
3. Spring Boot — Must Know
31. What is Spring Boot?
32. Spring Framework vs Spring Boot?
33. What does @SpringBootApplication contain?
@SpringBootApplication

Internally:
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan

34. What is @EnableAutoConfiguration?
35. How does Spring Boot Auto Configuration work?
36. What is spring.factories / AutoConfiguration.imports?
37. How does Spring Boot know which configuration to apply?
38. What is Starter dependency?
39. What happens when you add:
spring-boot-starter-web

40. Embedded Tomcat kaise start hota hai?
41. What happens internally when you run:
SpringApplication.run()

42. What is application.properties vs application.yml?
43. What are Spring Profiles?
44. How do you maintain different configurations for:
dev
qa
prod

45. What is Actuator?
4. Spring MVC — Very Important
46. What is DispatcherServlet?
47. Explain complete request flow in Spring Boot.
Client
 ↓
Tomcat
 ↓
Filter
 ↓
DispatcherServlet
 ↓
Interceptor
 ↓
Controller
 ↓
Service
 ↓
Repository
 ↓
DB

48. What is HandlerMapping?
49. What is HandlerAdapter?
50. @Controller vs @RestController?
51. @RequestMapping vs @GetMapping?
52. @RequestParam vs @PathVariable?
53. @RequestBody kaise work karta hai?
54. What is HttpMessageConverter?
55. JSON ko Java object mein kaun convert karta hai?
56. JSON serialization/deserialization kya hai?
5. ⭐ Filter vs Interceptor
57. Filter vs Interceptor?
58. Filter kaha execute hota hai?
59. Interceptor kaha execute hota hai?
60. Why JWT authentication is generally implemented using Filter?
61. Can JWT authentication be implemented using Interceptor?
62. Multiple Filters kaise order karoge?
63. Multiple Interceptors kaise order karoge?
64. preHandle(), postHandle(), afterCompletion()?
65. chain.doFilter() kya karta hai?
66. return false in interceptor kya karta hai?
6. Spring Security — 🔥🔥
67. What is Spring Security?
68. Explain authentication vs authorization.
69. Explain JWT authentication flow.
Login
 ↓
Username/password
 ↓
Authentication
 ↓
JWT generated
 ↓
Client
 ↓
Every request
 ↓
JWT Filter
 ↓
Validate JWT
 ↓
SecurityContext
 ↓
Authorization

70. What is SecurityContext?
71. What is SecurityContextHolder?
72. What is UserDetails?
73. UserDetailsService kya karta hai?
74. What is AuthenticationManager?
75. What is AuthenticationProvider?
76. JWT kaise validate hota hai?
77. Access Token vs Refresh Token?
78. Stateless authentication kya hota hai?
79. Why JWT is called stateless?
80. Role vs Authority?
81. hasRole() vs hasAuthority()?
82. How do you secure /admin/**?
83. How do you allow /login without authentication?
84. What is CSRF?
85. Why is CSRF usually disabled for stateless JWT APIs?
7. Spring Data JPA — 🔥
86. What is JPA?
87. JPA vs Hibernate?
88. What is Persistence Context?
89. Entity lifecycle kya hota hai?
Transient
   ↓
Persistent
   ↓
Detached
   ↓
Removed

90. What is dirty checking?
91. What is first-level cache?
92. What is second-level cache?
93. save() internally kya karta hai?
94. save() vs saveAndFlush()?
95. Lazy vs Eager loading?
96. What is N+1 problem?
97. How do you solve N+1?
98. @OneToMany mapping kaise work karta hai?
99. Cascade types kya hain?
100. orphanRemoval kya hai?
8. Transactions — 🔥🔥
101. What is @Transactional?
102. How does @Transactional work internally?
103. Transaction propagation kya hai?
REQUIRED
REQUIRES_NEW
NESTED
SUPPORTS
MANDATORY
NEVER
NOT_SUPPORTED

104. Isolation levels kya hain?
READ_UNCOMMITTED
READ_COMMITTED
REPEATABLE_READ
SERIALIZABLE

105. Dirty read?
106. Non-repeatable read?
107. Phantom read?
108. Rollback kab hota hai?
109. Checked vs unchecked exception rollback?
110. Why does @Transactional sometimes not work?
111. Self-invocation problem kya hai?
Example:
this.method();

112. Transaction DB connection ke saath kaise related hai?
9. Spring AOP — 🔥
113. What is AOP?
114. Why do we need AOP?
115. What are:
Aspect
Join Point
Pointcut
Advice
Weaving

116. @Before vs @After vs @Around?
117. How does Spring AOP work internally?
118. What is a proxy?
119. JDK Dynamic Proxy vs CGLIB?
120. Why does self-invocation break AOP?
Real examples:
@Transactional
@Cacheable
@Async
@Secured

10. Spring Cache
121. What is Spring Cache abstraction?
122. @Cacheable?
123. @CachePut?
124. @CacheEvict?
125. @Cacheable internally kaise work karta hai?
126. Redis vs local cache?
127. Cache invalidation kaise handle karoge?
128. Redis down ho gaya toh kya karoge?
129. Cache stampede kya hai?
130. Cache penetration kya hai?
131. Cache avalanche kya hai?
11. Spring Boot Production Questions
Ye 5 YOE ke liye especially important hain.
132. Your API suddenly becomes slow. How will you debug?
133. CPU 100% ho gaya. What will you check?
134. Memory continuously increase ho rahi hai. Why?
135. API timeout ho raha hai. What could be the reasons?
136. Database slow hai. Application kaise impact hoga?
137. External service down ho gayi. What will you implement?
Answer:
Timeout
Retry
Circuit Breaker
Bulkhead
Fallback

138. How do you prevent cascading failure?
139. What is graceful shutdown?
140. How do you handle high traffic?
141. How do you make Spring Boot application horizontally scalable?
142. How do you implement distributed locking?
143. How do you handle duplicate requests?
12. Architecture Questions
144. What is Layered Architecture?
145. What is Clean Architecture?
146. What is Hexagonal Architecture?
147. Clean vs Hexagonal?
148. Monolith vs Microservices?
149. SOA vs Microservices?
150. What is Event-Driven Architecture?
151. Synchronous vs Asynchronous communication?
152. What is CQRS?
153. What is Saga?
154. What is Outbox Pattern?
155. What is Idempotency?
156. What is Bulkhead Pattern?
157. What is Circuit Breaker?
158. Retry vs Circuit Breaker?
159. Filter vs Interceptor?
160. API Gateway kya karta hai?
🔥 Scenario-Based Questions
Ye actual interview mein sabse zyada useful hain.
Scenario 1
Payment service down hai. Order service ko kaise protect karoge?

Expected:
Timeout
   +
Circuit Breaker
   +
Retry
   +
Fallback
   +
Bulkhead

Scenario 2
Two users simultaneously same seat book kar rahe hain.

Discuss:
Redis Lock
+
DB constraint
+
Transaction
+
Idempotency

Scenario 3
DB save ho gaya but Kafka event publish nahi hua.

Answer:
Transactional Outbox Pattern

Scenario 4
Same payment request 3 times aa gayi.

Answer:
Idempotency Key

Scenario 5
One microservice slow ho gaya aur pura system slow ho gaya.

Answer:
Timeout
Circuit Breaker
Bulkhead

Scenario 6
Redis down ho gaya.

Discuss:
Redis
 ↓
Fallback
 ↓
Local Cache
 ↓
DB

Then discuss cache consistency/invalidation.
Scenario 7
1000 requests simultaneously same data maang rahi hain and cache miss hai.

Potential:
Cache Stampede

Solutions:
Distributed Lock
Request Coalescing
Pre-warming
TTL jitter

🎯 Tere liye priority order
Sab kuch ek saath mat padh.
Level 1 — Must Master
IoC / DI
Beans
Bean Lifecycle
ApplicationContext
@SpringBootApplication
Auto Configuration
DispatcherServlet
Filter / Interceptor
REST
Exception Handling
Profiles

Level 2 — Strong Spring
Spring Security
JWT
JPA
@Transactional
Propagation
Isolation
AOP
Caching

Level 3 — 5 YOE / System Design
Clean Architecture
Microservices
Circuit Breaker
Bulkhead
Retry
Timeout
Rate Limiter
Saga
Outbox
CQRS
Idempotency
Distributed Lock
Event Driven Architecture
