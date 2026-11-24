package strings;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Find the first character that doesn't repeat, in three ways.
 *
 * <p>Keywords: LinkedHashMap counts, Collectors.groupingBy, indexOf, lastIndexOf, O(n) vs O(n^2).
 *
 * <p>First written: 2025-10-21 (indexOf way), 2026-04-16 (counting way) and 2026-11-23
 * (streams way) (old location: {@code testers.Trying}, {@code march.SampleCodes} and
 * Questions.md coding question 2).
 */
public class FirstNonRepeatedCharacter {

    public static void main(String[] args) {
        usingCounts("swiss");       // w
        usingStreams("swiss");      // w
        usingIndexOf("intervienw"); // t
    }

    // Count each character in a LinkedHashMap (it keeps the original order),
    // then print the first one whose count is 1. O(n).
    private static void usingCounts(String str) {
        Map<Character, Integer> counts = new LinkedHashMap<>();
        for (char c : str.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
            if (entry.getValue() == 1) {
                System.out.println("First non-repeated character: " + entry.getKey());
                return;
            }
        }
        System.out.println("Every character in " + str + " repeats");
    }

    // The same counting idea with streams: groupingBy into a LinkedHashMap keeps the order
    private static void usingStreams(String input) {
        Character result = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new,
                        Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        System.out.println("First non-repeated character (streams): " + result);
    }

    // "intervienw" has two 'n's, so indexOf and lastIndexOf give different positions
    private static void usingIndexOf(String s) {
        System.out.println("indexOf('n'): " + s.indexOf('n'));         // 1
        System.out.println("lastIndexOf('n'): " + s.lastIndexOf('n')); // 8

        // A character appears only once when its first and last positions are the same. O(n^2).
        Optional<Character> first = s.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> s.indexOf(c) == s.lastIndexOf(c))
                .findFirst();
        System.out.println("First non-repeated character: " + first.orElse(null)); // t
    }
}
