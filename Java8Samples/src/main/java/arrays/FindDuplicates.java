package arrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Find the numbers that appear more than once.
 *
 * <p>Keywords: HashSet, Set.add return value, Stream.filter, duplicates.
 *
 * <p>First written: 2025-10-21 (old location: {@code testers.AugSeventeen}).
 */
public class FindDuplicates {

    // Set.add() returns false when the number is already in the set, so those are the repeats.
    // Input: [1, 5, 8, 9, 3, 5, 7, 7, 8]  Output: [5, 7, 8]
    public static void main(String[] args) {
        int[] a = {1, 5, 8, 9, 3, 5, 7, 7, 8};
        Set<Integer> seen = new HashSet<>();
        List<Integer> repeated = Arrays.stream(a).boxed()
                .filter(e -> !seen.add(e))
                .distinct()     // a number that appears 3 or more times is listed only once
                .toList();
        System.out.println("Repeated integers: " + repeated);
    }
}
