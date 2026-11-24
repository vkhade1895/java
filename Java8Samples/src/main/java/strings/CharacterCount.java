package strings;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Count each character of a string, first with a loop and then with streams.
 *
 * <p>Keywords: HashMap.getOrDefault, String.chars, Collectors.groupingBy, Collectors.counting.
 *
 * <p>First written: 2023-01-05 (old location: {@code testers.Occurrences}).
 */
public class CharacterCount {

    public static void main(String[] args) {
        String str = "Pelican is a fintech company.";

        Map<Character, Integer> counts = new HashMap<>();
        for (char c : str.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        System.out.println("Character counts with a loop: " + counts);

        Map<Character, Long> streamCounts = str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("Character counts with streams: " + streamCounts);
    }
}
