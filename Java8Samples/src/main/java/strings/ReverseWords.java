package strings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reverse the order of the words in a sentence ("Java is fun" becomes "fun is Java"), and
 * the follow-up: reverse the letters of each word but keep the word order.
 *
 * <p>Keywords: reverse words, split on spaces, trim, StringBuilder, Collections.reverse,
 * String.join, Collectors.joining.
 *
 * <p>First written: 2026-11-24.
 */
public class ReverseWords {

    public static void main(String[] args) {
        String sentence = "  Java   is fun ";
        System.out.println("[" + withLoop(sentence) + "]");          // [fun is Java]
        System.out.println("[" + withCollections(sentence) + "]");   // [fun is Java]
        System.out.println("[" + reverseEachWord(sentence) + "]");   // [avaJ si nuf]
    }

    // trim() removes the spaces at both ends, and split("\\s+") splits on one or more spaces,
    // so extra spaces don't make empty words. Then add the words from the last to the first.
    private static String withLoop(String sentence) {
        String[] words = sentence.trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            result.append(words[i]);
            if (i > 0) {
                result.append(' ');
            }
        }
        return result.toString();
    }

    // The same idea with library methods: reverse the list of words, then join them.
    private static String withCollections(String sentence) {
        List<String> words = Arrays.asList(sentence.trim().split("\\s+"));
        Collections.reverse(words);
        return String.join(" ", words);
    }

    // Follow-up: reverse the letters of each word, but keep the words in the same order.
    private static String reverseEachWord(String sentence) {
        return Arrays.stream(sentence.trim().split("\\s+"))
                .map(word -> new StringBuilder(word).reverse().toString())
                .collect(Collectors.joining(" "));
    }
}
