package strings;

/**
 * Find the longest prefix that all the words in an array share.
 *
 * <p>Keywords: startsWith, shrinking prefix, horizontal scanning.
 *
 * <p>First written: 2026-11-23 (old location: Questions.md, coding question 11).
 */
public class LongestCommonPrefix {

    public static void main(String[] args) {
        System.out.println("Common prefix: '"
                + longestCommonPrefix(new String[] {"flower", "flow", "flight"}) + "'"); // 'fl'
        System.out.println("Common prefix: '"
                + longestCommonPrefix(new String[] {"dog", "racecar", "car"}) + "'"); // ''
    }

    // Start with the first word as the prefix and cut off its last letter
    // until every other word starts with it.
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }
        return prefix;
    }
}
