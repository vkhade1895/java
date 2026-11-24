package strings;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Hide the words of a message that aren't in a dictionary by replacing them with "###".
 *
 * <p>Keywords: HashSet lookup, split, String.join, case-insensitive match, Codility practice.
 *
 * <p>First written: 2026-04-07 (old location: {@code march.Q}).
 */
public class ReplaceUnknownWords {

    public static void main(String[] args) {
        String dictionary = "The Codility coders test";
        String message = "Codility tests the coders codes";
        System.out.println("Message: " + replaceUnknownWordsWithHash(dictionary, message));
        // Codility ### the coders ###
    }

    // Replace every word that isn't in the dictionary with "###" (upper/lower case is ignored)
    static String replaceUnknownWordsWithHash(String dictionary, String message) {
        Set<String> knownWords = Arrays.stream(dictionary.split(" "))
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        String[] words = message.split(" ");
        for (int i = 0; i < words.length; i++) {
            if (!knownWords.contains(words[i].toLowerCase())) {
                words[i] = "###";
            }
        }
        return String.join(" ", words);
    }
}
