Bilkul bhai 🔥 **exactly isi format mein saare 23 patterns** ek master table mein:

## 🟢 Creational Patterns

| **Pattern**          | **Simple meaning**        | **Kyu zarurat padti hai?**                             | **Real-life example**                               |
| -------------------- | ------------------------- | ------------------------------------------------------ | --------------------------------------------------- |
| **Singleton**        | 🔴 **ONE OBJECT**         | Puri application mein sirf ek hi instance chahiye      | Ek building ka single security/control room         |
| **Factory**          | 🏭 **CREATE OBJECT**      | Object creation logic ko client se hide karna hai      | Restaurant → order ke according Pizza/Burger banana |
| **Abstract Factory** | 🏭 **CREATE FAMILY**      | Related objects ki consistent family create karni hai  | Windows UI → Windows Button + Checkbox              |
| **Builder**          | 🧱 **BUILD STEP-BY-STEP** | Complex object ke bahut saare optional parameters hain | Customized Pizza → size + cheese + toppings         |
| **Prototype**        | 📋 **COPY OBJECT**        | Existing object ki copy efficiently banani hai         | Original document → duplicate document              |

---

## 🔵 Structural Patterns

| **Pattern**   | **Simple meaning**     | **Kyu zarurat padti hai?**                                                           | **Real-life example**                           |
| ------------- | ---------------------- | ------------------------------------------------------------------------------------ | ----------------------------------------------- |
| **Adapter**   | 🔌 **CONNECT**         | Do classes/interfaces compatible nahi hain                                           | Indian plug → foreign socket                    |
| **Decorator** | 🎁 **ADD FEATURE**     | Existing object ko modify kiye bina extra functionality chahiye                      | Coffee + Milk + Sugar                           |
| **Proxy**     | 🛡️ **CONTROL ACCESS** | Real object ko direct access nahi dena / pehle checking karni hai                    | Security guard → actual office                  |
| **Facade**    | 🎛️ **SIMPLIFY**       | Bahut saare complex services ko ek simple API ke through expose karna                | Hotel reception                                 |
| **Composite** | 🌳 **TREE**            | Individual objects aur groups ko same tarike se handle karna hai                     | Folder → files + subfolders                     |
| **Bridge**    | 🌉 **SEPARATE**        | Do independent dimensions ko alag rakhna hai taaki dono independently change ho sake | Remote + TV/Radio                               |
| **Flyweight** | 🪶 **SHARE**           | Bahut saare similar objects ki wajah se memory bachani hai                           | Game mein 1 lakh trees → common tree data share |

---

## 🟠 Behavioral Patterns

| **Pattern**                 | **Simple meaning**            | **Kyu zarurat padti hai?**                                                                     | **Real-life example**                            |
| --------------------------- | ----------------------------- | ---------------------------------------------------------------------------------------------- | ------------------------------------------------ |
| **Strategy**                | 🎯 **CHOOSE BEHAVIOR**        | Same kaam karne ke multiple algorithms/tareeke hain                                            | Google Maps → Fastest / Cheapest / No-Toll route |
| **Observer**                | 📢 **NOTIFY**                 | Ek object change ho toh multiple interested objects ko notify karna hai                        | YouTube channel → subscribers                    |
| **Chain of Responsibility** | 🔗 **PASS REQUEST**           | Request ko multiple handlers se sequentially process karna hai                                 | Customer complaint → L1 → L2 → L3                |
| **Command**                 | 🎮 **ACTION AS OBJECT**       | Action/request ko store, queue ya undo karna hai                                               | Remote → ON/OFF command                          |
| **Template Method**         | 📐 **FIXED SKELETON**         | Algorithm ka overall flow same hai, kuch steps different hain                                  | Tea/Coffee → boil → ingredients → serve          |
| **State**                   | 🔄 **STATE CHANGES BEHAVIOR** | Object ka behavior uski current state ke according change hota hai                             | Order → New → Paid → Shipped → Delivered         |
| **Iterator**                | 🚶 **TRAVERSE**               | Collection ke elements ko sequentially access karna hai without internal structure expose kiye | List iterator                                    |
| **Mediator**                | 🧑‍⚖️ **MIDDLEMAN**           | Bahut saare objects ke direct communication/dependencies ko reduce karna hai                   | Chat room → users communicate through server     |
| **Memento**                 | 📸 **SNAPSHOT**               | Object ki previous state save karke baad mein restore karni hai                                | Text editor → Undo                               |
| **Visitor**                 | 🧑‍💼 **ADD OPERATION**       | Existing classes ko modify kiye bina naye operations add karne hain                            | Shopping cart → Tax/Discount/Shipping visitors   |

---

# 🧠 Ek aur ultra-short table — interview mein scenario identify karne ke liye

| Agar problem mein ye dikhe                  | Pattern          |
| ------------------------------------------- | ---------------- |
| **Sirf ek object chahiye**                  | Singleton        |
| **Type ke according object banana hai**     | Factory          |
| **Related objects ki family banani hai**    | Abstract Factory |
| **Complex object step-by-step banana hai**  | Builder          |
| **Existing object ki copy chahiye**         | Prototype        |
| **Interface mismatch**                      | Adapter          |
| **Extra functionality dynamically add**     | Decorator        |
| **Access control / lazy loading / caching** | Proxy            |
| **Complex system ko simple banana**         | Facade           |
| **Tree / hierarchy**                        | Composite        |
| **Do dimensions independently change**      | Bridge           |
| **Memory bachane ke liye objects share**    | Flyweight        |
| **Same kaam ke different algorithms**       | Strategy         |
| **Ek change → multiple notifications**      | Observer         |
| **Request ko next handler ko pass**         | Chain            |
| **Action ko object banana**                 | Command          |
| **Same algorithm ka fixed flow**            | Template Method  |
| **State ke according behavior change**      | State            |
| **Collection traverse karna**               | Iterator         |
| **Objects ke beech middleman**              | Mediator         |
| **Save + restore previous state**           | Memento          |
| **Existing objects par naye operations**    | Visitor          |

---

# 🔥 3-category memory trick

```text
CREATIONAL
"OBJECT KAISE BANEGA?"
        ↓
ONE → CREATE → FAMILY → BUILD → COPY
```

```text
STRUCTURAL
"OBJECTS KAISE CONNECT HONGE?"
        ↓
CONNECT → ADD → CONTROL → SIMPLIFY
→ TREE → SEPARATE → SHARE
```

```text
BEHAVIORAL
"OBJECTS KAISE BEHAVE KARENGE?"
        ↓
CHOOSE → NOTIFY → PASS → ACTION
→ SKELETON → STATE → TRAVERSE
→ MIDDLEMAN → SNAPSHOT → OPERATION
```

**Bas ye table + previous detailed explanations = tera complete Design Pattern revision material.** 🔥



😂 Haan bhai, ab **proper master notes** banate hain. Sirf one-liner nahi. Main har pattern ke liye:

**Definition → Problem → Solution → Structure → Full Java code → Real-life use → Kab use karna → Interview question**

Aur sabse pehle ek rule:

> **Design Pattern koi library/annotation nahi hai. Ye recurring software problem ka proven design/solution hai.**

---

# 🧠 DESIGN PATTERNS — COMPLETE JAVA NOTES

```text
                         DESIGN PATTERNS
                               |
          ┌────────────────────┼────────────────────┐
          ↓                    ↓                    ↓
     CREATIONAL            STRUCTURAL           BEHAVIORAL
     Object banana         Objects connect       Behavior
          |                    |                    |
     Factory               Adapter              Strategy
     Builder               Decorator            Observer
     Singleton             Proxy                Command
     Prototype             Facade               State
     Abstract Factory      Composite             etc.
```

---

# 🟢 PART 1 — CREATIONAL PATTERNS

Creational ka simple question:

> **"Object kaise create/manage karna hai?"**

---

# 1. Singleton Pattern

## 💡 Problem

Maan le application mein `Logger` hai.

Har jagah:

```java
new Logger();
```

kar diya toh multiple Logger objects banenge.

```text
Service A → Logger 1
Service B → Logger 2
Service C → Logger 3
```

Humein sirf **ONE Logger** chahiye.

---

## ✅ Singleton solution

```java
class Logger {

    private static Logger instance;

    private Logger() {
        // private constructor
    }

    public static Logger getInstance() {

        if (instance == null) {
            instance = new Logger();
        }

        return instance;
    }

    public void log(String message) {
        System.out.println(message);
    }
}
```

Use:

```java
Logger l1 = Logger.getInstance();
Logger l2 = Logger.getInstance();

System.out.println(l1 == l2);
```

Output:

```text
true
```

---

## Structure

```text
          Logger
            |
      private constructor
            |
      getInstance()
            |
        ONE object
```

---

## Real-world use

* Logger
* Configuration manager
* Cache manager
* Application-wide registry

⚠️ Spring Boot mein `@Component` beans default singleton scope ke hote hain, so manually Singleton banana har baar necessary nahi.

---

## Interview

**Q: Singleton kaise ensure karoge?**

Answer:

1. Constructor private
2. Static instance
3. Static access method
4. Thread safety ka dhyan

---

# 2. Factory Pattern

## 💡 Problem

Suppose payment:

```text
UPI
CARD
PAYPAL
```

Client ko nahi pata hona chahiye ki actual class kaise create hoti hai.

---

## Without Factory

```java
Payment payment;

if (type.equals("UPI")) {
    payment = new UpiPayment();
} else if (type.equals("CARD")) {
    payment = new CardPayment();
}
```

Creation logic client/service mein aa gaya.

---

## ✅ Factory

```java
interface Payment {
    void pay();
}
```

Implementations:

```java
class UpiPayment implements Payment {

    public void pay() {
        System.out.println("Payment using UPI");
    }
}
```

```java
class CardPayment implements Payment {

    public void pay() {
        System.out.println("Payment using Card");
    }
}
```

Factory:

```java
class PaymentFactory {

    public static Payment createPayment(String type) {

        if (type.equalsIgnoreCase("UPI")) {
            return new UpiPayment();
        }

        if (type.equalsIgnoreCase("CARD")) {
            return new CardPayment();
        }

        throw new IllegalArgumentException(
                "Unknown payment type"
        );
    }
}
```

Client:

```java
Payment payment =
        PaymentFactory.createPayment("UPI");

payment.pay();
```

---

## Flow

```text
Client
  |
  | "UPI"
  ↓
PaymentFactory
  |
  ↓
UpiPayment
  |
  ↓
pay()
```

### Main purpose:

> **Object creation ko centralize/hide karna.**

---

# 3. Abstract Factory

Factory:

> **ONE type ka product**

Abstract Factory:

> **RELATED products ki FAMILY**

Suppose UI:

```text
Windows:
Button + Checkbox

Mac:
Button + Checkbox
```

Interfaces:

```java
interface Button {
    void render();
}

interface Checkbox {
    void render();
}
```

Windows:

```java
class WindowsButton implements Button {

    public void render() {
        System.out.println("Windows Button");
    }
}

class WindowsCheckbox implements Checkbox {

    public void render() {
        System.out.println("Windows Checkbox");
    }
}
```

Mac:

```java
class MacButton implements Button {

    public void render() {
        System.out.println("Mac Button");
    }
}

class MacCheckbox implements Checkbox {

    public void render() {
        System.out.println("Mac Checkbox");
    }
}
```

Abstract factory:

```java
interface UIFactory {

    Button createButton();

    Checkbox createCheckbox();
}
```

Windows factory:

```java
class WindowsFactory implements UIFactory {

    public Button createButton() {
        return new WindowsButton();
    }

    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}
```

Mac factory:

```java
class MacFactory implements UIFactory {

    public Button createButton() {
        return new MacButton();
    }

    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}
```

Client:

```java
UIFactory factory = new WindowsFactory();

Button button = factory.createButton();
Checkbox checkbox = factory.createCheckbox();

button.render();
checkbox.render();
```

Flow:

```text
              UIFactory
              /       \
             /         \
 WindowsFactory       MacFactory
      |                   |
 Button + Checkbox     Button + Checkbox
```

### Memory:

> **Factory = ONE PRODUCT**
> **Abstract Factory = PRODUCT FAMILY**

---

# 4. Builder Pattern

## 💡 Problem

Complex object:

```java
User user = new User(
    "Siddhesh",
    30,
    "email",
    "Mumbai",
    "999999",
    ...
);
```

Bahut parameters.

Especially optional parameters → constructor hell.

---

## Builder

```java
class User {

    private String name;
    private int age;
    private String email;
    private String city;

    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.email = builder.email;
        this.city = builder.city;
    }

    public static class Builder {

        private String name;
        private int age;
        private String email;
        private String city;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
```

Use:

```java
User user = new User.Builder()
        .name("Siddhesh")
        .age(30)
        .email("test@gmail.com")
        .city("Mumbai")
        .build();
```

### Why useful?

Readable:

```text
name()
age()
email()
city()
build()
```

instead of:

```text
new User(a,b,c,d,e,f,g)
```

### Real-world

* DTO construction
* HTTP request objects
* Configuration
* Complex immutable objects

---

# 5. Prototype Pattern

## Problem

Object banana expensive hai.

Instead of creating from scratch:

> Existing object ki copy bana do.

```java
class User implements Cloneable {

    String name;

    User(String name) {
        this.name = name;
    }

    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
```

Use:

```java
User user1 = new User("Siddhesh");

User user2 = user1.clone();
```

```text
user1
  ↓ clone()
user2
```

### Use

* Expensive object creation
* Templates
* Object duplication

---

# 🔵 PART 2 — STRUCTURAL PATTERNS

Structural ka question:

> **"Objects/classes ko kaise connect, wrap ya organize karna hai?"**

---

# 6. Adapter Pattern

## Problem

Tera application expects:

```java
interface PaymentProcessor {

    void pay();
}
```

Third-party:

```java
class Razorpay {

    void makePayment() {
        System.out.println("Razorpay");
    }
}
```

Mismatch:

```text
Application → pay()

Razorpay → makePayment()
```

---

## Adapter

```java
class RazorpayAdapter
        implements PaymentProcessor {

    private Razorpay razorpay;

    RazorpayAdapter(Razorpay razorpay) {
        this.razorpay = razorpay;
    }

    public void pay() {
        razorpay.makePayment();
    }
}
```

Use:

```java
Razorpay razorpay = new Razorpay();

PaymentProcessor processor =
        new RazorpayAdapter(razorpay);

processor.pay();
```

Flow:

```text
Application
     |
   pay()
     ↓
 Adapter
     |
makePayment()
     ↓
 Razorpay
```

### Main purpose:

> **Incompatible interfaces ko compatible banana.**

---

# 7. Decorator Pattern

## Problem

Coffee hai:

```text
Coffee = ₹100
```

Add milk:

```text
₹120
```

Add sugar:

```text
₹125
```

Add whipped cream:

```text
₹150
```

Agar classes banaoge:

```text
MilkCoffee
SugarCoffee
MilkSugarCoffee
MilkSugarCreamCoffee
...
```

Classes explode.

---

## Decorator

Common interface:

```java
interface Coffee {

    double cost();
}
```

Basic:

```java
class SimpleCoffee implements Coffee {

    public double cost() {
        return 100;
    }
}
```

Decorator:

```java
abstract class CoffeeDecorator
        implements Coffee {

    protected Coffee coffee;

    CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }
}
```

Milk:

```java
class MilkDecorator extends CoffeeDecorator {

    MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    public double cost() {
        return coffee.cost() + 20;
    }
}
```

Sugar:

```java
class SugarDecorator extends CoffeeDecorator {

    SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    public double cost() {
        return coffee.cost() + 5;
    }
}
```

Use:

```java
Coffee coffee =
    new SugarDecorator(
        new MilkDecorator(
            new SimpleCoffee()
        )
    );

System.out.println(coffee.cost());
```

Output:

```text
125
```

### Main idea:

> **Existing object ko wrap karke functionality add karo.**

---

# 8. Proxy Pattern

## Problem

Real object ko direct access nahi dena.

Example:

```text
Client
  ↓
Payment
```

But authorization check chahiye.

---

## Proxy

```java
interface Payment {
    void pay();
}
```

Real object:

```java
class RealPayment implements Payment {

    public void pay() {
        System.out.println("Payment done");
    }
}
```

Proxy:

```java
class PaymentProxy implements Payment {

    private RealPayment realPayment;

    public void pay() {

        if (!isAuthorized()) {
            System.out.println("Access denied");
            return;
        }

        if (realPayment == null) {
            realPayment = new RealPayment();
        }

        realPayment.pay();
    }

    private boolean isAuthorized() {
        return true;
    }
}
```

Flow:

```text
Client
 ↓
Proxy
 ↓
Authorization
 ↓
Real Object
```

### Real use

* Authorization
* Caching
* Lazy loading
* Logging
* Remote objects

### Memory:

> **Proxy = Gatekeeper**

---

# 9. Facade Pattern

## Problem

Order place karte waqt:

```text
Inventory
Payment
Shipping
Notification
```

Client ko 4 services call karni pad rahi hain.

---

## Facade

```java
class OrderFacade {

    private InventoryService inventory;
    private PaymentService payment;
    private ShippingService shipping;
    private NotificationService notification;

    public void placeOrder() {

        inventory.reserve();

        payment.pay();

        shipping.ship();

        notification.send();
    }
}
```

Client:

```java
OrderFacade facade = new OrderFacade();

facade.placeOrder();
```

Instead of:

```java
inventory.reserve();
payment.pay();
shipping.ship();
notification.send();
```

Client ko sirf:

```text
placeOrder()
```

pata hai.

### Main idea:

> **Complex subsystem ko simple interface do.**

---

# 10. Composite Pattern

## Problem

File system:

```text
Folder
 ├── File
 ├── File
 └── Folder
      └── File
```

File aur Folder dono ko same way handle karna hai.

---

## Common interface

```java
interface FileSystemItem {

    void show();
}
```

File:

```java
class File implements FileSystemItem {

    private String name;

    File(String name) {
        this.name = name;
    }

    public void show() {
        System.out.println("File: " + name);
    }
}
```

Folder:

```java
class Folder implements FileSystemItem {

    private String name;
    private List<FileSystemItem> items =
            new ArrayList<>();

    Folder(String name) {
        this.name = name;
    }

    public void add(FileSystemItem item) {
        items.add(item);
    }

    public void show() {

        System.out.println("Folder: " + name);

        for (FileSystemItem item : items) {
            item.show();
        }
    }
}
```

Use:

```java
Folder root = new Folder("Root");

root.add(new File("a.txt"));
root.add(new File("b.txt"));

Folder docs = new Folder("Docs");
docs.add(new File("resume.pdf"));

root.add(docs);

root.show();
```

### Main idea:

> **Individual object aur group ko same interface se treat karo.**

### Memory:

> **Composite = TREE**

---

# 11. Bridge Pattern

## Problem

Suppose:

```text
Remote
TV
Radio
AC
```

Agar direct combinations:

```text
SamsungTVRemote
SonyTVRemote
SamsungRadioRemote
SonyRadioRemote
...
```

Combinations explode.

---

## Bridge

Separate dimensions:

```text
Remote
   |
   ↓
Device
 ├── TV
 ├── Radio
 └── AC
```

Code:

```java
interface Device {

    void on();
    void off();
}
```

```java
class TV implements Device {

    public void on() {
        System.out.println("TV ON");
    }

    public void off() {
        System.out.println("TV OFF");
    }
}
```

Remote:

```java
class Remote {

    protected Device device;

    Remote(Device device) {
        this.device = device;
    }

    void turnOn() {
        device.on();
    }

    void turnOff() {
        device.off();
    }
}
```

Use:

```java
Device tv = new TV();

Remote remote = new Remote(tv);

remote.turnOn();
```

### Main idea:

> **Abstraction aur implementation ko independently change karna.**

### Memory:

> **Bridge = Separate two dimensions**

---

# 12. Flyweight Pattern

## Problem

Game mein 1 lakh trees hain.

Har tree ke andar same:

```text
color
texture
type
```

store karna waste hai.

---

## Solution

Common data share:

```text
TreeType
   ↑
   |
100000 Tree objects
```

Example:

```java
class TreeType {

    String name;
    String color;
    String texture;

    TreeType(String name, String color, String texture) {
        this.name = name;
        this.color = color;
        this.texture = texture;
    }
}
```

Factory/cache:

```java
class TreeFactory {

    private static Map<String, TreeType> cache =
            new HashMap<>();

    static TreeType getTreeType(
            String name,
            String color,
            String texture) {

        String key = name + color + texture;

        return cache.computeIfAbsent(
            key,
            k -> new TreeType(name, color, texture)
        );
    }
}
```

### Main idea:

> **Common immutable state share karke memory save karo.**

---

# 🟠 PART 3 — BEHAVIORAL PATTERNS

Behavioral:

> **"Objects ka behavior/communication kaise manage karna hai?"**

---

# 13. Strategy Pattern ⭐

Ab jo humne detail mein kiya.

## Problem

Google Maps:

```text
Same job:
Find Route

Different algorithms:
Fastest
Cheapest
Avoid Toll
```

Common interface:

```java
interface RouteStrategy {

    void findRoute();
}
```

Implementations:

```java
class FastestRoute implements RouteStrategy {

    public void findRoute() {
        System.out.println("Fastest route");
    }
}
```

```java
class CheapestRoute implements RouteStrategy {

    public void findRoute() {
        System.out.println("Cheapest route");
    }
}
```

```java
class NoTollRoute implements RouteStrategy {

    public void findRoute() {
        System.out.println("Avoid toll route");
    }
}
```

Context:

```java
class GoogleMaps {

    private RouteStrategy strategy;

    GoogleMaps(RouteStrategy strategy) {
        this.strategy = strategy;
    }

    void navigate() {
        strategy.findRoute();
    }

    void setStrategy(RouteStrategy strategy) {
        this.strategy = strategy;
    }
}
```

Use:

```java
GoogleMaps maps =
        new GoogleMaps(new FastestRoute());

maps.navigate();
```

Later:

```java
maps.setStrategy(new NoTollRoute());

maps.navigate();
```

### Main point:

```text
SAME JOB
   ↓
DIFFERENT WAYS
   ↓
CHOOSE / SWITCH
   ↓
STRATEGY
```

### Interview:

> "Multiple interchangeable algorithms and runtime selection."

➡️ Strategy

---

# 14. Observer Pattern ⭐

## Problem

YouTube channel:

```text
Channel
 ├── User A
 ├── User B
 └── User C
```

New video upload hua → sabko notification.

---

## Observer

```java
interface Observer {

    void update(String message);
}
```

Users:

```java
class User implements Observer {

    private String name;

    User(String name) {
        this.name = name;
    }

    public void update(String message) {
        System.out.println(
            name + " received: " + message
        );
    }
}
```

Subject:

```java
class YouTubeChannel {

    private List<Observer> observers =
            new ArrayList<>();

    void subscribe(Observer observer) {
        observers.add(observer);
    }

    void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    void uploadVideo(String video) {

        System.out.println(
            "Uploaded: " + video
        );

        notifyObservers(video);
    }

    private void notifyObservers(String video) {

        for (Observer observer : observers) {
            observer.update(video);
        }
    }
}
```

Use:

```java
YouTubeChannel channel =
        new YouTubeChannel();

User user1 = new User("A");
User user2 = new User("B");

channel.subscribe(user1);
channel.subscribe(user2);

channel.uploadVideo("Java Design Patterns");
```

Flow:

```text
                Channel
                   |
             video uploaded
                   |
                notify()
              /    |    \
             ↓     ↓     ↓
            A      B      C
```

### Main idea:

> **One-to-many notification.**

---

# 15. Chain of Responsibility

## Problem

HTTP request ko multiple checks se guzarna hai:

```text
Authentication
      ↓
Authorization
      ↓
Validation
      ↓
Business Logic
```

Har handler request ko handle kare ya next ko bheje.

---

## Code

```java
abstract class Handler {

    protected Handler next;

    public void setNext(Handler next) {
        this.next = next;
    }

    public abstract void handle(String request);
}
```

Authentication:

```java
class AuthenticationHandler extends Handler {

    public void handle(String request) {

        System.out.println("Authentication");

        if (next != null) {
            next.handle(request);
        }
    }
}
```

Authorization:

```java
class AuthorizationHandler extends Handler {

    public void handle(String request) {

        System.out.println("Authorization");

        if (next != null) {
            next.handle(request);
        }
    }
}
```

Chain:

```java
Handler auth = new AuthenticationHandler();
Handler authorization =
        new AuthorizationHandler();

auth.setNext(authorization);

auth.handle("REQUEST");
```

### Memory:

> **PASS REQUEST**

---

# 16. Command Pattern

## Problem

Remote control:

```text
ON
OFF
VOLUME UP
```

Button ko actual TV logic nahi pata hona chahiye.

---

## Command interface

```java
interface Command {

    void execute();
}
```

Receiver:

```java
class TV {

    void on() {
        System.out.println("TV ON");
    }

    void off() {
        System.out.println("TV OFF");
    }
}
```

Command:

```java
class TurnOnCommand implements Command {

    private TV tv;

    TurnOnCommand(TV tv) {
        this.tv = tv;
    }

    public void execute() {
        tv.on();
    }
}
```

Remote:

```java
class Remote {

    private Command command;

    void setCommand(Command command) {
        this.command = command;
    }

    void pressButton() {
        command.execute();
    }
}
```

Use:

```java
TV tv = new TV();

Command command =
        new TurnOnCommand(tv);

Remote remote = new Remote();

remote.setCommand(command);

remote.pressButton();
```

### Main idea:

> **Action/request ko object bana do.**

### Real use:

* Undo/Redo
* Queue
* Scheduler
* Transactions

---

# 17. Template Method

## Problem

Tea aur Coffee banani hai.

Overall steps same:

```text
Boil Water
↓
Add Ingredients
↓
Pour
```

But ingredients different.

---

## Code

```java
abstract class Beverage {

    public final void prepare() {

        boilWater();

        addIngredients();

        pour();
    }

    private void boilWater() {
        System.out.println("Boiling water");
    }

    protected abstract void addIngredients();

    private void pour() {
        System.out.println("Pouring");
    }
}
```

Tea:

```java
class Tea extends Beverage {

    protected void addIngredients() {
        System.out.println("Adding tea");
    }
}
```

Coffee:

```java
class Coffee extends Beverage {

    protected void addIngredients() {
        System.out.println("Adding coffee");
    }
}
```

Use:

```java
Beverage tea = new Tea();

tea.prepare();
```

### Main idea:

> **Algorithm ka skeleton fixed, kuch steps subclasses implement karti hain.**

---

# 18. State Pattern

## Problem

Order ka behavior state ke according change hota hai.

```text
NEW
 ↓
PAID
 ↓
SHIPPED
 ↓
DELIVERED
```

NEW mein:

```text
Cancel allowed
```

SHIPPED mein:

```text
Cancel not allowed
Tracking allowed
```

DELIVERED mein:

```text
Return allowed
```

Instead of giant:

```java
if (state.equals("NEW")) ...
else if ...
else if ...
```

State objects:

```java
interface OrderState {

    void handle(Order order);
}
```

New state:

```java
class NewState implements OrderState {

    public void handle(Order order) {
        System.out.println("Order is new");
    }
}
```

Paid:

```java
class PaidState implements OrderState {

    public void handle(Order order) {
        System.out.println("Order is paid");
    }
}
```

Order:

```java
class Order {

    private OrderState state;

    void setState(OrderState state) {
        this.state = state;
    }

    void process() {
        state.handle(this);
    }
}
```

Use:

```java
Order order = new Order();

order.setState(new NewState());
order.process();

order.setState(new PaidState());
order.process();
```

### Main idea:

> **Internal state changes → behavior changes.**

---

# 19. Iterator Pattern

## Problem

Collection ka internal structure expose nahi karna.

Tu bas elements traverse karna chahta hai.

Java already provides:

```java
Iterator<String> iterator =
        list.iterator();

while (iterator.hasNext()) {
    System.out.println(iterator.next());
}
```

### Main idea:

> **Collection ko sequentially traverse karna without exposing internal structure.**

Real Java:

```text
List
Set
Map
Iterator
```

---

# 20. Mediator Pattern

## Problem

Agar 10 objects directly ek doosre se baat karein:

```text
A ↔ B
A ↔ C
A ↔ D
B ↔ C
B ↔ D
...
```

Dependencies explode.

Mediator:

```text
A
 \
  Mediator
 /   |   \
B    C    D
```

Example chat room:

```java
interface ChatMediator {

    void sendMessage(String message, User user);
}
```

Mediator:

```java
class ChatRoom implements ChatMediator {

    private List<User> users = new ArrayList<>();

    void addUser(User user) {
        users.add(user);
    }

    public void sendMessage(
            String message,
            User sender) {

        for (User user : users) {

            if (user != sender) {
                user.receive(message);
            }
        }
    }
}
```

User:

```java
class User {

    private ChatMediator mediator;
    private String name;

    User(String name, ChatMediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }

    void send(String message) {
        mediator.sendMessage(message, this);
    }

    void receive(String message) {
        System.out.println(
            name + " received: " + message
        );
    }
}
```

### Main idea:

> **Objects ke beech communication ka middleman.**

---

# 21. Memento Pattern

## Problem

Text editor:

```text
State 1
 ↓
User edits
 ↓
State 2
 ↓
Undo
 ↓
State 1 restore
```

Snapshot chahiye.

---

## Code

```java
class Editor {

    private String text;

    void setText(String text) {
        this.text = text;
    }

    String getText() {
        return text;
    }

    EditorMemento save() {
        return new EditorMemento(text);
    }

    void restore(EditorMemento memento) {
        this.text = memento.getText();
    }
}
```

Memento:

```java
class EditorMemento {

    private final String text;

    EditorMemento(String text) {
        this.text = text;
    }

    String getText() {
        return text;
    }
}
```

Use:

```java
Editor editor = new Editor();

editor.setText("Hello");

EditorMemento backup = editor.save();

editor.setText("Hello World");

editor.restore(backup);

System.out.println(editor.getText());
```

Output:

```text
Hello
```

### Main idea:

> **Snapshot → modify → restore**

---

# 22. Visitor Pattern

Ye thoda advanced hai.

## Problem

Suppose shopping cart mein:

```text
Book
Laptop
Food
```

Kal tax calculation add karna hai.

Phir discount.

Phir shipping.

Agar har class mein methods add karte rahe:

```text
Book:
 calculateTax()
 calculateDiscount()
 calculateShipping()

Laptop:
 calculateTax()
 calculateDiscount()
 calculateShipping()

Food:
 ...
```

Classes grow hoti jayengi.

Visitor operations ko bahar nikalta hai.

---

## Element

```java
interface Item {

    void accept(Visitor visitor);
}
```

Book:

```java
class Book implements Item {

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
```

Laptop:

```java
class Laptop implements Item {

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
```

Visitor:

```java
interface Visitor {

    void visit(Book book);

    void visit(Laptop laptop);
}
```

Tax visitor:

```java
class TaxVisitor implements Visitor {

    public void visit(Book book) {
        System.out.println("Book tax");
    }

    public void visit(Laptop laptop) {
        System.out.println("Laptop tax");
    }
}
```

Use:

```java
Item book = new Book();

book.accept(new TaxVisitor());
```

### Main idea:

> **Objects same rakho, operations ko separate visitors mein rakho.**

### Memory:

> **Visitor = ADD NEW OPERATIONS**

---

# 🧠 AB SAARE PATTERNS KO REAL PROBLEM SE IDENTIFY KAR

## 🟢 CREATIONAL

| Problem                     | Pattern              |
| --------------------------- | -------------------- |
| Sirf ONE instance           | **Singleton**        |
| Type ke according object    | **Factory**          |
| Related objects ki family   | **Abstract Factory** |
| Complex object step-by-step | **Builder**          |
| Existing object ki copy     | **Prototype**        |

---

## 🔵 STRUCTURAL

| Problem                             | Pattern       |
| ----------------------------------- | ------------- |
| Interface mismatch                  | **Adapter**   |
| Existing object mein feature add    | **Decorator** |
| Access control                      | **Proxy**     |
| Complex system ko simple banana     | **Facade**    |
| Tree hierarchy                      | **Composite** |
| Two dimensions independently change | **Bridge**    |
| Memory save by sharing              | **Flyweight** |

---

## 🟠 BEHAVIORAL

| Problem                                 | Pattern             |
| --------------------------------------- | ------------------- |
| Same job, different algorithms          | **Strategy**        |
| One change → many notifications         | **Observer**        |
| Request handler-to-handler pass         | **Chain**           |
| Action ko object banana                 | **Command**         |
| Same algorithm skeleton                 | **Template Method** |
| State ke according behavior change      | **State**           |
| Collection traverse                     | **Iterator**        |
| Objects ke beech middleman              | **Mediator**        |
| Snapshot/undo                           | **Memento**         |
| New operations without changing objects | **Visitor**         |

---

# 🔥 AB SABSE IMPORTANT CONFUSIONS

## Factory vs Strategy

```text
Factory
   ↓
"KAUNSA OBJECT?"
```

```text
Strategy
   ↓
"SAME KAAM KAISE KARNA HAI?"
```

Example:

```text
Factory:
"FastestRoute ka object do."

Strategy:
"Route calculate karne ke liye Fastest algorithm use karo."
```

**Factory Strategy object ko create bhi kar sakti hai.**
Dono ek saath bhi use ho sakte hain.

---

# Adapter vs Decorator

### Adapter

```text
A → Adapter → B
```

Purpose:

> **Interface compatible karna**

### Decorator

```text
A → Decorator → Extra Feature
```

Purpose:

> **Functionality add karna**

---

# Decorator vs Proxy

Dono wrapping karte hain:

```text
Client
 ↓
Wrapper
 ↓
Real Object
```

But:

```text
Decorator → ADD functionality
Proxy     → CONTROL access
```

---

# Strategy vs State

Dono mein behavior objects hote hain.

### Strategy:

**Client chooses behavior**

```text
GoogleMaps
   ↓
Fastest / Cheapest / NoToll
```

### State:

**Object ka internal state behavior decide karta hai**

```text
Order
 ↓
NEW → PAID → SHIPPED
```

---

# Factory vs Abstract Factory

```text
Factory
   ↓
ONE PRODUCT
```

```text
Abstract Factory
   ↓
PRODUCT FAMILY
```

Example:

```text
Factory:
Payment → UPI
```

```text
Abstract Factory:
Windows → Button + Checkbox + Textbox
```

---

# 🔥 FINAL MEMORY MAP

Bhai isko literally yaad kar:

```text
                 DESIGN PATTERNS
                       |
        ┌──────────────┼──────────────┐
        ↓              ↓              ↓
      CREATE         STRUCTURE       BEHAVIOR
        ↓              ↓              ↓

   Singleton        Adapter         Strategy
   Factory          Decorator       Observer
   Abstract Factory Proxy           Chain
   Builder          Facade          Command
   Prototype        Composite       Template
                    Bridge          State
                    Flyweight       Iterator
                                   Mediator
                                   Memento
                                   Visitor
```

## Ek-ek word:

```text
Singleton       → ONE
Factory         → CREATE
AbstractFactory → FAMILY
Builder         → BUILD
Prototype       → COPY

Adapter         → CONNECT
Decorator       → ADD
Proxy           → CONTROL
Facade          → SIMPLIFY
Composite       → TREE
Bridge          → SEPARATE
Flyweight       → SHARE

Strategy        → CHOOSE
Observer        → NOTIFY
Chain           → PASS
Command         → ACTION
Template        → SKELETON
State           → STATE
Iterator        → TRAVERSE
Mediator        → MIDDLEMAN
Memento         → SNAPSHOT
Visitor         → OPERATION
```

### Aur interview mein scenario aaye toh pehle sirf ye 3 questions:

> **1. Object banana/manage karna hai? → Creational**

> **2. Existing objects ko connect/wrap/organize karna hai? → Structural**

> **3. Behavior/communication manage karna hai? → Behavioral**

Phir exact pattern identify kar:

```text
ONE?           → Singleton
CREATE?        → Factory
FAMILY?        → Abstract Factory
BUILD?         → Builder
COPY?          → Prototype

CONNECT?       → Adapter
ADD?           → Decorator
CONTROL?       → Proxy
SIMPLIFY?      → Facade
TREE?          → Composite
SEPARATE?      → Bridge
SHARE?         → Flyweight

CHOOSE?        → Strategy
NOTIFY?        → Observer
PASS?          → Chain
ACTION?        → Command
SKELETON?      → Template
STATE?         → State
TRAVERSE?      → Iterator
MIDDLEMAN?     → Mediator
SNAPSHOT?      → Memento
OPERATION?     → Visitor
```

**Ye 23 GoF patterns ka proper interview-level foundation hai.** Ab inko ratne se better hai scenario dekh ke pattern identify karna — wahi actual interview mein test hota hai.







---------------------------- important ----------------------------------------------




Aaja bhai, poore Iron Man suit ke example se **Factory**, **Strategy**, aur **Dono ka Combo** ek jhatke mein recap kar lete hain!

---

### 1. Factory Pattern = Suit Ko Build Karna (Creation)

Tony Stark ke paas alag-alag missions ke liye alag Suits hain (Hulkbuster, Stealth Suit, Mark 85).

Factory ka kaam hai **Mission type dekh kar NAYA SUIT OBJECT build karke dena**.

```java
// Common Interface
interface IronManSuit {
    void displayArmor();
}

class Hulkbuster implements IronManSuit {
    public void displayArmor() { System.out.println("Heavy Armor Activated!"); }
}

class StealthSuit implements IronManSuit {
    public void displayArmor() { System.out.println("Invisible Camouflage Activated!"); }
}

// FACTORY CLASS: Naya Suit Build Karke Degi
class SuitFactory {
    public static IronManSuit buildSuit(String missionType) {
        if ("HEAVY_FIGHT".equalsIgnoreCase(missionType)) {
            return new Hulkbuster(); // Naya Heavy Object
        } else if ("STEALTH".equalsIgnoreCase(missionType)) {
            return new StealthSuit(); // Naya Stealth Object
        }
        throw new IllegalArgumentException("Unknown Mission");
    }
}

// USAGE: Mission heavy hai -> Factory ne NAYA Hulkbuster suit bana ke diya!
IronManSuit mySuit = SuitFactory.buildSuit("HEAVY_FIGHT");

```

> **Factory ka Secret:** Tu input dega (`"HEAVY_FIGHT"`), wo tujhe brand-new **Suit ka Object** `new` karke hand-over kar degi.

---

### 2. Strategy Pattern = Suit Ke Weapons Swap Karna (Behavior)

Ab Tony Suit ke andar pehle se pehna hua hai (**Suit Object same hai**). Dushman door hai ya paas, uske hisab se wo **Weapon Mode (Strategy)** badalta hai.

```java
// Weapon Strategy Interface
interface WeaponStrategy {
    void fire();
}

class RepulsorRay implements WeaponStrategy {
    public void fire() { System.out.println("Pew Pew! Long range Repulsor Beam!"); }
}

class NanoBlade implements WeaponStrategy {
    public void fire() { System.out.println("Slash! Close range Nano Sword!"); }
}

// CONTEXT: Suit Object PEHLE SE BANA HUA HAI (Same Suit)
class ActiveSuitContext {
    private WeaponStrategy currentWeapon;

    // Weapon Change karne ka System (No new Suit created)
    public void setWeapon(WeaponStrategy weapon) {
        this.currentWeapon = weapon;
    }

    public void attack() {
        currentWeapon.fire(); // Active weapon execution
    }
}

// USAGE:
ActiveSuitContext armor = new ActiveSuitContext(); // EK HI SUIT HAI

// Step A: Dushman door hai -> Repulsor Beam Mode
armor.setWeapon(new RepulsorRay());
armor.attack();

// Step B: Dushman paas aaya -> Suit NAYA NAHI BANA, bas Weapon Mode change kiya!
armor.setWeapon(new NanoBlade());
armor.attack();

```

> **Strategy ka Secret:** Suit naya nahi khareeda/bana! Wahi Suit pehne-pehne bas **Weapon Chip (Behavior)** badal di.

---

### 3. Combo (Factory + Strategy Ek Saath)

Real life code mein dono milkar kaam karte hain:

1. **Factory** check karti hai ki kaunsa Weapon Mode chahiye aur uska **Weapon Object create** karti hai.
2. **Active Suit** us Weapon Object ko apne andar **plug-in karke fire** karta hai.

```java
public class AvengersBattle {
    public static void main(String[] args) {
        ActiveSuitContext suit = new ActiveSuitContext(); // 1. Suit pehle se pehna hua hai

        String enemyDistance = "CLOSE"; // User / Game Situation Input

        // STEP 1 (FACTORY): Situation ke hisab se Sahi Weapon ka Object banao
        WeaponStrategy weaponFromFactory = WeaponFactory.getWeapon(enemyDistance); 

        // STEP 2 (STRATEGY): Wahi Suit ke andar wo Weapon plug-in kar do
        suit.setWeapon(weaponFromFactory);

        // STEP 3: Execute!
        suit.attack(); 
    }
}

```

---

### Final Iron Man Cheatsheet

* **Factory:** **"JARVIS, mere liye naya Hulkbuster Suit BANAO!"** *(Creation of new Object)*
* **Strategy:** **"JARVIS, is waqt isi Suit mein Nano Blade ACTIVATE KARO!"** *(Behavior change on existing Object)*
