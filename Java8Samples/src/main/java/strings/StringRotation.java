package strings;

/**
 * Check if one string is a rotation of another: "erbottlewat" is "waterbottle" rotated.
 *
 * <p>Keywords: string rotation, s1 + s1 trick, contains, length check.
 *
 * <p>First written: 2026-11-24.
 */
public class StringRotation {

    public static void main(String[] args) {
        System.out.println(isRotation("waterbottle", "erbottlewat"));   // true
        System.out.println(isRotation("abcd", "cdab"));                 // true
        System.out.println(isRotation("abcd", "acbd"));                 // false
        System.out.println(isRotation("abcd", "bc"));                   // false
    }

    // A rotation moves some letters from the front to the back: "abcd" -> "bcda" -> "cdab".
    // Trick: every rotation of s1 is inside s1 + s1 ("abcdabcd" contains "bcda", "cdab" and
    // "dabc"). The lengths must match too, or "bc" would count, because "abcdabcd" also
    // contains "bc".
    private static boolean isRotation(String s1, String s2) {
        return s1.length() == s2.length() && (s1 + s1).contains(s2);
    }
}
