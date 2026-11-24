package strings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The length of the longest substring without repeating characters, in two ways.
 *
 * <p>Keywords: sliding window, HashSet, HashMap of last positions, two pointers, O(n).
 *
 * <p>First written: 2026-04-07 and 2026-11-23 (old location: {@code march.Q} and
 * Questions.md coding question 5).
 */
public class LongestSubstringWithoutRepeats {

    public static void main(String[] args) {
        System.out.println("Longest substring without repeats in pwwkewq: "
                + lengthOfLongestSubstring("pwwkewq")); // 4 ("kewq")
        System.out.println("Same answer with a last-seen index map: "
                + lengthUsingLastSeenIndex("pwwkewq")); // 4
    }

    // Way 1: grow the window on the right. When a character repeats,
    // shrink the window from the left until the repeat is gone.
    static int lengthOfLongestSubstring(String str) {
        int left = 0;
        int right = 0;
        int maxLength = 0;
        Set<Character> window = new HashSet<>();
        while (right < str.length()) {
            if (!window.contains(str.charAt(right))) {
                window.add(str.charAt(right));
                maxLength = Math.max(maxLength, right - left + 1);
                right++;
            } else {
                window.remove(str.charAt(left));
                left++;
            }
        }
        return maxLength;
    }

    // Way 2: remember where each character was last seen, so the left side can
    // jump straight past a repeat instead of moving one step at a time.
    static int lengthUsingLastSeenIndex(String s) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int maxLength = 0;
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (lastSeen.containsKey(c)) {
                // Math.max: never move left backwards (for example in "abba")
                left = Math.max(left, lastSeen.get(c) + 1);
            }
            lastSeen.put(c, right);
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }
}
