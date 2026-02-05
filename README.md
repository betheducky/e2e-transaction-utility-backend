# Transaction Utility API (Java Backend)

## Overview
This project implements a lightweight HTTP server in Java to manage financial transactions.  
It demonstrates a full CRUD backend with separation of concerns across Controller → Service → Store layers, implemented without frameworks to showcase core Java backend skills.

Key features:
- Create, read, update, and delete transactions
- Partial updates supported for all mutable fields
- Input validation and error handling
- Simple in-memory store, easily extendable to a database

---

## API Endpoints

### 1. Create Transaction

```
POST /transactions
Content-Type: application/json
```

**Request Body:**

```json
{
  "description": "Coffee",
  "amount": 3.50
}
```

**Response:**

* `201 Created` with created transaction JSON

---

### 2. Get All Transactions

```
GET /transactions
```

**Response:**

* `200 OK` with array of transactions

```json
[]
```

---

### 3. Get Transaction by ID

```
GET /transactions/{id}
```

**Response:**

* `200 OK` with transaction JSON if found
* `404 Not Found` if the ID does not exist

---

### 4. Update Transaction

```
PUT /transactions/{id}
Content-Type: application/json
```

**Request Body (partial updates allowed):**

```json
{
  "description": "Updated description",
  "amount": 5.00
}
```

**Behavior:**

* Only fields provided are updated
* `amount` uses `0.0` as “not provided” sentinel:

  * `> 0` → updates value
  * `0` → keeps existing value
  * `< 0` → invalid, returns `400 Bad Request`

**Response:**

* `200 OK` with updated transaction
* `400 Bad Request` for invalid input
* `404 Not Found` if ID does not exist

---

### 5. Delete Transaction

```
DELETE /transactions/{id}
```

**Response:**

* `200 OK` on successful deletion
* `404 Not Found` if ID does not exist

---

## Architectural Notes

* **Controller** handles HTTP parsing, request routing, and response formatting
* **Service** enforces business rules and validation
* **Store** manages in-memory transaction data, no HTTP logic
* **Routing logic**: `/transactions` → collection, `/transactions/{id}` → individual transaction
* **Update sentinel**: `0.0` is used as a “not provided” signal for `amount` in `PUT` requests (see Update behavior above)
* All endpoints tested with `curl` to verify behavior

---

## Running the Server

1. Compile the project:

```bash
javac -d out $(find src/main/java -name "*.java")
# Compiles all Java source files recursively into the "out" directory.
# $(find ...) does not work on Windows OS; compile files manually or with an IDE.
```

2. Run the server:

```bash
java -cp out example.transactions.Main
```

3. Manually test endpoints with `curl` or Postman.

---

## Running Unit Tests

This project includes unit tests for the service layer using JUnit 5.

### 1. Download the JUnit Platform Console Standalone JAR and place it in a `lib/` folder:

```bash
mkdir -p lib
cd lib
curl -LO https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.0/junit-platform-console-standalone-1.10.0.jar
cd ..
```

### 2. Compile the source and tests.

```bash
javac -cp lib/junit-platform-console-standalone-1.10.0.jar -d out \
  src/main/java/**/*.java \
  tests/java/**/*.java
```

### 3. Run the tests.

```bash
java -jar lib/junit-platform-console-standalone-1.10.0.jar execute \
  --class-path out \
  --scan-class-path
```

## Current Test Coverage

Tests verify the core business logic in TransactionService:

* Creating transactions
* Validation of negative amounts
* Partial update behavior (preserving of existing values)


## Future Implementations

* Tracking of account ledger via account balance variable
* Implementation of transaction types: REFUND, FEE, TRANSFER
* Implementation of account types: SAVINGS, CHECKING
