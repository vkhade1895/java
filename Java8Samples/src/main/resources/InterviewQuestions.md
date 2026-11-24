# Java Interview Questions (from this project)

- [Part 1: Java concepts](#part-1-java-concepts): question + short answer
- [Part 2: Coding questions](#part-2-coding-questions): the problem in simple words, an example, and how to solve it
- [Part 3: Spot the bug](#part-3-spot-the-bug): mistakes that were in this repo's code, now fixed (good practice)
- [Part 4: Advanced topics and system design](#part-4-advanced-topics-and-system-design): short version of [Questions.md](Questions.md)
- [JavaVersions.md](JavaVersions.md): what changed in Java 8, 11, 17, 21 and 25, with code

The code is in `Java8Samples/src/main/java/`:
`oop`, `functional` (interfaces and lambdas), `modern` (newer Java features, from Java 10 to 21), `streams`, `arrays`, `strings`, `datastructures` (stack, queue, linked list, tree, LRU cache), `numbers`, `concurrency`, `exceptions`, `design` (SOLID and design patterns) and `model` (shared records). [README.md](../../../README.md) lists every class with its keywords and the date it was first written.

---

## Part 1: Java concepts

### 1.1 OOP: inheritance, overriding, abstract classes

**Q1. What is inheritance?**
A class reuses another class with `extends`. `Circle extends Shape`, so `Circle` gets all of `Shape`'s methods and can change them. (`oop/Shape.java`, `oop/Circle.java`)

**Q2. What is method overriding? What does `Shape s = new Circle(); s.m1();` print?**
Overriding means a child class writes its own version of a parent method (same name, same parameters). It prints `inside circle`, because Java picks the method of the real object at runtime. This is called runtime polymorphism. (`oop/InheritanceDemo.java` runs this example.)

**Q3. Overloading vs overriding?**

| | Overloading | Overriding |
|---|---|---|
| Where | same class | parent and child class |
| Parameters | must be different | must be the same |
| Decided at | compile time | runtime |
| Example here | `call(String)` and `call(Object)` in `oop/MethodOverloading` | `m1()` in `Shape` and `Circle` |

**Q4. What are the rules for overriding?**
- Same name and parameters. The return type is the same or a subtype.
- Access can't get stricter (`protected` to `public` is fine, `public` to `private` is not).
- Can't throw new or broader checked exceptions.
- `final`, `static` and `private` methods can't be overridden (a `static` method with the same signature only hides the parent's).
- Add `@Override` so the compiler catches mistakes.

**Q5. What is an abstract class?**
A class marked `abstract` that you can't create with `new`. It can have abstract methods (no body), normal methods, fields and constructors. A child class must implement every abstract method or be abstract itself. `ConcreteChild` implements `m1()` and `m3()` from `AbstractParent` and gets `display()` for free.

**Q6. Can an abstract class have a constructor?**
Yes. It runs when a child object is created (through `super()`), usually to set common fields.

**Q7. Abstract class vs interface?**

| Abstract class | Interface |
|---|---|
| Can have instance fields and constructors | Only constants, no constructors |
| A class can extend only one | A class can implement many |
| Methods can have any access modifier | Methods are public (private helpers allowed since Java 9) |
| Use for shared code and state in a family of classes | Use for a capability or contract ("can do X") |

**Q8. Can a class extend a class and implement an interface at the same time?**
Yes. `extends` comes first: `class ConcreteChild extends AbstractParent implements Calculator`.

**Q9. Why can't a Java class extend two classes?**
To avoid the "diamond problem": if both parents have the same method, which one wins? Interfaces can still clash through default methods. Then the class must override the method and pick one, for example `A.super.hello()`.

### 1.2 Interfaces (Java 8+)

**Q10. What is a default method, and why was it added?**
A method with a body inside an interface, like `default double sqrt(int a)` in `functional/Calculator.java`. Java 8 added them so old interfaces (like `Collection`) could get new methods (like `stream()`) without breaking every class that implements them.

**Q11. What is a static method in an interface? How do you call it?**
A helper that belongs to the interface, like `static int positive(int a)`. Call it only through the interface name: `Calculator.positive(10)`. It is not inherited, so `obj.positive(10)` or `InterfaceMethodsDemo.positive(10)` won't compile.

**Q12. Can a class override a default method? A static interface method?**
Default: yes. Static: no, because it isn't inherited. (A class can have its own static method with the same name, but it's a separate method.)

**Q13. What is a functional interface?**
An interface with exactly one abstract method. Default and static methods don't count, and neither do methods that `Object` already has (that's why `Comparator`, which declares `equals`, is still functional). Examples here: `MathOperation` (`operate`), `Calculator` (`calculate`) and `Checker` inside `FunctionalInterfaceDemo` (`check`). You can use a lambda anywhere a functional interface is expected.

**Q14. What does `@FunctionalInterface` do? Is it required?**
It's not required. It makes the compiler check that there is exactly one abstract method, so adding a second one becomes a compile error.

**Q15. Name the main built-in functional interfaces.**

| Interface | Takes → returns | Method |
|---|---|---|
| `Function<T, R>` | T → R | `apply` |
| `Predicate<T>` | T → boolean | `test` |
| `Consumer<T>` | T → nothing | `accept` |
| `Supplier<T>` | nothing → T | `get` |
| `BiFunction<T, U, R>` | T, U → R | `apply` |
| `UnaryOperator<T>` / `BinaryOperator<T>` | T → T / T, T → T | `apply` |

Older interfaces that are also functional: `Runnable`, `Callable`, `Comparator`.

**Q16. How does `functional/FunctionalInterfaceDemo` use its `Checker` interface (abstract `check()`, default `speed()`, static `type()`)?**
`Checker checker = () -> System.out.println("Calling abstract");` makes the lambda the body of `check()`. `checker.speed()` runs the default method, and `Checker.type()` runs the static one.

### 1.3 Lambdas and method references

**Q17. What is a lambda?**
A short way to implement a functional interface, like `(a, b) -> a * b`. A 6-line anonymous `Comparator` class becomes one line: `myList.sort((o1, o2) -> o1.compareTo(o2));`. `LambdaDemo` shows both (its lambda sorts in reverse with `o2.compareTo(o1)`).

**Q18. Lambda vs anonymous class?**
- A lambda only works for functional interfaces. An anonymous class can implement any interface or abstract class, with many methods and its own fields.
- Inside a lambda, `this` means the surrounding object. Inside an anonymous class, it means the anonymous object.
- Lambdas don't create an extra `.class` file at compile time (they use `invokedynamic`).

**Q19. What is "effectively final"?**
A local variable used inside a lambda must never change after it's set, even without the `final` keyword. The lambda captures a copy of the value, so changing it later would be confusing. Fields can be changed (but watch thread safety).

**Q20. What is a method reference? Name the 4 kinds.**
A shorter lambda that only calls an existing method.

| Kind | Examples in this repo |
|---|---|
| Static method | `Integer::parseInt` (MethodReferenceDemo), `Integer::sum` (LambdaDemo) |
| Method of one specific object | `vowels::contains` (CountVowels), `seen::add` (UniqueCharactersInOrder) |
| Method of any object of a type | `String::toLowerCase`, `String::length`, `Employee::salary` |
| Constructor | `LinkedHashMap::new` (SortMapByKeyAndValue) |

**Q21. `MathOperation` returns `double`. How does `Integer::sum` (which returns `int`) fit?**
Java widens the `int` result to `double` automatically.

**Q22. Why is division written as `(a, b) -> (double) a / b`?**
`a / b` on two ints is integer division: `5 / 2` is `2`. Casting one side to `double` first gives `2.5`.

### 1.4 Records (Java 16+)

**Q23. What is a record?**
A short way to write a data class: `record Department(String name, int id) {}`. Java writes the constructor, the getters (`name()`, `id()`, with no `get` prefix), `equals()`, `hashCode()` and `toString()` for you. (`model/Employee.java`, `model/Department.java`, `Worker` in `streams/SalaryStatistics.java`)

**Q24. Are records immutable?**
The fields are `private final` and there are no setters. But it's only shallow: if a field is a `List`, the list's contents can still change. Copy it in the constructor (`List.copyOf(...)`) to be safe.

**Q25. Can a record extend a class? Implement an interface? Add methods?**
It can't extend anything (it already extends `java.lang.Record` and is `final`). It can implement interfaces and add methods and static fields, but no extra instance fields.

**Q26. What is a compact constructor?**
A constructor without the parameter list, used for checks ([RecordWithChecks.java](../java/modern/RecordWithChecks.java)):

```java
record Department(String name, int id) {
    Department {
        if (id < 0) throw new IllegalArgumentException("id must be >= 0");
    }
}
```

**Q27. Why can `Department` be the key in `groupingBy(Employee::department, ...)` (streams/HighestPaidByDepartment)?**
`groupingBy` puts the keys in a `HashMap`, which needs `equals()` and `hashCode()`. Records generate both from all fields, so two separate `new Department("IT", 1)` objects count as the same key. A normal class without `equals`/`hashCode` would make a separate group for every object.

### 1.5 Streams

**Q28. What is a stream?**
A pipeline that processes data from a source (a list, an array, a string) step by step: source → intermediate operations → one terminal operation. It doesn't store data and doesn't change the source.

**Q29. Intermediate vs terminal operations?**
- Intermediate operations are lazy and return a new stream: `filter`, `map`, `flatMap`, `distinct`, `sorted`, `skip`, `limit`, `peek`, `boxed`, `mapToInt`, `mapToObj`.
- Terminal operations start the work and give a result: `collect`, `toList`, `forEach`, `reduce`, `count`, `findFirst`, `anyMatch`, `max`, `min`, `average`, `sum`.

**Q30. What does "lazy" mean?**
Nothing runs until a terminal operation is called. Then elements go one by one through the whole pipeline, and short-circuit operations like `findFirst` or `anyMatch` stop early.

**Q31. Can you reuse a stream?**
No. After a terminal operation it's used up. Using it again throws `IllegalStateException: stream has already been operated upon or closed`.

**Q32. `map` vs `flatMap`?**
`map`: one element in, one element out (a word becomes its length). `flatMap`: one element in, a stream out, and all those streams are joined into one. Use it to flatten `List<List<String>>` into `List<String>`: `lists.stream().flatMap(List::stream)` (streams/FlattenListOfLists).

**Q33. How does `reduce` work? What is the identity?**
It combines all elements into one value. `reduce(0, (a, b) -> a + b)` is a sum and `reduce(1, (a, b) -> a * b)` is a product (streams/ReduceExamples). The identity is the starting value and must not change the answer: 0 for a sum, 1 for a product. (See bug B1.)

**Q34. `Stream<Integer>` vs `IntStream`?**
`IntStream` holds primitive `int`s (no boxing, so it's faster) and has `sum()`, `average()`, `max()` and `range()`. Convert with `mapToInt(e -> e)`, and go back with `boxed()`.

**Q35. Why does `average()` return `OptionalDouble`?**
The stream could be empty, and then there is no average. Use `orElse(0)`, or `getAsDouble()` if you're sure it isn't empty.

**Q36. `IntStream.range(1, 10)` vs `rangeClosed(1, 10)`?**
`range` stops before the end (1 to 9). `rangeClosed` includes the end (1 to 10).

**Q37. Why write `str.chars().mapToObj(c -> (char) c)`?**
`chars()` gives an `IntStream` of character codes. The cast turns them back into characters, so you can group, filter or print them as letters.

**Q38. `findFirst` vs `findAny`?**
`findFirst` returns the first element in order. `findAny` can return any element, which is faster in parallel streams.

**Q39. `stream.toList()` vs `collect(Collectors.toList())`?**
`toList()` (Java 16) returns a list you can't modify. `Collectors.toList()` gives a normal `ArrayList` in practice, but Java doesn't promise that.

**Q40. What is `peek` for?**
To look at elements as they pass, mostly for debugging. It only runs when a terminal operation runs.

**Q41. How do you sort with streams?**
- Natural order: `sorted()`
- Reverse order: `sorted(Comparator.reverseOrder())`
- By a field: `sorted(Comparator.comparing(Employee::salary))`
- By a field, highest first: `Comparator.comparing(Employee::salary).reversed()`
- Tie-breaker: `.thenComparing(Employee::name)`

**Q42. How do you find the longest string in a list?**
`list.stream().max(Comparator.comparing(String::length))` returns an `Optional<String>` (streams/LongestAndShortestString). For numbers, use `mapToInt(...).max()`.

**Q43. What are parallel streams, and when should you avoid them?**
`parallelStream()` splits the work across threads in the common ForkJoin pool. Avoid it for small data, for blocking calls (database, HTTP), when order matters, or when the lambdas change shared variables.

**Q44. How does `filter(set::add)` work?**
`Set.add` returns `true` only the first time an element is added. So `filter(set::add)` keeps first appearances (UniqueCharactersInOrder) and `filter(e -> !set.add(e))` keeps the repeats (FindDuplicates). It depends on shared state, so only use it on sequential streams. For plain unique values, `distinct()` is simpler.

### 1.6 Collectors

**Q45. What does `groupingBy` do?**
It groups elements into a `Map<key, List<element>>`. `groupingBy(w -> w.charAt(0))` groups words by their first letter (GroupWordsByFirstLetter).

**Q46. Which downstream collectors does this repo use?**

| Collector | Result | Used for |
|---|---|---|
| `counting()` | `Map<K, Long>` | how many times each character appears |
| `maxBy(comparator)` | `Map<K, Optional<T>>` | highest-paid employee per department |
| `averagingInt(...)` | `Map<K, Double>` | average salary by gender |

Others to know: `summingInt`, `mapping`, `toSet`, `collectingAndThen`.

**Q47. How do you get `Map<Department, Employee>` instead of `Map<Department, Optional<Employee>>`?**
Use `groupingBy(Employee::department, collectingAndThen(maxBy(comparing(Employee::salary)), Optional::get))`, or `toMap(Employee::department, e -> e, BinaryOperator.maxBy(comparing(Employee::salary)))`. `Optional::get` is safe here because a group is never empty.

**Q48. `groupingBy` vs `partitioningBy`?**
`partitioningBy(condition)` always gives exactly two groups, `true` and `false` (for example, active vs inactive users). `groupingBy` gives one group per key.

**Q49. What happens with duplicate keys in `Collectors.toMap`?**
It throws `IllegalStateException`. Pass a merge function as the third argument: `toMap(k, v, (a, b) -> a)`.

**Q50. Why pass `LinkedHashMap::new` to `toMap` (streams/SortMapByKeyAndValue)?**
`toMap` makes a `HashMap` by default, which loses the sorted order. The fourth argument picks the map type, and `LinkedHashMap` keeps entries in the order they were added.

**Q51. How do you sort a map by key or by value?**
Use `map.entrySet().stream().sorted(Map.Entry.comparingByKey())` (or `comparingByValue(Comparator.reverseOrder())` for the highest value first), then collect into a `LinkedHashMap`. To sort by key only, `new TreeMap<>(map)` is simplest.

### 1.7 Optional

**Q52. What is `Optional`, and why use it?**
A box that may or may not hold a value. It makes "no result" clear and avoids `NullPointerException`. `findFirst`, `max`, `min` and `maxBy` return it.

**Q53. How do you read an Optional safely?**
Use `orElse(default)`, `orElseGet(() -> ...)`, `orElseThrow()`, `ifPresent(v -> ...)` or `map(...)`. Calling `get()` on an empty Optional throws `NoSuchElementException`.

**Q54. Why does printing an Optional show `Optional[6]` instead of `6`?**
It prints the Optional box itself. Use `orElse(...)` or `get()` to print the value; `arrays/SecondHighest` uses `secondHighest.orElse(null)`.

**Q55. `orElse` vs `orElseGet`?**
`orElse(x)` always builds `x`, even when a value is present. `orElseGet(supplier)` only calls the supplier when the Optional is empty, so use it when the default is expensive to build.

### 1.8 Collections

**Q56. How does `HashMap` work inside?**
- It's an array of buckets. `hashCode()` picks the bucket, and `equals()` finds the key inside it.
- Keys in the same bucket form a linked list. The list becomes a balanced tree when a bucket gets more than 8 entries (once the table has at least 64 buckets).
- The default size is 16 and the load factor is 0.75: when the table is 75% full, it doubles and the entries are spread out again.
- `get` and `put` are O(1) on average.

**Q57. Why override both `equals()` and `hashCode()` for map keys?**
Equal objects must have the same hash code. Otherwise they land in different buckets and `get` can't find them. Records do this for you.

**Q58. HashMap vs LinkedHashMap vs TreeMap?**

| | Order | Speed |
|---|---|---|
| `HashMap` | no order | O(1) |
| `LinkedHashMap` | insertion order (or access order, used for LRU caches) | O(1) |
| `TreeMap` | sorted by key (red-black tree) | O(log n) |

**Q59. HashMap vs Hashtable vs ConcurrentHashMap?**
- `HashMap`: not thread-safe; allows one null key and null values.
- `Hashtable`: old; every method is `synchronized`, so it's slow; no nulls.
- `ConcurrentHashMap`: thread-safe using small per-bucket locks and CAS; reads don't lock; no null keys or values.

**Q60. Which handy Map methods does this repo use?**
`getOrDefault(k, 0)`, `containsKey`, `computeIfAbsent(k, x -> new ArrayList<>()).add(v)` (group anagrams), `forEach((k, v) -> ...)` and `entrySet()`. Also learn `merge(k, 1, Integer::sum)`, which counts in one line.

**Q61. How does `HashSet` work?**
It uses a `HashMap` inside, with the elements as keys. `add` returns `false` if the element is already there, which is handy for finding duplicates.

**Q62. `retainAll` vs `removeAll`?**
`a.retainAll(b)` keeps only what is also in `b` (intersection, ArrayIntersection). `a.removeAll(b)` deletes everything that is in `b` (difference, UncommonWords).

**Q63. ArrayList vs LinkedList?**
`ArrayList` is a growing array: `get(i)` is O(1), but inserting or removing in the middle is O(n). `LinkedList` is a chain of nodes: `get(i)` is O(n), but adding or removing at either end is O(1). Pick `ArrayList` by default.

**Q64. `Arrays.asList` vs `List.of` vs `new ArrayList<>(...)`?**
- `Arrays.asList(...)`: fixed size and backed by the array. `set` and `sort` work, but `add` and `remove` throw `UnsupportedOperationException`. Nulls are allowed.
- `List.of(...)` (Java 9): can't be changed at all, and no nulls.
- `new ArrayList<>(list)`: a normal copy you can change. That's why UncommonWords copies the lists before calling `removeAll`.

**Q65. Why avoid `Stack`, and what should you use instead?**
`Stack` is an old class built on `Vector`, and every method is synchronized, so it's slower. Use `Deque<Character> stack = new ArrayDeque<>();` with `push`, `pop` and `peek`.

**Q66. Comparable vs Comparator?**
`Comparable`: the class defines its own natural order (`compareTo`), like `String` and `Integer`. `Comparator`: a separate object (`compare`), so one class can be sorted in many ways. `Collections.sort(list)` uses Comparable; `list.sort(comparator)` uses a Comparator.

**Q67. What rules must a Comparator follow?**
Return a negative number, zero or a positive number. Return 0 for equal items. `compare(a, b)` and `compare(b, a)` must have opposite signs. Breaking these rules can make sorting throw "Comparison method violates its general contract!". Use `Integer.compare(b, a)` or `Comparator.reverseOrder()` instead of hand-written if/else. (See bug B6.)

**Q68. What is `ConcurrentModificationException`?**
You get it when you add or remove items while looping over a collection with for-each (the iterator is "fail-fast"). Use `iterator.remove()` or `list.removeIf(...)` instead. See [ModifyListWhileLooping.java](../java/datastructures/ModifyListWhileLooping.java).

**Q69. Big-O cheat sheet**

| Operation | Cost |
|---|---|
| `ArrayList` get, add at the end | O(1) |
| `ArrayList` insert or remove in the middle | O(n) |
| `HashMap` / `HashSet` get, put, contains | O(1) on average |
| `TreeMap` / `TreeSet` get, put | O(log n) |
| `ArrayDeque` push, pop | O(1) |
| `PriorityQueue` offer, poll | O(log n) (`peek` is O(1)) |
| Sorting (`Arrays.sort`, `Collections.sort`) | O(n log n) |

### 1.9 Strings

**Q70. Why is `String` immutable?**
For safety (a string used as a map key, file path or password can't be changed behind your back), for thread safety, so the hash code can be cached, and for the String pool, where many variables share one object.

**Q71. String vs StringBuilder vs StringBuffer?**
`String` can't change. `StringBuilder` can change and is fast, so use it to build strings in loops (`ReplaceWithGrowingDollars`). `StringBuffer` is the same but synchronized, so it's slower and rarely needed.

**Q72. Why is `c = c + str.charAt(i)` in a loop slow? (The old version of `strings/ReverseString` did this.)**
Each `+` creates a new String and copies all the characters, so the whole loop is O(n²). `new StringBuilder(str).reverse().toString()` is O(n).

**Q73. `==` vs `equals()` for strings?**
`==` checks whether both are the same object. `equals()` checks the characters. Always use `equals` (or `equalsIgnoreCase`).

**Q74. What is the String pool?**
A part of the heap where string literals are stored once and shared. `"abc" == "abc"` is `true` (same object), but `new String("abc") == "abc"` is `false`. `intern()` returns the pooled copy.

**Q75. Which String methods appear in this repo?**
`split(" ")`, `String.join(" ", words)`, `toCharArray()`, `chars()`, `charAt(i)`, `toLowerCase()`, `repeat(n)` (Java 11), `compareTo` (for sorting), and `indexOf` / `lastIndexOf` (a character appears only once if both return the same index, as in `FirstNonRepeatedCharacter.usingIndexOf`).

### 1.10 Exceptions

**Q76. What does `FinallyReturn.finallyOverridesReturn()` return? The `try` throws, the `catch` returns -2 and the `finally` returns -3.**
It returns `-3`. `finally` always runs, and a `return` inside it replaces the earlier return value. It also silently swallows exceptions, so never return from `finally`.

**Q77. When does `finally` not run?**
Only when the JVM stops: `System.exit()`, a crash, or the machine powering off. It still runs after a `return` or an exception.

**Q78. Checked vs unchecked exceptions?**
- Checked (`Exception`, `IOException`, `SQLException`): the compiler forces you to catch them or declare them with `throws`.
- Unchecked (`RuntimeException`, like `NullPointerException` or `IllegalArgumentException`): no compile-time check.
- `Error` (like `OutOfMemoryError`): serious JVM problems. Don't catch them.

**Q79. What is try-with-resources?**
`try (var in = new FileInputStream(file)) { ... }` closes the resource automatically, even if an exception happens. The resource must implement `AutoCloseable`. See [TryWithResources.java](../java/exceptions/TryWithResources.java).

**Q80. `throw` vs `throws`?**
`throw` actually throws an exception object. `throws` in a method signature says which checked exceptions the method may throw.

### 1.11 Tricky output questions

**Q81. `oop/MethodOverloading` has `call(String)` and `call(Object)`. What does `call(null)` print?**
`string called`. Java picks the most specific type, and `String` is a subclass of `Object`. If you also add `call(Integer)`, it won't compile ("reference to call is ambiguous"), because `String` and `Integer` are unrelated.

**Q82. What does `System.out.println(stream)` print (an old version of `LongestAndShortestString` did this)?**
Something like `java.util.stream.SortedOps$OfRef@1b6d3586`: the stream object, not its elements. Collect it first with `toList()`, or use `forEach(System.out::println)`.

**Q83. `Integer a = 127, b = 127; a == b` is true. What about 128?**
`false`. Java caches `Integer` objects only from -128 to 127, and `==` compares objects. Always compare wrapper objects with `equals()`.

**Q84. What does LambdaDemo print for the fruit list?**
`[kiwi, apple, peach, orange]` (the original list), then `[apple, kiwi, orange, peach]` after the anonymous-class sort, then `[peach, orange, kiwi, apple]` after `myList.sort((o1, o2) -> o2.compareTo(o1))`.

### 1.12 Numbers

**Q85. How do you round 8.547 to 8.55 (numbers/RoundToTwoDecimals)?**
- Quick: `Math.round(8.547 * 100) / 100.0` gives `8.55`.
- For money: `new BigDecimal("8.547").setScale(2, RoundingMode.HALF_UP)`.
- Only for display: `String.format("%.2f", 8.547)`.

**Q86. Why not use `double` for money?**
`double` can't store most decimals exactly: `0.1 + 0.2` is `0.30000000000000004`. Use `BigDecimal` (create it from a String), or store amounts in pence or cents as a `long`.

**Q87. What is integer overflow?**
`int` goes up to 2,147,483,647. 12! fits, but 13! doesn't, and the number silently wraps around to a wrong value. Use `long`, `Math.multiplyExact` (throws on overflow) or `BigInteger`.

**Q88. How do `% 10` and `/ 10` help reverse a number (numbers/ReverseInteger)?**
`num % 10` gives the last digit and `num / 10` removes it. Build the result with `rev = rev * 10 + digit`.

### 1.13 Concurrency basics (concurrency/ParallelProductAndSum.java)

**Q89. What is `CompletableFuture`?**
A Future you can chain: start work in the background and say what to do with the result, without blocking. `supplyAsync(() -> work())` runs on the common ForkJoin pool unless you pass your own executor.

**Q90. How do you combine two background results (the ParallelProductAndSum task)?**
`f1.thenCombine(f2, Integer::sum).join()`. For many futures, use `CompletableFuture.allOf(...)`: see [CallServicesInParallel.java](../java/concurrency/CallServicesInParallel.java).

**Q91. `thenApply` vs `thenCompose` vs `thenAccept`?**
`thenApply` transforms the result (like `map`). `thenCompose` is for a next step that itself returns a `CompletableFuture` (like `flatMap`; it avoids nested futures). `thenAccept` uses the result and returns nothing.

**Q92. `join()` vs `get()`?**
Both wait for the result. `get()` throws checked exceptions (`ExecutionException`, `InterruptedException`). `join()` throws the unchecked `CompletionException`.

**Q93. How do you handle errors?**
Use `exceptionally(ex -> fallback)`, `handle((result, ex) -> ...)` or `whenComplete(...)`. The error usually arrives wrapped in a `CompletionException`, so call `ex.getCause()` to see the real one.

**Q94. What does `new CompletableFuture<>()` do?**
It makes an empty future that some other code finishes later by calling `complete(value)` or `completeExceptionally(ex)`.

### 1.14 Java versions and Maven

**Q95. Which Java features does this project use, and from which version?**

| Version | Features used here |
|---|---|
| Java 8 | lambdas, streams, method references, default and static interface methods, `Optional` |
| Java 9 | `List.of` |
| Java 11 | `String.repeat` |
| Java 16 | records, `stream.toList()` |

The `pom.xml` compiles for Java 21.

**Q96. What's new in Java 17 and Java 21 (both long-term support)?**
- Up to 17: switch expressions (14), text blocks (15), records and pattern matching for `instanceof` (16), sealed classes (17).
- 21: virtual threads, pattern matching for `switch`, record patterns, sequenced collections (`getFirst()`, `getLast()`, `reversed()`) and generational ZGC.

For a simple side-by-side guide to Java 8, 11, 17, 21 and 25 with code examples, see [JavaVersions.md](JavaVersions.md).

**Q97. What's inside `pom.xml`?**
`groupId` (the organisation), `artifactId` (the project name) and `version` (`1.0-SNAPSHOT` means "still in development"), plus properties: Java version 21 (`maven.compiler.source` and `maven.compiler.target`) and UTF-8 encoding. Common commands: `mvn compile`, `mvn test`, `mvn package`.

---

## Part 2: Coding questions

How to answer in an interview:
1. Say the problem back in your own words.
2. Ask about edge cases: empty input, duplicates, negative numbers.
3. Give the simple solution first, then improve it.
4. State the time and space Big-O.
5. Walk through the example to test it.

### Arrays

**C1. Two Sum** ([TwoSum.java](../java/arrays/TwoSum.java))
Find the positions of two numbers that add up to a target.
- Example: `[2, 7, 11, 15]`, target `9` → `[0, 1]`
- Idea: walk the array once, keeping a map of number → index. For each number, check whether `target - number` is already in the map. O(n) time, O(n) space.

**C2. Move all zeros to the end** ([MoveZeros.java](../java/arrays/MoveZeros.java), `moveZerosToEnd`)
Keep the other numbers in the same order.
- Example: `[1, 0, 3, 0, 4, 0, 9, 0]` → `[1, 3, 4, 9, 0, 0, 0, 0]`
- Idea: copy every non-zero to a write position that moves forward, then fill the rest with zeros. O(n), no extra space.

**C3. Move all zeros to the front** ([MoveZeros.java](../java/arrays/MoveZeros.java), `moveZerosToFront`)
- Example: `[1, 0, 3, 0, 4, 0, 9, 6]` → `[0, 0, 0, 1, 3, 4, 9, 6]`
- Idea: same as C2, but walk from the right end.

**C4. Second highest number** ([SecondHighest.java](../java/arrays/SecondHighest.java))
- Example: `[1, 6, 9, 0, 2, 3]` → `6`, and `[5, 2, 9, 9, 7, 3, 1]` → `7` (the repeated 9 counts once)
- With streams: `boxed().distinct().sorted(Comparator.reverseOrder()).skip(1).findFirst()`
- With one loop: keep `first` and `second`. A bigger number pushes `first` down into `second`. O(n). Ask what to return when there is no second value.

**C5. Find the missing number** ([MissingNumber.java](../java/arrays/MissingNumber.java))
The array holds the numbers 1 to n with one missing.
- Example: `[8, 2, 4, 5, 3, 7, 1]` (n = 8) → `6`
- Idea: `n * (n + 1) / 2 - sum` (no extra space), a count array as in the code, or XOR.

**C6. Find duplicate numbers** ([FindDuplicates.java](../java/arrays/FindDuplicates.java))
- Example: `[1, 5, 8, 9, 3, 5, 7, 7, 8]` → `[5, 7, 8]`
- Idea: `filter(e -> !seen.add(e))`. Collect into a Set if a number can appear 3 or more times.

**C7. Count how many times each number appears** ([CountOccurrences.java](../java/arrays/CountOccurrences.java))
- Example: `[1, 2, 3, 2, 3, 1, 1]` → `{1=3, 2=2, 3=2}`
- Idea: `map.merge(n, 1, Integer::sum)` in a loop, or `groupingBy(Function.identity(), Collectors.counting())`.

**C8. Intersection of two arrays** ([ArrayIntersection.java](../java/arrays/ArrayIntersection.java))
List the values found in both arrays, each one once.
- Example: `[4, 9, 5]` and `[9, 4, 9, 8, 4]` → `[4, 9]` (any order)
- Idea: put both arrays into sets, then call `set1.retainAll(set2)`. O(n + m).

**C9. Left-rotate an array by k** ([ArrayRotation.java](../java/arrays/ArrayRotation.java), `leftRotateAnArray`)
- Example: `[0, 1, 2, 4, 5, 6, 7]`, k = 3 → `[4, 5, 6, 7, 0, 1, 2]`
- Idea: `result[i] = arr[(i + k) % n]`. To do it in place, reverse the first k items, reverse the rest, then reverse the whole array. Use `k % n` first.

**C10. Right-rotate an array by k** ([ArrayRotation.java](../java/arrays/ArrayRotation.java), `rightRotateAnArray`)
- Example: `[1, 2, 3, 4, 5]`, k = 2 → `[4, 5, 1, 2, 3]`
- Idea: `result[(i + k) % n] = arr[i]`. In place: reverse the whole array, then the first k items, then the rest.

**C11. K-th element that is not repeated** ([KthNonRepeatedElement.java](../java/arrays/KthNonRepeatedElement.java))
- Example: `[1, 2, 1, 2, 3, 4]`, k = 2 → `4` (the values that appear once are 3 and 4)
- Idea: count with a `LinkedHashMap` (it keeps the order), then walk it and return the k-th key whose count is 1.

**C12. Sum, product and average of a list** ([ReduceExamples.java](../java/streams/ReduceExamples.java))
- Example: `[1, 6, 7, 9, 2]` → sum `25`, product `756`, average `5.0`
- Idea: `reduce(0, Integer::sum)`, `reduce(1, (a, b) -> a * b)` and `mapToInt(e -> e).average()`.

**C13. Sort a map by key and by value** ([SortMapByKeyAndValue.java](../java/streams/SortMapByKeyAndValue.java))
- Example: `{9=a, 118=c, 4=z}` sorted by key → `{4=z, 9=a, 118=c}`
- Idea: see Q51.

### Strings

**C14. Longest substring without repeating characters** ([LongestSubstringWithoutRepeats.java](../java/strings/LongestSubstringWithoutRepeats.java))
- Example: `"pwwkewq"` → `4` (`"kewq"`), and `"abcabcbb"` → `3`
- Idea: a sliding window. Move the right end forward. While the new character is already in the window's set, remove characters from the left. O(n).
- Second way (`lengthUsingLastSeenIndex`): a map of each character's last position lets the left end jump past a repeat in one step.

**C15. Replace words that aren't in a dictionary with `###`** ([ReplaceUnknownWords.java](../java/strings/ReplaceUnknownWords.java))
Ignore upper and lower case.
- Example: dictionary `"The Codility coders test"`, message `"Codility tests the coders codes"` → `"Codility ### the coders ###"`
- Idea: put the dictionary words in a lowercase `Set`, replace each message word that isn't in the set, then `String.join(" ", words)`.

**C16. Group anagrams** ([GroupAnagrams.java](../java/strings/GroupAnagrams.java))
Put words made of the same letters together.
- Example: `["eat", "tea", "tan", "ate", "nat", "bat"]` → `[[eat, tea, ate], [tan, nat], [bat]]` (any order)
- Idea: sort each word's letters to make a key, then `map.computeIfAbsent(key, k -> new ArrayList<>()).add(word)`. O(n · k log k).
- With streams (`groupWithStreams`): `Collectors.groupingBy(word -> sortedLetters(word))` does the same grouping.

**C17. Are two words anagrams?** ([AnagramCheck.java](../java/strings/AnagramCheck.java))
- Example: `"Race"` and `"Care"` → `true`
- Idea: lowercase both, compare lengths, sort both char arrays and compare them with `Arrays.equals`. Faster: count letters in an `int[26]`.

**C18. Is a sentence a pangram (does it use every letter from a to z)?** ([PangramCheck.java](../java/strings/PangramCheck.java))
- Example: `"The quick brown fox jumps over the lazy dog"` → `true`, and `"Hello world"` → `false`
- Idea: add each letter to a `Set`. It's a pangram if the set's size is 26.

**C19. Words that two sentences don't share** ([UncommonWords.java](../java/strings/UncommonWords.java))
- Example: `"I live in A B"` and `"I live in "` → `[A, B]`
- Idea: copy both word lists, call `removeAll` in both directions, and join what's left.

**C20. First character that doesn't repeat** ([FirstNonRepeatedCharacter.java](../java/strings/FirstNonRepeatedCharacter.java))
- Example: `"swiss"` → `'w'`, and `"intervienw"` → `'t'`
- Idea: count with a `LinkedHashMap` and return the first key whose count is 1. O(n). The `indexOf(c) == lastIndexOf(c)` trick also works, but it's O(n²).
- With streams (`usingStreams`): `groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting())`, then the first entry whose count is 1.

**C21. Count vowels** ([CountVowels.java](../java/strings/CountVowels.java))
- Example: `"Canada"` → `3`
- Idea: lowercase the string, then count the characters that are in `Set.of('a', 'e', 'i', 'o', 'u')`.

**C22. Count each word in a sentence** ([WordCount.java](../java/strings/WordCount.java))
- Example: `"This is is done DONE by VISHAL vishal"` → `is=2`, and every other word `1` (case matters)
- Idea: `split(" ")`, then `map.merge(word, 1, Integer::sum)`. Ask whether "DONE" and "done" should count as the same word.

**C23. Unique characters in order of first appearance** ([UniqueCharactersInOrder.java](../java/strings/UniqueCharactersInOrder.java))
- Example: `"My name is Pelican"` → `[M, y, ' ', n, a, m, e, i, s, P, l, c]`
- Idea: `filter(seen::add)` or `distinct()`.

**C24. Replace a character with a growing number of `$` signs** ([ReplaceWithGrowingDollars.java](../java/strings/ReplaceWithGrowingDollars.java))
The 1st match becomes `$`, the 2nd `$$`, the 3rd `$$$`, and so on.
- Example: `"tomorrow"` with the character `'o'` → `"t$m$$rr$$$w"`
- Idea: use one `StringBuilder` and a counter, and append `"$".repeat(count++)` for each match.

**C25. Is a string a palindrome?** ([Palindrome.java](../java/strings/Palindrome.java), `isPalindromeUsingReverse`)
- Example: `"radar"` → `true`
- Idea: two pointers that move in from both ends, or `new StringBuilder(s).reverse().toString().equals(s)`.

**C26. Reverse a string** ([ReverseString.java](../java/strings/ReverseString.java))
- Example: `"mystring"` → `"gnirtsym"`
- Idea: `new StringBuilder(s).reverse()`, or loop from the end into a `StringBuilder` (not `+`).

**C27. Palindrome pairs** ([Palindrome.java](../java/strings/Palindrome.java), `palindromePairs`)
Find all pairs (i, j), with i ≠ j, where `words[i] + words[j]` is a palindrome.
- Example: `["abcd", "dcba", "lls", "s", "sssll"]` → `[[0,1], [1,0], [2,4], [3,2]]`
- Idea: check every pair, as the code does (O(n² · word length)). Faster: a map of the reversed words.

**C28. Longest and shortest string in a list** ([LongestAndShortestString.java](../java/streams/LongestAndShortestString.java))
- Example: `[laptop, mobile, abcd, abcz, ballbat]` → longest `ballbat`, shortest `abcd`
- Idea: `max(Comparator.comparing(String::length))` and `min(...)`.

**C29. Group words by first letter** ([GroupWordsByFirstLetter.java](../java/streams/GroupWordsByFirstLetter.java))
- Example: `[apple, apricot, banana, brown, cat, crown]` → `{a=[apple, apricot], b=[banana, brown], c=[cat, crown]}`
- Idea: `groupingBy(w -> w.charAt(0))`.

### Numbers

**C30. Reverse an integer** ([ReverseInteger.java](../java/numbers/ReverseInteger.java))
- Example: `12345` → `54321`
- Idea: see Q88. Ask about negative numbers and overflow.

**C31. Round a double to 2 decimal places** ([RoundToTwoDecimals.java](../java/numbers/RoundToTwoDecimals.java))
- Example: `8.547` → `8.55`
- Idea: see Q85.

### Stack, linked list and matrix

**C32. Valid brackets** ([ValidParentheses.java](../java/datastructures/ValidParentheses.java))
Every `(`, `{` and `[` must be closed by the same type, in the right order.
- Example: `"{[()]}"` → `true`, `"([)]"` → `false`, `"((())"` → `false`
- Idea: push opening brackets onto a stack. On a closing bracket, pop and check that it matches. At the end the stack must be empty. O(n).

**C33. Detect a loop in a linked list** ([LinkedListCycle.java](../java/datastructures/LinkedListCycle.java))
- Example: 1 → 2 → 3 → 4 → 5 → back to 3 gives `true`
- Simple way (`hasLoopUsingSet`): remember visited nodes in a `HashSet` (O(n) extra space).
- Best way (Floyd, `hasLoop`): a slow pointer moves 1 step and a fast pointer moves 2 steps. If they ever meet, there's a loop. O(1) extra space.
- Follow-up: to find where the loop starts, move one pointer back to the head, then move both 1 step at a time. They meet at the start of the loop.

**C34. Reverse a linked list** ([ReverseLinkedList.java](../java/datastructures/ReverseLinkedList.java))
- Example: `a → b → q → p` → `p → q → b → a`
- Idea: `Collections.reverse(list)` for Java's `LinkedList`. For your own node class, use three pointers (`prev`, `curr`, `next`) in one loop.

**C35. Print a matrix in spiral order** ([SpiralMatrix.java](../java/arrays/SpiralMatrix.java))
- Example: the 4×4 matrix with 1 to 16 → `[1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10]`
- Idea: keep four borders (top, bottom, left, right). Go right, down, left and up, shrinking a border after each side. O(rows × columns).

### Design

**C36. LRU cache** ([LRUCache.java](../java/datastructures/LRUCache.java))
Build a cache with a fixed capacity. `get(key)` returns the value, or -1 if the key isn't there. `put(key, value)` adds or updates a key. When the cache is full, remove the least recently used key. Both operations must be O(1), and you can't extend any Map class.
- Example (capacity 2): put(1,1), put(2,2), get(1) → 1, put(3,3) removes key 2, get(2) → -1, put(4,4) removes key 1, get(1) → -1, get(3) → 3, get(4) → 4
- Idea: a `HashMap` from key to node, plus a doubly linked list with the most recent node at the tail and the oldest at the head. Dummy head and tail nodes avoid null checks. On get or put, move the node to the tail. When the cache is full, remove the node after the head.
- Follow-ups: with `LinkedHashMap`, use `new LinkedHashMap<>(cap, 0.75f, true)` and override `removeEldestEntry` ([LRUCacheWithLinkedHashMap.java](../java/datastructures/LRUCacheWithLinkedHashMap.java)). For a thread-safe version, put a lock around get and put.

### Streams on objects (Employee records)

**C37. Highest-paid employee in each department** ([HighestPaidByDepartment.java](../java/streams/HighestPaidByDepartment.java))
- Example: IT (100, 200, 500) and DEV (400, 500) → `IT: THREE 500` and `DEV: FIVE 500`
- Idea: `groupingBy(Employee::department, maxBy(Comparator.comparing(Employee::salary)))`. See Q47 to drop the Optional.

**C38. Average salary by gender** ([SalaryStatistics.java](../java/streams/SalaryStatistics.java))
- Example: Male 100 and 10, Female 1000 and 50 → `{Female=525.0, Male=55.0}`
- Idea: `groupingBy(Worker::gender, averagingInt(Worker::salary))`.

**C39. Total of all salaries** ([SalaryStatistics.java](../java/streams/SalaryStatistics.java))
- Example: 100 + 1000 + 50 + 10 → `1160`
- Idea: `mapToInt(Worker::salary).sum()` or `reduce(0, Integer::sum)`.

**C40. Give everyone a 10% raise** ([EmployeeStreamPractice.java](../java/streams/EmployeeStreamPractice.java))
- Idea: `map(e -> e.salary() * 1.1)`. For real money, use `BigDecimal`.

**C41. Names in upper case for salaries above 100** ([EmployeeStreamPractice.java](../java/streams/EmployeeStreamPractice.java))
- Idea: `filter(e -> e.salary() > 100).map(e -> e.name().toUpperCase()).toList()`.

More practice from the comments in SalaryStatistics: group employees by department, and count active vs inactive users (`partitioningBy`).

### Concurrency

**C42. Use two threads: one multiplies 1 to 10, the other adds 20 to 30; then add both results** ([ParallelProductAndSum.java](../java/concurrency/ParallelProductAndSum.java))
- Expected: `3,628,800 + 275 = 3,629,075`
- Idea:

```java
CompletableFuture<Integer> product = CompletableFuture.supplyAsync(
        () -> IntStream.rangeClosed(1, 10).reduce(1, (a, b) -> a * b));
CompletableFuture<Integer> sum = CompletableFuture.supplyAsync(
        () -> IntStream.rangeClosed(20, 30).sum());
int result = product.thenCombine(sum, Integer::sum).join();
```

### More practice (moved from Questions.md)

**C43. Separate 0s and 1s** ([SeparateZerosAndOnes.java](../java/arrays/SeparateZerosAndOnes.java))
Put all the 0s before all the 1s, in place.
- Example: `[1, 0, 1, 0, 0, 1, 1, 0]` → `[0, 0, 0, 0, 1, 1, 1, 1]`
- Idea: two pointers. The left one stops at a 1, the right one stops at a 0; swap them and keep going. O(n).

**C44. Merge two sorted arrays in place** ([MergeSortedArrays.java](../java/arrays/MergeSortedArrays.java))
`nums1` has empty slots at the end for the numbers of `nums2`.
- Example: `[1, 2, 3, 0, 0, 0]` and `[2, 5, 6]` → `[1, 2, 2, 3, 5, 6]`
- Idea: fill `nums1` from the back with the bigger of the two last numbers, so nothing is overwritten too early. O(m + n).

**C45. Maximum subarray sum (Kadane's algorithm)** ([MaximumSubarraySum.java](../java/arrays/MaximumSubarraySum.java))
- Example: `[-2, 1, -3, 4, -1, 2, 1, -5, 4]` → `6` (from `[4, -1, 2, 1]`); all negative `[-3, -1, -2]` → `-1`
- Idea: `current = max(num, current + num)`, and remember the best `current` seen. O(n).

**C46. Top K frequent elements** ([TopKFrequentElements.java](../java/arrays/TopKFrequentElements.java))
- Example: `[1, 1, 1, 2, 2, 3]`, k = 2 → `[2, 1]` (any order)
- Idea: count each number in a map, then keep a min-heap (`PriorityQueue`) of at most k entries ordered by count. O(n log k).

**C47. Characters repeated across a list of words** ([DuplicateCharactersInWords.java](../java/strings/DuplicateCharactersInWords.java))
- Example: `[apple, banana, cherry]` → `[p, a, r, e, n]` (any order)
- Idea: `flatMapToInt(String::chars)` joins the characters of all the words, then `groupingBy(..., counting())` and keep the counts above 1.

**C48. Longest common prefix** ([LongestCommonPrefix.java](../java/strings/LongestCommonPrefix.java))
- Example: `[flower, flow, flight]` → `"fl"`, and `[dog, racecar, car]` → `""`
- Idea: start with the first word as the prefix and cut off its last letter until every word `startsWith` it.

**C49. Flatten a nested list, remove duplicates and sort** ([FlattenListOfLists.java](../java/streams/FlattenListOfLists.java))
- Example: `[[1, 2, 3], [3, 4, 5], [5, 6, 7]]` → `[1, 2, 3, 4, 5, 6, 7]`
- Idea: `flatMap(List::stream).distinct().sorted().toList()`.

The other coding questions from Questions.md were already in the code, and now also show its version: Two Sum (C1), second highest with a repeated maximum (C4), longest substring with a last-seen map (C14), group anagrams with streams (C16) and first non-repeated character with streams (C20).

### Interview favourites (added on 2026-11-24)

**C50. Shapes with a sealed interface and `switch`** ([ShapeArea.java](../java/modern/ShapeArea.java))
- Example: `area(new Rectangle(2, 3))` → `6.0`, and `describe(new Square(20))` → `"a big square"`
- Idea: the sealed interface lists every kind of shape, so the `switch` needs no `default`. Record patterns read the fields, and `when` adds a condition.

**C51. Run thousands of waiting tasks with virtual threads** ([VirtualThreadsDemo.java](../java/concurrency/VirtualThreadsDemo.java))
- Example: 10,000 tasks that each wait 200 ms finish in about 0.2 seconds. 1,000 tasks on 100 normal threads take about 2 seconds.
- Idea: `Executors.newVirtualThreadPerTaskExecutor()`. Limit calls to a slow service with a `Semaphore`.

**C52. Two threads print odd and even numbers in turn** ([OddEvenPrinter.java](../java/concurrency/OddEvenPrinter.java))
- Example: `odd thread: 1`, `even thread: 2`, ... up to 10
- Idea: one shared lock. A thread prints when it is its turn and calls `notify()`; otherwise it calls `wait()`.

**C53. Producer-consumer** ([ProducerConsumer.java](../java/concurrency/ProducerConsumer.java))
- Example: the consumer takes 1, 2, 3, 4, 5 in order
- Idea: `BlockingQueue.put()` and `take()` wait for you. With `wait()` and `notifyAll()`, always check the condition in a `while` loop.

**C54. Deadlock: make it, find it, fix it** ([DeadlockDemo.java](../java/concurrency/DeadlockDemo.java))
- Example: `Deadlocked threads found: 2`
- Idea: two threads take two locks in the opposite order. Find it with `ThreadMXBean` or a thread dump (`jstack`). Fix it by always taking the locks in the same order, or with `tryLock` and a timeout.

**C55. Many threads adding to one counter** ([CounterRace.java](../java/concurrency/CounterRace.java))
- Example: `volatile` usually gives less than 400000 (a different number each run); `AtomicInteger` and `synchronized` always give 400000
- Idea: `count++` is read, add, write. `volatile` doesn't make it one step. Use `AtomicInteger` or `synchronized`.

**C56. Thread-safe Singleton** ([Singletons.java](../java/design/Singletons.java))
- Idea: double-checked locking with `volatile`, a holder class, or an `enum` (the simplest).

**C57. Immutable class, equals and hashCode** ([ImmutableKey.java](../java/oop/ImmutableKey.java))
- Example: a key without `equals` and `hashCode` can't be found again: `null`
- Idea: a final class, private final fields, no setters, and copies of lists you receive. Equal objects need the same hash code.

**C58. Nth highest salary** ([NthHighestSalary.java](../java/streams/NthHighestSalary.java))
- Example: salaries 900, 700, 900, 500, 700 → 2nd highest is `700`, earned by `[Ravi, Sara]`
- Idea: `map(Employee::salary).distinct().sorted(Comparator.reverseOrder()).skip(n - 1).findFirst()`

**C59. Employee stream questions** ([EmployeeGroupingAndSorting.java](../java/streams/EmployeeGroupingAndSorting.java))
- Example: count per department → `{HR=2, IT=2, Ops=1}`
- Idea: `groupingBy` with `counting()` or `mapping()`, `partitioningBy`, `joining(", ", "[", "]")`, and `Comparator.comparing(...).thenComparing(...)`.

**C60. Your own linked list** ([SinglyLinkedList.java](../java/datastructures/SinglyLinkedList.java))
- Example: `1 -> 2 -> 3 -> 4 -> 5` reversed is `5 -> 4 -> 3 -> 2 -> 1`, the middle is `3`, and removing the 2nd from the end gives `1 -> 2 -> 3 -> 5`
- Idea: prev/curr/next pointers to reverse, slow and fast pointers for the middle, and a dummy node to remove a node.

**C61. Binary tree basics** ([BinaryTree.java](../java/datastructures/BinaryTree.java))
- Example: inorder `[2, 3, 4, 5, 7, 8, 9]`, height `3`, and the lowest common ancestor of 2 and 9 is `5`
- Idea: recursion for the three orders and the height, a queue for level order, and min/max limits for the BST check.

**C62. SOLID principles** ([SolidPrinciples.java](../java/design/SolidPrinciples.java))
- Example: `BadSquare` extends `BadRectangle`, so setting the width to 5 and the height to 4 gives an area of `16`, not `20`
- Idea: one job per class (S), add new classes instead of editing old code (O), a child class must work wherever its parent works (L), small interfaces (I), and depend on interfaces passed in through the constructor (D). Each part shows code that breaks the rule and code that follows it.

**C63. A record that checks its values** ([RecordWithChecks.java](../java/modern/RecordWithChecks.java))
- Example: `new Payment("Ravi", -5, List.of())` throws `amount must be above 0: -5`
- Idea: a compact constructor (no parameter list) checks or changes the values before the fields are set. `List.copyOf` keeps the record immutable.

**C64. `var`, switch expressions and text blocks** ([NewerSyntax.java](../java/modern/NewerSyntax.java))
- Idea: `var` for local variables with an obvious type, `case SATURDAY, SUNDAY -> "Weekend"` with no `break`, `yield` to return a value from a block, and `"""` for strings over several lines.

**C65. First, last and reversed (sequenced collections)** ([SequencedCollectionsDemo.java](../java/modern/SequencedCollectionsDemo.java))
- Example: for `[1, 2, 3]`, `getFirst()` is `1`, `getLast()` is `3` and `reversed()` is `[3, 2, 1]`
- Idea: Java 21 adds `getFirst()`, `getLast()` and `reversed()` to lists and to `LinkedHashSet`, and `firstEntry()`, `lastEntry()` and `reversed()` to `LinkedHashMap`.

**C66. CountDownLatch and CyclicBarrier** ([LatchAndBarrier.java](../java/concurrency/LatchAndBarrier.java))
- Idea: a latch waits for N `countDown()` calls and can be used only once. A barrier makes N threads wait for each other and can be used again. A `Semaphore` limits how many threads can run a piece of code at the same time (see C51).

**C67. Call 3 services at once, with a timeout and fallbacks** ([CallServicesInParallel.java](../java/concurrency/CallServicesInParallel.java))
- Example: `[user: done, orders: none (service failed), offers: none (too slow)]` in about 500 ms
- Idea: `supplyAsync` on your own thread pool, `exceptionally` for errors, `completeOnTimeout` for slow calls, then `CompletableFuture.allOf(...).join()`.

**C68. Min stack** ([MinStack.java](../java/datastructures/MinStack.java))
- Example: push 5, 3, 7 → the min is `3`; pop twice → the min is `5`
- Idea: a second stack that holds the smallest value at each level, so `getMin()` is O(1).

**C69. Queue using two stacks** ([QueueUsingTwoStacks.java](../java/datastructures/QueueUsingTwoStacks.java))
- Example: add 1, 2, 3, then remove → `1`
- Idea: push onto `in`. When `out` is empty, move everything from `in` to `out`. Each item moves only once, so it is O(1) on average.

**C70. Change a list while looping over it** ([ModifyListWhileLooping.java](../java/datastructures/ModifyListWhileLooping.java))
- Example: removing the even numbers from `[1, 2, 3, 4, 5, 6]` in a for-each loop throws `ConcurrentModificationException`. The safe ways give `[1, 3, 5]`.
- Idea: `Iterator.remove()`, `removeIf()` (the simplest), or a `CopyOnWriteArrayList`. Trap: removing the second-last item gives no exception, and the last item is never checked.

**C71. try-with-resources and a custom exception** ([TryWithResources.java](../java/exceptions/TryWithResources.java))
- Example: prints `Close second`, then `Close first`, then `Caught: Empty payment on second`
- Idea: any `AutoCloseable` is closed for you, in reverse order, before `catch` runs. If the try block threw and `close()` throws too, the exception from `close()` is kept as a suppressed exception.

**C72. Generics: `? extends` and `? super`** ([GenericsWildcards.java](../java/oop/GenericsWildcards.java))
- Idea: PECS: `? extends` to read, `? super` to add. Type erasure: at run time a `List<String>` and a `List<Integer>` are the same class.

**C73. Reverse the words in a sentence** ([ReverseWords.java](../java/strings/ReverseWords.java))
- Example: `"  Java   is fun "` → `"fun is Java"`. Follow-up (reverse each word): `"avaJ si nuf"`
- Idea: `trim()` and `split("\\s+")`, then a loop from the end, or `Collections.reverse` and `String.join`.

**C74. String rotation** ([StringRotation.java](../java/strings/StringRotation.java))
- Example: `"waterbottle"` and `"erbottlewat"` → `true`
- Idea: the same length, and `(s1 + s1).contains(s2)`.

**C75. Duplicate words and the first repeated character** ([DuplicateWords.java](../java/strings/DuplicateWords.java))
- Example: `"Java is fun and java is fast"` → `[java, is]`, and `"programming"` → `r`
- Idea: `Set.add()` returns false when the item is already in the set.

**C76. Fibonacci** ([Fibonacci.java](../java/numbers/Fibonacci.java))
- Example: fib(10) = `55`, fib(50) = `12586269025`
- Idea: a loop that keeps the last two numbers (O(n)). Plain recursion is O(2^n). Saving the answers (memoization) makes recursion O(n). `Stream.iterate` works on pairs.

**C77. Prime, palindrome number, Armstrong number and factorial** ([ClassicNumberPrograms.java](../java/numbers/ClassicNumberPrograms.java))
- Example: 29 is prime, 121 is a palindrome, 153 = 1³ + 5³ + 3³, and 25! = `15511210043330985984000000`
- Idea: try divisors only up to the square root, reverse the digits, and use `BigInteger` because 21! doesn't fit in a `long`.

**C78. Builder pattern** ([BuilderPattern.java](../java/design/BuilderPattern.java))
- Example: `new User.Builder("Asha").email("asha@example.com").age(30).build()`
- Idea: a private constructor that takes the builder, methods that set one value and return `this`, and checks in `build()`.

---

## Part 3: Spot the bug

Interviewers like asking "what's wrong with this code?". Each problem below was in this repo's code until the clean-up on 23 Nov 2026, and is fixed now. Try to spot the problem from the code before reading the answer.

| # | Code that was in the repo | Problem | Fix |
|---|---|---|---|
| B1 | Sum of salaries with `reduce(1, (a, b) -> a + b)` (now `streams/SalaryStatistics`) | It printed 1161 instead of 1160 | The identity for a sum is 0: `reduce(0, Integer::sum)` |
| B2 | `IntStream.range(1, 10).reduce(1, (a, b) -> a * b)` to multiply 1 to 10 (now `concurrency/ParallelProductAndSum`) | `range` stops at 9, so it gave 362,880 | `rangeClosed(1, 10)` |
| B3 | Vowels set `{'a', 'e', 'i', 'o'}` (now `strings/CountVowels`) | No `'u'`, and capital vowels weren't counted | Add `'u'` and lowercase the string first |
| B4 | A class called `FloydCycleAlgo` (now `datastructures/LinkedListCycle`) that stored visited nodes in a `HashMap` | The answer was right, but that isn't Floyd's algorithm, and it uses O(n) extra space | Slow and fast pointers, O(1) space (C33) |
| B5 | Second highest with `sorted(Comparator.reverseOrder()).skip(1)` (now `arrays/SecondHighest`) | `[9, 9, 6]` gives 9 | Add `.distinct()` before sorting |
| B6 | Sorting with the comparator `(o1, o2) -> o2 > o1 ? 1 : -1` (now `arrays/CountOccurrences`) | It never returns 0 for equal numbers, which breaks the Comparator rules (Q67) | `Comparator.reverseOrder()` |
| B7 | After counting characters, a second loop did `h.put(x, h.getOrDefault(x, 1) + 1)` (now `strings/CharacterCount`) | Every count was doubled | Delete the second loop |
| B8 | A second-highest loop that compared every number with the first one and never updated it, and a word count with an empty `if` branch (now `arrays/SecondHighest` and `strings/WordCount`) | Wrong second highest, and no count ever went above 1 | See C4 and C22 |
| B9 | `Arrays.sort(a)` at the end of `moveZerosToEnd` (now `arrays/MoveZeros`) | Sorting moved the zeros back to the front | Remove the sort |
| B10 | `System.out.println("longest sorted:" + sorted)`, where `sorted` was a `Stream` (now `streams/LongestAndShortestString`) | It printed the stream object, not the strings (Q82) | Call `.toList()` before printing |
| B11 | `c = c + str.charAt(i)` in a loop (now `strings/ReverseString`) | Slow: every step creates a new String (Q72) | Use `StringBuilder` |
| B12 | A pangram check that printed `uniqueLetters.size()` (now `strings/PangramCheck`) | It never said yes or no | `return uniqueLetters.size() == 26;` |
| B13 | Raw types `new HashSet()` and `TreeMap treeMap` (in the old `Trying` and `MahDay` classes) | The compiler can't check the element types | `new HashSet<>()`, `Map<Integer, String> treeMap = new TreeMap<>(map)` |
| B14 | `Stack<Character>` for bracket matching (now `datastructures/ValidParentheses`) | `Stack` is an old, synchronized class (Q65) | `Deque<Character> stack = new ArrayDeque<>()` |

---

## Part 4: Advanced topics and system design

The full answers are in [Questions.md](Questions.md); the key points below are the short version.

### Advanced Java and backend

| # | Question | Key points |
|---|---|---|
| A1 | How does `volatile` work in the Java Memory Model? | It makes writes visible to other threads and stops reordering (a volatile write *happens-before* every later read of that field). It is not atomic: `count++` still needs `AtomicInteger` or a lock. |
| A2 | How does `ThreadPoolExecutor` work inside? | Its threads are `Worker` objects (they extend AQS and implement `Runnable`) that keep taking tasks from the queue. For a new task: fewer than core threads → start a thread; otherwise → put it in the queue; queue full and fewer than max threads → start a thread; otherwise → rejection policy (`AbortPolicy` by default, or `CallerRunsPolicy` and others). |
| A3 | `map` vs `flatMap`? | See Q32. |
| A4 | What is a circuit breaker? | Closed (calls go through) → too many failures → Open (fail fast) → after a wait → Half-open (a few trial calls) → success goes back to Closed, failure to Open. Library: Resilience4j. |
| A5 | What are virtual threads (Java 21)? | Very cheap threads managed by the JVM, great for blocking I/O. Don't pool them. In Java 21, blocking inside `synchronized` "pins" the carrier thread (use `ReentrantLock` instead); Java 24 fixed this (JEP 491). |
| A6 | How does `ConcurrentHashMap` work? | Since Java 8: CAS to fill an empty bucket, `synchronized` on the bucket's first node to update it, and reads without locks. `compute` and `merge` are atomic per key. No nulls. |
| A7 | How do you handle errors in `CompletableFuture`? | See Q93: errors are wrapped in `CompletionException`, so use `getCause()`. |
| A8 | What is the cache-aside pattern (Redis)? | Read: check the cache; on a miss, read the DB and store the value with a TTL. Write: update the DB, then delete the cache key. One shared Redis needs nothing else; local in-memory caches on many servers need pub/sub invalidation or short TTLs. |
| A9 | What are the transaction isolation levels? | Read Uncommitted (dirty reads possible) → Read Committed (no dirty reads) → Repeatable Read (re-reading a row gives the same data) → Serializable (the result is as if transactions ran one after another; the DB doesn't really run them one at a time, it uses locks or conflict checks and may abort one). |
| A10 | Optimistic vs pessimistic locking? | Optimistic: a version column (`@Version`) checked on update, with a retry on conflict; good when conflicts are rare. Pessimistic: lock the row (`SELECT ... FOR UPDATE`). For money, rely on database locks and constraints, not Redis locks. |
| A11 | G1 vs ZGC? | G1: the default GC; it splits the heap into regions and aims for pauses under 200 ms by default (`-XX:MaxGCPauseMillis`). ZGC: aims for pauses under a millisecond, even on huge heaps; generational since Java 21 (the default mode since Java 23). |
| A12 | What is Kafka consumer rebalancing? | Eager: every consumer in the group gives up all its partitions and waits (the consumer group stops, not the cluster). Cooperative sticky: only the partitions that move are paused. Static membership (`group.instance.id`) avoids rebalances on restarts. |
| A13 | What is the outbox pattern? | Save the business row and an "outbox" event row in the same DB transaction. A separate relay (a poller or Debezium) sends the events to Kafka. No events are lost or sent by mistake, but consumers must handle duplicates (at-least-once delivery). |
| A14 | What are the common `@Transactional` mistakes? | Calls from the same class skip the proxy, private methods are ignored, checked exceptions don't roll back by default, and catching the exception makes it commit. `REQUIRED` joins the current transaction; `REQUIRES_NEW` starts a separate one. |
| A15 | What is the JPA N+1 problem? | 1 query for N parents, then 1 more query per parent for a lazy collection. Fix it with `JOIN FETCH`, `@EntityGraph`, batch fetching or a DTO projection. |
| A16 | Bean scopes and constructor injection? | `singleton` (the default), `prototype`, `request`, `session`. Constructor injection gives final fields, complete objects and easy tests. A prototype bean inside a singleton is created only once: use `ObjectProvider`. |
| A17 | `synchronized` vs `ReentrantLock`? | Both lock and make changes visible. `ReentrantLock` adds `tryLock` with a timeout, waits that can be interrupted, a fair mode and conditions. Always unlock it in `finally`. |
| A18 | What problems can `ThreadLocal` cause? | Pools reuse threads, so an old value leaks into the next request, and its memory is kept while the thread is alive. Always call `remove()` in `finally`. `ScopedValue` (Java 25) is safer. |
| A19 | What are the JVM memory areas? | Heap (objects: `Java heap space`), stack (`StackOverflowError`), Metaspace (classes: `Metaspace`) and direct memory (NIO buffers). |
| A20 | How do you find a memory leak? | The heap grows after every GC → take a heap dump with `jcmd` → open it in Eclipse MAT → fix the cause: a growing static collection or cache, a `ThreadLocal` never removed, or a resource never closed. |
| A21 | How do you size a thread pool? | CPU-heavy work: about the number of cores. Work that waits: more threads (`cores × (1 + wait / CPU time)`). Use a bounded queue and a rejection policy, or virtual threads. |
| A22 | How does class loading work? | Loading → linking → initialization, the first time a class is used. Bootstrap, Platform and Application loaders. Parent first, so core classes can't be replaced and a class the parent can load is loaded only once, by the parent. `ClassNotFoundException` (loading by name fails) vs `NoClassDefFoundError` (missing at run time, or its static init failed). |
| A23 | `? extends` vs `? super`, and type erasure? | PECS: `extends` to read (producer), `super` to add (consumer). A `List<Integer>` is not a `List<Number>`. Erasure removes the generic types after compiling, so no `new T()` and no `instanceof List<String>` on an `Object`. |
| A24 | Which design patterns does Spring use? | Singleton (one per container), Factory (`BeanFactory`, `@Bean`), Proxy (`@Transactional`, AOP), Template Method (`JdbcTemplate`), dependency injection, Observer (events), Strategy, Front Controller (`DispatcherServlet`) and Builder. |
| A25 | What happens when Spring Boot starts? | `SpringApplication.run` → environment and properties → context → component scan → auto-configuration (`@ConditionalOnClass`, `@ConditionalOnMissingBean`) → beans created → embedded server → runners → `ApplicationReadyEvent`. `--debug` shows which auto-configurations ran. |
| A26 | How do you make microservices resilient? | Timeouts on every call, a few retries with exponential backoff and jitter (only for safe calls), circuit breakers, bulkheads, fallbacks, and sagas with compensating actions instead of one big transaction. Library: Resilience4j. |
| A27 | Moving to a newer Java: gains and risks? | Gains: shorter code, virtual threads, better GCs, long-term support, Spring Boot 3. Risks: removed Java EE modules, `javax` → `jakarta` (Spring Boot 3), locked JDK internals break old libraries, old build plugins. Upgrade libraries first, use `jdeps`, test under load. |
| A28 | What is structured concurrency? | Subtasks started in a `StructuredTaskScope` must finish before the scope ends, and one failure cancels the others. Still a preview in Java 25, so use `CompletableFuture` for now. |

### System design in Questions.md

| # | Design | Key points |
|---|---|---|
| S1 | Rate limiter | Token bucket or sliding window. Redis `INCR` + `EXPIRE` is a fixed window (it allows bursts at the window edges), and the two commands aren't atomic unless you run them together (a Lua script or `MULTI`/`EXEC`). |
| S2 | Unique ID generator (Snowflake) | 64 bits: 41 bits of time in ms, 10 bits of machine ID and 12 bits of sequence (4,096 IDs per ms per machine). Handle the clock going backwards (wait or refuse). |
| S3 | Notification system | A queue (Kafka) → one worker per channel (email, SMS, push) → retries with backoff → a dead-letter queue. Also user preferences, templates, rate limits and idempotency. |
| S4 | Logging and observability | Structured JSON logs with a trace ID, metrics, distributed tracing (OpenTelemetry), central storage, and alerts on the problems users actually feel. |
| S5 | Idempotent payments | The client sends an idempotency key, with a unique DB constraint on it. Insert a PENDING row first and pass the same key to the payment provider. On a timeout, check the status (reconcile) instead of blindly retrying. |
| S6 | URL shortener | Base62 codes: 7 characters give 62⁷ ≈ 3.5 trillion codes (about 11 years at 10,000 new links per second). A 64-bit Snowflake ID is 11 characters in base62, not 7, so use a counter or ID ranges for 7-character codes. Redirect with 301 (browsers cache it) or 302 (every click reaches you, which is good for analytics). Cache popular links. |
