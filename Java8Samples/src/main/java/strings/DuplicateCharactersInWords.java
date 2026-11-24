package strings;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Find the characters that appear more than once across a list of words.
 *
 * <p>Keywords: flatMapToInt, Collectors.groupingBy, Collectors.counting, Set.
 *
 * <p>First written: 2026-11-23 (old location: Questions.md, coding question 4).
 */
public class DuplicateCharactersInWords {

    public static void main(String[] args) {
        List<String> words = List.of("apple", "banana", "cherry");

        // flatMapToInt joins the characters of all the words into one stream; then count them
        Set<Character> duplicates = words.stream()
                .flatMapToInt(String::chars)
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        System.out.println("Characters repeated across the words: " + duplicates);
    }
}
