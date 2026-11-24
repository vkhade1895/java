package numbers;

/**
 * Reverse the digits of a number: 12345 becomes 54321.
 *
 * <p>Keywords: modulo 10, integer division, digits, int overflow.
 *
 * <p>First written: 2026-04-07 (old location: {@code testers.Basic}).
 */
public class ReverseInteger {

    public static void main(String[] args) {
        System.out.println("Reversed integer: " + reverseInteger(12345)); // 54321
    }

    // num % 10 gives the last digit, num / 10 removes it.
    // Watch out: a big number can overflow when reversed (for example 1999999999).
    private static int reverseInteger(int num) {
        int reversed = 0;
        while (num != 0) {
            int lastDigit = num % 10;
            reversed = reversed * 10 + lastDigit;
            num /= 10;
        }
        return reversed;
    }
}
