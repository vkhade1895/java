package strings;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The words that appear in only one of two sentences.
 *
 * <p>Keywords: LinkedHashSet, removeAll, symmetric difference, copy before changing a set.
 *
 * <p>First written: 2023-01-05 (old location: {@code testers.Q}).
 */
public class UncommonWords {

    public static void main(String[] args) {
        uncommonWords("I live in A B", "I live in "); // [A, B]
    }

    // Print the words that are in only one of the two sentences.
    // A LinkedHashSet keeps each word only once, in the order it first appears.
    private static void uncommonWords(String str1, String str2) {
        Set<String> str1Words = new LinkedHashSet<>(Arrays.asList(str1.split(" ")));
        Set<String> str2Words = new LinkedHashSet<>(Arrays.asList(str2.split(" ")));
        Set<String> str1WordsCopy = new LinkedHashSet<>(str1Words);

        str1Words.removeAll(str2Words);     // keep the words that are only in str1
        str2Words.removeAll(str1WordsCopy); // keep the words that are only in str2

        Set<String> uncommonWords = new LinkedHashSet<>(str1Words);
        uncommonWords.addAll(str2Words);
        System.out.println("Uncommon words: " + uncommonWords);
    }
}
