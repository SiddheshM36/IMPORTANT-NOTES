Think of the **Kafka Outbox Pattern** as a way to solve this problem:

> **"I need to update my database AND publish a Kafka message. What if one succeeds and the other fails?"**

This is extremely common in microservices.

---

## The problem

Suppose you have an **Order Service**.

When an order is created, you need to:

1. Save order in DB
2. Publish `OrderCreated` to Kafka

Naively you might write:

```java
@Transactional
public void createOrder(Order order) {

    orderRepository.save(order);

    kafkaTemplate.send("order-topic", order);
}
```

Looks fine, but imagine:

```text
DB save ✅
Kafka publish ❌
```

Now your database says:

```text
Order = CREATED
```

but other services never receive the event.

For example:

```text
Order Service
     ↓
Payment Service ❌
Inventory Service ❌
Notification Service ❌
```

Your system is inconsistent.

---

# Outbox Pattern solves this

Instead of directly publishing to Kafka, you first save the event in an **Outbox table in the same database transaction**.

```text
              Database
        ┌───────────────────┐
        │                   │
        │ orders            │
        │                   │
        │ outbox_events     │
        │                   │
        └───────────────────┘
```

When creating the order:

```text
                 DB Transaction
                      │
             ┌────────┴────────┐
             ↓                 ↓
       orders table       outbox table
             │                 │
       Order CREATED      OrderCreated
```

Both are committed together.

---

# Example

### `orders`

| id  | customer | status  |
| --- | -------- | ------- |
| 101 | Rahul    | CREATED |

### `outbox_events`

| id | event_type   | payload         | status |
| -- | ------------ | --------------- | ------ |
| 1  | OrderCreated | `{orderId:101}` | NEW    |

Both happen in **one DB transaction**.

```java
@Transactional
public void createOrder(Order order) {

    orderRepository.save(order);

    OutboxEvent event = new OutboxEvent();

    event.setEventType("OrderCreated");
    event.setPayload("{\"orderId\":101}");
    event.setStatus("NEW");

    outboxRepository.save(event);
}
```

---

# Now who sends it to Kafka?

A separate process called an **Outbox Publisher**.

```text
             MySQL / PostgreSQL
                    │
             ┌──────┴───────┐
             │              │
          orders       outbox_events
                            │
                            ↓
                    Outbox Publisher
                            │
                            ↓
                          Kafka
                            │
              ┌─────────────┼─────────────┐
              ↓             ↓             ↓
          Payment       Inventory      Notification
```

The publisher continuously reads:

```sql
SELECT *
FROM outbox_events
WHERE status = 'NEW';
```

Then:

```text
Outbox Event
     ↓
Kafka
     ↓
success
     ↓
mark event SENT
```

---

# Why is this better?

Suppose the application crashes:

```text
Order DB insert       ✅
Outbox DB insert      ✅
Application crashes   💥
Kafka publish         ❌
```

No problem.

The event is still sitting in:

```text
outbox_events
```

When the publisher comes back:

```text
outbox_events
      ↓
find NEW event
      ↓
publish Kafka
      ↓
mark SENT
```

So the event isn't lost.

---

# But what if Kafka succeeds and application crashes?

This is an **important interview question**.

Imagine:

```text
Outbox event
     ↓
Kafka publish ✅
     ↓
Application crashes 💥
     ↓
DB status still NEW
```

When the publisher restarts:

```text
NEW event
   ↓
publish again
   ↓
Kafka receives duplicate
```

So Outbox generally gives you **at-least-once delivery**, not magically exactly-once end-to-end.

Therefore consumers should be **idempotent**.

For example:

```text
eventId = 123
```

Payment Service stores processed event IDs:

```text
processed_events

event_id
--------
123
```

If event `123` comes again:

```text
Already processed?
       ↓
      YES
       ↓
Ignore
```

---

# This is the key interview point

If interviewer asks:

> **"Does Outbox guarantee exactly once?"**

Say:

> **No. The Outbox Pattern reliably persists the event with the business transaction and typically provides at-least-once delivery. Kafka publication can be retried, so duplicates are possible. Consumers should use idempotency/deduplication.**

That's a very important answer.

---

# How does the publisher know what to publish?

There are two common approaches.

### 1. Polling

Application periodically checks:

```text
outbox_events
      ↓
SELECT NEW events
      ↓
Kafka
```

For example:

```text
Every 1 second
     ↓
read NEW events
     ↓
publish Kafka
```

Simple, but database polling creates additional DB load.

---

### 2. CDC — Change Data Capture

This is a more scalable approach.

For example:

```text
Application
    ↓
DB transaction
    ↓
outbox_events
    ↓
Debezium
    ↓
Kafka
```

Debezium watches the database transaction log, such as:

```text
PostgreSQL WAL
MySQL binlog
```

and publishes the outbox event to Kafka.

This avoids continuously querying the outbox table.

---

# Polling vs Debezium

|                        | Polling  | CDC/Debezium |
| ---------------------- | -------- | ------------ |
| Complexity             | Low      | Higher       |
| DB queries             | More     | Less         |
| Scalability            | Moderate | Better       |
| Real-time              | Good     | Very good    |
| Operational complexity | Low      | Higher       |

For an interview, I'd first explain **polling**, then say:

> "For high throughput, I would consider CDC with Debezium."

---

# Real-world example

Suppose you're building a banking transaction service.

Customer deposits ₹10,000.

You need:

```text
Transaction DB
     +
Kafka event
```

Without Outbox:

```text
Debit/Credit DB ✅
Kafka ❌
```

Other services don't know about the transaction.

With Outbox:

```text
┌─────────────────────────────┐
│ DB Transaction              │
│                             │
│ account_balance → 60000     │
│                             │
│ outbox → DepositCreated     │
└─────────────────────────────┘
             ↓
           COMMIT
             ↓
       Outbox Publisher
             ↓
           Kafka
             ↓
     ┌───────┼────────┐
     ↓       ↓        ↓
   Audit   Report   Notification
```

That's why the pattern is particularly useful in **microservices + Kafka + transactional databases**.

---

## Remember this one line

**Outbox = DB business change + Kafka event stored together in one DB transaction, then a separate publisher sends the stored event to Kafka.**

And the most important failure scenario:

```text
DB + Outbox ✅
Kafka ❌
       ↓
event remains in Outbox
       ↓
retry
       ↓
Kafka ✅
```

That's the core concept you should be able to explain in a system-design interview.


**What if kafka gets new from db and process it but while getting response went down then again new it will pick right?**

Exactly. **That's the main reason Outbox consumers need idempotency.**

Let's take your exact scenario.

### Scenario

Outbox has:

```text
eventId = 101
status = NEW
```

Publisher picks it:

```text
Outbox
  ↓
event 101
  ↓
Kafka
```

Kafka successfully receives it:

```text
Kafka ✅
```

But now publisher crashes **before it gets Kafka's response**:

```text
Kafka → ACK
          ↓
      Publisher 💥
```

The publisher never updates:

```text
status = SENT
```

So DB still says:

```text
eventId    status
101        NEW
```

When publisher restarts:

```text
SELECT * FROM outbox
WHERE status = 'NEW'
```

It picks **101 again**.

So yes:

```text
101 → Kafka
     ↓
Publisher crashes
     ↓
101 → Kafka AGAIN
```

### This creates a duplicate Kafka message.

That's okay **if your consumer is idempotent**.

---

## How consumer handles it

Suppose Payment Service receives:

```json
{
  "eventId": 101,
  "type": "OrderCreated",
  "orderId": 5001
}
```

Before processing, it checks:

```text
processed_events
----------------
101
```

### First time

```text
event 101
   ↓
not found
   ↓
process payment
   ↓
save eventId 101
```

### Second time

```text
event 101
   ↓
found 101
   ↓
DO NOT process again
```

So the payment doesn't happen twice.

---

## But there's an even better way

Don't do this:

```java
if (!processed(eventId)) {
    processPayment();
    saveProcessedEvent(eventId);
}
```

Because two consumers/threads could potentially do:

```text
Consumer 1 → check → NOT FOUND
Consumer 2 → check → NOT FOUND

Consumer 1 → payment
Consumer 2 → payment ❌
```

Instead use a **unique constraint**:

```sql
CREATE TABLE processed_events (
    event_id VARCHAR(100) PRIMARY KEY
);
```

Then your processing logic is designed around that uniqueness.

---

## Important distinction

There are actually **two different duplicate problems**:

### Problem 1 — Outbox publisher duplicate

```text
DB
 ↓
Outbox
 ↓
Kafka
 ↓
publisher crashes
 ↓
Kafka message sent again
```

**Solution:** idempotent consumer.

### Problem 2 — Kafka consumer duplicate

Kafka consumer processes:

```text
message 101
 ↓
DB update
 ↓
consumer crashes before Kafka offset commit
```

Kafka thinks:

```text
101 = not committed
```

So Kafka delivers:

```text
101 AGAIN
```

Again:

**Solution = idempotent consumer.**

---

### So remember this for your interview:

> **Outbox provides reliable at-least-once event publishing, but duplicates are possible. Therefore downstream consumers must be idempotent.**

That's actually one of the **most important things to understand about the Outbox Pattern**.



**So when payment is already done how to update state can you explain in req response and table structure also if i have 3events and one fails need to revert all **

Yes. This is the **important part**: once payment has already succeeded, you **cannot simply roll back the database transaction** because the payment is an external side effect.

Also, if you have 3 events and one fails, you generally **do not revert all 3 automatically**. You use a **Saga pattern + compensating transactions**.

Let's take a concrete example.

# Example: Order → Payment → Inventory → Notification

Suppose user places an order.

```text
Order Service
      ↓
Payment Service
      ↓
Inventory Service
      ↓
Notification Service
```

We have:

```text
Order = ₹1,000
```

---

# 1. Request comes in

```http
POST /orders
Content-Type: application/json

{
    "userId": 101,
    "productId": 500,
    "quantity": 1,
    "amount": 1000
}
```

Order Service creates the order.

### Order DB

| order_id | user_id | amount | status  |
| -------: | ------: | -----: | ------- |
|     9001 |     101 |   1000 | PENDING |

And in the **same DB transaction**, create an outbox event.

### Outbox

| event_id | aggregate_id | event_type   | status |
| -------- | -----------: | ------------ | ------ |
| E1       |         9001 | OrderCreated | NEW    |

Response:

```http
202 Accepted

{
    "orderId": 9001,
    "status": "PENDING"
}
```

---

# 2. Outbox → Kafka

Publisher reads:

```text
E1 OrderCreated
```

and publishes:

```text
Kafka
  ↓
order-events
```

Then:

```text
outbox.status = SENT
```

---

# 3. Payment Service receives it

Payment Service gets:

```json
{
    "eventId": "E1",
    "orderId": 9001,
    "amount": 1000
}
```

It creates a payment.

### Payment table

| payment_id | order_id | amount | status     |
| ---------- | -------: | -----: | ---------- |
| P1         |     9001 |   1000 | PROCESSING |

Then it calls the external payment gateway.

```http
POST /payment-gateway/pay
```

Gateway:

```http
200 OK

{
    "transactionId": "TX123",
    "status": "SUCCESS"
}
```

Now Payment Service updates:

```text
P1 → SUCCESS
```

### Payment table

| payment_id | order_id | amount | status  | gateway_txn |
| ---------- | -------: | -----: | ------- | ----------- |
| P1         |     9001 |   1000 | SUCCESS | TX123       |

And creates:

```text
PaymentSucceeded
```

in the outbox.

---

# 4. Kafka sends PaymentSucceeded

```text
Payment Service
      ↓
Outbox
      ↓
Kafka
      ↓
Inventory Service
```

Inventory receives:

```json
{
    "eventId": "E2",
    "orderId": 9001,
    "productId": 500,
    "quantity": 1
}
```

It reserves the product.

### Inventory

```text
Stock = 10
```

After reservation:

```text
Stock = 9
```

Inventory publishes:

```text
InventoryReserved
```

---

# 5. Now imagine Inventory FAILS

This is where your question becomes important.

Suppose:

```text
Order       ✅
Payment     ✅
Inventory   ❌
```

You **cannot do this**:

```text
ROLLBACK
```

because payment already happened outside your DB transaction.

You need a **compensating transaction**.

---

# 6. Compensation

Inventory publishes:

```text
InventoryReservationFailed
```

Payment Service receives:

```json
{
    "eventId": "E3",
    "orderId": 9001,
    "reason": "OUT_OF_STOCK"
}
```

Payment Service then asks the payment gateway for a refund.

```http
POST /payment-gateway/refund
```

Request:

```json
{
    "transactionId": "TX123",
    "amount": 1000,
    "reason": "INVENTORY_FAILED"
}
```

Gateway:

```http
200 OK

{
    "refundId": "REF123",
    "status": "SUCCESS"
}
```

Now Payment DB becomes:

| payment_id | order_id | amount | status   | gateway_txn | refund_id |
| ---------- | -------: | -----: | -------- | ----------- | --------- |
| P1         |     9001 |   1000 | REFUNDED | TX123       | REF123    |

And Order becomes:

| order_id | amount | status    |
| -------: | -----: | --------- |
|     9001 |   1000 | CANCELLED |

---

# Complete flow

```text
                Order Request
                     │
                     ▼
               Order Service
                     │
             DB + Outbox
                     │
                     ▼
                   Kafka
                     │
                     ▼
              Payment Service
                     │
             Payment Gateway
                     │
                  SUCCESS
                     │
                     ▼
                   Kafka
                     │
                     ▼
             Inventory Service
                     │
                  FAILED ❌
                     │
                     ▼
                   Kafka
                     │
                     ▼
              Payment Service
                     │
                   Refund
                     │
                     ▼
              Payment Gateway
                     │
                  SUCCESS
                     │
                     ▼
               Order CANCELLED
```

---

# Now your question: "I have 3 events and one fails, should I revert all?"

**Not necessarily.**

This is the key difference between a **DB transaction** and a **distributed Saga**.

Suppose:

```text
Event 1 → Payment       ✅
Event 2 → Inventory     ✅
Event 3 → Shipping      ❌
```

You don't literally rollback E1 and E2.

Instead, you execute their **compensating actions**:

```text
Payment       → Refund Payment
Inventory     → Release Stock
Shipping      → Cancel Shipment
```

So:

```text
Forward transaction          Compensation
------------------------------------------------
Payment ₹1000          →     Refund ₹1000
Reserve stock          →     Release stock
Create shipment       →     Cancel shipment
```

---

# Tables you would typically have

## Order

```sql
CREATE TABLE orders (
    order_id       BIGINT PRIMARY KEY,
    user_id        BIGINT,
    amount         DECIMAL(10,2),
    status         VARCHAR(30),
    created_at     TIMESTAMP
);
```

Possible statuses:

```text
PENDING
CONFIRMED
CANCELLED
```

---

## Payment

```sql
CREATE TABLE payments (
    payment_id       BIGINT PRIMARY KEY,
    order_id         BIGINT,
    amount           DECIMAL(10,2),
    status           VARCHAR(30),
    gateway_txn_id   VARCHAR(100),
    refund_id        VARCHAR(100),
    created_at       TIMESTAMP
);
```

Statuses:

```text
PROCESSING
SUCCESS
FAILED
REFUND_PENDING
REFUNDED
```

---

## Outbox

```sql
CREATE TABLE outbox_events (
    event_id          VARCHAR(100) PRIMARY KEY,
    aggregate_id      BIGINT,
    event_type        VARCHAR(100),
    payload           JSON,
    status             VARCHAR(20),
    retry_count        INT,
    created_at         TIMESTAMP,
    published_at      TIMESTAMP
);
```

---

## Processed Events

This handles Kafka duplicates.

```sql
CREATE TABLE processed_events (
    event_id       VARCHAR(100) PRIMARY KEY,
    consumer_name  VARCHAR(100),
    processed_at   TIMESTAMP
);
```

Suppose Kafka sends:

```text
E2
E2
```

Consumer:

```text
E2 → process
E2 → already exists → ignore
```

---

# One more VERY important problem

Suppose payment succeeds:

```text
Gateway → SUCCESS
```

but Payment Service crashes **before updating its DB**.

Now:

```text
Gateway:
TX123 = SUCCESS

Payment DB:
P1 = PROCESSING
```

What do we do?

We **don't blindly charge again**.

We use an **idempotency key**:

```http
POST /payment

Idempotency-Key: ORDER-9001
```

The gateway knows:

```text
ORDER-9001 → TX123
```

If we retry:

```text
POST /payment
Idempotency-Key: ORDER-9001
```

gateway returns the existing transaction:

```json
{
    "transactionId": "TX123",
    "status": "SUCCESS"
}
```

So you don't accidentally charge ₹1,000 twice.

---

# This is the architecture you should remember

```text
                     ┌─────────────┐
                     │ Order DB    │
                     └──────┬──────┘
                            │
                         Outbox
                            │
                            ▼
                         Kafka
                            │
              ┌─────────────┼─────────────┐
              ▼             ▼             ▼
          Payment        Inventory      Shipping
              │             │             │
              ▼             ▼             ▼
         Payment GW       Stock DB     Shipping DB
              │
              │
           FAILURE
              │
              ▼
          Compensation
              │
              ▼
            Refund
```

### Interview answer

If interviewer asks:

> **"Payment succeeded but inventory failed. How do you rollback?"**

Say:

> "We cannot perform a distributed DB rollback because payment is an external side effect. We use a Saga. The inventory failure emits a failure event, and the payment service executes a compensating transaction, such as a refund. Each step has its own local transaction and outbox event, and consumers are idempotent to handle retries and duplicate messages."

That's the exact concept you want to understand: **Outbox solves reliable event publishing; Saga solves consistency across multiple services when one step fails.**


**Outbox table will be used by 3services?
**
No. Each service normally has its own Outbox table in its own database. This is an important point.

For example:

                Kafka
                  ↑
                  │
       ┌──────────┼──────────┐
       │          │          │
       │          │          │
   Order DB   Payment DB   Inventory DB
       │          │          │
   outbox      outbox      outbox
