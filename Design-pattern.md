Bilkul bhai. **In 4 patterns ko ek hi scenario se compare karte hain**, tab difference permanently clear ho jayega.

Maan le hamara system **Payment** handle karta hai.

| Pattern       | Simple meaning         | Kyu zarurat padti hai?                                                | Real-life example              |
| ------------- | ---------------------- | --------------------------------------------------------------------- | ------------------------------ |
| **Adapter**   | 🔌 **CONNECT**         | Do classes/interfaces compatible nahi hain                            | Indian plug → foreign socket   |
| **Decorator** | 🎁 **ADD FEATURE**     | Existing object ko modify kiye bina extra functionality chahiye       | Coffee + Milk + Sugar          |
| **Proxy**     | 🛡️ **CONTROL ACCESS** | Real object ko direct access nahi dena / pehle checking karni hai     | Security guard → actual office |
| **Facade**    | 🎛️ **SIMPLIFY**       | Bahut saare complex services ko ek simple API ke through expose karna | Hotel reception                |

---

# 1️⃣ Adapter → CONNECT 🔌

### Problem kya hai?

Tera application expect karta hai:

```java
interface PaymentProcessor {
    void pay();
}
```

But third-party library deti hai:

```java
class Razorpay {
    void makePayment() {
        System.out.println("Payment done");
    }
}
```

Dekha?

```text
Our application       Third-party

pay()                 makePayment()
  |                       |
  +-------- ❌ -----------+
```

Application `pay()` call karna chahta hai.

Razorpay mein `makePayment()` hai.

**Directly connect nahi ho sakte.**

### Solution → Adapter

```java
class RazorpayAdapter implements PaymentProcessor {

    private Razorpay razorpay;

    RazorpayAdapter(Razorpay razorpay) {
        this.razorpay = razorpay;
    }

    @Override
    public void pay() {
        razorpay.makePayment();
    }
}
```

Ab:

```java
PaymentProcessor processor =
        new RazorpayAdapter(new Razorpay());

processor.pay();
```

Flow:

```text
Application
    |
    | pay()
    ↓
Adapter
    |
    | makePayment()
    ↓
Razorpay
```

### 🧠 Kyu Adapter ki zarurat padi?

Kyuki:

> **Existing code ko change nahi karna aur third-party code ka interface bhi apne according nahi hai.**

Common situations:

* Third-party API integration
* Legacy code
* Old/new system integration
* Different libraries with different method names

### Interview line:

> **Adapter converts the interface of an existing class into an interface expected by the client.**

---

# 2️⃣ Decorator → ADD FEATURE 🎁

Ab doosra problem.

Maan le:

```java
interface Coffee {
    int cost();
}
```

Basic coffee:

```java
class SimpleCoffee implements Coffee {

    public int cost() {
        return 50;
    }
}
```

Ab customer bolta hai:

```text
Coffee
+ Milk
+ Sugar
+ Cream
```

Problem ye hai ki har combination ke liye class banaye?

```text
Coffee
CoffeeWithMilk
CoffeeWithSugar
CoffeeWithMilkAndSugar
CoffeeWithMilkSugarCream
...
```

😵‍💫 Bahut classes ho jayengi.

### Solution → Decorator

```java
class MilkDecorator implements Coffee {

    private Coffee coffee;

    MilkDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    public int cost() {
        return coffee.cost() + 20;
    }
}
```

Use:

```java
Coffee coffee = new SimpleCoffee();

coffee = new MilkDecorator(coffee);
```

Now:

```text
SimpleCoffee
     ↓
MilkDecorator
```

₹50 + ₹20 = ₹70

Aur sugar:

```java
coffee = new SugarDecorator(coffee);
```

Now:

```text
SimpleCoffee
     ↓
MilkDecorator
     ↓
SugarDecorator
```

### 🧠 Kyu Decorator ki zarurat padi?

Kyuki:

> **Runtime par object mein optional features add karne hain without modifying original class.**

### Important

Decorator mein **original object same rehta hai**.

Hum usko wrap karte hain:

```text
Decorator
    |
    └── has-a → Coffee
```

### Real Java example

Java I/O mein decorator concept bahut common hai:

```java
InputStream
    ↓
BufferedInputStream
    ↓
DataInputStream
```

Har layer additional functionality add kar sakti hai.

### Interview line:

> **Decorator dynamically adds additional responsibilities to an object without modifying its class.**

---

# 3️⃣ Proxy → CONTROL ACCESS 🛡️

Ab maan le actual object expensive/sensitive hai.

```text
Client
   ↓
Real Payment Service
```

Hum client ko directly access nahi dena chahte.

Kyu?

Maybe:

```text
Authentication
Authorization
Logging
Caching
Lazy loading
Security
```

karna hai.

Toh beech mein Proxy:

```text
Client
   ↓
 Proxy
   ↓
Real Object
```

### Example

```java
interface PaymentService {
    void pay();
}
```

Actual service:

```java
class RealPaymentService implements PaymentService {

    public void pay() {
        System.out.println("Payment processed");
    }
}
```

Proxy:

```java
class PaymentProxy implements PaymentService {

    private RealPaymentService realService;

    public void pay() {

        System.out.println("Checking authentication...");

        // authentication check

        if (realService == null) {
            realService = new RealPaymentService();
        }

        realService.pay();
    }
}
```

Client:

```java
PaymentService payment =
        new PaymentProxy();

payment.pay();
```

Flow:

```text
Client
  |
  | pay()
  ↓
Proxy
  |
  +-- Authentication
  +-- Authorization
  +-- Logging
  +-- Caching
  |
  ↓
RealPaymentService
```

### 🧠 Kyu Proxy ki zarurat padi?

Kyuki:

> **Real object ke access ko control karna hai.**

For example:

### Security

```text
User
 ↓
Security Proxy
 ↓
Service
```

### Caching

```text
User
 ↓
Cache Proxy
 ↓
Database
```

Agar cache mein data hai:

```text
Proxy → Cache HIT → return
```

Database jaane ki zarurat nahi.

### Lazy loading

```text
Client
 ↓
Proxy
 ↓
Object actually needed?
 ↓
YES → Create Real Object
```

### Spring connection

Spring mein proxy concept **bahut important** hai.

Examples:

```java
@Transactional
@Cacheable
@Async
```

Spring AOP ke through proxy-based behavior commonly implement hota hai.

For example:

```text
Client
   ↓
Spring Proxy
   ↓
Transaction start
   ↓
Service method
   ↓
Transaction commit/rollback
```

### Interview line:

> **Proxy provides a surrogate or placeholder that controls access to another object.**

---

# 4️⃣ Facade → SIMPLIFY 🎛️

Ab maan le tere hotel booking system mein:

```text
RoomService
PaymentService
BookingService
NotificationService
```

Ek room book karne ke liye client ko sab kuch pata hona pade:

```java
roomService.checkAvailability();

roomService.reserve();

paymentService.pay();

bookingService.createBooking();

notificationService.sendConfirmation();
```

Client ke liye system unnecessarily complicated ho gaya.

### Solution → Facade

Ek simple class:

```java
class HotelFacade {

    public void bookRoom() {

        roomService.checkAvailability();

        roomService.reserve();

        paymentService.pay();

        bookingService.createBooking();

        notificationService.sendConfirmation();
    }
}
```

Client:

```java
HotelFacade hotel = new HotelFacade();

hotel.bookRoom();
```

Bas ek method.

Flow:

```text
                    Client
                       |
                       ↓
                  HotelFacade
                       |
       +---------------+---------------+
       ↓               ↓               ↓
 RoomService     PaymentService   BookingService
                                       |
                                       ↓
                               NotificationService
```

### 🧠 Kyu Facade ki zarurat padi?

Kyuki:

> **System ke andar bahut saare components/services hain aur client ko unki complexity nahi dikhani.**

Client ko bas:

```java
bookRoom();
```

pata hai.

Usko andar kya ho raha:

```text
check room
↓
reserve
↓
payment
↓
booking
↓
notification
```

ye sab nahi pata.

### Interview line:

> **Facade provides a simplified interface to a complex subsystem.**

---

# 🔥 Ab chaaron ko SAME example se compare kar

Suppose Payment system hai.

### Adapter

Third-party payment provider ka interface different hai.

```text
Our Code
   ↓
Adapter
   ↓
Third Party
```

**Problem → CONNECT nahi ho raha**

---

### Decorator

Payment mein additional features chahiye.

```text
Payment
   ↓
Logging
   ↓
Encryption
   ↓
Metrics
```

**Problem → EXTRA FEATURE chahiye**

---

### Proxy

Payment service ko secure/control karna hai.

```text
Client
   ↓
Security Proxy
   ↓
Payment Service
```

**Problem → DIRECT ACCESS nahi dena**

---

### Facade

Payment process mein bahut saare services involved hain.

```text
Client
   ↓
PaymentFacade
   ↓
Validation
Payment
Fraud Check
Transaction
Notification
```

**Problem → COMPLEXITY hide karni hai**

---

# 🧠 Ye diagram yaad kar le

```text
                 STRUCTURAL PATTERNS
                         |
          +--------------+--------------+
          |              |              |
       ADAPTER       DECORATOR        PROXY
          |              |              |
       CONNECT        ADD FEATURE    CONTROL ACCESS
          |              |              |
     "Fit nahi ho     "Feature       "Direct access
       raha"           chahiye"         nahi dena"


                         +
                       FACADE
                         |
                     SIMPLIFY
                         |
                  "Bahut complex
                   system hai"
```

## ⚡ Ekdum exam/interview shortcut

**Adapter:**

> "Bhai dono connect nahi ho rahe, beech mein adapter laga."

**Decorator:**

> "Object ko change mat kar, upar se feature laga."

**Proxy:**

> "Actual object ko direct access mat de, pehle mere through aa."

**Facade:**

> "Andar 10 services hain, client ko ek simple method de."

```text
ADAPTER   = CONNECT
DECORATOR = ADD
PROXY     = CONTROL
FACADE    = SIMPLIFY
```

**Sabse important difference:**
Adapter **interface ko change/translate** karta hai, Decorator **behavior add** karta hai, Proxy **access control** karta hai, aur Facade **complexity hide** karta hai.





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
