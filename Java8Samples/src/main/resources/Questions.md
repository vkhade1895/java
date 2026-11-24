> Coding questions are not kept in this file: each one is a Java class in the project, with comments explaining it. [README.md](../../../README.md) lists them all.

# Part 1: Advanced Java & Backend Concepts (8–10 Years Experience)

### 1. How does the Java Memory Model (JMM) relate to multi-threading and the `volatile` keyword?

**Answer:** The JMM defines how threads interact through memory, ensuring visibility and ordering via the happens-before relationship. The `volatile` keyword guarantees visibility and ordering: a write to a volatile variable happens-before every later read of it by other threads, so no thread sees a stale value, and code is not reordered around it. It does not provide atomicity (unlike `synchronized` or `AtomicInteger`), so `count++` on a volatile field is still unsafe.

### 2. Explain the internal working of `ThreadPoolExecutor` and how active vs. dead threads are managed.

**Answer:** A `ThreadPoolExecutor` uses a core pool size, maximum pool size, work queue, and a rejection policy. A new task starts a new thread while fewer than core threads are running; after that it goes into the queue; if the queue is full, a new thread starts up to the maximum; beyond that, the rejection policy runs. Worker threads encapsulate tasks within a `Worker` class (which implements `Runnable`, extends `AbstractQueuedSynchronizer` and wraps a thread). They continuously poll the blocking queue using `getTask()`. If a worker throws an unexpected exception or the pool scales down past keep-alive limits, the thread terminates and is cleaned up by the executor framework.

### 3. How do `map()` and `flatMap()` differ in Java Streams, and when would you use `flatMap`?

**Answer:** `map()` takes a function and applies it to each element, transforming `Stream<T>` to `Stream<R>` (1-to-1 mapping). `flatMap()` transforms each element into a stream and then flattens the resulting streams into a single output stream (1-to-many mapping). You use `flatMap` when dealing with nested structures like a `List<List<String>>` or processing objects containing collections.

### 4. What is a Circuit Breaker pattern, and why is it essential in a Spring Boot microservices architecture?

**Answer:** A Circuit Breaker (implemented via Resilience4j or similar libraries) prevents cascading failures in distributed systems. It wraps calls to external services and monitors for failures. When failures exceed a threshold, the circuit "trips" (opens), immediately returning a fallback response instead of blocking threads and waiting for timeouts, allowing the downstream service time to recover.

### 5. What are Virtual Threads (Java 21 / Project Loom), and how do they differ from OS Platform Threads?

**Answer:**
- **Platform Threads:** Traditional Java threads map 1:1 to OS threads. They are expensive in memory (~1MB per stack) and limited in quantity.
- **Virtual Threads:** Managed directly by the JVM rather than the OS. When a virtual thread performs a blocking I/O operation (e.g., a database query or HTTP call), the JVM unmounts it from its underlying OS "carrier" thread, allowing other virtual threads to execute.
- **Why it matters:** It allows Java applications to handle millions of concurrent requests without running out of system memory or requiring complex reactive code.

### 6. How does `ConcurrentHashMap` achieve thread safety internally in Java 8+?

**Answer:**
- **No global lock:** Unlike `Hashtable` or synchronized maps, `ConcurrentHashMap` does not lock the entire table.
- **Bucket-level locking:** In Java 8+, it uses fine-grained locking (`synchronized` on individual bucket head nodes) only when inserting new nodes into an occupied bucket.
- **CAS operations:** Non-blocking Compare-And-Swap instructions are used for insertion when a bucket is completely empty.
- **Volatile reads:** Table nodes use volatile references to ensure read operations (like `get()`) are lock-free and always see the latest written data.

### 7. How does `CompletableFuture` handle asynchronous exception propagation compared to a traditional `Future`?

**Answer:** A traditional `Future.get()` blocks the thread and throws an `ExecutionException` wrapping the underlying error. `CompletableFuture` provides non-blocking, functional exception handling via callbacks:
- `.exceptionally(ex -> fallbackValue)` transforms an exception into a fallback result.
- `.handle((result, ex) -> ...)` processes both success and failure outcomes in a single block.
- `.whenComplete((result, ex) -> ...)` executes a side-effect action without altering the returned value or exception.

### 8. What is the Cache-Aside pattern, and how do you handle cache invalidation in a Spring Boot microservices cluster using Redis?

**Answer:** In the Cache-Aside pattern, the application checks Redis first. On a cache miss, it reads from the primary database, writes the result to Redis with a Time-To-Live (TTL), and returns the data. When data changes, update the database first and then evict the key, for example with Spring's `@CacheEvict(value = "cacheName", key = "#id")`. Because Redis is shared, one eviction is seen by every instance. Only if each instance also keeps a local in-memory cache do you need to publish a Kafka/Redis Pub-Sub event so every instance evicts its local copy.

### 9. How do `@Transactional` isolation levels work, and how do you avoid Dirty Reads and Phantom Reads?

**Answer:** SQL defines four isolation levels:
- **Read Uncommitted:** Allows reading uncommitted data (Dirty Reads).
- **Read Committed:** Prevents dirty reads; only reads committed data (default in PostgreSQL/Oracle).
- **Repeatable Read:** Guarantees repeated reads of the same row within a transaction return identical results (default in MySQL InnoDB).
- **Serializable:** Strictest level; the result is as if transactions ran one after another (they still run concurrently, and the database rolls back a transaction that would break this), preventing Phantom Reads (new rows inserted by another transaction that match the query criteria).

Higher isolation levels increase consistency but reduce concurrent write throughput. Best practice: default to `READ_COMMITTED` for high throughput, and use optimistic locking (`@Version`) or database row locks (`SELECT ... FOR UPDATE`) for concurrent financial/inventory transactions. A Redis lock alone is not a safe guard for money: it can expire or be lost while the work is still running.

### 10. What is the difference between Optimistic and Pessimistic Locking in JPA/Hibernate, and when is each appropriate?

**Answer:**
- **Pessimistic Locking** (`LockModeType.PESSIMISTIC_WRITE`): Locks the row at the database level (`SELECT ... FOR UPDATE`), blocking other transactions until commit. Best for high-contention, critical write paths (e.g., inventory count deduction).
- **Optimistic Locking** (`@Version` field): Does not lock rows upfront. On update, Hibernate checks if the version column matches; if it changed due to another transaction, it throws `OptimisticLockException`. Best for read-heavy architectures with low write conflicts.

### 11. How do Java Garbage Collectors (G1GC vs. ZGC) differ, and when should you choose ZGC in high-throughput backend systems?

**Answer:** G1GC partitions the heap into equal-sized regions and balances throughput with pause times (default pause-time goal: 200 ms, set with `-XX:MaxGCPauseMillis`). ZGC is a scalable, low-latency garbage collector that performs almost all GC work concurrently (including marking and object relocation), targeting sub-millisecond pause times regardless of heap size, even multiple terabytes, in exchange for some extra CPU use. Choose ZGC for low-latency fintech, trading, or payment microservices where GC stop-the-world pauses directly degrade API SLAs.

### 12. How does Kafka consumer group rebalancing work, and how can cooperative sticky assignors prevent latency spikes?

**Answer:** When a consumer joins or leaves a group (or partitions are added), Kafka rebalances partition assignments. The older "eager" protocol revokes all partitions from all consumers, halting consumption for the whole consumer group until reassignment finishes (other groups keep running). The "cooperative sticky assignor" performs incremental rebalancing: consumers continue processing untouched partitions and only release/acquire the specific partitions being moved, avoiding stop-the-world lag.

### 13. What is the Outbox Pattern in microservices, and how does it solve distributed dual-write inconsistencies?

**Answer:** When a service must update its database and publish an event to Kafka, doing both independently can fail midway (the dual-write problem). The Outbox Pattern writes both the business entity and an "outbox" event record into the local database within the same ACID transaction. A background poller reads the outbox table (or Debezium reads new outbox rows from the database transaction log, using Change Data Capture) and streams the messages reliably to Kafka with at-least-once delivery guarantees, so consumers must handle duplicates.

---

# Part 2: System Design Scenarios

### 1. Design a Scalable, Distributed Rate Limiter for a High-Traffic API / Payment Gateway

**Scenario:** Your API handles millions of requests per second and needs to throttle clients exceeding a specific quota (e.g., 100 requests/minute) to prevent overload, across multiple app instances.

**Key Solution Components:**
- Use the Token Bucket or Sliding Window Log algorithm.
- Track request counts in a distributed store (Redis). `INCR` + `EXPIRE` gives a simple fixed-window counter, a sliding window log uses a sorted set, and a token bucket is usually a Lua script. Run the read-and-update steps together (a Lua script or `MULTI`/`EXEC`) so they stay atomic across server nodes.
- Place the rate limiter at the API Gateway layer (e.g., Spring Cloud Gateway or Envoy) so throttled traffic is rejected early with `HTTP 429 Too Many Requests`, before it reaches backend microservices.

### 2. Design a Distributed ID Generator (Like Twitter Snowflake)

**Scenario:** Your microservices architecture generates millions of orders daily across multiple data centers, requiring unique, time-sortable 64-bit IDs without relying on a single database auto-increment sequence.

**Key Solution Components:**
- Structure the 64-bit ID: 1 bit (unused sign) + 41 bits (timestamp millis) + 10 bits (machine/node ID) + 12 bits (sequence number per millisecond).
- Use ZooKeeper or an initialization service to dynamically assign unique machine IDs to instances, avoiding collisions.
- This guarantees global uniqueness and ensures IDs are roughly sortable by creation time, optimizing database index performance.

### 3. Design an Event-Driven Notification System (Push, SMS, Email) with Retry Queues

**Scenario:** A core banking or e-commerce platform needs to send real-time notifications to millions of users across multiple channels without blocking business workflows.

**Key Solution Components:**
- **Producer layer:** Core services publish `NotificationRequested` events to Apache Kafka topics, partitioned by `userId`.
- **Consumer microservices:** Dedicated, decoupled services (`EmailService`, `SMSService`, `PushService`) consume messages concurrently via Kafka consumer groups.
- **Fault tolerance & retries:** Use a Dead Letter Topic (DLT) pattern with exponential backoff (e.g., retry after 1 min → 5 min → 15 min → DLT) to handle transient vendor API failures (e.g., Twilio/SendGrid outages) without losing messages.
- **Idempotency:** Include a unique `notificationId` to prevent duplicate alerts if network retries cause message redelivery.

### 4. Design a Distributed, Centralized Logging & Observability Pipeline

**Scenario:** You manage 50+ Spring Boot microservices running across multiple environments and need unified logging, trace aggregation, and alerting.

**Key Solution Components:**
- **Trace context propagation:** Integrate OpenTelemetry / Micrometer Tracing to inject `traceId` and `spanId` into standard SLF4J/MDC log entries, HTTP headers, and Kafka records.
- **Log collection agent:** Run a lightweight collector (e.g., FluentBit or Vector) as a sidecar or daemonset to scrape application log files.
- **Ingestion pipeline:** Stream logs through Apache Kafka to buffer high log bursts, then ingest them into OpenSearch/Elasticsearch.
- **Storage & visualization:** Use Grafana or Kibana for unified log search and automated threshold alerts (e.g., HTTP 5xx spikes).

### 5. Design an Idempotent Payment Processing API

**Scenario:** A high-concurrency payment microservice processing UPI and card transactions must ensure a user is never double-charged during network retries or double-clicks.

**Key Solution Components:**
- **Idempotency key:** The client generates a unique UUID `Idempotency-Key` in the request header.
- **Database guard first:** Insert a payment row with the `Idempotency-Key` and status `PAYMENT_PENDING`, with a unique constraint on the key. A retry with the same key fails on that constraint, so it can never create a second charge. A Redis lock with a short TTL can be an extra, faster check, but not the only guard, because it can expire.
- **Database validation:** Query the transaction database for the unique key — if already `COMPLETED`, immediately return the cached receipt.
- **Atomic state transition:** Call the third-party gateway with the same idempotency key, then update the database record to `COMPLETED` within a local transaction (and update Redis, if used). If the gateway call times out, mark the payment as unknown and check its status with the gateway (reconcile) instead of retrying blindly.

### 6. Design a Distributed URL Shortener (Like Bitly/TinyURL)

**Scenario:** Build a URL shortener service that handles 10,000 writes/sec and 100,000 reads/sec with auto-generated 7-character aliases.

**Key Solution Components:**
- **Encoding algorithm:** Base62 encoding (`[a-z, A-Z, 0-9]`) of a distributed counter (e.g., ID ranges handed out by Redis) to generate 7-character codes (62⁷ ≈ 3.5 trillion unique URLs, about 11 years at 10,000 writes/sec). A 64-bit Snowflake ID would not fit: it needs 11 Base62 characters.
- **Read-heavy caching:** Place Redis in front of the database using the Cache-Aside pattern (90%+ of read traffic served directly from memory with LRU eviction).
- **Database strategy:** Use a NoSQL document/key-value store (like DynamoDB or MongoDB), or a partitioned PostgreSQL instance sharded on the hashed key.
- **Redirection:** Return `HTTP 302 Found` (temporary redirect) so click analytics are captured on every request, without permanent browser caching.
