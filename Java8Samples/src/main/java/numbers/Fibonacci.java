package numbers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Fibonacci numbers (0, 1, 1, 2, 3, 5, 8, ...): each one is the sum of the two before it.
 * Shown with a loop, with recursion, with recursion that saves its answers (memoization),
 * and with Stream.iterate.
 *
 * <p>Keywords: Fibonacci, loop, recursion, memoization, HashMap, Stream.iterate, O(n) vs
 * O(2^n).
 *
 * <p>First written: 2026-11-24.
 */
public class Fibonacci {

    // The answers that withMemo() has saved so far: n -> fib(n).
    private static final Map<Integer, Long> MEMO = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("fib(10) with a loop: " + withLoop(10));            // 55
        System.out.println("fib(10) with recursion: " + withRecursion(10));    // 55
        System.out.println("fib(50) with memo: " + withMemo(50));              // 12586269025
        // [0, 1, 1, 2, 3, 5, 8, 13, 21, 34]
        System.out.println("First 10 with Stream.iterate: " + firstNumbers(10));
    }

    // Keep only the last two numbers. O(n) time and O(1) memory: the usual answer.
    // A long holds up to fib(92). fib(93) is too big and comes out negative.
    private static long withLoop(int n) {
        long current = 0;       // fib(0)
        long next = 1;          // fib(1)
        for (int i = 0; i < n; i++) {
            long sum = current + next;
            current = next;
            next = sum;
        }
        return current;         // after n steps, current is fib(n)
    }

    // Short, but it works out the same numbers again and again: fib(5) works out fib(3)
    // twice and fib(2) three times. O(2^n): fine for 10, far too slow for 50.
    private static long withRecursion(int n) {
        return n <= 1 ? n : withRecursion(n - 1) + withRecursion(n - 2);
    }

    // The same recursion, but each answer is saved the first time, so it is worked out only
    // once. O(n). (Don't use MEMO.computeIfAbsent() here: a HashMap throws
    // ConcurrentModificationException when the function you pass in changes the map.)
    private static long withMemo(int n) {
        if (n <= 1) {
            return n;
        }
        Long saved = MEMO.get(n);
        if (saved != null) {
            return saved;
        }
        long result = withMemo(n - 1) + withMemo(n - 2);
        MEMO.put(n, result);
        return result;
    }

    // Stream.iterate makes the pairs (0, 1), (1, 1), (1, 2), (2, 3), ... and we keep the
    // first number of each pair.
    private static List<Long> firstNumbers(int count) {
        return Stream.iterate(new long[] {0, 1}, pair -> new long[] {pair[1], pair[0] + pair[1]})
                .limit(count)
                .map(pair -> pair[0])
                .toList();
    }
}
