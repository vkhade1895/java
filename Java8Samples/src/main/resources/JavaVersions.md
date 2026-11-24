# Java 8 vs 11 vs 17 vs 21 vs 25

Every two years, Java releases a **long-term support (LTS)** version. Companies use these versions for real projects because they get updates for many years.

This page shows what each LTS version added. This project uses **Java 21**.

---

## Quick comparison

| Version | Main idea | Most important new features | Garbage collector (GC) |
|---|---|---|---|
| [Java 8](#java-8) (2014) | Write shorter code with small functions | Lambdas, streams, `Optional`, new date API | Default: Parallel GC. Metaspace replaced PermGen |
| [Java 11](#java-11) (2018) | Small helpful additions | `var`, `List.of`, new `String` methods, HTTP client | Default: G1 (from Java 9). ZGC added as a test version |
| [Java 17](#java-17) (2021) | Write less repeated code | Records, text blocks, `switch` expressions, sealed classes | Default: G1. ZGC and Shenandoah ready for real projects |
| [Java 21](#java-21) (2023) | Easier multithreading | Virtual threads, smarter `switch`, `getFirst()` and `getLast()` | Default: G1. New: generational ZGC (you turn it on) |
| [Java 25](#java-25) (2025) | Simpler programs | `main` without a class, code before `super()`, scoped values | Default: G1. ZGC is always generational. Generational Shenandoah ready for real projects |

Click a version to see its features and code examples.

**GC in short:** G1 has been the default since Java 9. Choose ZGC or Shenandoah when your app needs very short pauses, for example `java -XX:+UseZGC MyApp`. "Generational" means the GC cleans new objects more often than old ones, which saves work. More details: [Changes inside the JVM](#changes-inside-the-jvm).

---

## Old way vs new way

The same task, written the old way (Java 8) and the new way.

| Task | Old way (Java 8) | New way | Added in |
|---|---|---|---|
| [Make a small list](#listof-example) | `Arrays.asList(1, 2, 3)` | `List.of(1, 2, 3)` | Java 9 |
| [Make a data class](#record-example) | A long class with getters, `equals` and `hashCode` | `record Employee(String name) {}` | Java 16 |
| [Write text on many lines](#text-block-example) | `"line 1\n" + "line 2"` | A text block: `""" ... """` | Java 15 |
| [Get a value from `switch`](#switch-expression-example) | `switch` with `break` in every case | `switch` with `->` | Java 14 |
| [Check a type and cast it](#instanceof-example) | `instanceof`, then a cast `(String) o` | `o instanceof String s` | Java 16 |
| [Turn a stream into a list](#tolist-example) | `.collect(Collectors.toList())` | `.toList()` | Java 16 |
| [Get the last item of a list](#getfirst-and-getlast-example) | `list.get(list.size() - 1)` | `list.getLast()` | Java 21 |
| [Run 10,000 tasks that wait](#virtual-thread-example) | A thread pool with a few threads | One virtual thread per task | Java 21 |
| [Ignore a variable you don't use](#underscore-example) | `catch (Exception e)` and never use `e` | `catch (Exception _)` | Java 22 |
| [Write "Hello World"](#void-main-example) | A class with `static void main(String[] args)` | `void main()`, no class needed | Java 25 |

---

## Java 8

**Released 2014.** The biggest change in Java's history. You can write small functions (lambdas) and work on lists with streams.

| Feature | What it does | Added in | Example |
|---|---|---|---|
| Lambda | Write a small function in one line | Java 8 | [See example](#lambda-example) |
| Method reference | A shorter lambda, like `String::length` | Java 8 | [See example](#lambda-example) |
| Stream API | Filter, change and collect a list in steps | Java 8 | [See example](#stream-example) |
| `Optional` | A box that may be empty. Use it instead of `null` | Java 8 | [See example](#optional-example) |
| Default method | An interface method that has code | Java 8 | [See example](#default-method-example) |
| New date API | `LocalDate` and `LocalDateTime`, easier than `Date` | Java 8 | [See example](#date-example) |
| `CompletableFuture` | Run a task in the background | Java 8 | [See example](#completablefuture-example) |

### Lambda example

```java
// Before Java 8: a whole anonymous class just to compare two strings
Collections.sort(names, new Comparator<String>() {
    @Override
    public int compare(String a, String b) {
        return a.compareTo(b);
    }
});

// Java 8: the same thing with a lambda
names.sort((a, b) -> a.compareTo(b));

// Even shorter: a method reference
names.sort(String::compareTo);
```

### Stream example

```java
List<String> longNames = names.stream()
        .filter(name -> name.length() > 3)   // keep names longer than 3 letters
        .map(String::toUpperCase)            // change each name to upper case
        .collect(Collectors.toList());       // put the result in a list
```

### Optional example

```java
Optional<String> first = names.stream().findFirst();
String value = first.orElse("none");   // "none" if the list was empty
```

### Default method example

```java
interface Greeter {
    void greet();

    default void greetTwice() {   // this method has code
        greet();
        greet();
    }
}
```

### Date example

```java
LocalDate today = LocalDate.now();
LocalDate nextWeek = today.plusDays(7);
```

### CompletableFuture example

```java
CompletableFuture<Integer> price = CompletableFuture.supplyAsync(() -> 100)
        .thenApply(p -> p * 2);
System.out.println(price.join());   // 200
```

---

## Java 11

**Released 2018.** Many small features that make daily code shorter. This section also includes features from Java 9 and 10.

| Feature | What it does | Added in | Example |
|---|---|---|---|
| `var` | The compiler finds the type for you | Java 10 | [See example](#var-example) |
| `List.of`, `Set.of`, `Map.of` | Make a small collection in one line. You can't change it later | Java 9 | [See example](#listof-example) |
| New `String` methods | `isBlank()`, `strip()`, `lines()`, `repeat()` | Java 11 | [See example](#string-methods-example) |
| HTTP client | Call a web URL without an extra library | Java 11 | [See example](#http-client-example) |
| Run a file directly | Run `java Hello.java` without compiling first | Java 11 | [See example](#run-a-file-example) |

### var example

```java
var names = new ArrayList<String>();   // Java knows names is an ArrayList<String>
```

The type cannot change later. With `var`, you just don't write the type name.

### List.of example

```java
List<Integer> numbers = List.of(1, 2, 3);
Map<String, Integer> ages = Map.of("Asha", 30, "Ravi", 25);
```

### String methods example

```java
"   ".isBlank();            // true
"  hi  ".strip();           // "hi"
"ab".repeat(3);             // "ababab"
"a\nb\nc".lines().count();  // 3
```

### HTTP client example

```java
HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder(URI.create("https://example.com")).build();
HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
```

### Run a file example

```bash
java Hello.java
```

---

## Java 17

**Released 2021.** Write less repeated code. This section also includes features from Java 12 to 16.

| Feature | What it does | Added in | Example |
|---|---|---|---|
| Records | A data class in one line | Java 16 | [See example](#record-example) |
| Text blocks | Write text on many lines easily | Java 15 | [See example](#text-block-example) |
| `switch` expressions | A `switch` that gives back a value | Java 14 | [See example](#switch-expression-example) |
| `instanceof` with a variable | Check the type and cast in one step | Java 16 | [See example](#instanceof-example) |
| Sealed classes | Choose exactly which classes can extend yours | Java 17 | [See example](#sealed-class-example) |
| `.toList()` on streams | A shorter way to collect a stream | Java 16 | [See example](#tolist-example) |
| Better error messages | A `NullPointerException` now says what was null | Java 14 | No code needed |

### Record example

```java
record Employee(String name, int salary) { }

Employee e = new Employee("Asha", 100);
e.name();   // "Asha"
```

Java writes the constructor, the getters, `equals`, `hashCode` and `toString` for you.

### Text block example

```java
String json = """
        {
          "name": "Asha",
          "role": "developer"
        }
        """;
```

### Switch expression example

```java
String type = switch (day) {
    case SATURDAY, SUNDAY -> "Weekend";
    default -> "Weekday";
};
```

No `break` is needed.

### instanceof example

```java
if (obj instanceof String s) {
    System.out.println(s.length());   // s is already a String, no cast needed
}
```

### Sealed class example

```java
sealed interface Shape permits Circle, Square { }   // only Circle and Square can be a Shape
record Circle(double radius) implements Shape { }
record Square(double side) implements Shape { }
```

### toList example

```java
List<String> upper = names.stream().map(String::toUpperCase).toList();
```

---

## Java 21

**Released 2023.** Easier multithreading, and a smarter `switch`. This section also includes features from Java 18 to 20.

| Feature | What it does | Added in | Example |
|---|---|---|---|
| Virtual threads | Threads that use very little memory, for tasks that wait (like database calls) | Java 21 | [See example](#virtual-thread-example) |
| Smarter `switch` | Check an object's type and read a record's fields | Java 21 | [See example](#pattern-matching-switch-example) |
| `getFirst()`, `getLast()` | Get the first or last item of a list in one call | Java 21 | [See example](#getfirst-and-getlast-example) |

### Virtual thread example

```java
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    for (int i = 0; i < 10_000; i++) {
        executor.submit(() -> {
            Thread.sleep(1000);   // wait for 1 second, like a slow database call
            return "done";
        });
    }
}   // all 10,000 tasks finish in about 1 second
```

A normal thread uses a lot of memory, so a program can only have a few thousand of them. A virtual thread uses very little memory, so a program can have millions.

**Watch out:** in Java 21, a virtual thread that waits inside a `synchronized` block keeps its real thread busy, so other virtual threads can't use that thread. Use `ReentrantLock` instead. Java 24 fixed this.

### Pattern matching switch example

```java
double area(Shape shape) {
    return switch (shape) {
        case Circle c -> Math.PI * c.radius() * c.radius();
        case Square(double side) -> side * side;   // reads the side field of the record
    };
}
```

No `default` is needed. The compiler knows that a `Shape` can only be a `Circle` or a `Square`.

```java
String describe(Object obj) {
    return switch (obj) {
        case null -> "null";
        case Integer i when i > 100 -> "big number";   // "when" adds a condition
        case Integer i -> "number";
        case String s -> "text";
        default -> "something else";
    };
}
```

### getFirst and getLast example

```java
List<Integer> list = List.of(1, 2, 3);
list.getFirst();   // 1
list.getLast();    // 3
list.reversed();   // [3, 2, 1]
```

---

## Java 25

**Released 2025.** Simpler programs. This section also includes features from Java 22 to 24.

| Feature | What it does | Added in | Example |
|---|---|---|---|
| `main` without a class | Write a small program in three lines | Java 25 | [See example](#void-main-example) |
| `_` for unused variables | Use `_` as the name of a variable you don't need | Java 22 | [See example](#underscore-example) |
| Stream gatherers | Add your own step to a stream, like making batches | Java 24 | [See example](#gatherer-example) |
| Code before `super()` | Check values before calling the parent constructor | Java 25 | [See example](#code-before-super-example) |
| Scoped values | Share a value (like a request ID) with called methods, without a parameter | Java 25 | [See example](#scoped-value-example) |
| Module import | Import a whole module in one line | Java 25 | [See example](#module-import-example) |

### void main example

```java
void main() {
    IO.println("Hello, Java 25!");
}
```

Save it as `Hello.java` and run `java Hello.java`.

### Underscore example

```java
try {
    Integer.parseInt(text);
} catch (NumberFormatException _) {   // we don't need the exception
    System.out.println("Not a number");
}

map.forEach((_, value) -> System.out.println(value));   // we don't need the key
```

### Gatherer example

```java
List<List<Integer>> batches = Stream.of(1, 2, 3, 4, 5, 6, 7)
        .gather(Gatherers.windowFixed(3))
        .toList();   // [[1, 2, 3], [4, 5, 6], [7]]
```

### Code before super example

```java
class Employee extends Person {
    Employee(String name) {
        if (name == null || name.isBlank()) {   // allowed before super() from Java 25
            throw new IllegalArgumentException("name is required");
        }
        super(name);
    }
}
```

### Scoped value example

```java
static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();

public static void main(String[] args) {
    ScopedValue.where(REQUEST_ID, "req-42").run(() -> handleRequest());
}

static void handleRequest() {
    System.out.println(REQUEST_ID.get());   // prints "req-42"
}
```

### Module import example

```java
import module java.base;   // imports List, Map, Files, Path and more at once
```

---

## Changes inside the JVM

You don't write code for these, but interviewers sometimes ask about them.

| Version | Change |
|---|---|
| Java 8 | Parallel GC is the default. PermGen (the memory for class data) was replaced by Metaspace |
| Java 9 | G1 became the default garbage collector |
| Java 11 | ZGC added as a test version (experimental) |
| Java 15 | ZGC and Shenandoah, two GCs with very short pauses, are ready for real projects |
| Java 18 | UTF-8 is the default text encoding everywhere |
| Java 21 | Generational ZGC added. Turn it on with `-XX:+UseZGC -XX:+ZGenerational` |
| Java 23 | Generational mode became the default for ZGC |
| Java 24 | ZGC is always generational (the old mode was removed) |
| Java 24 | A virtual thread waiting inside `synchronized` no longer keeps its real thread busy |
| Java 25 | Generational Shenandoah is ready for real projects |
| Java 25 | An option lets objects use less memory (`-XX:+UseCompactObjectHeaders`) |

## Removed features

| Version | Removed |
|---|---|
| Java 11 | Java EE parts (JAXB, JAX-WS). Add them as libraries if you need them |
| Java 15 | The Nashorn JavaScript engine |
| Java 16-17 | Code can no longer use the JDK's internal classes. Some old libraries break |
| Java 24 | The Security Manager |

## Preview features

A **preview** feature is a test version. It can still change. It works only if you turn it on:

```bash
javac --release 25 --enable-preview Main.java
java --enable-preview Main
```

Don't use preview features in real projects. Structured concurrency is still a preview feature in Java 25 (question 28 in [Questions.md](Questions.md) explains it).

---

## Where to find examples in this project

| Feature | File |
|---|---|
| Lambdas | [LambdaDemo.java](../java/functional/LambdaDemo.java) |
| Method references | [MethodReferenceDemo.java](../java/functional/MethodReferenceDemo.java) |
| Default and static methods | [Calculator.java](../java/functional/Calculator.java) |
| Streams | [HighestPaidByDepartment.java](../java/streams/HighestPaidByDepartment.java) |
| `Optional` | [SecondHighest.java](../java/arrays/SecondHighest.java) |
| `CompletableFuture` | [ParallelProductAndSum.java](../java/concurrency/ParallelProductAndSum.java) |
| `CompletableFuture` with a timeout and fallbacks | [CallServicesInParallel.java](../java/concurrency/CallServicesInParallel.java) |
| `List.of` and `Set.of` | [CountVowels.java](../java/strings/CountVowels.java) |
| `var` | [NewerSyntax.java](../java/modern/NewerSyntax.java) |
| `String.repeat` | [ReplaceWithGrowingDollars.java](../java/strings/ReplaceWithGrowingDollars.java) |
| Switch expressions and text blocks | [NewerSyntax.java](../java/modern/NewerSyntax.java) |
| Records | [Employee.java](../java/model/Employee.java) |
| Records that check their values (compact constructor) | [RecordWithChecks.java](../java/modern/RecordWithChecks.java) |
| `.toList()` | [FlattenListOfLists.java](../java/streams/FlattenListOfLists.java) |
| `instanceof` with a variable | [ImmutableKey.java](../java/oop/ImmutableKey.java) |
| Sealed classes, records and smarter `switch` | [ShapeArea.java](../java/modern/ShapeArea.java) |
| Virtual threads | [VirtualThreadsDemo.java](../java/concurrency/VirtualThreadsDemo.java) |
| `getFirst()`, `getLast()` and `reversed()` | [SequencedCollectionsDemo.java](../java/modern/SequencedCollectionsDemo.java) |

Not in the project code yet: the features from Java 22 to 25, because this project uses Java 21. The [Java 25](#java-25) section above explains them with examples.

---

## Interview questions

**Why should a team move from Java 8 to Java 17 or 21?**
The code is shorter, the program runs faster, and it gets security fixes. Also, Spring Boot 3 needs Java 17 or newer. For what can go wrong during the move, see question 27 in [Questions.md](Questions.md).

**What is an LTS version?**
A version that gets updates for many years. The LTS versions are 8, 11, 17, 21 and 25.

**Does `var` make Java a dynamic language?**
No. The compiler decides the type, and it cannot change later. With `var`, you just don't write the type name.

**When should I use a record instead of a class?**
Use a record for simple data that does not change. Use a normal class when the data changes or when you need inheritance.

**When are virtual threads not useful?**
When the task uses the CPU all the time, like a big calculation. They help when tasks spend most of their time waiting.

**What is a preview feature?**
A test version of a new feature. You must turn it on with `--enable-preview`, and it can still change.
