# Java Samples

Small Java 21 programs for interview practice. Each class covers one concept or one coding problem, and most have a `main` method you can run.

- Study notes: [InterviewQuestions.md](src/main/resources/InterviewQuestions.md), [Questions.md](src/main/resources/Questions.md) (concepts and system design) and [JavaVersions.md](src/main/resources/JavaVersions.md) (Java 8 vs 11 vs 17 vs 21 vs 25).
- Every class starts with a short comment: what it does, **Keywords** to search for, and **First written** (the date of the first git commit that contains the code).
- The tables below are built from those comments. "Old name" is where the code lived before the clean-up on 2026-11-23.

## How to run

In IntelliJ, open a class and click the green Run arrow next to `main`. From a terminal, in the `Java8Samples` folder:

```bash
mvn -q compile
java -cp target/classes arrays.TwoSum
```

## Packages

| Package | What's inside |
|---|---|
| [`oop`](#oop) | Object-oriented basics: inheritance, overriding, abstract classes, overloading, immutable classes, generics (8 classes) |
| [`functional`](#functional) | Java 8 interfaces: default and static methods, functional interfaces, lambdas, method references (6 classes) |
| [`modern`](#modern) | Newer Java (10 to 21): var, switch expressions, text blocks, records, sealed interfaces, pattern matching, sequenced collections (4 classes) |
| [`streams`](#streams) | Stream API: reduce, groupingBy, flatMap, sorting maps, employee examples (10 classes) |
| [`arrays`](#arrays) | Array problems (including the spiral matrix) (14 classes) |
| [`strings`](#strings) | String problems (19 classes) |
| [`datastructures`](#datastructures) | Stacks, queues, linked lists, trees, LRU caches, and changing a list while looping (10 classes) |
| [`numbers`](#numbers) | Number problems: reverse digits, rounding, prime, palindrome, Armstrong, factorial, Fibonacci (4 classes) |
| [`concurrency`](#concurrency) | Threads: CompletableFuture, virtual threads, locks and deadlock, wait and notify, latches and barriers (8 classes) |
| [`exceptions`](#exceptions) | try / catch / finally, try-with-resources and custom exceptions (2 classes) |
| [`design`](#design) | SOLID principles, and the Singleton and Builder patterns (3 classes) |
| [`model`](#model) | Shared records used by the employee examples (2 classes) |

## All classes

### oop

| Class | What it shows | Keywords | First written | Old name |
|---|---|---|---|---|
| [AbstractParent](src/main/java/oop/AbstractParent.java) | An abstract class can mix abstract methods (no body) with normal methods. It can't be created with `new`; `ConcreteChild` extends it. | abstract class, abstract method, concrete method | 2025-10-21 | `samples.MyAbstractClass` |
| [Circle](src/main/java/oop/Circle.java) | Child class that overrides `Shape.m1()`. | inheritance, `@Override`, runtime polymorphism | 2025-10-21 | `samples.Circle` |
| [ConcreteChild](src/main/java/oop/ConcreteChild.java) | Extends an abstract class and implements an interface at the same time. | extends, implements, abstract class, interface | 2025-10-21 | `samples.ExtendAbstractClass` |
| [GenericsWildcards](src/main/java/oop/GenericsWildcards.java) | Generics with wildcards: `? extends` to read from a list and `? super` to add to a list (PECS: Producer Extends, Consumer Super), a generic method, and type erasure. | generics, wildcard, ? extends, ? super, PECS, invariance, generic method, type erasure | 2026-11-24 | - |
| [ImmutableKey](src/main/java/oop/ImmutableKey.java) | An immutable class used as a HashMap key, and what goes wrong without `equals` and `hashCode`. | immutable class, final fields, defensive copy, equals and hashCode contract, HashMap key | 2026-11-24 | - |
| [InheritanceDemo](src/main/java/oop/InheritanceDemo.java) | Runs the inheritance examples: method overriding, runtime polymorphism and an abstract class. | runtime polymorphism, dynamic dispatch, method overriding, abstract class | 2025-10-21 | `samples.MyClass` |
| [MethodOverloading](src/main/java/oop/MethodOverloading.java) | Which overloaded method runs for `call(null)` when there is a `call(String)` and a `call(Object)`? | overloading, null argument, most specific method, compile-time polymorphism | 2026-04-07 | `testers.A` |
| [Shape](src/main/java/oop/Shape.java) | Parent class for the inheritance example. `Circle` overrides `m1()`. | inheritance, method overriding, parent class | 2025-10-21 | `samples.Shape` |

### functional

| Class | What it shows | Keywords | First written | Old name |
|---|---|---|---|---|
| [Calculator](src/main/java/functional/Calculator.java) | A functional interface: exactly one abstract method, plus a default and a static method. | functional interface, default method, static interface method, Java 8 | 2023-10-23 | `samples.I1` |
| [FunctionalInterfaceDemo](src/main/java/functional/FunctionalInterfaceDemo.java) | A lambda implements a functional interface that also has a default and a static method. | functional interface, lambda, default method, static method | 2023-01-05 | `testers.I1` and `testers.Main` |
| [InterfaceMethodsDemo](src/main/java/functional/InterfaceMethodsDemo.java) | Calls the three kinds of interface methods in `Calculator`: abstract, default and static. | interface, abstract method, default method, static method, implements | 2023-10-23 | `samples.InterfaceTests` |
| [LambdaDemo](src/main/java/functional/LambdaDemo.java) | Sorting with an anonymous class vs a lambda, and lambdas that implement the custom functional interface `MathOperation`. | lambda expression, anonymous class, Comparator, method reference | 2025-10-21 | `samples.LambdaExample` |
| [MathOperation](src/main/java/functional/MathOperation.java) | A custom functional interface: one abstract method, so a lambda can implement it. | `@FunctionalInterface`, custom functional interface, lambda target type | 2025-10-21 | `samples.MyFunctionalInterface` |
| [MethodReferenceDemo](src/main/java/functional/MethodReferenceDemo.java) | Method reference example: `Integer::parseInt` is a short way to write `s -> Integer.parseInt(s)`. | method reference, `Function<T, R>`, static method reference | 2025-10-21 | `samples.MethodReferences` |

### modern

| Class | What it shows | Keywords | First written | Old name |
|---|---|---|---|---|
| [NewerSyntax](src/main/java/modern/NewerSyntax.java) | Newer syntax that makes everyday code shorter: `var` (Java 10), switch expressions (Java 14) and text blocks (Java 15). | var, local variable type inference, switch expression, arrow case, yield, no fall-through, text block, String.formatted | 2026-11-24 | - |
| [RecordWithChecks](src/main/java/modern/RecordWithChecks.java) | A record that checks its values when it is created, using a compact constructor. | record, compact constructor, validation, IllegalArgumentException, defensive copy, List.copyOf, immutable | 2026-11-24 | - |
| [SequencedCollectionsDemo](src/main/java/modern/SequencedCollectionsDemo.java) | Sequenced collections (Java 21): the same methods give the first item, the last item and a reversed view of a List and a LinkedHashSet. A LinkedHashMap gets firstEntry, lastEntry and reversed. | SequencedCollection, SequencedMap, getFirst, getLast, reversed, addFirst, addLast, firstEntry, lastEntry | 2026-11-24 | - |
| [ShapeArea](src/main/java/modern/ShapeArea.java) | Java 21 style: a sealed interface, records, and pattern matching for `switch`. | sealed interface, permits, record, pattern matching for switch, record patterns, when condition | 2026-11-24 | - |

### streams

| Class | What it shows | Keywords | First written | Old name |
|---|---|---|---|---|
| [EmployeeGroupingAndSorting](src/main/java/streams/EmployeeGroupingAndSorting.java) | Common stream questions on an employee list: count and list the names per department, split into two groups, join the names into one string, and sort by several fields. | groupingBy, counting, mapping, partitioningBy, joining, Comparator.thenComparing | 2026-11-24 | - |
| [EmployeeStreamPractice](src/main/java/streams/EmployeeStreamPractice.java) | Stream practice on employees: a 10% raise, and upper-case names of employees earning more than 100. (The highest salary per department is in `HighestPaidByDepartment`.) | map, filter, Stream.toList | 2026-04-07 | `testers.Test9` |
| [FlattenListOfLists](src/main/java/streams/FlattenListOfLists.java) | Flatten a list of lists into one list, and also remove duplicates and sort it. | flatMap, `List<List<T>>`, distinct, sorted, Stream.toList | 2025-10-21, 2026-11-23 | `testers.Basic` and Questions.md coding question 12 |
| [GroupWordsByFirstLetter](src/main/java/streams/GroupWordsByFirstLetter.java) | Group words by their first letter. | Collectors.groupingBy, classifier function, charAt | 2026-04-07 | `testers.AugSeventeen` |
| [HighestPaidByDepartment](src/main/java/streams/HighestPaidByDepartment.java) | The highest-paid employee in each department. | groupingBy, maxBy, Comparator.comparing, Optional, record as map key | 2026-01-24 | `testers.Main` |
| [LongestAndShortestString](src/main/java/streams/LongestAndShortestString.java) | The longest and shortest string in a list, and the list sorted by length. | Stream.max, Stream.min, Comparator.comparing, String::length, sorted | 2026-01-24 | `testers.Aumni` |
| [NthHighestSalary](src/main/java/streams/NthHighestSalary.java) | Find the Nth highest salary, and the employees who earn it. Salaries can repeat. | distinct, sorted, Comparator.reverseOrder, skip, findFirst, Optional, filter | 2026-11-24 | - |
| [ReduceExamples](src/main/java/streams/ReduceExamples.java) | Sum, product and average of a list using streams. | Stream.reduce, identity value, mapToInt, average, OptionalDouble | 2025-10-21 | `testers.Sun` |
| [SalaryStatistics](src/main/java/streams/SalaryStatistics.java) | Salary statistics with streams: the average, the total with reduce, and the average by gender. | mapToInt, average, reduce, Collectors.groupingBy, Collectors.averagingInt | 2026-04-07 | `samples.LambdaExample` |
| [SortMapByKeyAndValue](src/main/java/streams/SortMapByKeyAndValue.java) | Sort a map by key, and by value from highest to lowest. | Map.Entry.comparingByKey, comparingByValue, Collectors.toMap, LinkedHashMap, TreeMap | 2025-10-21 | `testers.MahDay` |

### arrays

| Class | What it shows | Keywords | First written | Old name |
|---|---|---|---|---|
| [ArrayIntersection](src/main/java/arrays/ArrayIntersection.java) | The values found in both arrays, each one listed once. | HashSet, retainAll, set intersection, unique values | 2026-04-07 | `march.SampleCodes` |
| [ArrayRotation](src/main/java/arrays/ArrayRotation.java) | Rotate an array k places to the left or to the right. | array rotation, modulo index `(i + k) % n`, extra result array | 2026-04-07 | `march.SampleCodes` |
| [CountOccurrences](src/main/java/arrays/CountOccurrences.java) | Count how many times each number appears, with a loop and with streams. | HashMap.getOrDefault, Collectors.groupingBy, Collectors.counting, Comparator.reverseOrder | 2023-01-05 | `testers.Occurrences` |
| [FindDuplicates](src/main/java/arrays/FindDuplicates.java) | Find the numbers that appear more than once. | HashSet, Set.add return value, Stream.filter, duplicates | 2025-10-21 | `testers.AugSeventeen` |
| [KthNonRepeatedElement](src/main/java/arrays/KthNonRepeatedElement.java) | Find the k-th element that appears only once. | LinkedHashMap, frequency count, insertion order | 2026-04-16 | `march.SampleCodes` |
| [MaximumSubarraySum](src/main/java/arrays/MaximumSubarraySum.java) | Find the largest sum of a contiguous subarray (Kadane's algorithm). | Kadane's algorithm, dynamic programming, running sum, O(n) | 2026-11-23 | Questions.md, coding question 8 |
| [MergeSortedArrays](src/main/java/arrays/MergeSortedArrays.java) | Merge two sorted arrays in place: nums1 has empty slots at the end for the numbers of nums2. | two pointers from the end, in-place merge, O(m + n) | 2026-11-23 | Questions.md, coding question 7 |
| [MissingNumber](src/main/java/arrays/MissingNumber.java) | The array has the numbers 1 to n with one missing. Find the missing number. | count array, numbers 1 to n, sum formula n * (n + 1) / 2 | 2026-04-07 | `testers.Basic` |
| [MoveZeros](src/main/java/arrays/MoveZeros.java) | Move all zeros to the front or to the end of an array, keeping the order of the other numbers. | two pointers, write index, in-place, stable order | 2025-10-21, 2026-04-07 | `testers.AugSeventeen` |
| [SecondHighest](src/main/java/arrays/SecondHighest.java) | Find the second highest number in an array (a repeated number counts once), with streams and with one loop. | Stream.distinct, sorted, skip, one pass, Integer.MIN_VALUE | 2025-10-21, 2026-11-23 | `testers.MahDay`, `testers.Test1` and Questions.md coding question 1 |
| [SeparateZerosAndOnes](src/main/java/arrays/SeparateZerosAndOnes.java) | Rearrange an array of only 0s and 1s in place, so all 0s come first and all 1s after them. | two pointers, in-place, binary array, O(n) | 2026-11-23 | Questions.md, coding question 3 |
| [SpiralMatrix](src/main/java/arrays/SpiralMatrix.java) | Print a matrix in spiral order: right along the top, down the right side, left along the bottom, up the left side, then repeat on the inner part. | 2D array, matrix traversal, four boundaries | 2026-04-07 | `march.SpiralMatrix` |
| [TopKFrequentElements](src/main/java/arrays/TopKFrequentElements.java) | Return the k numbers that appear most often in an array. | frequency map, PriorityQueue, min-heap of size k, O(n log k) | 2026-11-23 | Questions.md, coding question 9 |
| [TwoSum](src/main/java/arrays/TwoSum.java) | Two Sum: find the positions of the two numbers that add up to a target. | HashMap, complement lookup, one pass, O(n) | 2026-01-24 | `testers.Aumni`; the same answer was also Questions.md coding question 10 |

### strings

| Class | What it shows | Keywords | First written | Old name |
|---|---|---|---|---|
| [AnagramCheck](src/main/java/strings/AnagramCheck.java) | Check whether two words are anagrams (made of the same letters). | anagram, char array, Arrays.sort, Arrays.equals | 2023-01-05 | `testers.Q` |
| [CharacterCount](src/main/java/strings/CharacterCount.java) | Count each character of a string, first with a loop and then with streams. | HashMap.getOrDefault, String.chars, Collectors.groupingBy, Collectors.counting | 2023-01-05 | `testers.Occurrences` |
| [CountVowels](src/main/java/strings/CountVowels.java) | Count the vowels in a string. | Set.of, method reference, String.chars, toLowerCase | 2026-04-16 | `march.T` |
| [DuplicateCharactersInWords](src/main/java/strings/DuplicateCharactersInWords.java) | Find the characters that appear more than once across a list of words. | flatMapToInt, Collectors.groupingBy, Collectors.counting, Set | 2026-11-23 | Questions.md, coding question 4 |
| [DuplicateWords](src/main/java/strings/DuplicateWords.java) | Find the words that appear more than once in a sentence, and the first character that repeats in a string. | duplicate words, first repeated character, Set.add returns false, LinkedHashSet, groupingBy, counting, ignore case | 2026-11-24 | - |
| [FirstNonRepeatedCharacter](src/main/java/strings/FirstNonRepeatedCharacter.java) | Find the first character that doesn't repeat, in three ways. | LinkedHashMap counts, Collectors.groupingBy, indexOf, lastIndexOf, O(n) vs O(n^2) | 2025-10-21, 2026-04-16, 2026-11-23 | `testers.Trying`, `march.SampleCodes` and Questions.md coding question 2 |
| [GroupAnagrams](src/main/java/strings/GroupAnagrams.java) | Group words that are made of the same letters, with a loop and with streams. | anagram, HashMap, computeIfAbsent, Collectors.groupingBy, sorted letters as key | 2026-04-07, 2026-11-23 | `march.SampleCodes` and Questions.md coding question 6 |
| [LongestCommonPrefix](src/main/java/strings/LongestCommonPrefix.java) | Find the longest prefix that all the words in an array share. | startsWith, shrinking prefix, horizontal scanning | 2026-11-23 | Questions.md, coding question 11 |
| [LongestSubstringWithoutRepeats](src/main/java/strings/LongestSubstringWithoutRepeats.java) | The length of the longest substring without repeating characters, in two ways. | sliding window, HashSet, HashMap of last positions, two pointers, O(n) | 2026-04-07, 2026-11-23 | `march.Q` and Questions.md coding question 5 |
| [Palindrome](src/main/java/strings/Palindrome.java) | Palindromes: check one word, and find all pairs of words that join into a palindrome. | palindrome, StringBuilder.reverse, two pointers, palindrome pairs | 2025-10-21, 2026-04-07 | `testers.Basic` and `march.SampleCodes` |
| [PangramCheck](src/main/java/strings/PangramCheck.java) | Check whether a sentence is a pangram (it uses every letter from a to z). | pangram, HashSet, alphabet a-z, 26 letters | 2025-10-21 | `testers.Q` |
| [ReplaceUnknownWords](src/main/java/strings/ReplaceUnknownWords.java) | Hide the words of a message that aren't in a dictionary by replacing them with "###". | HashSet lookup, split, String.join, case-insensitive match, Codility practice | 2026-04-07 | `march.Q` |
| [ReplaceWithGrowingDollars](src/main/java/strings/ReplaceWithGrowingDollars.java) | Replace each match of a character with a growing number of '$' signs: the 1st becomes "$", the 2nd "$$", the 3rd "$$$", and so on. | StringBuilder, String.repeat, counter | 2025-10-21 | `testers.Trying` |
| [ReverseString](src/main/java/strings/ReverseString.java) | Reverse a string. | StringBuilder, loop from the end, String immutability | 2026-01-24 | `testers.Basic` |
| [ReverseWords](src/main/java/strings/ReverseWords.java) | Reverse the order of the words in a sentence ("Java is fun" becomes "fun is Java"), and the follow-up: reverse the letters of each word but keep the word order. | reverse words, split on spaces, trim, StringBuilder, Collections.reverse, String.join, Collectors.joining | 2026-11-24 | - |
| [StringRotation](src/main/java/strings/StringRotation.java) | Check if one string is a rotation of another: "erbottlewat" is "waterbottle" rotated. | string rotation, s1 + s1 trick, contains, length check | 2026-11-24 | - |
| [UncommonWords](src/main/java/strings/UncommonWords.java) | The words that appear in only one of two sentences. | List.removeAll, symmetric difference, copy before changing a list | 2023-01-05 | `testers.Q` |
| [UniqueCharactersInOrder](src/main/java/strings/UniqueCharactersInOrder.java) | The unique characters of a string, in the order they first appear. | HashSet, filter(set::add), encounter order, Stream.distinct | 2025-10-21 | `testers.Occurrences` |
| [WordCount](src/main/java/strings/WordCount.java) | Count how many times each word appears in a sentence. | split, HashMap.getOrDefault, word frequency, case-sensitive | 2025-10-21 | `testers.Test1` |

### datastructures

| Class | What it shows | Keywords | First written | Old name |
|---|---|---|---|---|
| [BinaryTree](src/main/java/datastructures/BinaryTree.java) | Binary tree basics: visit the nodes in different orders and level by level, find the height, check a binary search tree (BST), and find the lowest common ancestor. | TreeNode, recursion, inorder, preorder, postorder, level order (BFS with a queue), height, BST check, lowest common ancestor (LCA) | 2026-11-24 | - |
| [LRUCache](src/main/java/datastructures/LRUCache.java) | Design a data structure that follows the rules of a Least Recently Used (LRU) cache, without extending any Map class. | LRU cache, HashMap, doubly linked list, dummy head and tail, O(1) | 2026-04-07 | `model.LRUCache` and `model.Test` |
| [LRUCacheWithLinkedHashMap](src/main/java/datastructures/LRUCacheWithLinkedHashMap.java) | The short way to build an LRU cache: extend LinkedHashMap, keep the entries in access order, and remove the least recently used entry when the cache is full. LRUCache builds the same thing by hand. | LRU cache, LinkedHashMap, access order, removeEldestEntry, serialVersionUID | 2026-11-24 | - |
| [LinkedListCycle](src/main/java/datastructures/LinkedListCycle.java) | Detect a loop (cycle) in a linked list, in two ways: (1) Remember the visited nodes in a set: simple, but uses O(n) extra space. (2) Floyd's cycle algorithm (tortoise and hare): uses O(1) extra space. | Floyd's cycle detection, tortoise and hare, slow and fast pointers, HashSet | 2026-04-07 | `march.FloydCycleAlgo` |
| [MinStack](src/main/java/datastructures/MinStack.java) | A stack that can also give its smallest item in O(1) time: push, pop, top and getMin. | min stack, two stacks, ArrayDeque as a stack, O(1) getMin | 2026-11-24 | - |
| [ModifyListWhileLooping](src/main/java/datastructures/ModifyListWhileLooping.java) | Removing items from a list in a for-each loop usually throws ConcurrentModificationException. Three safe ways to do it: Iterator.remove, removeIf and CopyOnWriteArrayList. | ConcurrentModificationException, fail-fast iterator, Iterator.remove, removeIf, CopyOnWriteArrayList, fail-safe, remove(Object) vs remove(int) | 2026-11-24 | - |
| [QueueUsingTwoStacks](src/main/java/datastructures/QueueUsingTwoStacks.java) | Build a queue (first in, first out) using two stacks (last in, first out). | queue, two stacks, FIFO, LIFO, amortized O(1), ArrayDeque | 2026-11-24 | - |
| [ReverseLinkedList](src/main/java/datastructures/ReverseLinkedList.java) | Reverse a linked list. | LinkedList, Collections.reverse, three pointers (prev, curr, next) | 2025-10-21 | `testers.Basic` |
| [SinglyLinkedList](src/main/java/datastructures/SinglyLinkedList.java) | Your own singly linked list (no java.util.LinkedList): reverse it with a loop and with recursion, find the middle node, and remove the Nth node from the end. | Node class, prev/curr/next pointers, recursion, slow and fast pointers, dummy node | 2026-11-24 | - |
| [ValidParentheses](src/main/java/datastructures/ValidParentheses.java) | Check that every bracket in a string is closed by the same type, in the right order. Supports (), {} and []. | stack, ArrayDeque, Deque, push and pop, bracket matching | 2026-04-07 | `march.CheckParenthesis` |

### numbers

| Class | What it shows | Keywords | First written | Old name |
|---|---|---|---|---|
| [ClassicNumberPrograms](src/main/java/numbers/ClassicNumberPrograms.java) | Classic number programs: check for a prime number, a palindrome number and an Armstrong number, and work out a big factorial with BigInteger. | prime number, square root limit, palindrome number, reverse digits, Armstrong number, factorial, BigInteger, long overflow | 2026-11-24 | - |
| [Fibonacci](src/main/java/numbers/Fibonacci.java) | Fibonacci numbers (0, 1, 1, 2, 3, 5, 8, ...): each one is the sum of the two before it. Shown with a loop, with recursion, with recursion that saves its answers (memoization), and with Stream.iterate. | Fibonacci, loop, recursion, memoization, HashMap, Stream.iterate, O(n) vs O(2^n) | 2026-11-24 | - |
| [ReverseInteger](src/main/java/numbers/ReverseInteger.java) | Reverse the digits of a number: 12345 becomes 54321. | modulo 10, integer division, digits, int overflow | 2026-04-07 | `testers.Basic` |
| [RoundToTwoDecimals](src/main/java/numbers/RoundToTwoDecimals.java) | Round 8.547 to two decimal places (8.55). | Math.round, decimal places, BigDecimal for money | 2026-04-07 | `testers.A` |

### concurrency

| Class | What it shows | Keywords | First written | Old name |
|---|---|---|---|---|
| [CallServicesInParallel](src/main/java/concurrency/CallServicesInParallel.java) | Call 3 slow services at the same time, wait for all of them with allOf, and use a fallback value when a service fails or is too slow. | CompletableFuture, supplyAsync, allOf, join, exceptionally, completeOnTimeout, orTimeout, fallback value, own thread pool | 2026-11-24 | - |
| [CounterRace](src/main/java/concurrency/CounterRace.java) | Many threads add 1 to the same counter. Why `volatile` is not enough, and what to use instead. | race condition, count++ is not atomic, volatile, AtomicInteger, synchronized | 2026-11-24 | - |
| [DeadlockDemo](src/main/java/concurrency/DeadlockDemo.java) | Deadlock: two threads wait for each other forever. How it happens, how to find it, and how to fix it. | deadlock, lock order, synchronized, ThreadMXBean, thread dump, jstack, tryLock | 2026-11-24 | - |
| [LatchAndBarrier](src/main/java/concurrency/LatchAndBarrier.java) | Make threads wait for each other with a CountDownLatch (wait until N tasks are done) and a CyclicBarrier (N threads wait at a meeting point, then all go on together). | CountDownLatch, countDown, await, CyclicBarrier, barrier action, reusable, Semaphore | 2026-11-24 | - |
| [OddEvenPrinter](src/main/java/concurrency/OddEvenPrinter.java) | Two threads print the numbers 1 to 10 in order: one prints the odd numbers, the other prints the even numbers. | wait and notify, synchronized, shared lock, taking turns between threads | 2026-11-24 | - |
| [ParallelProductAndSum](src/main/java/concurrency/ParallelProductAndSum.java) | Use 2 threads: one multiplies 1 to 10 (1 x 2 x 3 x ... x 10), the other adds 20 to 30 (20 + 21 + ... + 30). Print product + sum. | CompletableFuture, supplyAsync, thenCombine, join, ForkJoinPool | 2026-04-16 | `march.T` |
| [ProducerConsumer](src/main/java/concurrency/ProducerConsumer.java) | Producer-consumer: one thread puts items into a box with limited space, another thread takes them out. Shown with a BlockingQueue (the easy way) and with wait and notify (the classic way). | BlockingQueue, ArrayBlockingQueue, put and take, wait and notifyAll, bounded buffer | 2026-11-24 | - |
| [VirtualThreadsDemo](src/main/java/concurrency/VirtualThreadsDemo.java) | Virtual threads (Java 21): run thousands of tasks that wait, compare with a normal thread pool, and limit how many tasks call a slow service at the same time. | virtual threads, Executors.newVirtualThreadPerTaskExecutor, fixed thread pool, Semaphore, tasks that wait (I/O) | 2026-11-24 | - |

### exceptions

| Class | What it shows | Keywords | First written | Old name |
|---|---|---|---|---|
| [FinallyReturn](src/main/java/exceptions/FinallyReturn.java) | Tricky question: the try block throws, catch returns -2 and finally returns -3. What does the method return? | try/catch/finally, return in finally, swallowed exception | 2025-10-21 | `testers.Trying` |
| [TryWithResources](src/main/java/exceptions/TryWithResources.java) | try-with-resources closes resources for you, even when an exception is thrown. Shown with our own AutoCloseable class and our own (custom) exception. | try-with-resources, AutoCloseable, close order, suppressed exception, custom exception, checked vs unchecked exception | 2026-11-24 | - |

### design

| Class | What it shows | Keywords | First written | Old name |
|---|---|---|---|---|
| [BuilderPattern](src/main/java/design/BuilderPattern.java) | The Builder pattern: build an object with many optional fields step by step, with readable code instead of one long constructor. | Builder pattern, telescoping constructor, method chaining, fluent API, immutable object, Lombok @Builder | 2026-11-24 | - |
| [Singletons](src/main/java/design/Singletons.java) | Three thread-safe ways to write a Singleton (a class that has only one object). | Singleton, double-checked locking, volatile, holder class, enum singleton | 2026-11-24 | - |
| [SolidPrinciples](src/main/java/design/SolidPrinciples.java) | The five SOLID principles. Each one has a small example that breaks the rule, the problem that causes, and the same example written the right way. | SOLID, single responsibility (SRP), open/closed (OCP), Liskov substitution (LSP), interface segregation (ISP), dependency inversion (DIP), Strategy pattern, constructor injection | 2026-11-24 | - |

### model

| Class | What it shows | Keywords | First written | Old name |
|---|---|---|---|---|
| [Department](src/main/java/model/Department.java) | A department. Records generate equals() and hashCode(), so it works as a map key when grouping employees by department. | record, equals and hashCode, map key | 2026-01-24 | - |
| [Employee](src/main/java/model/Employee.java) | An employee, used by the employee examples in the `streams` package. | record, immutable data class, accessor methods | 2026-01-24 | - |
