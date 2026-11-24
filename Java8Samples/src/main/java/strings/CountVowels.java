package strings;

import java.util.Set;

/**
 * Count the vowels in a string.
 *
 * <p>Keywords: Set.of, method reference, String.chars, toLowerCase.
 *
 * <p>First written: 2026-04-16 (old location: {@code march.T}).
 */
public class CountVowels {

    public static void main(String[] args) {
        System.out.println("Number of vowels in Canada: " + countVowels("Canada")); // 3
    }

    static long countVowels(String str) {
        Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u');
        return str.toLowerCase().chars()
                .mapToObj(c -> (char) c)
                .filter(vowels::contains)
                .count();
    }
}
