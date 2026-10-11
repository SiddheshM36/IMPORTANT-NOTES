kya hoga agar saare columns mai indexes lagga du toh
Bhai, agar tu database table ke saare columns par index laga dega, toh read queries kuch cases mein fast ho sakti hain, lekin database ka overall performance kharab bhi ho sakta hai.

1. Index lagane ke advantages aur disadvantages
Operation

Saare columns par index ka effect

SELECT

Kuch queries fast ho sakti hain, agar index useful ho.

INSERT

Slow ho sakta hai, kyunki har relevant index update karna padega.

UPDATE

Indexed column change hone par index bhi maintain karna padega.

DELETE

Index entries bhi remove karni padti hain.

Storage

Disk space zyada lagega.

Database maintenance

Index rebuild aur maintenance ka overhead badhega.

2. Example se samajh
Maan le banking application mein ye table hai:

CREATE TABLE CUSTOMER (
    CUSTOMER_ID BIGINT,
    NAME VARCHAR(100),
    CITY VARCHAR(50),
    PHONE VARCHAR(15),
    EMAIL VARCHAR(100),
    BALANCE DECIMAL(15,2)
);

Tu har column par index bana deta hai:

CREATE INDEX idx_name ON CUSTOMER(NAME);
CREATE INDEX idx_city ON CUSTOMER(CITY);
CREATE INDEX idx_phone ON CUSTOMER(PHONE);
CREATE INDEX idx_email ON CUSTOMER(EMAIL);
CREATE INDEX idx_balance ON CUSTOMER(BALANCE);

Ab maan le tu 10,000 customers insert karta hai. Database ko table mein rows insert karne ke saath in indexes ko bhi maintain karna padega.

Lekin agar query hai:

SELECT *
FROM CUSTOMER
WHERE CUSTOMER_ID = 101;

Aur CUSTOMER_ID par index nahi hai, toh database ko phir bhi table scan karna pad sakta hai. Baaki columns par index lagane ka is query mein koi direct fayda nahi.

3. Toh indexes kahan lagane chahiye?
Primary key: Usually automatically index hota hai, database par depend karta hai.

Foreign key: Frequently joins mein use hoti ho toh index consider karo.

WHERE conditions: Frequently filter hone wale columns.

JOIN conditions: Frequently join hone wale columns.

ORDER BY / GROUP BY: Query pattern ke hisaab se index useful ho sakta hai.

Ek aur important baat: agar query mein WHERE CITY = ? AND BALANCE > ? hai, toh do alag indexes ke bajaye kabhi-kabhi composite index bhi better ho sakta hai. Yeh query pattern aur database optimizer par depend karta hai.

Interview answer: “Indexing all columns is generally not recommended because indexes consume storage and increase the cost of INSERT, UPDATE, and DELETE operations. We should create indexes based on query patterns, selectivity, joins, and execution plans.”

