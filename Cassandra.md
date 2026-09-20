**Haan bhai, but thoda precise bolna:**
**Denormalization JOIN ko avoid karne ka ek major reason/approach hai, lekin "denormalization ki wajah se JOIN nahi hai" technically complete explanation nahi hai.**

Cassandra ka **distributed architecture + query-driven design** primary reason hai.

### Simple relation

```text
Cassandra distributed architecture
          ↓
Cross-node JOIN expensive
          ↓
JOIN avoid karna better
          ↓
Query-driven data modeling
          ↓
Denormalization
          ↓
Same data multiple tables mein duplicate
          ↓
Direct partition read
          ↓
JOIN ki zarurat nahi
```

### Example

Relational DB:

```text
Customer
101 | Siddhesh

Transaction
T1 | 101 | 5000
T2 | 101 | 3000
```

JOIN:

```sql
Customer JOIN Transaction
```

Cassandra:

```text
transactions_by_customer

101 | Siddhesh | T1 | 5000
101 | Siddhesh | T2 | 3000
```

Ab:

```sql
WHERE customer_id = 101
```

**Direct partition read → JOIN nahi.**

### Interview mein bolna:

> **Cassandra uses denormalization and query-driven data modeling to avoid expensive cross-partition or cross-node joins. Since Cassandra is designed for distributed, scalable access patterns, we duplicate data across tables when necessary so queries can directly access the required partition.**

**Short version:**
**JOIN avoid karne ke liye → Query-driven modeling → Denormalization.**
