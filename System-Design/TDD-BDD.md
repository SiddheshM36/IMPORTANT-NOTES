Haan bhai, **interviewer ke saamne theory nahi bolni hai**. Tu bas ek simple real example ke through bol.

### Interviewer: "What is TDD?"

Tu bol:

> **"TDD stands for Test Driven Development. In TDD, we write the test case first, then write the actual code to make that test pass. We follow Red, Green and Refactor cycle."**

Agar example maange:

> **"For example, if I have a withdraw method, first I write a test saying if the balance is 1000 and I withdraw 200, the expected balance should be 800. Initially the test fails, then I write the implementation to make it pass, and finally I refactor the code if required."**

Bas. **Ye TDD hai.**

---

# Ab BDD

Interviewer: **"What is BDD?"**

Tu bol:

> **"BDD stands for Behavior Driven Development. It focuses on what the application should do from the user's or business perspective. We define the expected behavior using Given, When and Then."**

Example:

> **"For example, for withdrawal, Given the account has 1000 rupees, When the user withdraws 200, Then the account balance should be 800."**

Bas. **Ye BDD hai.**

---

## Ab difference interviewer ko aise bol

Agar interviewer bole:

**"What's the difference between TDD and BDD?"**

Tu:

> **"TDD is mainly focused on writing tests before implementation and validating the code behavior at a development level. BDD focuses more on describing the expected business behavior in a way that developers, testers and business people can understand."**

### Ek line mein yaad rakh:

```text
TDD = Code ko test ke through drive karna

BDD = Business behavior ko define karna
```

### Aur ye yaad rakh:

```text
TDD
Test → Code → Refactor
      ↓
 Red → Green → Refactor


BDD
Business Requirement
        ↓
 Given → When → Then
        ↓
 Expected Behavior
```

### Tere Spring Boot project ka example

Suppose **APY service** mein:

**TDD:**

```text
Test:
calculatePension(1000)
expected = 500

       ↓
Write Java code
       ↓
Test passes
```

**BDD:**

```text
Given customer has valid APY details

When customer requests pension calculation

Then system should return the
calculated pension amount
```

**Important:** Agar interviewer pooche **"Have you actually used BDD/TDD?"**, aur tune use nahi kiya hai, toh fake mat bolna. Bol:

> **"I have worked with unit and integration testing, but I haven't extensively worked with a formal BDD/TDD methodology."**

Ye answer safe aur honest hai.
