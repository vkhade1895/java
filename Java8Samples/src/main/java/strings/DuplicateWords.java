package strings;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Find the words that appear more than once in a sentence, and the first character that
 * repeats in a string.
 *
 * <p>Keywords: duplicate words, first repeated character, Set.add returns false,
 * LinkedHashSet, groupingBy, counting, ignore case.
 *
 * <p>First written: 2026-11-24.
 */
public class DuplicateWords {

    public static void main(String[] args) {
        String sentence = "Java is fun and java is fast";
        System.out.println("Duplicate words: " + duplicateWords(sentence));       // [java, is]
        System.out.println("With streams: " + duplicateWordsWithStreams(sentence));
        System.out.println("First repeated character: "
                + firstRepeatedCharacter("programming"));                          // r
    }

    // Set.add() returns false when the item is already in the set, so that word is a
    // duplicate. toLowerCase() makes "Java" and "java" the same word. A LinkedHashSet keeps
    // the duplicates in the order they were found.
    private static Set<String> duplicateWords(String sentence) {
        Set<String> seen = new HashSet<>();
        Set<String> duplicates = new LinkedHashSet<>();
        for (String word : sentence.toLowerCase().split("\\s+")) {
            if (!seen.add(word)) {
                duplicates.add(word);
            }
        }
        return duplicates;
    }

    // With streams: count each word, then keep the words that appear more than once.
    private static Set<String> duplicateWordsWithStreams(String sentence) {
        return Arrays.stream(sentence.toLowerCase().split("\\s+"))
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new,
                        Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    // The same Set.add() trick: the first character that is already in the set is the
    // answer. "programming" is p, r, o, g, then r again, so the answer is 'r'.
    // Returns null when no character repeats. (For the opposite question, see
    // FirstNonRepeatedCharacter.)
    private static Character firstRepeatedCharacter(String text) {
        Set<Character> seen = new HashSet<>();
        for (char c : text.toCharArray()) {
            if (!seen.add(c)) {
                return c;
            }
        }
        return null;
    }
}
