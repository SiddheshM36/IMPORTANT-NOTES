Bhai, sabse important baat: S3 mein upload hui 1 GB CSV ko hum ek saath RAM mein load nahi karenge. Spring Boot S3 se file ka stream open karega aur Spring Batch usse record-by-record read karega. Chalo actual flow aur Java code dekhte hain.

## 1. S3 se file read kaise hogi?

Amazon S3

`bulk-payments/BATCH-1001/input.csv`

Spring Boot — S3 SDK

`getObject()` → `ResponseInputStream`

BufferedReader / CSV Parser

Stream se chhota hissa read karke records parse

Spring Batch

Reader → Processor → Writer → DB chunk commit

S3 file ko database mein pehle copy karne ki zaroorat nahi hai. Validation job S3 object ko directly read kar sakti hai.

## 2. Step 1: S3 se stream open karna

AWS SDK for Java v2 ka example:

```
@Service
@RequiredArgsConstructor
public class S3FileService {

    private final S3Client s3Client;

    public ResponseInputStream<GetObjectResponse> readFile(
            String bucket,
            String key) {

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        return s3Client.getObject(request);
    }
}
```

Yahan `getObject()` ek `ResponseInputStream` deta hai. Iska matlab yeh nahi ki poori 1 GB file Java heap mein aa gayi. SDK stream se data progressively read kiya ja sakta hai.

Example S3 object key:

```
bucket: my-bank-bulk-files
key:    bulk-payments/BATCH-1001/input.csv
```

`key` file ka S3 path hai. Isko database ke `payment_batch` record mein save karke validation job ko pass kar sakte ho.

## 3. Step 2: Stream se CSV records read karna

Maan lo CSV file aisi hai:

```
paymentId,accountNumber,amount
P101,1234567890,500
P102,9876543210,1500
P103,1111222233,250
```

Basic concept:

```
try (ResponseInputStream<GetObjectResponse> stream =
         s3FileService.readFile(bucket, key);
     BufferedReader reader = new BufferedReader(
         new InputStreamReader(stream, StandardCharsets.UTF_8))) {

    String line;

    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
}
```

Is loop mein har iteration agla line read karti hai. `BufferedReader` chhota buffer use karta hai, isliye poori file ek saath memory mein rakhne ki zaroorat nahi.

Lekin production mein CSV ke liye `readLine()` aur `split(",")` par depend mat karna: quoted fields mein comma aur multiline values ho sakti hain. Proper CSV parser use karna chahiye.

## 4. Step 3: Spring Batch mein kaise connect hoga?

Production mein main Spring Batch ka `ItemReader → ItemProcessor → ItemWriter` pattern use karunga.

ItemReader

S3 stream se ek payment record read

ItemProcessor

Amount, account format aur business rules validate

ItemWriter

500 records ka illustrative chunk DB mein write

DB commit

Agla chunk process hoga

Agar tum Spring Batch ka custom reader likh rahe ho, toh woh S3 stream ko open karega, CSV parser se records parse karega aur `read()` method har call par ek record return karega. Spring Batch configured chunk size ke hisaab se records processor aur writer tak bhejega.

Ek aur option hai: S3 file ko application ke temporary disk par download karke `FlatFileItemReader` use karna. Isse streaming reader implement karna simpler ho sakta hai, lekin temporary disk capacity, cleanup aur failed-job restart ka dhyan rakhna hoga. Direct S3 streaming mein reader restart/checkpoint logic carefully implement karna padta hai.

## 5. Agar validation job fail ho gayi toh?

- Original CSV S3 mein rahegi.
- Spring Batch job ka execution status DB metadata mein record hoga.
- Successfully committed chunks ke records DB mein reh sakte hain.
- Restart par duplicate rows nahi banani chahiye; stable payment ID, unique constraint aur checkpoint/restart strategy use karo.
- Validation poori tarah successful hone ke baad hi batch `VALIDATION_SUCCESS` hoga.
- Kisi bhi invalid row par `VALIDATION_FAILED` hoga aur CBS payment processing start nahi hogi.

Bhai, simple formula yaad rakh: S3 file storage hai, `InputStream` data lane ka raasta hai, CSV parser records nikalta hai, aur Spring Batch un records ko chunks mein process karta hai.
