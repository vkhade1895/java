package arrays;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

/**
 * Find the second highest number in an array (a repeated number counts once),
 * with streams and with one loop.
 *
 * <p>Keywords: Stream.distinct, sorted, skip, one pass, Integer.MIN_VALUE.
 *
 * <p>First written: 2025-10-21 and 2026-11-23 (old location: {@code testers.MahDay},
 * {@code testers.Test1} and Questions.md coding question 1).
 */
public class SecondHighest {

    public static void main(String[] args) {
        secondHighestWithStreams(new int[] {1, 6, 9, 0, 2, 3});    // 6
        secondHighestWithStreams(new int[] {5, 2, 9, 9, 7, 3, 1}); // 7: the repeated 9 counts once

        int[] a = {1, 3, 6, 8, 2};
        System.out.println("Second highest: " + findSecondHighest(a)); // 6
    }

    // distinct() makes a repeated maximum like [5, 2, 9, 9, 7, 3, 1] give 7, not 9
    private static void secondHighestWithStreams(int[] a) {
        Optional<Integer> secondHighest = Arrays.stream(a).boxed()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst();
        System.out.println("Second highest element: " + secondHighest.orElse(null));
    }

    // Keep the highest and second highest while walking the array once.
    // Returns Integer.MIN_VALUE if there is no second highest (for example [5, 5]).
    private static int findSecondHighest(int[] a) {
        int highest = Integer.MIN_VALUE;
        int secondHighest = Integer.MIN_VALUE;
        for (int value : a) {
            if (value > highest) {
                secondHighest = highest;
                highest = value;
            } else if (value > secondHighest && value != highest) {
                secondHighest = value;
            }
        }
        return secondHighest;
    }
}
