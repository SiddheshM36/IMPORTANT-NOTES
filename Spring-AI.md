# Banking AI Chatbot — Complete Technical Documentation

## 1. Objective

Build a secure banking chatbot using:

* Angular — frontend/chat UI
* Spring Boot — backend
* Spring AI — AI integration/orchestration
* Llama — LLM
* Ollama — local Llama model runtime
* Keycloak — authentication/identity
* Spring Security — JWT validation and authorization
* Banking Microservices — balance, transactions, tasks, etc.
* Database — source of truth
* Optional LLM Gateway — model abstraction/routing
* Optional MCP — standardized external AI/tool integration

---

# 2. High-Level Architecture

```text
                         USER
                           |
                           v
                    +-------------+
                    |   Angular   |
                    |  Chat UI    |
                    +------+------+
                           |
                           | HTTPS
                           | Authorization: Bearer JWT
                           v
                    +-------------+
                    | Spring Boot |
                    | AI Service  |
                    +------+------+
                           |
                    Spring Security
                           |
                           v
                     +-----------+
                     | Keycloak  |
                     +-----------+
                           |
                    Authenticated User
                    e.g. BANK_12345
                           |
                           v
                    +-------------+
                    |  Spring AI  |
                    +------+------+
                           |
                           v
                    +-------------+
                    | LLM Gateway |
                    +------+------+
                           |
             +-------------+-------------+
             |             |             |
             v             v             v
           Llama          GPT         Claude
             |
             v
          Tool Calling
             |
      +------+-------+---------+
      |              |         |
      v              v         v
   Balance      Transactions  Tasks
      |              |         |
      +--------------+---------+
                     |
                     v
              Banking Services
                     |
                     v
                    DB
```

---

# 3. Role of Each Component

| Component             | Responsibility                                                         |
| --------------------- | ---------------------------------------------------------------------- |
| Angular               | Chat UI and API calls                                                  |
| Spring Boot           | Application/business layer                                             |
| Keycloak              | Authentication and identity                                            |
| Spring Security       | JWT validation and authorization                                       |
| Spring AI             | Connects Spring application to LLM and orchestrates AI features        |
| Llama                 | Generates/reasons over natural language and selects tools              |
| Ollama                | Runs/serves Llama locally                                              |
| LLM Gateway           | Provider abstraction, routing, credentials, rate limits, observability |
| Tool                  | Controlled business capability exposed to LLM                          |
| Banking Microservices | Actual banking operations                                              |
| Database              | Source of truth                                                        |
| MCP                   | Standardized protocol for exposing/accessing tools/resources           |

---

# 4. Angular → Spring Boot Flow

User enters:

```text
What is my current balance?
```

Angular sends:

```http
POST /api/chat
Authorization: Bearer <BANKING_JWT>
Content-Type: text/plain

What is my current balance?
```

Angular does NOT communicate directly with Llama.

Correct:

```text
Angular
   ↓
Spring Boot
   ↓
Spring AI
   ↓
LLM
```

Incorrect:

```text
Angular
   ↓
Llama
```

---

# 5. Angular Chat Service

Example:

```typescript
@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private url = 'http://localhost:8080/api/chat';

  constructor(private http: HttpClient) {}

  sendMessage(message: string): Observable<string> {

    return this.http.post(
      this.url,
      message,
      {
        responseType: 'text'
      }
    );
  }
}
```

Component:

```typescript
export class ChatComponent {

  message = '';
  response = '';

  constructor(private chatService: ChatService) {}

  sendMessage() {

    this.chatService
      .sendMessage(this.message)
      .subscribe(res => {
        this.response = res;
      });
  }
}
```

---

# 6. Spring Boot Controller

```java
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public String chat(@RequestBody String message) {
        return chatService.chat(message);
    }
}
```

---

# 7. Spring AI + Llama

Llama is the actual LLM/model.

It is not the same thing as Spring AI or Ollama.

```text
Llama
= Model

Ollama
= Runtime/server that serves the model

Spring AI
= Java/Spring abstraction used by our application
```

Architecture:

```text
Spring Boot
     |
     v
Spring AI
     |
     | HTTP
     v
Ollama
     |
     v
Llama
```

---

# 8. Basic Spring AI Code

```java
@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String chat(String message) {

        return chatClient
                .prompt()
                .user(message)
                .call()
                .content();
    }
}
```

The application code communicates with `ChatClient`.

Spring AI handles the provider-specific communication.

---

# 9. Llama Request Concept

Conceptually, the model runtime receives something like:

```json
{
  "model": "llama3.2",
  "messages": [
    {
      "role": "user",
      "content": "Explain Kafka"
    }
  ]
}
```

The model internally processes:

```text
Text
 ↓
Tokenizer
 ↓
Token IDs
 ↓
Embeddings
 ↓
Transformer
 ↓
Next-token prediction
 ↓
Generated response
```

---

# 10. LLM Provider Abstraction

Because Spring AI provides an abstraction, the architecture can be kept provider-independent.

Current:

```text
Spring AI
    ↓
Ollama
    ↓
Llama
```

Future:

```text
Spring AI
    ↓
OpenAI
    ↓
GPT
```

Or:

```text
Spring AI
    ↓
Anthropic
    ↓
Claude
```

The Angular API does not need to know which model is being used.

---

# 11. Tool Calling

Tool calling means:

> The LLM decides that it needs a particular backend capability and requests that tool to be executed.

The LLM does NOT directly execute the Java method.

Example tools:

```java
@Component
public class BankingTools {

    @Tool(
        description = "Get current balance of the authenticated customer"
    )
    public BalanceResponse getMyBalance() {
        // implementation
    }

    @Tool(
        description = "Get recent transactions of the authenticated customer"
    )
    public List<Transaction> getMyTransactions() {
        // implementation
    }

    @Tool(
        description = "Get pending approval tasks of the authenticated employee"
    )
    public List<Task> getPendingTasks() {
        // implementation
    }

    @Tool(
        description = "Transfer money to another account"
    )
    public TransferResponse transferMoney(
            String toAccount,
            BigDecimal amount) {
        // implementation
    }
}
```

---

# 12. How Does Llama Know Which Tool Exists?

Llama does not magically know that Java methods exist.

Spring AI exposes tool metadata/schema to the LLM.

Conceptually:

```json
{
  "tools": [
    {
      "name": "getMyBalance",
      "description": "Get current balance of authenticated customer",
      "parameters": {}
    },
    {
      "name": "getMyTransactions",
      "description": "Get recent transactions of authenticated customer",
      "parameters": {}
    },
    {
      "name": "getPendingTasks",
      "description": "Get pending approval tasks",
      "parameters": {}
    },
    {
      "name": "transferMoney",
      "description": "Transfer money to another account",
      "parameters": {
        "toAccount": "string",
        "amount": "number"
      }
    }
  ]
}
```

The LLM receives:

* Tool name
* Tool description
* Parameters
* Parameter types/schema

It does not receive the actual Java implementation.

---

# 13. Tool Selection

Suppose available tools are:

```text
getMyBalance()
getMyTransactions()
getPendingTasks()
transferMoney()
```

User:

```text
What's my balance?
```

LLM determines:

```text
Required operation:
getMyBalance()
```

Conceptually it produces:

```json
{
  "tool": "getMyBalance",
  "arguments": {}
}
```

Spring AI then executes the actual Java method.

---

# 14. Tool Calling Flow

```text
User
 |
 | "What's my balance?"
 v
Angular
 |
 v
Spring Boot
 |
 v
Spring AI
 |
 v
Llama
 |
 | decides:
 | getMyBalance()
 v
Spring AI
 |
 | executes Java method
 v
getMyBalance()
 |
 v
Account Service
 |
 v
Database
 |
 | ₹50,000
 v
Spring AI
 |
 v
Llama
 |
 | natural language response
 v
Spring Boot
 |
 v
Angular
 |
 v
User
```

---

# 15. Multiple Tools

Multiple tools can be registered.

Example:

```text
Tool 1 → getMyBalance()
Tool 2 → getMyTransactions()
Tool 3 → getPendingTasks()
Tool 4 → transferMoney()
```

The LLM chooses based on the user's intent and the tool descriptions.

Example:

```text
User:
"Show my recent transactions"

LLM:
→ getMyTransactions()
```

Another:

```text
User:
"How much money do I have?"

LLM:
→ getMyBalance()
```

Another:

```text
User:
"Show my pending approval tasks"

LLM:
→ getPendingTasks()
```

---

# 16. Multiple Tool Calls in One Request

A user may ask:

```text
What's my balance and show my last 5 transactions?
```

The model may request:

```text
getMyBalance()

+

getMyTransactions(limit = 5)
```

Conceptually:

```json
[
  {
    "tool": "getMyBalance",
    "arguments": {}
  },
  {
    "tool": "getMyTransactions",
    "arguments": {
      "limit": 5
    }
  }
]
```

Spring AI executes the required tools and gives the results back to the model.

The model then creates the final response.

---

# 17. Important Tool Calling Distinction

```text
LLM
 |
 | Decides WHAT operation is required
 v
Spring AI
 |
 | Executes
 v
Java Tool
 |
 v
Business Service
 |
 v
Database
```

Therefore:

### LLM

* Understands user request
* Selects appropriate tool
* Generates tool arguments
* Processes tool results
* Generates final natural-language answer

### LLM does NOT

* Directly query the database
* Directly execute Java methods
* Decide authorization
* Own banking data
* Replace backend business logic

---

# 18. Authentication with Keycloak

Angular authenticates the user using Keycloak.

Conceptually:

```text
Angular
   |
   | Login
   v
Keycloak
   |
   | Access Token / JWT
   v
Angular
```

Then:

```text
Angular
   |
   | Authorization: Bearer JWT
   v
Spring Boot
```

Spring Security validates the JWT.

Example JWT claims:

```json
{
  "sub": "BANK_12345",
  "scope": "balance:read",
  "roles": ["CUSTOMER"],
  "exp": 1790000000
}
```

After validation:

```text
SecurityContext
      |
      v
BANK_12345
```

---

# 19. Authentication vs Authorization

### Authentication

Answers:

```text
WHO are you?
```

Example:

```text
BANK_12345
```

### Authorization

Answers:

```text
WHAT are you allowed to do?
```

Example:

```text
balance:read
transaction:read
```

Both must be enforced by the backend.

---

# 20. Secure Banking Tool Design

Avoid:

```java
@Tool
public BalanceResponse getBalance(String userId) {
    return accountService.getBalance(userId);
}
```

Why?

The LLM could potentially generate:

```json
{
  "userId": "BANK_99999"
}
```

Instead use:

```java
@Tool(
    description = "Get the current balance of the authenticated customer"
)
public BalanceResponse getMyBalance() {

    String userId =
        SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

    return accountService.getBalance(userId);
}
```

Flow:

```text
LLM
 |
 | getMyBalance()
 v
Spring AI
 |
 v
SecurityContext
 |
 v
BANK_12345
 |
 v
Account Service
 |
 v
Database
```

The user identity comes from trusted authentication context, not from the LLM.

---

# 21. Sensitive Banking Data

Do not unnecessarily send sensitive information to the LLM.

Avoid sending:

```text
JWT
CIF
PAN
Aadhaar
Password
Full account number
Internal credentials
Complete customer record
```

Prefer sending only the minimum required information.

Example:

```json
{
  "balance": 50000,
  "currency": "INR"
}
```

Then the LLM can answer:

```text
Your current balance is ₹50,000.
```

---

# 22. Complete Secure Banking Flow

```text
                     USER
                       |
                       v
                    Angular
                       |
                  Banking JWT
                       |
                       v
                Spring Security
                       |
                  Keycloak
                       |
                       v
               Authenticated User
                  BANK_12345
                       |
                       v
                  AI Service
                       |
                       v
                   Spring AI
                       |
                       v
                  LLM Gateway
                       |
                       v
                     Llama
                       |
                  Tool Request
                       |
                       v
                 getMyBalance()
                       |
                SecurityContext
                       |
                   BANK_12345
                       |
                       v
                 Account Service
                       |
                       v
                      DB
                       |
                   ₹50,000
                       |
                       v
                     Llama
                       |
                       v
             "Your balance is ₹50,000"
                       |
                       v
                    Angular
```

---

# 23. LLM Gateway

An LLM Gateway is an intermediate layer between the application and model providers.

Without gateway:

```text
Spring Boot
    |
    +----> Llama
    |
    +----> GPT
    |
    +----> Claude
```

With gateway:

```text
Spring Boot
      |
      v
 LLM Gateway
      |
 +----+-----+-----+
 |          |     |
 v          v     v
Llama      GPT  Claude
```

---

# 24. Why Use an LLM Gateway?

It can centralize:

* Model routing
* Provider abstraction
* API credentials
* Rate limiting
* Timeouts
* Retries
* Fallback
* Token/cost tracking
* Logging
* Metrics
* Observability
* Model selection policies

Example:

```text
User request
     |
     v
LLM Gateway
     |
     v
Model Policy
     |
     +---- General request → Model A
     |
     +---- Complex request → Model B
     |
     +---- Approved sensitive workload → Private model
```

---

# 25. Simple Gateway Structure

```java
@RestController
@RequestMapping("/llm")
public class LlmGatewayController {

    private final LlmGatewayService service;

    public LlmGatewayController(LlmGatewayService service) {
        this.service = service;
    }

    @PostMapping("/chat")
    public LlmResponse chat(@RequestBody LlmRequest request) {
        return service.chat(request);
    }
}
```

DTO:

```java
public record LlmRequest(
    String message
) {}
```

Response:

```java
public record LlmResponse(
    String content
) {}
```

Service:

```java
@Service
public class LlmGatewayService {

    private final ChatClient chatClient;

    public LlmGatewayService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public LlmResponse chat(LlmRequest request) {

        String response = chatClient
                .prompt()
                .user(request.message())
                .call()
                .content();

        return new LlmResponse(response);
    }
}
```

This is a simplified example. Production gateway would have provider-specific clients/adapters and policies.

---

# 26. LLM Gateway Model Routing

Conceptually:

```java
public enum ModelType {
    LLAMA,
    GPT,
    CLAUDE
}
```

A simplified routing approach:

```java
public String chat(String prompt, ModelType model) {

    return switch (model) {

        case LLAMA ->
            llamaClient.call(prompt);

        case GPT ->
            openAiClient.call(prompt);

        case CLAUDE ->
            claudeClient.call(prompt);
    };
}
```

In production, model selection should normally be controlled by application policy/configuration rather than allowing arbitrary user-selected models.

---

# 27. Three Different Meanings of "Token"

This is extremely important.

## A. Banking JWT

Used for authentication/authorization.

```text
Angular
   ↓
JWT
   ↓
Spring Security
```

It answers:

```text
Who is this user?
```

---

## B. LLM Provider API Key/Token

Used to authenticate the application with the LLM provider.

```text
LLM Gateway
      ↓
API Key / Provider Credential
      ↓
LLM Provider
```

This must remain server-side.

Never put it in Angular.

---

## C. LLM Tokens

These are pieces of text processed by the model.

```text
Input tokens
+
Output tokens
=
Total tokens
```

Example:

```text
Input  = 500 tokens
Output = 200 tokens

Total = 700 tokens
```

These are related to model context, limits, latency and potentially usage/cost.

---

# 28. Token Flow

```text
                 BANKING JWT
Angular --------------------------> Spring Boot
                                      |
                                      v
                              Spring Security
                                      |
                                      v
                                SecurityContext


                 LLM CREDENTIAL
LLM Gateway -----------------------> LLM Provider
                                      |
                                      v
                                    Model


                 LLM TOKENS
Prompt -----------------------------> LLM
                                      |
                                Token processing
                                      |
                                      v
                                  Response
```

These three token concepts should never be confused.

---

# 29. What Should Never Go to Angular?

Do not expose:

```text
LLM API Key
Provider Secret
Database Credentials
Internal Service Credentials
Refresh Tokens unnecessarily
Internal model server credentials
```

Angular should generally only communicate with your backend using the application's authentication mechanism.

---

# 30. Can Llama Be Changed Later?

Yes.

Current:

```text
Angular
 ↓
Spring Boot
 ↓
Spring AI
 ↓
Ollama
 ↓
Llama
```

Future:

```text
Angular
 ↓
Spring Boot
 ↓
Spring AI
 ↓
OpenAI
 ↓
GPT
```

Or:

```text
Angular
 ↓
Spring Boot
 ↓
Spring AI
 ↓
Anthropic
 ↓
Claude
```

The business tools can remain largely unchanged.

Example:

```java
@Tool
public BalanceResponse getMyBalance() {
    ...
}
```

The model can change while the banking business capability remains the same.

However, every model switch should be tested because models can differ in:

* Tool calling behavior
* Structured output support
* Context window
* Latency
* Cost
* Reasoning quality
* Instruction following
* Safety behavior

---

# 31. MCP

MCP = Model Context Protocol.

MCP is NOT:

```text
An LLM
```

MCP is a standardized protocol for AI clients to interact with tools/resources exposed by an MCP server.

---

# 32. Tool Calling vs MCP

### Normal Spring AI Tool Calling

```text
Spring Boot
    |
    v
Spring AI
    |
    v
Llama
    |
    v
Spring AI Tool
    |
    v
Banking Service
```

This is enough for our own Angular + Spring Boot chatbot.

### MCP

```text
Claude / Other AI Client
          |
          v
      MCP Server
          |
     +----+-----+
     |    |     |
     v    v     v
 Balance Txns  Tasks
     |
     v
Banking Services
```

MCP becomes useful when external AI clients/agents need standardized access to our tools/resources.

---

# 33. Do We Need MCP Initially?

For the current application:

```text
Angular
+
Spring Boot
+
Spring AI
+
Llama
+
Banking Tools
```

MCP is not required.

Spring AI's normal tool calling is sufficient.

MCP can be added later if we need:

* External AI clients
* Claude integration
* Multiple AI agents
* Standardized tool exposure
* Shared tool ecosystem

---

# 34. MCP Does Not Replace Security

Even with MCP:

```text
MCP
```

does not replace:

```text
Keycloak
+
Spring Security
+
Authorization
+
Audit
+
Least privilege
+
Encryption
```

For banking:

```text
AI Client
    |
    v
MCP Server
    |
    v
Authentication
    |
    v
Authorization
    |
    v
Business Tool
    |
    v
Banking Service
```

---

# 35. Final Production Architecture

A mature version of the system can look like:

```text
                         USER
                           |
                           v
                       Angular
                           |
                           | JWT
                           v
                      API Gateway
                           |
                           v
                    AI Chat Service
                           |
                  +--------+--------+
                  |                 |
                  v                 v
            Spring Security    Spring AI
                  |                 |
                  v                 v
              Keycloak        LLM Gateway
                                    |
                           +--------+--------+
                           |        |        |
                           v        v        v
                         Llama      GPT    Claude
                           |
                           |
                       Tool Calling
                           |
              +------------+------------+
              |            |            |
              v            v            v
          Balance      Transactions    Tasks
              |            |            |
              +------------+------------+
                           |
                           v
                    Banking Services
                           |
                           v
                          DB
```

Optional external integration:

```text
External AI Client
        |
        v
    MCP Server
        |
        v
 Banking Tools
        |
        v
Banking Services
```

---

# 36. Security Principles

For a banking AI system:

### 1. Never trust the LLM for authorization

LLM can request:

```text
getMyBalance()
```

but backend decides whether the authenticated user is allowed to perform it.

### 2. Never trust user IDs generated by the LLM

Use:

```text
SecurityContext
```

instead.

### 3. Never send JWT to LLM

JWT stays within the authenticated application/backend flow.

### 4. Never expose provider API keys to Angular

Keys remain server-side.

### 5. Minimize PII

Send only the information necessary for generating the response.

### 6. Do not give LLM direct database access

Expose controlled business tools.

### 7. Audit sensitive operations

Especially:

```text
Transfer
Payment
Approval
Account changes
Beneficiary changes
```

### 8. Apply least privilege

Each tool should expose only the minimum capability required.

---

# 37. Important Mental Model

Remember this:

```text
Angular
   =
UI

Keycloak
   =
Identity

Spring Security
   =
Authentication + Authorization enforcement

Spring Boot
   =
Business/Application layer

Spring AI
   =
AI integration/orchestration

Llama/GPT/Claude
   =
LLM / Brain

Tool
   =
Controlled capability

LLM Gateway
   =
Model/provider management layer

MCP
   =
Standardized AI-to-tool protocol

Database
   =
Source of truth
```

---

# 38. Most Important End-to-End Example

User:

```text
"What is my balance?"
```

### Step 1 — Angular

```text
POST /api/chat

Authorization: Bearer <JWT>

"What is my balance?"
```

### Step 2 — Spring Security

```text
JWT
 ↓
Keycloak validation
 ↓
sub = BANK_12345
 ↓
SecurityContext
```

### Step 3 — Spring AI

Sends user request plus available tool definitions to Llama.

### Step 4 — Llama

Determines:

```text
getMyBalance()
```

### Step 5 — Spring AI

Executes:

```java
getMyBalance()
```

### Step 6 — Backend

Gets user ID from:

```text
SecurityContext
```

Not from Llama.

### Step 7 — Banking Service

```text
BANK_12345
 ↓
Account Service
 ↓
Database
 ↓
₹50,000
```

### Step 8 — Tool Result

Only necessary data is returned:

```json
{
  "balance": 50000,
  "currency": "INR"
}
```

### Step 9 — Llama

Generates:

```text
Your current balance is ₹50,000.
```

### Step 10 — Angular

Displays:

```text
AI:
Your current balance is ₹50,000.
```

---

# 39. Interview Summary

If asked:

### "How does Llama know which tool to call?"

Answer:

> We register tools in the Spring AI application. Spring AI exposes the tool name, description and parameter schema to the LLM. The LLM analyzes the user's request and generates a tool-call request. Spring AI receives that request and executes the corresponding Java method. The result is then passed back to the LLM to generate the final response.

### "Does Llama execute the Java method?"

> No. Llama only generates the tool-call request. The application framework executes the actual Java method.

### "How do you identify the banking user?"

> We use Keycloak-issued JWTs and Spring Security. The authenticated user's identity is obtained from the SecurityContext rather than trusting a user ID supplied by the LLM.

### "Why use an LLM Gateway?"

> To abstract model providers and centralize model routing, credentials, rate limiting, retries, timeouts, observability and token/cost tracking.

### "Why MCP?"

> MCP provides a standardized protocol for AI clients to access tools and resources. It is useful when tools need to be exposed to external AI clients or multiple AI agents. It is not necessary for a simple Angular + Spring AI chatbot using local tools.

### "Can you replace Llama with GPT?"

> Yes. If the application is built around Spring AI abstractions and provider-specific configuration is isolated, the LLM provider can be changed without changing the Angular API or core banking business logic. The new model still needs compatibility and regression testing.

---

# 40. Recommended Learning Order

For this project, learn in this order:

```text
1. LLM Basics
       ↓
2. Spring AI
       ↓
3. Llama + Ollama
       ↓
4. ChatClient
       ↓
5. Tool Calling ⭐
       ↓
6. Multiple Tools
       ↓
7. Keycloak + JWT
       ↓
8. Secure Tool Calling
       ↓
9. RAG
       ↓
10. Agents
       ↓
11. LLM Gateway
       ↓
12. MCP
       ↓
13. Production AI Security
       ↓
14. Observability + Evaluation
```

The most important concepts to master first are:

```text
LLM
 ↓
Spring AI
 ↓
Tool Calling
 ↓
Security
 ↓
RAG
 ↓
Agents
 ↓
MCP
 ↓
Production Architecture
```
