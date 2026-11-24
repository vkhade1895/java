package strings;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The unique characters of a string, in the order they first appear.
 *
 * <p>Keywords: HashSet, filter(set::add), encounter order, Stream.distinct.
 *
 * <p>First written: 2025-10-21 (old location: {@code testers.Occurrences}).
 */
public class UniqueCharactersInOrder {

    // Set.add() is true only the first time, so filter(seen::add) keeps first appearances.
    // Input: "My name is Pelican"  Output: [M, y,  , n, a, m, e, i, s, P, l, c]
    public static void main(String[] args) {
        String str = "My name is Pelican";
        Set<Character> seen = new HashSet<>();
        List<Character> uniqueCharacters = str.chars()
                .mapToObj(c -> (char) c)
                .filter(seen::add)
                .toList();
        System.out.println("Unique characters in order: " + uniqueCharacters);
        // Simpler way, same result: str.chars().mapToObj(c -> (char) c).distinct().toList()
    }
}
