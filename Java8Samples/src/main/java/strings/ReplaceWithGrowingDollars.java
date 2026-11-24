package strings;

/**
 * Replace each match of a character with a growing number of '$' signs:
 * the 1st becomes "$", the 2nd "$$", the 3rd "$$$", and so on.
 *
 * <p>Keywords: StringBuilder, String.repeat, counter.
 *
 * <p>First written: 2025-10-21 (old location: {@code testers.Trying}).
 */
public class ReplaceWithGrowingDollars {

    public static void main(String[] args) {
        System.out.println(replaceWithGrowingDollars("tomorrow", 'o')); // t$m$$rr$$$w
    }

    // Input: "tomorrow", 'o'  Output: "t$m$$rr$$$w"
    private static String replaceWithGrowingDollars(String s, char c) {
        StringBuilder result = new StringBuilder();
        int count = 1;
        for (char value : s.toCharArray()) {
            if (value == c) {
                result.append("$".repeat(count));
                count++;
            } else {
                result.append(value);
            }
        }
        return result.toString();
    }
}
