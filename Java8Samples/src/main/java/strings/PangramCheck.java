package strings;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Check whether a sentence is a pangram (it uses every letter from a to z).
 *
 * <p>Keywords: pangram, HashSet, alphabet a-z, 26 letters.
 *
 * <p>First written: 2025-10-21 (old location: {@code testers.Q}).
 */
public class PangramCheck {

    public static void main(String[] args) {
        List<String> sentences = List.of(
                "The quick brown fox jumps over the lazy dog", // a classic pangram
                "Pack my box with five dozen liquor jugs",     // another pangram
                "Hello world");                                // not a pangram
        for (String sentence : sentences) {
            System.out.println("'" + sentence + "' is a pangram: " + isPangram(sentence));
        }
    }

    // A pangram uses every letter from a to z at least once
    private static boolean isPangram(String str) {
        Set<Character> uniqueLetters = new HashSet<>();
        for (char ch : str.toLowerCase().toCharArray()) {
            if (ch >= 'a' && ch <= 'z') {
                uniqueLetters.add(ch);
            }
        }
        return uniqueLetters.size() == 26;
    }
}
