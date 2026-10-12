Bhai, chal dono ka code ek hi example se samajhte hain. Ek-ek line ka matlab batata hoon. 😄

Maan le database mein ek product hai aur uski quantity `10` hai. Rahul aur Amit dono quantity update kar rahe hain.

## 1. Optimistic Locking — `@Version`

### Entity

```
@Entity
public class Product {

    @Id
    private Long id;

    private int quantity;

    @Version
    private Long version;
}
```

Ab line by line:

- `@Entity` — ye Java class database table ko represent karti hai.
- `@Id` — product ki unique ID.
- `quantity` — product ki quantity.
- `@Version` — database record ka version track karta hai, taaki pata chale ki kisi aur ne record change kiya hai ya nahi.

### Service

```
@Transactional
public void updateQuantity(Long id, int quantity) {

    Product product = repository.findById(id)
            .orElseThrow();

    product.setQuantity(quantity);
}
```

Yahan kya ho raha hai?

1. Rahul product read karta hai: quantity `10`, version `0`.
2. Amit bhi quantity `10`, version `0` read karta hai.
3. Rahul quantity `8` karta hai aur transaction commit hota hai. Version `1` ho jaata hai.
4. Amit apne purane data se update karne ki koshish karta hai. Hibernate detect karta hai ki version badal gaya hai.
5. Amit ka conflicting update fail ho sakta hai.

Yaad rakh: Optimistic mein pehle se row lock nahi lete. Update ke waqt check hota hai ki data badla hai ya nahi.

## 2. Pessimistic Locking — `PESSIMISTIC_WRITE`

### Repository

```
public interface ProductRepository
        extends JpaRepository<Product, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findByIdForUpdate(Long id);
}
```

Ab line by line:

- `@Lock(LockModeType.PESSIMISTIC_WRITE)` — database se write lock lene ko kehta hai.
- `@Query("SELECT ...")` — product ko read karta hai.
- `findByIdForUpdate()` — product read karte waqt lock acquire karne ke liye method hai.

Important: `SELECT` sirf read karta hai, lekin `@Lock` ki wajah se read ke saath database lock bhi acquire hota hai.

### Service

```
@Transactional
public void updateQuantity(Long id, int quantity) {

    Product product = repository.findByIdForUpdate(id)
            .orElseThrow();

    product.setQuantity(quantity);
}
```

Ab kya hoga?

1. Rahul product read karta hai aur row par lock lagta hai.
2. Amit same row ko lock karne ki koshish karta hai. Usko wait karna pad sakta hai.
3. Rahul quantity update karta hai aur transaction commit karta hai.
4. Rahul ka lock release hota hai.
5. Amit aage badhta hai aur latest data read/check kar sakta hai.

Yaad rakh: Pessimistic mein pehle lock lete hain, phir data update karte hain.

## Dono ka final difference

| Optimistic                          | Pessimistic                                  |
| ----------------------------------- | -------------------------------------------- |
| `@Version`                          | `@Lock(PESSIMISTIC_WRITE)`                   |
| Pehle lock nahi leta                | Read ke waqt lock leta hai                   |
| Update par conflict detect hota hai | Doosra transaction wait kar sakta hai        |
| Conflict rare ho toh useful         | Conflict frequent ho toh useful ho sakta hai |

Bhai, sabse important baat: `@Transactional` dono mein zaroori hai, taaki database transaction aur locking ka lifecycle sahi rahe. Optimistic locking mein version conflict par exception handle karni pad sakti hai; pessimistic locking mein timeout aur deadlock ka bhi dhyaan rakhna padta hai.
