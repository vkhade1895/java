package strings;

/**
 * Reverse a string.
 *
 * <p>Keywords: StringBuilder, loop from the end, String immutability.
 *
 * <p>First written: 2026-01-24 (old location: {@code testers.Basic}).
 */
public class ReverseString {

    public static void main(String[] args) {
        System.out.println("Reversed string: " + reverseString("mystring")); // gnirtsym
    }

    // StringBuilder is used because "text + char" creates a new String every time
    private static String reverseString(String str) {
        StringBuilder reversed = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            reversed.append(str.charAt(i));
        }
        return reversed.toString();
    }
}
