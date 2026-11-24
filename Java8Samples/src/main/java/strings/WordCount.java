package strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Count how many times each word appears in a sentence.
 *
 * <p>Keywords: split, HashMap.getOrDefault, word frequency, case-sensitive.
 *
 * <p>First written: 2025-10-21 (old location: {@code testers.Test1}).
 */
public class WordCount {

    public static void main(String[] args) {
        String test = "This is is done DONE by VISHAL vishal";
        System.out.println("Word counts: " + countWords(test)); // is=2, every other word 1
    }

    // Words are case-sensitive here, so "DONE" and "done" are different words
    private static Map<String, Integer> countWords(String text) {
        Map<String, Integer> counts = new HashMap<>();
        for (String word : text.split(" ")) {
            counts.put(word, counts.getOrDefault(word, 0) + 1);
        }
        return counts;
    }
}
