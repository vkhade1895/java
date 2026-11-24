package arrays;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Count how many times each number appears, with a loop and with streams.
 *
 * <p>Keywords: HashMap.getOrDefault, Collectors.groupingBy, Collectors.counting,
 * Comparator.reverseOrder.
 *
 * <p>First written: 2023-01-05 (old location: {@code testers.Occurrences}).
 */
public class CountOccurrences {

    public static void main(String[] args) {
        countIntegers();
        countIntegersWithStreams();
    }

    // Input: [1, 2, 3, 2, 3, 1, 1]  Output: {1=3, 2=2, 3=2}
    private static void countIntegers() {
        int[] array = {1, 2, 3, 2, 3, 1, 1};
        Map<Integer, Integer> occurrences = new HashMap<>();
        for (int num : array) {
            occurrences.put(num, occurrences.getOrDefault(num, 0) + 1);
        }
        System.out.println("Occurrences: " + occurrences);
    }

    private static void countIntegersWithStreams() {
        Integer[] array = {1, 2, 3, 2, 3, 1, 3, 2, 1, 2};
        Map<Integer, Long> occurrences = Arrays.stream(array)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("Occurrences with streams: " + occurrences);

        // A comparator must return 0 for equal values; Comparator.reverseOrder() does that
        Arrays.sort(array, Comparator.reverseOrder());
        System.out.println("Sorted in descending order: " + Arrays.toString(array));
    }
}
