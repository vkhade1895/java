package strings;

import java.util.Arrays;

/**
 * Check whether two words are anagrams (made of the same letters).
 *
 * <p>Keywords: anagram, char array, Arrays.sort, Arrays.equals.
 *
 * <p>First written: 2023-01-05 (old location: {@code testers.Q}).
 */
public class AnagramCheck {

    public static void main(String[] args) {
        System.out.println("Race and Care are anagrams: " + isAnagram("Race", "Care")); // true
    }

    // Anagrams have the same letters: sort the letters of both words and compare them
    public static boolean isAnagram(String str1, String str2) {
        char[] charArray1 = str1.toLowerCase().toCharArray();
        char[] charArray2 = str2.toLowerCase().toCharArray();
        if (charArray1.length != charArray2.length) {
            return false;
        }
        Arrays.sort(charArray1);
        Arrays.sort(charArray2);
        return Arrays.equals(charArray1, charArray2);
    }
}
