Haan bhai, **ye important concept hai**. `mapToInt()` ki zarurat mainly isliye padi kyunki hamare paas `Stream<Integer>` tha, aur `sum()` `IntStream` ka terminal operation hai.

### Simple samajh

Tere paas:

```java
List<Integer> numbers = Arrays.asList(10, 20, 30, 40);
```

Jab tu:

```java
numbers.stream()
```

karta hai, type hota hai:

```text
Stream<Integer>
```

Ab `Stream<Integer>` mein directly:

```java
.sum()
```

nahi hai.

Isliye:

```java
.mapToInt(x -> x)
```

lagaya.

Ye convert karta hai:

```text
Stream<Integer>
      ↓
  mapToInt()
      ↓
  IntStream
```

Aur `IntStream` mein `sum()` available hai:

```java
numbers.stream()
       .mapToInt(x -> x)
       .sum();
```

Result:

```text
100
```

### Lekin `mapToInt()` actually karta kya hai?

Ye `Integer` objects ko primitive `int` values mein convert karta hai.

```text
Integer → int
```

Example:

```java
Stream<Integer>
```

becomes:

```java
IntStream
```

### `map()` vs `mapToInt()`

Ye difference **interview mein bahut puchte hain**:

| Method          | Return         | Example               |
| --------------- | -------------- | --------------------- |
| `map()`         | `Stream<R>`    | `map(x -> x * 2)`     |
| `mapToInt()`    | `IntStream`    | `mapToInt(x -> x)`    |
| `mapToLong()`   | `LongStream`   | `mapToLong(x -> x)`   |
| `mapToDouble()` | `DoubleStream` | `mapToDouble(x -> x)` |

For example:

```java
numbers.stream()
       .map(x -> x * 2)
```

returns:

```text
Stream<Integer>
```

Whereas:

```java
numbers.stream()
       .mapToInt(x -> x * 2)
```

returns:

```text
IntStream
```

And then you can do:

```java
.sum()
.average()
.min()
.max()
```

### Ek aur important point

`mapToInt(Integer::intValue)` thoda verbose hai.

Tu normally simply:

```java
.mapToInt(x -> x)
```

likh sakta hai.

Because Java automatically unboxes:

```text
Integer 10 → int 10
```

So interview mein ye yaad rakh:

> **`Stream<Integer>` → `mapToInt()` → `IntStream` → numeric operations like `sum()`, `average()`, `min()`, `max()`.**

Aur haan, **har baar `mapToInt()` zaruri nahi hai**. Agar sirf filtering, sorting, collecting etc. karna hai, `Stream<Integer>` perfectly fine hai.





numbers.stream().mapToInt(x->x).min().orElse(-1)     => sum,max, min humesha mapToInt kyuki stream integers ka stream dedi hai jismai ye methods nhi hai toh aapne ko convet krna hoga IntStream mai








# Java Streams – Programming Questions & Interview Notes

## 1. Basic Stream Syntax

### Create Stream

```java
List<Integer> numbers = Arrays.asList(10, 20, 30, 40);

numbers.stream()
       .forEach(System.out::println);
```

### General Pipeline

```java
collection.stream()
          .filter(...)
          .map(...)
          .sorted(...)
          .collect(...);
```

Remember:

**Source → Intermediate Operations → Terminal Operation**

Example:

```java
numbers.stream()
       .filter(x -> x % 2 == 0)
       .map(x -> x * 2)
       .collect(Collectors.toList());
```

---

# 2. filter()

Used when you want to **select elements based on a condition**.

### Even numbers

```java
numbers.stream()
       .filter(x -> x % 2 == 0)
       .forEach(System.out::println);
```

### Numbers greater than 50

```java
numbers.stream()
       .filter(x -> x > 50)
       .collect(Collectors.toList());
```

### Important

`filter()` must return **boolean**.

❌ Wrong:

```java
.filter(x -> x * 2)
```

✅ Correct:

```java
.filter(x -> x % 2 == 0)
```

---

# 3. map()

Used to **transform every element**.

### Double numbers

```java
numbers.stream()
       .map(x -> x * 2)
       .collect(Collectors.toList());
```

### Convert names to uppercase

```java
names.stream()
     .map(String::toUpperCase)
     .collect(Collectors.toList());
```

### Find length of every name

```java
names.stream()
     .map(String::length)
     .collect(Collectors.toList());
```

### Important

`map()` can also change the data type.

```java
List<String> names = Arrays.asList("Sid", "Rahul");

List<Integer> lengths = names.stream()
                             .map(String::length)
                             .collect(Collectors.toList());
```

`Stream<String>` → `Stream<Integer>`

---

# 4. map() + filter()

### Find even numbers and double them

```java
numbers.stream()
       .filter(x -> x % 2 == 0)
       .map(x -> x * 2)
       .collect(Collectors.toList());
```

### Names whose length > 4 and convert uppercase

```java
names.stream()
     .filter(x -> x.length() > 4)
     .map(String::toUpperCase)
     .collect(Collectors.toList());
```

---

# 5. distinct()

Removes duplicate elements.

```java
List<Integer> numbers =
    Arrays.asList(10, 20, 20, 30, 30, 40);

List<Integer> result = numbers.stream()
                              .distinct()
                              .collect(Collectors.toList());
```

Output:

```text
10 20 30 40
```

---

# 6. sorted()

Sort elements.

### Ascending

```java
numbers.stream()
       .sorted()
       .forEach(System.out::println);
```

### Descending

```java
numbers.stream()
       .sorted(Comparator.reverseOrder())
       .forEach(System.out::println);
```

### String sorting

```java
names.stream()
     .sorted()
     .forEach(System.out::println);
```

---

# 7. distinct + sorted

Very common interview question.

```java
numbers.stream()
       .distinct()
       .sorted()
       .collect(Collectors.toList());
```

---

# 8. limit()

Takes only first N elements.

```java
numbers.stream()
       .limit(3)
       .collect(Collectors.toList());
```

---

# 9. skip()

Skips first N elements.

```java
numbers.stream()
       .skip(2)
       .collect(Collectors.toList());
```

### Pagination-style example

```java
numbers.stream()
       .skip(10)
       .limit(10)
       .collect(Collectors.toList());
```

---

# 10. count()

Count elements.

### Count even numbers

```java
long count = numbers.stream()
                    .filter(x -> x % 2 == 0)
                    .count();
```

---

# 11. sum()

For integers:

```java
int sum = numbers.stream()
                 .mapToInt(x -> x)
                 .sum();
```

### Even numbers sum

```java
int sum = numbers.stream()
                 .filter(x -> x % 2 == 0)
                 .mapToInt(x -> x)
                 .sum();
```

---

# 12. mapToInt()

Converts `Stream<Integer>` to `IntStream`.

```java
numbers.stream()
       .mapToInt(x -> x)
       .sum();
```

Useful methods:

```java
.sum()
.average()
.min()
.max()
.count()
```

Example:

```java
double avg = numbers.stream()
                    .mapToInt(x -> x)
                    .average()
                    .orElse(0);
```

---

# 13. mapToLong()

```java
long result = numbers.stream()
                     .mapToLong(x -> x)
                     .sum();
```

Useful when working with large numeric values.

---

# 14. mapToDouble()

```java
double result = numbers.stream()
                       .mapToDouble(x -> x)
                       .average()
                       .orElse(0);
```

---

# 15. boxed()

Primitive stream → Object stream.

```java
List<Integer> result =
    IntStream.range(1, 6)
             .boxed()
             .collect(Collectors.toList());
```

`IntStream` → `Stream<Integer>`

---

# 16. min()

```java
OptionalInt min =
    numbers.stream()
           .mapToInt(x -> x)
           .min();
```

With default value:

```java
int min = numbers.stream()
                 .mapToInt(x -> x)
                 .min()
                 .orElse(-1);
```

---

# 17. max()

```java
int max = numbers.stream()
                 .mapToInt(x -> x)
                 .max()
                 .orElse(-1);
```

---

# 18. findFirst()

Returns first matching element.

```java
Optional<Integer> result =
    numbers.stream()
           .filter(x -> x > 20)
           .findFirst();
```

Default value:

```java
int result = numbers.stream()
                    .filter(x -> x > 20)
                    .findFirst()
                    .orElse(-1);
```

---

# 19. findAny()

Returns any matching element.

```java
Optional<Integer> result =
    numbers.stream()
           .filter(x -> x > 20)
           .findAny();
```

Mostly interesting with parallel streams.

---

# 20. anyMatch()

Checks whether **at least one** element matches.

```java
boolean result =
    numbers.stream()
           .anyMatch(x -> x > 100);
```

---

# 21. allMatch()

Checks whether **all** elements match.

```java
boolean result =
    numbers.stream()
           .allMatch(x -> x > 0);
```

---

# 22. noneMatch()

Checks whether **no** element matches.

```java
boolean result =
    numbers.stream()
           .noneMatch(x -> x < 0);
```

### Easy memory trick

```text
anyMatch  → koi ek
allMatch  → sab
noneMatch → ek bhi nahi
```

---

# 23. reduce()

Used to combine elements into one result.

### Sum

```java
int result =
    numbers.stream()
           .reduce(0, (a, b) -> a + b);
```

### Product

```java
int result =
    numbers.stream()
           .reduce(1, (a, b) -> a * b);
```

### Maximum

```java
int max =
    numbers.stream()
           .reduce(Integer.MIN_VALUE,
                   Integer::max);
```

### Without identity

```java
Optional<Integer> result =
    numbers.stream()
           .reduce((a, b) -> a + b);
```

Because stream can be empty, result is `Optional`.

---

# 24. collect(toList())

```java
List<Integer> result =
    numbers.stream()
           .filter(x -> x % 2 == 0)
           .collect(Collectors.toList());
```

---

# 25. collect(toSet())

```java
Set<Integer> result =
    numbers.stream()
           .collect(Collectors.toSet());
```

Useful for removing duplicates.

---

# 26. Collect to LinkedList

```java
List<Integer> result =
    numbers.stream()
           .collect(Collectors.toCollection(LinkedList::new));
```

---

# 27. joining()

For Strings.

```java
List<String> names =
    Arrays.asList("Sid", "Rahul", "Amit");

String result =
    names.stream()
         .collect(Collectors.joining(", "));
```

Output:

```text
Sid, Rahul, Amit
```

---

# 28. joining with prefix/suffix

```java
String result =
    names.stream()
         .collect(Collectors.joining(
             ", ",
             "[",
             "]"
         ));
```

Output:

```text
[Sid, Rahul, Amit]
```

---

# 29. groupingBy()

One of the MOST IMPORTANT interview topics.

### Group names by length

```java
Map<Integer, List<String>> result =
    names.stream()
         .collect(Collectors.groupingBy(String::length));
```

Example:

```text
3 → [Sid, Ram]
5 → [Rahul]
```

---

# 30. Group employees by department

```java
Map<String, List<Employee>> result =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getDepartment
             ));
```

---

# 31. Department-wise employee count

```java
Map<String, Long> result =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getDepartment,
                 Collectors.counting()
             ));
```

---

# 32. Department-wise average salary

```java
Map<String, Double> result =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getDepartment,
                 Collectors.averagingInt(
                     Employee::getSalary
                 )
             ));
```

---

# 33. Department-wise total salary

```java
Map<String, Integer> result =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getDepartment,
                 Collectors.summingInt(
                     Employee::getSalary
                 )
             ));
```

---

# 34. Highest-paid employee per department

```java
Map<String, Optional<Employee>> result =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getDepartment,
                 Collectors.maxBy(
                     Comparator.comparingInt(
                         Employee::getSalary
                     )
                 )
             ));
```

---

# 35. Lowest-paid employee per department

```java
Map<String, Optional<Employee>> result =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getDepartment,
                 Collectors.minBy(
                     Comparator.comparingInt(
                         Employee::getSalary
                     )
                 )
             ));
```

---

# 36. Department → employee names

Use `mapping()`.

```java
Map<String, List<String>> result =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getDepartment,
                 Collectors.mapping(
                     Employee::getName,
                     Collectors.toList()
                 )
             ));
```

Result:

```text
IT → [Sid, Rahul]
HR → [Amit, Raj]
```

---

# 37. partitioningBy()

Creates only two groups:

```text
true
false
```

### Even and odd

```java
Map<Boolean, List<Integer>> result =
    numbers.stream()
           .collect(Collectors.partitioningBy(
               x -> x % 2 == 0
           ));
```

Output conceptually:

```text
true  → [2,4,6]
false → [1,3,5]
```

---

# 38. groupingBy vs partitioningBy

### groupingBy

```java
groupingBy(Employee::getDepartment)
```

Can create:

```text
IT
HR
Finance
Sales
```

### partitioningBy

```java
partitioningBy(x -> x > 50)
```

Only:

```text
true
false
```

---

# 39. mapping() collector

Used when grouping but you want only a particular field.

```java
Map<String, List<String>> result =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getDepartment,
                 Collectors.mapping(
                     Employee::getName,
                     Collectors.toList()
                 )
             ));
```

---

# 40. filtering() collector

Filters elements **inside each group**.

```java
Map<String, List<Employee>> result =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getDepartment,
                 Collectors.filtering(
                     e -> e.getSalary() > 70000,
                     Collectors.toList()
                 )
             ));
```

Important difference:

### Normal filter

```java
employees.stream()
         .filter(e -> e.getSalary() > 70000)
         .collect(Collectors.groupingBy(
             Employee::getDepartment
         ));
```

Departments with no matching employee may disappear.

### filtering()

```java
groupingBy(
    Employee::getDepartment,
    filtering(...)
)
```

Can preserve the department with an empty list.

---

# 41. Nested Collectors

Very common advanced question.

### Department → names of employees earning >70K

```java
Map<String, List<String>> result =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getDepartment,
                 Collectors.filtering(
                     e -> e.getSalary() > 70000,
                     Collectors.mapping(
                         Employee::getName,
                         Collectors.toList()
                     )
                 )
             ));
```

Think:

```text
groupingBy
    ↓
filtering
    ↓
mapping
    ↓
toList
```

---

# 42. toMap()

Convert stream into Map.

Example:

```java
Map<Integer, String> result =
    employees.stream()
             .collect(Collectors.toMap(
                 Employee::getId,
                 Employee::getName
             ));
```

Output:

```text
101 → Sid
102 → Rahul
103 → Amit
```

---

# 43. toMap() duplicate-key problem

This is a VERY common interview question.

Suppose:

```text
Employee ID → duplicate
```

Then:

```java
Collectors.toMap(
    Employee::getId,
    Employee::getName
)
```

can throw:

```text
IllegalStateException
```

Handle duplicates:

```java
Map<Integer, String> result =
    employees.stream()
             .collect(Collectors.toMap(
                 Employee::getId,
                 Employee::getName,
                 (oldValue, newValue) -> oldValue
             ));
```

Or keep new value:

```java
(oldValue, newValue) -> newValue
```

---

# 44. Comparator with sorted()

### Sort employees by salary

```java
employees.stream()
         .sorted(
             Comparator.comparingInt(
                 Employee::getSalary
             )
         )
         .collect(Collectors.toList());
```

### Descending salary

```java
employees.stream()
         .sorted(
             Comparator.comparingInt(
                 Employee::getSalary
             ).reversed()
         )
         .collect(Collectors.toList());
```

---

# 45. Sort by multiple fields

First salary, then name:

```java
employees.stream()
         .sorted(
             Comparator.comparingInt(
                 Employee::getSalary
             ).thenComparing(
                 Employee::getName
             )
         )
         .collect(Collectors.toList());
```

---

# 46. Highest salary

Simple way:

```java
Employee result =
    employees.stream()
             .max(
                 Comparator.comparingInt(
                     Employee::getSalary
                 )
             )
             .orElse(null);
```

---

# 47. Second highest salary

VERY common interview question.

```java
Optional<Integer> result =
    employees.stream()
             .map(Employee::getSalary)
             .distinct()
             .sorted(Comparator.reverseOrder())
             .skip(1)
             .findFirst();
```

Why `distinct()`?

Because:

```text
100000
100000
90000
```

Without distinct, second value would again be `100000`.

---

# 48. Third highest salary

```java
Optional<Integer> result =
    employees.stream()
             .map(Employee::getSalary)
             .distinct()
             .sorted(Comparator.reverseOrder())
             .skip(2)
             .findFirst();
```

General:

```java
.skip(n - 1)
.findFirst()
```

---

# 49. Highest salary by department

```java
Map<String, Optional<Employee>> result =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getDepartment,
                 Collectors.maxBy(
                     Comparator.comparingInt(
                         Employee::getSalary
                     )
                 )
             ));
```

---

# 50. Second highest salary by department

Concept:

```text
group department
      ↓
sort salary descending
      ↓
skip(1)
      ↓
findFirst()
```

Example:

```java
Map<String, Optional<Employee>> result =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getDepartment,
                 Collectors.collectingAndThen(
                     Collectors.toList(),
                     list -> list.stream()
                                 .sorted(
                                     Comparator.comparingInt(
                                         Employee::getSalary
                                     ).reversed()
                                 )
                                 .skip(1)
                                 .findFirst()
                 )
             ));
```

For interview discussion, explain the approach even if you don't remember the exact collector syntax.

---

# 51. flatMap()

Very important.

Suppose:

```java
List<List<Integer>> numbers = Arrays.asList(
    Arrays.asList(1, 2),
    Arrays.asList(3, 4),
    Arrays.asList(5, 6)
);
```

### map()

```java
numbers.stream()
       .map(List::stream)
```

Result:

```text
Stream<Stream<Integer>>
```

### flatMap()

```java
numbers.stream()
       .flatMap(List::stream)
       .collect(Collectors.toList());
```

Result:

```text
[1,2,3,4,5,6]
```

### Easy memory

```text
map     → transform
flatMap → transform + flatten
```

---

# 52. flatMap() with nested employee data

Suppose:

```text
Department
    ↓
Employees
```

You can flatten employees:

```java
departments.stream()
           .flatMap(d -> d.getEmployees().stream())
           .collect(Collectors.toList());
```

---

# 53. flatMap() with Strings

Convert words into individual characters:

```java
List<String> words =
    Arrays.asList("Java", "Spring");

List<String> result =
    words.stream()
         .flatMap(word ->
             Arrays.stream(word.split(""))
         )
         .collect(Collectors.toList());
```

Conceptually:

```text
Java   → J a v a
Spring → S p r i n g
```

---

# 54. Duplicate characters removal

```java
List<String> result =
    words.stream()
         .flatMap(word ->
             Arrays.stream(word.split(""))
         )
         .distinct()
         .collect(Collectors.toList());
```

---

# 55. Convert List<List<String>> to List<String>

```java
List<String> result =
    nestedList.stream()
              .flatMap(List::stream)
              .collect(Collectors.toList());
```

---

# 56. Stream of Arrays

```java
List<String[]> data = ...;

List<String> result =
    data.stream()
        .flatMap(Arrays::stream)
        .collect(Collectors.toList());
```

---

# 57. Optional with Streams

### First employee with salary > 1 lakh

```java
Optional<Employee> employee =
    employees.stream()
             .filter(e -> e.getSalary() > 100000)
             .findFirst();
```

### Get object or null

```java
Employee employee =
    employees.stream()
             .filter(e -> e.getSalary() > 100000)
             .findFirst()
             .orElse(null);
```

### Get default object

```java
Employee employee =
    employees.stream()
             .filter(e -> e.getSalary() > 100000)
             .findFirst()
             .orElse(defaultEmployee);
```

---

# 58. anyMatch / allMatch / noneMatch

### Any employee earning >1L

```java
boolean result =
    employees.stream()
             .anyMatch(e -> e.getSalary() > 100000);
```

### Everyone earning >50K

```java
boolean result =
    employees.stream()
             .allMatch(e -> e.getSalary() > 50000);
```

### No employee earning <20K

```java
boolean result =
    employees.stream()
             .noneMatch(e -> e.getSalary() < 20000);
```

---

# 59. Find duplicate elements

Very common.

```java
Set<Integer> duplicates =
    numbers.stream()
           .filter(x -> Collections.frequency(numbers, x) > 1)
           .collect(Collectors.toSet());
```

But this approach is inefficient.

Better interview approach:

```java
Set<Integer> seen = new HashSet<>();

Set<Integer> duplicates =
    numbers.stream()
           .filter(x -> !seen.add(x))
           .collect(Collectors.toSet());
```

Why?

`Set.add()` returns:

```text
true  → element was new
false → element already existed
```

Therefore:

```java
!seen.add(x)
```

means duplicate.

---

# 60. Find unique elements

```java
Set<Integer> unique =
    numbers.stream()
           .collect(Collectors.toSet());
```

If you mean elements appearing exactly once:

```java
Map<Integer, Long> frequency =
    numbers.stream()
           .collect(Collectors.groupingBy(
               Function.identity(),
               Collectors.counting()
           ));

List<Integer> result =
    frequency.entrySet()
             .stream()
             .filter(e -> e.getValue() == 1)
             .map(Map.Entry::getKey)
             .collect(Collectors.toList());
```

---

# 61. Frequency of each element

```java
Map<Integer, Long> frequency =
    numbers.stream()
           .collect(Collectors.groupingBy(
               Function.identity(),
               Collectors.counting()
           ));
```

For:

```text
[1,2,2,3,3,3]
```

Result:

```text
1 → 1
2 → 2
3 → 3
```

---

# 62. Frequency of characters

```java
String str = "java";

Map<String, Long> result =
    Arrays.stream(str.split(""))
          .collect(Collectors.groupingBy(
              Function.identity(),
              Collectors.counting()
          ));
```

---

# 63. First non-repeated character

Concept:

```text
characters
    ↓
frequency
    ↓
find first count == 1
```

```java
Map<Character, Long> frequency =
    str.chars()
       .mapToObj(c -> (char) c)
       .collect(Collectors.groupingBy(
           Function.identity(),
           LinkedHashMap::new,
           Collectors.counting()
       ));

Character result =
    frequency.entrySet()
             .stream()
             .filter(e -> e.getValue() == 1)
             .map(Map.Entry::getKey)
             .findFirst()
             .orElse(null);
```

`LinkedHashMap` is important because we want insertion order.

---

# 64. First repeated character

```java
Set<Character> seen = new HashSet<>();

Character result =
    str.chars()
       .mapToObj(c -> (char) c)
       .filter(c -> !seen.add(c))
       .findFirst()
       .orElse(null);
```

---

# 65. Find longest string

```java
String result =
    names.stream()
         .max(Comparator.comparingInt(String::length))
         .orElse(null);
```

---

# 66. Find shortest string

```java
String result =
    names.stream()
         .min(Comparator.comparingInt(String::length))
         .orElse(null);
```

---

# 67. Sort strings by length

Ascending:

```java
names.stream()
     .sorted(Comparator.comparingInt(String::length))
     .collect(Collectors.toList());
```

Descending:

```java
names.stream()
     .sorted(
         Comparator.comparingInt(String::length)
                   .reversed()
     )
     .collect(Collectors.toList());
```

---

# 68. Find strings starting with A

```java
names.stream()
     .filter(x -> x.startsWith("A"))
     .collect(Collectors.toList());
```

---

# 69. Find duplicate strings

```java
Set<String> seen = new HashSet<>();

Set<String> duplicates =
    names.stream()
         .filter(x -> !seen.add(x))
         .collect(Collectors.toSet());
```

---

# 70. Convert List to Map

```java
Map<Integer, String> map =
    employees.stream()
             .collect(Collectors.toMap(
                 Employee::getId,
                 Employee::getName
             ));
```

---

# 71. Convert Map to Stream

```java
map.entrySet()
   .stream()
   .forEach(System.out::println);
```

Keys:

```java
map.keySet()
   .stream()
   .forEach(System.out::println);
```

Values:

```java
map.values()
   .stream()
   .forEach(System.out::println);
```

---

# 72. Sort Map by value

```java
map.entrySet()
   .stream()
   .sorted(Map.Entry.comparingByValue())
   .forEach(System.out::println);
```

Descending:

```java
map.entrySet()
   .stream()
   .sorted(
       Map.Entry.<Integer, String>
           comparingByKey()
           .reversed()
   );
```

---

# 73. Find top 3 salaries

```java
List<Integer> result =
    employees.stream()
             .map(Employee::getSalary)
             .distinct()
             .sorted(Comparator.reverseOrder())
             .limit(3)
             .collect(Collectors.toList());
```

---

# 74. Find employees with top 3 salaries

```java
List<Employee> result =
    employees.stream()
             .sorted(
                 Comparator.comparingInt(
                     Employee::getSalary
                 ).reversed()
             )
             .limit(3)
             .collect(Collectors.toList());
```

If distinct salary ranking is required, use a different approach because duplicate salaries matter.

---

# 75. Sum of salaries

```java
int total =
    employees.stream()
             .mapToInt(Employee::getSalary)
             .sum();
```

---

# 76. Average salary

```java
double average =
    employees.stream()
             .mapToInt(Employee::getSalary)
             .average()
             .orElse(0);
```

---

# 77. Maximum salary

```java
int max =
    employees.stream()
             .mapToInt(Employee::getSalary)
             .max()
             .orElse(0);
```

---

# 78. Minimum salary

```java
int min =
    employees.stream()
             .mapToInt(Employee::getSalary)
             .min()
             .orElse(0);
```

---

# 79. Salary statistics

Very useful collector:

```java
IntSummaryStatistics stats =
    employees.stream()
             .collect(
                 Collectors.summarizingInt(
                     Employee::getSalary
                 )
             );
```

Now:

```java
stats.getCount();
stats.getSum();
stats.getMin();
stats.getMax();
stats.getAverage();
```

---

# 80. Employees grouped by salary range

Example:

```java
Map<String, List<Employee>> result =
    employees.stream()
             .collect(Collectors.groupingBy(e -> {
                 if (e.getSalary() < 50000)
                     return "LOW";
                 else if (e.getSalary() < 100000)
                     return "MEDIUM";
                 else
                     return "HIGH";
             }));
```

---

# 81. Department → highest salary value only

Instead of employee object:

```java
Map<String, Integer> result =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getDepartment,
                 Collectors.collectingAndThen(
                     Collectors.maxBy(
                         Comparator.comparingInt(
                             Employee::getSalary
                         )
                     ),
                     optional -> optional
                         .map(Employee::getSalary)
                         .orElse(0)
                 )
             ));
```

---

# 82. Department → employee names joined

```java
Map<String, String> result =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getDepartment,
                 Collectors.mapping(
                     Employee::getName,
                     Collectors.joining(", ")
                 )
             ));
```

Output:

```text
IT → Sid, Rahul, Amit
HR → Raj, John
```

---

# 83. Remove null values

```java
list.stream()
    .filter(Objects::nonNull)
    .collect(Collectors.toList());
```

---

# 84. Convert null-safe strings

```java
names.stream()
     .filter(Objects::nonNull)
     .map(String::toUpperCase)
     .collect(Collectors.toList());
```

---

# 85. Sum only positive numbers

```java
int sum =
    numbers.stream()
           .filter(x -> x > 0)
           .mapToInt(Integer::intValue)
           .sum();
```

---

# 86. Sum of squares

```java
int result =
    numbers.stream()
           .mapToInt(x -> x * x)
           .sum();
```

---

# 87. Sum of squares of even numbers

```java
int result =
    numbers.stream()
           .filter(x -> x % 2 == 0)
           .mapToInt(x -> x * x)
           .sum();
```

---

# 88. Product of all numbers

```java
int result =
    numbers.stream()
           .reduce(1, (a, b) -> a * b);
```

Identity for multiplication:

```text
1
```

Identity for addition:

```text
0
```

---

# 89. Reverse strings

```java
List<String> result =
    names.stream()
         .map(name ->
             new StringBuilder(name)
                 .reverse()
                 .toString()
         )
         .collect(Collectors.toList());
```

---

# 90. Find palindrome strings

```java
List<String> result =
    names.stream()
         .filter(name ->
             name.equals(
                 new StringBuilder(name)
                     .reverse()
                     .toString()
             )
         )
         .collect(Collectors.toList());
```

---

# 91. Remove duplicate characters from String

```java
String result =
    str.chars()
       .mapToObj(c -> (char) c)
       .distinct()
       .map(String::valueOf)
       .collect(Collectors.joining());
```

---

# 92. Convert String to character list

```java
List<Character> result =
    str.chars()
       .mapToObj(c -> (char) c)
       .collect(Collectors.toList());
```

---

# 93. Count vowels

```java
long count =
    str.toLowerCase()
       .chars()
       .filter(c ->
           c == 'a' ||
           c == 'e' ||
           c == 'i' ||
           c == 'o' ||
           c == 'u'
       )
       .count();
```

---

# 94. Find duplicate employees by ID

```java
Set<Integer> seen = new HashSet<>();

Set<Integer> duplicates =
    employees.stream()
             .map(Employee::getId)
             .filter(id -> !seen.add(id))
             .collect(Collectors.toSet());
```

---

# 95. Group employees by age

```java
Map<Integer, List<Employee>> result =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getAge
             ));
```

---

# 96. Count employees by age

```java
Map<Integer, Long> result =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getAge,
                 Collectors.counting()
             ));
```

---

# 97. Find most common element

```java
Map<Integer, Long> frequency =
    numbers.stream()
           .collect(Collectors.groupingBy(
               Function.identity(),
               Collectors.counting()
           ));

Integer result =
    frequency.entrySet()
             .stream()
             .max(Map.Entry.comparingByValue())
             .map(Map.Entry::getKey)
             .orElse(null);
```

---

# 98. Stream pipeline lazy evaluation

Intermediate operations don't execute immediately.

```java
numbers.stream()
       .filter(x -> {
           System.out.println("filter: " + x);
           return x > 10;
       });
```

Nothing executes because there is no terminal operation.

Add:

```java
.forEach(System.out::println);
```

Now pipeline executes.

---

# 99. Short-circuit operations

These can stop processing early:

```java
findFirst()
findAny()
anyMatch()
allMatch()
noneMatch()
limit()
```

Example:

```java
numbers.stream()
       .filter(x -> x > 50)
       .findFirst();
```

It doesn't necessarily process every element.

---

# 100. Stream cannot be reused

Wrong:

```java
Stream<Integer> stream = numbers.stream();

stream.count();

stream.forEach(System.out::println);
```

Second operation throws:

```text
IllegalStateException:
stream has already been operated upon or closed
```

Correct:

```java
numbers.stream().count();

numbers.stream().forEach(System.out::println);
```

---

# 101. Stream vs Collection

| Collection              | Stream                         |
| ----------------------- | ------------------------------ |
| Stores data             | Processes data                 |
| Can be reused           | Usually single-use             |
| Eager                   | Lazy                           |
| Data structure          | Processing pipeline            |
| Can add/remove elements | Doesn't modify source normally |

---

# 102. Intermediate vs Terminal Operations

### Intermediate

Return another Stream:

```text
filter
map
flatMap
sorted
distinct
limit
skip
peek
```

### Terminal

Produce final result:

```text
collect
forEach
count
reduce
min
max
findFirst
findAny
anyMatch
allMatch
noneMatch
```

---

# 103. Stateless vs Stateful operations

### Stateless

Each element can be processed independently:

```text
filter
map
flatMap
```

### Stateful

Need information about other elements:

```text
sorted
distinct
limit
skip
```

---

# 104. Common Interview Trap – filter vs map

Remember:

```text
filter → SHOULD I KEEP THIS?
map    → WHAT SHOULD THIS BECOME?
```

Example:

```java
.filter(x -> x > 10)
.map(x -> x * 2)
```

---

# 105. Common Interview Trap – map vs flatMap

```text
map:
A → B

flatMap:
A → multiple B → flatten
```

Example:

```java
List<List<Integer>>
```

Use:

```java
flatMap(List::stream)
```

---

# 106. Common Interview Trap – sorted vs sort

Streams use:

```java
.sorted()
```

Not:

```java
.sort()
```

---

# 107. Common Interview Trap – sum()

This won't work directly on:

```java
Stream<Integer>
```

Use:

```java
mapToInt(Integer::intValue)
```

Then:

```java
.sum()
```

Example:

```java
numbers.stream()
       .mapToInt(Integer::intValue)
       .sum();
```

---

# 108. Common Interview Trap – Optional

Don't blindly do:

```java
.get()
```

Example:

```java
numbers.stream()
       .filter(x -> x > 1000)
       .findFirst()
       .get();
```

Can throw:

```text
NoSuchElementException
```

Better:

```java
.orElse(null)
```

or:

```java
.orElse(-1)
```

or:

```java
.orElseThrow(...)
```

---

# 109. Common Interview Trap – toMap duplicate key

This:

```java
Collectors.toMap(
    Employee::getDepartment,
    Employee::getName
)
```

can fail if multiple employees have same department.

Use:

```java
groupingBy()
```

if one key should map to multiple values.

---

# 110. Common Interview Trap – groupingBy vs toMap

### One key → multiple employees

```java
groupingBy()
```

### One key → one value

```java
toMap()
```

---

# 111. Common Interview Trap – distinct objects

For custom objects:

```java
employees.stream()
         .distinct()
```

uses `equals()` and `hashCode()`.

So if `equals()` / `hashCode()` are not properly implemented, duplicate detection may not behave as expected.

---

# 112. Common Interview Question – Does Stream modify original collection?

Normally:

```java
List<Integer> numbers =
    Arrays.asList(1,2,3,4);
```

After:

```java
numbers.stream()
       .map(x -> x * 2)
       .collect(Collectors.toList());
```

Original list remains:

```text
[1,2,3,4]
```

Result:

```text
[2,4,6,8]
```

---

# 113. Common Interview Question – Parallel Stream

```java
numbers.parallelStream()
       .forEach(System.out::println);
```

Parallel streams use multiple threads through the common ForkJoinPool.

But don't blindly use them.

For small collections, CPU-heavy work, ordering requirements, shared mutable state, or blocking I/O, parallel streams can be inappropriate.

---

# 114. Classic Programming Questions You MUST Practice

Before interview, be able to code these without looking:

### Level 1

```text
1. Find even numbers
2. Find odd numbers
3. Sum of numbers
4. Average
5. Min
6. Max
7. Count
8. Square every number
9. Remove duplicates
10. Sort ascending
11. Sort descending
12. Find numbers > N
```

### Level 2

```text
13. Find duplicate numbers
14. Find unique numbers
15. Frequency of numbers
16. Frequency of characters
17. First repeated character
18. First non-repeated character
19. Find longest string
20. Find shortest string
21. Sort strings by length
22. Find palindrome strings
23. Reverse strings
24. Count vowels
```

### Level 3 – Employee

```text
25. Highest salary
26. Lowest salary
27. Second highest salary
28. Third highest salary
29. Top 3 salaries
30. Department-wise employees
31. Department-wise count
32. Department-wise average salary
33. Department-wise total salary
34. Highest salary per department
35. Lowest salary per department
36. Employee names by department
37. Employees earning >1L
38. Salary range grouping
39. Duplicate employee IDs
40. Sort employees by salary
41. Sort by salary then name
```

### Level 4 – Advanced

```text
42. flatMap nested lists
43. Department → employee names
44. Department → highest-paid employee
45. Second-highest salary per department
46. Nested groupingBy
47. groupingBy + filtering
48. groupingBy + mapping
49. groupingBy + counting
50. groupingBy + maxBy
51. groupingBy + averagingInt
52. toMap duplicate-key handling
53. collectingAndThen
54. partitioningBy
55. first non-repeated character preserving order
56. most frequent element
57. third highest distinct salary
58. top N employees
59. flatten nested objects
60. combine filter + map + groupingBy
```

---

# 115. The 15 Questions You Should Definitely Master

If interviewer says:

**"Give me some Java 8 Streams programs."**

These are the ones I'd practice first:

```text
1. Find even numbers
2. Sum of even numbers
3. Square of even numbers and sum
4. Find duplicate numbers
5. Find frequency of elements
6. Find first non-repeated character
7. Find second-highest salary
8. Find third-highest salary
9. Find highest salary by department
10. Find average salary by department
11. Count employees by department
12. Group employees by department
13. Sort employees by salary
14. Find top 3 salaries
15. Flatten List<List<T>> using flatMap
```

---

# 116. Stream Interview Cheat Sheet

```text
filter()
    ↓
keep/remove elements

map()
    ↓
transform elements

flatMap()
    ↓
flatten nested structures

distinct()
    ↓
remove duplicates

sorted()
    ↓
sorting

limit()
    ↓
first N

skip()
    ↓
skip first N

count()
    ↓
number of elements

reduce()
    ↓
combine into one value

collect()
    ↓
convert into collection/map/etc.

groupingBy()
    ↓
group by key

partitioningBy()
    ↓
true/false grouping

mapping()
    ↓
transform inside collector

filtering()
    ↓
filter inside collector

joining()
    ↓
combine strings

counting()
    ↓
count grouped values

summingInt()
    ↓
sum grouped values

averagingInt()
    ↓
average grouped values

maxBy()
    ↓
maximum object

minBy()
    ↓
minimum object

findFirst()
    ↓
first matching element

findAny()
    ↓
any matching element

anyMatch()
    ↓
at least one?

allMatch()
    ↓
everyone?

noneMatch()
    ↓
nobody?

mapToInt()
    ↓
Stream<Integer> → IntStream
```

---

# 117. Interview Thinking Pattern

When you see a Streams question, first identify:

### Step 1 — Filter?

```java
.filter(...)
```

### Step 2 — Transform?

```java
.map(...)
```

### Step 3 — Nested data?

```java
.flatMap(...)
```

### Step 4 — Sort?

```java
.sorted(...)
```

### Step 5 — Need one result?

```java
.reduce()
max()
min()
findFirst()
findAny()
```

### Step 6 — Need collection?

```java
.collect(...)
```

### Step 7 — Need groups?

```java
.groupingBy(...)
```

### Step 8 — Need true/false groups?

```java
.partitioningBy(...)
```

### Step 9 — Need numeric operation?

```java
.mapToInt(...)
```

---

# 118. Golden Formulae for Interviews

### Even sum

```java
numbers.stream()
       .filter(x -> x % 2 == 0)
       .mapToInt(Integer::intValue)
       .sum();
```

### Second highest

```java
numbers.stream()
       .distinct()
       .sorted(Comparator.reverseOrder())
       .skip(1)
       .findFirst();
```

### Frequency

```java
numbers.stream()
       .collect(Collectors.groupingBy(
           Function.identity(),
           Collectors.counting()
       ));
```

### Department count

```java
employees.stream()
         .collect(Collectors.groupingBy(
             Employee::getDepartment,
             Collectors.counting()
         ));
```

### Department average

```java
employees.stream()
         .collect(Collectors.groupingBy(
             Employee::getDepartment,
             Collectors.averagingInt(
                 Employee::getSalary
             )
         ));
```

### Highest salary by department

```java
employees.stream()
         .collect(Collectors.groupingBy(
             Employee::getDepartment,
             Collectors.maxBy(
                 Comparator.comparingInt(
                     Employee::getSalary
                 )
             )
         ));
```

### Flatten nested list

```java
nestedList.stream()
          .flatMap(List::stream)
          .collect(Collectors.toList());
```

### Duplicate elements

```java
Set<Integer> seen = new HashSet<>();

numbers.stream()
       .filter(x -> !seen.add(x))
       .collect(Collectors.toSet());
```

### First non-repeated character

```java
Map<Character, Long> frequency =
    str.chars()
       .mapToObj(c -> (char)c)
       .collect(Collectors.groupingBy(
           Function.identity(),
           LinkedHashMap::new,
           Collectors.counting()
       ));
```

---

# 119. Final Interview Priority

For a 5-year Java developer, priority should be:

### 🔴 Must know perfectly

```text
filter
map
flatMap
sorted
distinct
reduce
collect
groupingBy
partitioningBy
toMap
Comparator
Optional
findFirst
findAny
anyMatch
allMatch
noneMatch
mapToInt
```

### 🟠 Must practice coding

```text
Second highest salary
Third highest salary
Duplicate elements
Frequency
First non-repeated character
Highest salary by department
Average salary by department
Employee count by department
Top N salaries
Nested List flattening
Nested groupingBy
toMap duplicate handling
```

### 🟡 Know conceptually

```text
Lazy evaluation
Short-circuiting
Stateless vs stateful
Sequential vs parallel stream
Primitive streams
Stream reuse
Side effects
```

### 🟢 Advanced

```text
collectingAndThen
mapping
filtering
summarizingInt
Custom collectors
Parallel stream internals
Spliterator
ForkJoinPool
```

## One-line mental model

```text
FILTER → kya rakhna hai?
MAP → kya banana hai?
FLATMAP → nested ko flat kaise karna hai?
SORTED → order kya hoga?
DISTINCT → duplicate hatane hain?
REDUCE → sabko ek value mein kaise combine karna hai?
COLLECT → result kis collection/map mein chahiye?
GROUPINGBY → kis basis pe group karna hai?
PARTITIONINGBY → true/false mein divide karna hai?
OPTIONAL → result ho bhi sakta hai, nahi bhi.
```

