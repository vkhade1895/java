# Part 1: Advanced Java & Backend Concepts (8–10 Years Experience)

### 1. How does the Java Memory Model (JMM) relate to multi-threading and the `volatile` keyword?

**Answer:** The JMM defines how threads interact through memory, ensuring visibility and ordering via the happens-before relationship. The `volatile` keyword guarantees that a variable's value is always read from and written directly to main memory, preventing thread-caching issues, but it does not provide atomicity (unlike `synchronized` or `AtomicInteger`).

### 2. Explain the internal working of `ThreadPoolExecutor` and how active vs. dead threads are managed.

**Answer:** A `ThreadPoolExecutor` uses a core pool size, maximum pool size, work queue, and a rejection policy. Worker threads encapsulate tasks within a `Worker` class (which extends `Runnable` and wraps a thread). They continuously poll the blocking queue using `getTask()`. If a worker throws an unexpected exception or the pool scales down past keep-alive limits, the thread terminates and is cleaned up by the executor framework.

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

**Answer:** In the Cache-Aside pattern, the application checks Redis first. On a cache miss, it reads from the primary database, writes the result to Redis with a Time-To-Live (TTL), and returns the data. To invalidate the cache across multiple microservice instances when data changes, use Spring's `@CacheEvict(value = "cacheName", key = "#id")`, or publish a Kafka/Pub-Sub event so all running instances evict their local cache simultaneously.

### 9. How do `@Transactional` isolation levels work, and how do you avoid Dirty Reads and Phantom Reads?

**Answer:** SQL defines four isolation levels:
- **Read Uncommitted:** Allows reading uncommitted data (Dirty Reads).
- **Read Committed:** Prevents dirty reads; only reads committed data (default in PostgreSQL/Oracle).
- **Repeatable Read:** Guarantees repeated reads of the same row within a transaction return identical results (default in MySQL InnoDB).
- **Serializable:** Strictest level; executes transactions sequentially, preventing Phantom Reads (new rows inserted by another transaction that match the query criteria).

Higher isolation levels increase consistency but reduce concurrent write throughput. Best practice: default to `READ_COMMITTED` for high throughput, and use optimistic locking (`@Version`) or Redis locks for concurrent financial/inventory transactions.

### 10. What is the difference between Optimistic and Pessimistic Locking in JPA/Hibernate, and when is each appropriate?

**Answer:**
- **Pessimistic Locking** (`LockModeType.PESSIMISTIC_WRITE`): Locks the row at the database level (`SELECT ... FOR UPDATE`), blocking other transactions until commit. Best for high-contention, critical write paths (e.g., inventory count deduction).
- **Optimistic Locking** (`@Version` field): Does not lock rows upfront. On update, Hibernate checks if the version column matches; if it changed due to another transaction, it throws `OptimisticLockException`. Best for read-heavy architectures with low write conflicts.

### 11. How do Java Garbage Collectors (G1GC vs. ZGC) differ, and when should you choose ZGC in high-throughput backend systems?

**Answer:** G1GC partitions the heap into equal-sized regions and balances throughput with pause times (aiming for tens of milliseconds). ZGC is a scalable, low-latency garbage collector that performs almost all GC work concurrently (including marking and object relocation), guaranteeing sub-millisecond maximum pause times regardless of heap size, even multiple terabytes. Choose ZGC for low-latency fintech, trading, or payment microservices where GC stop-the-world pauses directly degrade API SLAs.

### 12. How does Kafka consumer group rebalancing work, and how can cooperative sticky assignors prevent latency spikes?

**Answer:** When a consumer joins or leaves a group (or partitions are added), Kafka rebalances partition assignments. The older "eager" protocol revokes all partitions from all consumers, halting consumption cluster-wide until reassignment finishes. The "cooperative sticky assignor" performs incremental rebalancing: consumers continue processing untouched partitions and only release/acquire the specific partitions being moved, avoiding stop-the-world lag.

### 13. What is the Outbox Pattern in microservices, and how does it solve distributed dual-write inconsistencies?

**Answer:** When a service must update its database and publish an event to Kafka, doing both independently can fail midway (the dual-write problem). The Outbox Pattern writes both the business entity and an "outbox" event record into the local database within the same ACID transaction. A background process (or Debezium via Change Data Capture) polls the outbox table and streams the messages reliably to Kafka with at-least-once delivery guarantees.

---

# Part 2: Industry Coding Questions (Frequently Asked in India / Bengaluru / Pune)

### Question 1: Find the second highest element in an array using Java 8 Streams

**Problem:** Given an array containing duplicate elements, find the second highest number using only the Stream API.

```java
int[] numbers = {5, 2, 9, 9, 7, 3, 1};
Integer secondHighest = Arrays.stream(numbers)
        .boxed()
        .distinct()
        .sorted(Comparator.reverseOrder())
        .skip(1)
        .findFirst()
        .orElse(null);
System.out.println("Second Highest: " + secondHighest);
```

### Question 2: Find the first non-repeated character in a string using Java Streams

**Problem:** Given a string, locate the first character that does not repeat.

```java
String input = "swiss";
Character result = input.chars()
        .mapToObj(c -> (char) c)
        .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
        .entrySet().stream()
        .filter(entry -> entry.getValue() == 1)
        .map(Map.Entry::getKey)
        .findFirst()
        .orElse(null);
System.out.println("First non-repeated: " + result);
```

### Question 3: Separate 0s and 1s in an array

**Problem:** Given an array consisting only of 0s and 1s, rearrange the array in-place so that all 0s are on the left side and all 1s are on the right side.

```java
public static void sortZerosAndOnes(int[] arr) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        while (arr[left] == 0 && left < right) left++;
        while (arr[right] == 1 && left < right) right--;
        if (left < right) {
            arr[left] = 0;
            arr[right] = 1;
            left++;
            right--;
        }
    }
}
```

### Question 4: Find duplicate characters in a list of strings using Streams

**Problem:** Find all characters that appear more than once across a list of string entries.

```java
List<String> words = Arrays.asList("apple", "banana", "cherry");
Set<Character> duplicates = words.stream()
        .flatMapToInt(CharSequence::chars)
        .mapToObj(c -> (char) c)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .entrySet().stream()
        .filter(e -> e.getValue() > 1)
        .map(Map.Entry::getKey)
        .collect(Collectors.toSet());
```

### Question 5: Find the length of the longest substring without repeating characters

**Problem:** Given a string `s`, find the length of the longest substring containing all distinct characters.

```java
public static int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> lastSeen = new HashMap<>();
    int maxLength = 0, left = 0;
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        if (lastSeen.containsKey(c)) {
            // Move left pointer past the last occurrence of the duplicate character
            left = Math.max(left, lastSeen.get(c) + 1);
        }
        lastSeen.put(c, right);
        maxLength = Math.max(maxLength, right - left + 1);
    }
    return maxLength;
}
```

### Question 6: Group anagrams from an array of strings using Java Streams

**Problem:** Given an array of strings, group the anagrams together.

```java
public static List<List<String>> groupAnagrams(String[] strs) {
    return new ArrayList<>(
            Arrays.stream(strs)
                    .collect(Collectors.groupingBy(str -> {
                        char[] chars = str.toCharArray();
                        Arrays.sort(chars);
                        return new String(chars);
                    }))
                    .values()
    );
}
```

### Question 7: Merge two sorted arrays without using extra space

**Problem:** Given two sorted integer arrays `nums1` (with `m` real elements) and `nums2` (with `n` elements), merge `nums2` into `nums1` in-place.

```java
public static void merge(int[] nums1, int m, int[] nums2, int n) {
    int p1 = m - 1;       // last element of nums1's original data
    int p2 = n - 1;       // last element of nums2
    int p = m + n - 1;    // end of the merged array
    while (p1 >= 0 && p2 >= 0) {
        if (nums1[p1] > nums2[p2]) {
            nums1[p--] = nums1[p1--];
        } else {
            nums1[p--] = nums2[p2--];
        }
    }
    while (p2 >= 0) {
        nums1[p--] = nums2[p2--];
    }
}
```

### Question 8: Find the maximum subarray sum (Kadane's Algorithm)

**Problem:** Given an integer array `nums`, find the contiguous subarray with the largest sum and return that sum.

```java
public static int maxSubArray(int[] nums) {
    int maxSoFar = nums[0];
    int currentMax = nums[0];
    for (int i = 1; i < nums.length; i++) {
        currentMax = Math.max(nums[i], currentMax + nums[i]);
        maxSoFar = Math.max(maxSoFar, currentMax);
    }
    return maxSoFar;
}
```

### Question 9: Find the top K frequent elements in an array

**Problem:** Given an integer array `nums` and an integer `k`, return the `k` most frequent elements.

```java
public static List<Integer> topKFrequent(int[] nums, int k) {
    Map<Integer, Long> freqMap = Arrays.stream(nums)
            .boxed()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    PriorityQueue<Map.Entry<Integer, Long>> minHeap =
            new PriorityQueue<>(Comparator.comparingLong(Map.Entry::getValue));

    for (Map.Entry<Integer, Long> entry : freqMap.entrySet()) {
        minHeap.offer(entry);
        if (minHeap.size() > k) {
            minHeap.poll();
        }
    }
    return minHeap.stream().map(Map.Entry::getKey).collect(Collectors.toList());
}
```

### Question 10: Two Sum — find indices that add up to a target

**Problem:** Given an array of integers `nums` and an integer `target`, return the indices of the two numbers that add up to `target`, in O(n) time.

```java
public static int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            return new int[]{map.get(complement), i};
        }
        map.put(nums[i], i);
    }
    return new int[]{};
}
```

### Question 11: Longest common prefix in an array of strings

**Problem:** Write a function to find the longest common prefix string amongst an array of strings.

```java
public static String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    String prefix = strs[0];
    for (int i = 1; i < strs.length; i++) {
        while (strs[i].indexOf(prefix) != 0) {
            prefix = prefix.substring(0, prefix.length() - 1);
            if (prefix.isEmpty()) return "";
        }
    }
    return prefix;
}
```

### Question 12: Flatten a nested list of integers using Java Streams

**Problem:** Given a `List<List<Integer>>`, flatten the structure into a single sorted list containing only distinct numbers.

```java
List<List<Integer>> nestedList = Arrays.asList(
        Arrays.asList(1, 2, 3),
        Arrays.asList(3, 4, 5),
        Arrays.asList(5, 6, 7)
);
List<Integer> flattened = nestedList.stream()
        .flatMap(List::stream)
        .distinct()
        .sorted()
        .collect(Collectors.toList());
```

---

# Part 3: System Design Scenarios

### 1. Design a Scalable, Distributed Rate Limiter for a High-Traffic API / Payment Gateway

**Scenario:** Your API handles millions of requests per second and needs to throttle clients exceeding a specific quota (e.g., 100 requests/minute) to prevent overload, across multiple app instances.

**Key Solution Components:**
- Use the Token Bucket or Sliding Window Log algorithm.
- Track request counts in a distributed store (Redis) using atomic commands (`INCR`, `EXPIRE`) or Lua scripts, to prevent race conditions across server nodes.
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
- **Distributed lock & state:** Acquire a Redis lock on the `Idempotency-Key`, and insert a record with status `PAYMENT_PENDING` in Redis with a short TTL (e.g., 2 minutes).
- **Database validation:** Query the transaction database for the unique key — if already `COMPLETED`, immediately return the cached receipt.
- **Atomic state transition:** Execute the payment through the third-party gateway, update the database record within a local transaction, update Redis to `COMPLETED`, and release the lock.

### 6. Design a Distributed URL Shortener (Like Bitly/TinyURL)

**Scenario:** Build a URL shortener service that handles 10,000 writes/sec and 100,000 reads/sec with auto-generated 7-character aliases.

**Key Solution Components:**
- **Encoding algorithm:** Base62 encoding (`[a-z, A-Z, 0-9]`) over a 64-bit distributed auto-increment ID (e.g., Redis range allocation or a Snowflake ID generator) to generate 7-character hashes (62⁷ ≈ 3.5 trillion unique URLs).
- **Read-heavy caching:** Place Redis in front of the database using the Cache-Aside pattern (90%+ of read traffic served directly from memory with LRU eviction).
- **Database strategy:** Use a NoSQL document/key-value store (like DynamoDB or MongoDB), or a partitioned PostgreSQL instance sharded on the hashed key.
- **Redirection:** Return `HTTP 302 Found` (temporary redirect) so click analytics are captured on every request, without permanent browser caching.
