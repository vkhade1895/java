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
- **Bucket-level locking:** In Java 8+, when it changes a bucket that already has entries (add, update or remove a key), it locks only that bucket, with `synchronized` on the bucket's first node.
- **CAS operations:** Non-blocking Compare-And-Swap instructions are used for insertion when a bucket is completely empty.
- **Volatile reads:** Table nodes use volatile references to ensure read operations (like `get()`) are lock-free and always see the latest written data.

### 7. How does `CompletableFuture` handle asynchronous exception propagation compared to a traditional `Future`?

**Answer:** A traditional `Future.get()` blocks the thread and throws an `ExecutionException` wrapping the underlying error. `CompletableFuture` provides non-blocking, functional exception handling via callbacks:
- `.exceptionally(ex -> fallbackValue)` transforms an exception into a fallback result.
- `.handle((result, ex) -> ...)` processes both success and failure outcomes in a single block.
- `.whenComplete((result, ex) -> ...)` executes a side-effect action without altering the returned value or exception.
- See the code: `concurrency/CallServicesInParallel.java` (a fallback with `exceptionally` and a timeout with `completeOnTimeout`).

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

### 14. What are the common mistakes with `@Transactional` in Spring?

**Answer:** Spring starts a transaction through a proxy (a wrapper object around your bean). Most mistakes come from that:
- **Calling the method from the same class** (self-invocation): the call doesn't go through the proxy, so no transaction starts. Move the method to another bean.
- **Private methods:** `@Transactional` has no effect on them. Before Spring 6, it worked only on public methods.
- **Checked exceptions don't roll back** by default. Only unchecked ones (`RuntimeException`, `Error`) do. Use `@Transactional(rollbackFor = Exception.class)` when you need it.
- **Catching the exception** inside the method without throwing it again: Spring never sees it, so it commits.
- **Propagation:** `REQUIRED` (the default) joins the current transaction. `REQUIRES_NEW` pauses it and starts a separate one, for example for an audit log that must be saved even if the main work fails.

### 15. What is the N+1 query problem in JPA/Hibernate, and how do you fix it?

**Answer:** You load N parents with 1 query (for example 100 orders). Then you read a lazy collection on each parent (its items). Hibernate runs 1 more query for every parent, so 1 + N queries in total.
- **How to see it:** turn on SQL logging (`spring.jpa.show-sql=true`) or Hibernate statistics, and count the queries.
- **Fixes:** `JOIN FETCH` in the JPQL query, `@EntityGraph` on the repository method, batch fetching (`@BatchSize` or `hibernate.default_batch_fetch_size`, which loads the children of many parents in one query), or a DTO projection that selects only the columns you need.
- **Note:** switching to EAGER loading doesn't fix it. It can still run one query per parent.

### 16. What are Spring bean scopes, and why is constructor injection preferred?

**Answer:**
- **Scopes:** `singleton` (the default: one object for the whole application), `prototype` (a new object every time you ask for one), and for web apps `request`, `session` and `application`.
- **Constructor injection is preferred:** the fields can be `final`, the object is always complete, it is easy to test without Spring (just call the constructor), and circular dependencies show up at startup.
- **Trap:** if a singleton gets a prototype bean by injection, it gets one object once and keeps it. To get a new object each time, use `ObjectProvider<T>`, `@Lookup` or a scoped proxy.
- See the code: `design/SolidPrinciples.java` (the D part passes a dependency in through the constructor).

### 17. What is the difference between `synchronized` and `ReentrantLock`?

**Answer:** Both let only one thread at a time run a piece of code, and both make changes visible to other threads.
- **`synchronized`** is simpler. Java unlocks automatically, even when an exception happens. But a waiting thread can't give up or be interrupted.
- **`ReentrantLock`** gives more control: `tryLock()` (try once, or with a timeout, then give up), `lockInterruptibly()`, a fair mode (first come, first served) and several `Condition` objects. You must call `unlock()` in a `finally` block.
- Use `synchronized` for simple cases, and `ReentrantLock` when you need a timeout or fairness. On Java 21, also use `ReentrantLock` where virtual threads wait while holding a lock (Java 24 removed this limit for `synchronized`).
- See the code: `concurrency/DeadlockDemo.java` and `concurrency/CounterRace.java`.

### 18. What problems can `ThreadLocal` cause?

**Answer:** A `ThreadLocal` value belongs to one thread, and it stays there until you remove it.
- **Thread pools reuse threads.** If you forget `remove()`, the next request on that thread can see the old value (for example another user's data). The memory is also kept while the thread is alive, and pool threads live for a long time.
- **Always clean up:** call `remove()` in a `finally` block.
- **Virtual threads:** with millions of threads, a big value in every `ThreadLocal` uses a lot of memory.
- **Alternative:** `ScopedValue` (final in Java 25) is set only for a block of code and is cleaned up automatically.
- Common uses: the logging context (MDC), the security context and request IDs.

### 19. What are the JVM memory areas, and which error does each one cause?

**Answer:**
- **Heap:** all objects live here. Most garbage collectors split it into a young and an old generation. When it is full: `OutOfMemoryError: Java heap space`. Set its size with `-Xmx`.
- **Stack:** one per thread. It holds method calls and local variables. Recursion that goes too deep: `StackOverflowError`. Set its size with `-Xss`.
- **Metaspace** (since Java 8): information about classes. Too many classes, often from a class loader leak: `OutOfMemoryError: Metaspace`. Limit it with `-XX:MaxMetaspaceSize`.
- **Direct memory:** memory outside the heap, used by NIO buffers: `OutOfMemoryError: Direct buffer memory`.
- **Too many threads:** an `OutOfMemoryError` that says it is unable to create a native thread.

### 20. How do you find a memory leak in a production Java service?

**Answer:**
- **Signs:** heap use keeps growing even after full garbage collections, GC runs more and more often, and in the end you get `OutOfMemoryError`.
- **Steps:**
  1. Watch heap use after GC in your metrics or GC logs (`-Xlog:gc`).
  2. Take a heap dump: `jcmd <pid> GC.heap_dump /tmp/heap.hprof`, or start the app with `-XX:+HeapDumpOnOutOfMemoryError`.
  3. Open the dump in Eclipse MAT (Memory Analyzer) and look at "Leak Suspects" and the biggest objects (the dominator tree).
  4. Use Java Flight Recorder (JFR) to see which code creates the most objects.
- **Usual causes:** a static collection or cache that only grows, `ThreadLocal` values that are never removed, connections or streams that are never closed, listeners that are never removed, and wrong `equals`/`hashCode` in map keys (the same key gets added many times).

### 21. How do you choose the size of a thread pool?

**Answer:**
- **CPU-heavy work** (calculations): about the number of CPU cores, `Runtime.getRuntime().availableProcessors()`. More threads only add switching work.
- **Work that waits** (database, HTTP): more threads, roughly `cores × (1 + wait time / CPU time)`. Then measure under real load and adjust.
- **Use a bounded queue and a rejection policy.** `Executors.newFixedThreadPool` uses a queue with no limit, so tasks can pile up until the app runs out of memory. `Executors.newCachedThreadPool` can create any number of threads.
- **On Java 21 and newer,** for many tasks that wait, virtual threads are often simpler than tuning a pool.
- See the code: `concurrency/VirtualThreadsDemo.java`.

### 22. How does class loading work, and why does a class loader ask its parent first?

**Answer:**
- **When:** Java loads a class the first time the program uses it, not all classes at the start.
- **Three steps:**
  1. **Loading:** find the `.class` file and read it into memory.
  2. **Linking:** check that the bytecode is safe (verification), and give the static fields their default values (`0`, `null`, `false`).
  3. **Initialization:** run the static blocks and give the static fields their real values. This happens only once, and the JVM makes it thread-safe.
- **The built-in class loaders:** Bootstrap (core classes such as `java.lang.String`), Platform (some other JDK modules, such as `java.sql`) and Application (your own classes and the libraries on the class path).
- **Parent first (parent delegation):** a class loader first asks its parent to load a class, and loads it itself only if the parent can't. So core classes always come from the JDK (nobody can replace `java.lang.String` with their own copy), and a class that the parent can load is loaded only once, by the parent.
- **A class is known by its name plus its class loader.** The same class loaded by two different loaders counts as two different classes. This gives strange errors such as "`Order` cannot be cast to `Order`", seen in app servers and with hot-reload tools.
- **`ClassNotFoundException` vs `NoClassDefFoundError`:** you get `ClassNotFoundException` (a checked exception) when you load a class by its name, for example with `Class.forName("...")`, and it isn't there. You get `NoClassDefFoundError` (an error) when a class was there at compile time but is missing at run time, or when its static initialization failed earlier.
- Because initialization is lazy and thread-safe, the holder-class Singleton needs no locks. See the code: `design/Singletons.java`.

### 23. What do `? extends` and `? super` mean in generics, and what is type erasure?

**Answer:**
- **Generics don't follow inheritance** (they are "invariant"): a `List<Integer>` is not a `List<Number>`, even though an `Integer` is a `Number`. If it were, you could put a `Double` into a list of `Integer`s.
- **`? extends T` (to read):** a `List<? extends Number>` parameter accepts a `List<Integer>`, a `List<Double>` or a `List<Number>`. You can read `Number`s from it, but you can't add to it (except `null`), because Java doesn't know the exact type.
- **`? super T` (to add):** a `List<? super Integer>` parameter accepts a `List<Integer>`, a `List<Number>` or a `List<Object>`. You can add `Integer`s to it, but reading from it only gives you an `Object`.
- **PECS (Producer Extends, Consumer Super):** use `extends` when the collection gives you items, and `super` when it takes items from you. The JDK does this in `Collections.copy(List<? super T> dest, List<? extends T> src)`.
- **Type erasure:** the compiler checks the generic types and then removes them, so at run time a `List<String>` and a `List<Integer>` are both just a `List`. That is why you can't write `new T()`, can't check `obj instanceof List<String>`, can't have two methods that differ only in the generic type (`print(List<String>)` and `print(List<Integer>)`), and can't use primitive types (`List<int>`; use `List<Integer>`).
- See the code: `oop/GenericsWildcards.java`.

### 24. Which design patterns does Spring use?

**Answer:**
- **Singleton:** a bean is a singleton by default. Here it means one object per bean in each Spring container, not one per class loader like the classic pattern (see `design/Singletons.java`).
- **Factory:** the `ApplicationContext` (a `BeanFactory`) creates your beans, and a `@Bean` method is a factory method.
- **Proxy:** `@Transactional`, `@Cacheable`, `@Async` and other AOP features wrap your bean in a proxy object that does extra work before and after your method. That is why a call from the same class skips them (see question 14).
- **Template Method:** `JdbcTemplate`, `RestTemplate` and `TransactionTemplate` do the fixed steps (open a connection, handle errors, clean up), and you write only the part that changes.
- **Dependency injection (inversion of control):** Spring creates the objects and passes in what they need, usually through the constructor (see the D part of `design/SolidPrinciples.java`).
- **Observer:** publish an `ApplicationEvent`, and Spring calls every `@EventListener` method for it.
- **Strategy:** inject a `List` or a `Map` of all the beans that implement one interface, and pick one at run time (see the O part of `design/SolidPrinciples.java`).
- **Front Controller:** in Spring MVC, the `DispatcherServlet` receives every request and passes it to the right controller.
- **Builder:** for example `UriComponentsBuilder`, `WebClient.builder()` and `ResponseEntity.ok().header(...).body(...)` (see `design/BuilderPattern.java`).

### 25. What happens when a Spring Boot app starts, and how does auto-configuration work?

**Answer:**
- **Start:** `main` calls `SpringApplication.run(App.class, args)`. Then, in short:
  1. Spring Boot decides the app type from the class path: a servlet web app (Spring MVC), a reactive web app (WebFlux) or not a web app.
  2. It prepares the `Environment`: `application.properties` or `application.yml`, the active profiles, environment variables and command-line arguments.
  3. It creates the `ApplicationContext` (the container that holds the beans).
  4. `@ComponentScan` finds your classes marked `@Component`, `@Service`, `@Repository`, `@Controller` or `@Configuration`, in the main class's package and the packages below it.
  5. It adds the auto-configured beans (see below).
  6. It creates the singleton beans, injects their dependencies and runs their `@PostConstruct` methods. For a web app, it starts the embedded server (by default Tomcat for Spring MVC, Reactor Netty for WebFlux).
  7. It runs the `CommandLineRunner` and `ApplicationRunner` beans, and then sends the `ApplicationReadyEvent`.
- **`@SpringBootApplication`** = `@SpringBootConfiguration` (a kind of `@Configuration`) + `@EnableAutoConfiguration` + `@ComponentScan`.
- **Auto-configuration:** Spring Boot comes with many auto-configuration classes, listed in `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` (Spring Boot 2.7 and newer; older versions used `spring.factories`). Each one runs only when its conditions are true:
  - `@ConditionalOnClass`: a library is on the class path.
  - `@ConditionalOnMissingBean`: you haven't defined this bean yourself.
  - `@ConditionalOnProperty`: a property has a certain value.
- **Example:** add `spring-boot-starter-data-jpa`, a database driver and the database URL (`spring.datasource.url`, plus the username and password), and Spring Boot creates the `DataSource`, the `EntityManagerFactory` and the transaction manager for you. If you define your own `DataSource` bean, Spring Boot's one backs off.
- **Useful tips:** start the app with `--debug` to print the conditions evaluation report (which auto-configurations ran, and why). Turn one off with `@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)`.
- See the code: `springboot/JavaSamplesApplication.java`. Start it with `mvn spring-boot:run`, then open http://localhost:8080/hello.

### 26. How do you keep microservices working when the services they call fail or are slow?

**Answer:**
- **Timeouts:** give every remote call a connect timeout and a read timeout. Without them, a slow service keeps your threads waiting until you run out of threads, and the problem spreads to your own callers (a cascading failure).
- **Retries with backoff:** retry only errors that may go away (a timeout, HTTP 503), and only a few times. Wait longer before each try (for example 100 ms, 200 ms, 400 ms: exponential backoff), and add a small random delay (jitter) so that many clients don't all retry at the same moment. Retry only calls that are safe to repeat, or send an idempotency key (see Part 2, design 5).
- **Circuit breaker:** stop calling a service that keeps failing, and fail fast for a while (see question 4).
- **Bulkhead:** give each service you call its own small pool of threads or connections (or a semaphore), so one slow service can't use up all of them. The name comes from the walls inside a ship that stop one leak from sinking the whole ship.
- **Fallback:** when a call fails, return a default or cached value, if that is acceptable. See the code: `concurrency/CallServicesInParallel.java`.
- **Saga pattern:** a business action across several services (each with its own database) can't use one database transaction. A saga is a chain of local transactions. If a step fails, the saga runs compensating actions that undo the earlier steps, for example "refund the payment" and "release the stock".
  - **Choreography:** each service listens for events and reacts to them. Simple for a few steps, but hard to follow when there are many.
  - **Orchestration:** one coordinator tells each service what to do next. Easier to follow and to change.
  - Send the saga's events with the outbox pattern (question 13), so no event is lost.
- **Library:** Resilience4j has `Retry`, `CircuitBreaker`, `Bulkhead`, `TimeLimiter` and `RateLimiter`, and it works with Spring Boot.

### 27. What does a team gain by moving to Java 17, 21 or 25, and what can go wrong?

**Answer:**
- **What you gain** (the features are explained in [JavaVersions.md](JavaVersions.md)):
  - Shorter code: records, `switch` expressions, text blocks, pattern matching and sealed classes.
  - Virtual threads (Java 21) for services that spend most of their time waiting for databases and HTTP calls.
  - Better garbage collectors and better performance, often with no code change.
  - Security fixes and long-term support. Spring Boot 3 also needs Java 17 or newer.
- **What can go wrong:**
  - **Removed Java EE modules (Java 11):** JAXB, JAX-WS and `javax.annotation` are no longer in the JDK, so add them as libraries.
  - **`javax` becomes `jakarta` (Spring Boot 3):** packages such as `javax.persistence` and `javax.servlet` are now `jakarta.persistence` and `jakarta.servlet`, so imports and libraries must change.
  - **JDK internals are locked (Java 16 and 17):** old versions of libraries that use the JDK's internal classes (such as Lombok, Mockito and ByteBuddy) fail with errors like `InaccessibleObjectException`. Upgrade them. `--add-opens` is only a short-term fix.
  - **Build tools and plugins** (the Maven compiler and Surefire plugins, JaCoCo, Gradle) need versions that understand the new Java. And code compiled for a newer Java fails on an older JVM with `UnsupportedClassVersionError`.
  - **Removed or changed APIs:** the Nashorn JavaScript engine (removed in Java 15), the Security Manager (turned off for good in Java 24) and `Thread.stop()` (no longer works).
  - **Behaviour changes:** GC defaults, memory use in containers and speed can change, so test under real load.
- **How to move safely:** upgrade the libraries and build tools first, run `jdeps --jdk-internals` to find code that uses internal classes, move one LTS version at a time if you are far behind (8 → 11 → 17 → 21), run all the tests, and try it in a test environment under load before production.

### 28. What is structured concurrency?

**Answer:**
- **The problem:** with an `ExecutorService` or a `CompletableFuture`, if you start 3 subtasks and one fails, the other two keep running. Nothing ties them to the method that started them, so work is wasted and threads can leak.
- **The idea:** treat the subtasks as one unit, like a block of code. You start them inside a `StructuredTaskScope`, and the method can't leave the scope until they have all finished. If one fails, the scope cancels the others. Thread dumps also show which task started which.
- **It fits virtual threads:** each subtask runs in its own virtual thread.
- **Status:** it is still a preview feature in Java 25 (it has been a preview since Java 21, and the API changed along the way), so you need `--enable-preview`. Don't use it in production yet. Until it is final, use `CompletableFuture` (see `concurrency/CallServicesInParallel.java`) or an `ExecutorService`.
- **Example** (the Java 25 preview API; Java 21 used `new StructuredTaskScope.ShutdownOnFailure()` and `scope.join().throwIfFailed()`):

```java
try (var scope = StructuredTaskScope.open()) {
    Subtask<String> user = scope.fork(() -> findUser());
    Subtask<Integer> order = scope.fork(() -> fetchOrder());
    scope.join();      // waits for both to succeed; if one fails, it cancels the other and throws
    return new Response(user.get(), order.get());
}
```

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
