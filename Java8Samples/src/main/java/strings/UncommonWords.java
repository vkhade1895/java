package strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The words that appear in only one of two sentences.
 *
 * <p>Keywords: List.removeAll, symmetric difference, copy before changing a list.
 *
 * <p>First written: 2023-01-05 (old location: {@code testers.Q}).
 */
public class UncommonWords {

    public static void main(String[] args) {
        uniqueWords("I live in A B", "I live in "); // [A, B]
    }

    // Print the words that are in only one of the two sentences
    private static void uniqueWords(String str1, String str2) {
        List<String> str1Words = new ArrayList<>(Arrays.asList(str1.split(" ")));
        List<String> str2Words = new ArrayList<>(Arrays.asList(str2.split(" ")));
        List<String> str1WordsCopy = new ArrayList<>(str1Words);

        str1Words.removeAll(str2Words);     // keep the words that are only in str1
        str2Words.removeAll(str1WordsCopy); // keep the words that are only in str2

        List<String> uniqueWords = new ArrayList<>(str1Words);
        uniqueWords.addAll(str2Words);
        System.out.println("Unique words: " + uniqueWords);
    }
}
