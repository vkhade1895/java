package strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Palindromes: check one word, and find all pairs of words that join into a palindrome.
 *
 * <p>Keywords: palindrome, StringBuilder.reverse, two pointers, palindrome pairs.
 *
 * <p>First written: 2025-10-21 (check) and 2026-04-07 (pairs)
 * (old location: {@code testers.Basic} and {@code march.SampleCodes}).
 */
public class Palindrome {

    public static void main(String[] args) {
        System.out.println("Is radar a palindrome? " + isPalindromeUsingReverse("radar")); // true
        palindromePairs();
    }

    // A palindrome reads the same backwards, like radar, level or racecar
    private static boolean isPalindromeUsingReverse(String s) {
        return new StringBuilder(s).reverse().toString().equals(s);
    }

    // Find all pairs (i, j) where words[i] + words[j] is a palindrome.
    // Input: ["abcd", "dcba", "lls", "s", "sssll"]
    // Output: [[0, 1], [1, 0], [2, 4], [3, 2]]
    // The palindromes are ["abcddcba", "dcbaabcd", "llssssll", "slls"]
    private static void palindromePairs() {
        List<String> words = List.of("abcd", "dcba", "lls", "s", "sssll");
        List<List<Integer>> results = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            for (int j = 0; j < words.size(); j++) {
                if (i != j && isPalindromeUsingTwoPointers(words.get(i) + words.get(j))) {
                    results.add(List.of(i, j));
                }
            }
        }
        System.out.println("Palindrome pairs: " + results);
    }

    // Compare characters from both ends, moving inward
    private static boolean isPalindromeUsingTwoPointers(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }
}
