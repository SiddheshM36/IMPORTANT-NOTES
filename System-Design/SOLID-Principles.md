Since you have **5 years of Java experience** and are preparing for interviews, you should understand SOLID not just by definition but by **why companies use it**.

The easiest way to remember SOLID is:

> **"Write code that is easy to change without breaking other code."**

Let's imagine we're building a **Banking Application**.

---

# What is SOLID?

SOLID consists of **5 design principles**.

| Letter | Principle                       | Meaning                                              |
| ------ | ------------------------------- | ---------------------------------------------------- |
| S      | Single Responsibility Principle | One class = One job                                  |
| O      | Open Closed Principle           | Open for extension, Closed for modification          |
| L      | Liskov Substitution Principle   | Child class should replace parent without problems   |
| I      | Interface Segregation Principle | Don't force classes to implement unnecessary methods |
| D      | Dependency Inversion Principle  | Depend on abstraction, not concrete classes          |

Think of them as **5 rules for writing clean Java code.**

---

# 1. S — Single Responsibility Principle (SRP)

### Meaning

A class should have **only one reason to change**.

One class = One responsibility.

---

## Wrong Example

Imagine you have

```java
class Employee {

    public void calculateSalary() {
        System.out.println("Calculating Salary");
    }

    public void saveEmployee() {
        System.out.println("Saving to Database");
    }

    public void sendEmail() {
        System.out.println("Sending Email");
    }
}
```

This class is doing **3 jobs**

* Salary
* Database
* Email

Tomorrow,

* salary logic changes
* database changes
* email changes

You'll modify the same class again and again.

Not good.

---

## Correct

Split responsibilities.

```java
class SalaryService {

    public void calculateSalary() {
        System.out.println("Calculating Salary");
    }
}
```

```java
class EmployeeRepository {

    public void saveEmployee() {
        System.out.println("Saving Employee");
    }
}
```

```java
class EmailService {

    public void sendEmail() {
        System.out.println("Sending Email");
    }
}
```

Now every class has only **one responsibility**.

### Real Bank Example

Instead of

```
TransactionService
```

doing

* debit
* email
* SMS
* audit
* PDF

Split into

```
TransactionService
NotificationService
AuditService
PdfService
```

Much cleaner.

---

# 2. O — Open Closed Principle (OCP)

### Meaning

Your code should be

✅ Open for adding new features

❌ Closed for modifying existing code

Imagine your manager says

> "Now support UPI."

You should **add new code**, not modify old working code.

---

## Wrong Example

```java
class PaymentService {

    public void pay(String type) {

        if(type.equals("CARD")) {
            System.out.println("Card Payment");
        }

        else if(type.equals("UPI")) {
            System.out.println("UPI Payment");
        }

        else if(type.equals("NETBANKING")) {
            System.out.println("Net Banking");
        }

    }

}
```

Tomorrow

Need Wallet

Need Crypto

Need PayPal

You'll keep changing this class.

---

## Better

Create interface

```java
interface Payment {

    void pay();
}
```

Card

```java
class CardPayment implements Payment {

    public void pay() {
        System.out.println("Card Payment");
    }

}
```

UPI

```java
class UpiPayment implements Payment {

    public void pay() {
        System.out.println("UPI Payment");
    }

}
```

Wallet

```java
class WalletPayment implements Payment {

    public void pay() {
        System.out.println("Wallet Payment");
    }

}
```

Main

```java
Payment payment = new WalletPayment();
payment.pay();
```

Need a new payment?

Just create another class.

Existing code stays untouched.

---

# 3. L — Liskov Substitution Principle (LSP)

This is the most confusing one.

Simple meaning:

> A child class should behave like its parent.

If parent works,

Child should also work.

---

### Good Example

Parent

```java
class Bird {

    public void eat() {
        System.out.println("Eating");
    }

}
```

Child

```java
class Sparrow extends Bird {

}
```

Now

```java
Bird bird = new Sparrow();

bird.eat();
```

Works perfectly.

---

### Wrong Example

Suppose

```java
class Bird {

    public void fly() {
        System.out.println("Flying");
    }

}
```

Then

```java
class Penguin extends Bird {

    @Override
    public void fly() {
        throw new UnsupportedOperationException();
    }

}
```

Now

```java
Bird bird = new Penguin();

bird.fly();
```

Crash!

Penguin cannot fly.

So Penguin should **not** inherit from Bird if `Bird` promises flying.

Better design:

```java
class Bird {

    public void eat() {}

}
```

```java
interface Flyable {

    void fly();

}
```

Now

```
Sparrow -> Bird + Flyable

Penguin -> Bird
```

No problem.

---

# 4. I — Interface Segregation Principle (ISP)

Meaning

Don't force a class to implement methods it doesn't need.

---

Wrong

```java
interface Worker {

    void work();

    void eat();

}
```

Human

```java
class Human implements Worker {

    public void work() {}

    public void eat() {}

}
```

Robot

```java
class Robot implements Worker {

    public void work() {}

    public void eat() {
        // Robot doesn't eat!
    }

}
```

Robot is forced to implement

```
eat()
```

even though it doesn't need it.

Bad design.

---

Correct

Split interfaces.

```java
interface Workable {

    void work();

}
```

```java
interface Eatable {

    void eat();

}
```

Human

```java
class Human implements Workable, Eatable {

    public void work(){}

    public void eat(){}

}
```

Robot

```java
class Robot implements Workable {

    public void work(){}

}
```

Perfect.

---

### Real Spring Boot Example

Instead of

```java
interface Notification {

    sendEmail();

    sendSMS();

    sendPush();

}
```

Split into

```
EmailNotification

SMSNotification

PushNotification
```

Each implementation only implements what it needs.

---

# 5. D — Dependency Inversion Principle (DIP)

This is the most important one in Spring Boot.

Meaning

High-level classes should depend on **interfaces**, not concrete classes.

---

Wrong

```java
class EmailService {

    public void send() {
        System.out.println("Email");
    }

}
```

```java
class NotificationService {

    EmailService email = new EmailService();

    public void notifyUser() {
        email.send();
    }

}
```

Problem

Tomorrow

Need SMS

Need WhatsApp

Need Push

Need Slack

Need Kafka

You'll modify NotificationService.

---

Correct

Create interface

```java
interface MessageService {

    void send();

}
```

Email

```java
class EmailService implements MessageService {

    public void send() {
        System.out.println("Email");
    }

}
```

SMS

```java
class SmsService implements MessageService {

    public void send() {
        System.out.println("SMS");
    }

}
```

Notification

```java
class NotificationService {

    private MessageService service;

    public NotificationService(MessageService service) {
        this.service = service;
    }

    public void notifyUser() {
        service.send();
    }

}
```

Now

```java
MessageService service = new EmailService();

NotificationService notification =
        new NotificationService(service);

notification.notifyUser();
```

Tomorrow

```java
MessageService service = new SmsService();
```

Nothing else changes.

---

## Spring Boot Example

When you write

```java
@Service
class EmailService implements NotificationService {

}
```

Then

```java
@Autowired
private NotificationService notificationService;
```

You are depending on the **interface**, not the implementation.

Spring injects the implementation.

That's Dependency Inversion in action.

---

# Easy Trick to Remember

| Principle | Think Like This                           |
| --------- | ----------------------------------------- |
| **S**     | One class = One job                       |
| **O**     | Add new code, don't change old code       |
| **L**     | Child should behave like parent           |
| **I**     | Small interfaces are better               |
| **D**     | Depend on interfaces, not implementations |

---
