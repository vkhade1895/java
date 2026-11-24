package streams;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Group words by their first letter.
 *
 * <p>Keywords: Collectors.groupingBy, classifier function, charAt.
 *
 * <p>First written: 2026-04-07 (old location: {@code testers.AugSeventeen}).
 */
public class GroupWordsByFirstLetter {

    // Input: [apple, apricot, banana, brown, cat, crown]
    // Output: {a=[apple, apricot], b=[banana, brown], c=[cat, crown]}
    public static void main(String[] args) {
        Map<Character, List<String>> byFirstLetter =
                Stream.of("apple", "apricot", "banana", "brown", "cat", "crown")
                        .collect(Collectors.groupingBy(word -> word.charAt(0)));
        System.out.println("Words grouped by first letter: " + byFirstLetter);
    }
}
