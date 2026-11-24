package streams;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * The longest and shortest string in a list, and the list sorted by length.
 *
 * <p>Keywords: Stream.max, Stream.min, Comparator.comparing, String::length, sorted.
 *
 * <p>First written: 2026-01-24 (old location: {@code testers.Aumni}).
 */
public class LongestAndShortestString {

    public static void main(String[] args) {
        List<String> myList = List.of("laptop", "mobile", "abcd", "abcz", "ballbat");
        Optional<String> longest = myList.stream().max(Comparator.comparing(String::length));
        Optional<String> shortest = myList.stream().min(Comparator.comparing(String::length));
        List<String> sortedByLength = myList.stream()
                .sorted(Comparator.comparing(String::length))
                .toList();

        System.out.println("Longest: " + longest.orElse(""));   // ballbat
        System.out.println("Shortest: " + shortest.orElse("")); // abcd
        System.out.println("Sorted by length: " + sortedByLength);
    }
}
