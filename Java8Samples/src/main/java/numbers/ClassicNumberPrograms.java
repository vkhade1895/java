package numbers;

import java.math.BigInteger;

/**
 * Classic number programs: check for a prime number, a palindrome number and an Armstrong
 * number, and work out a big factorial with BigInteger.
 *
 * <p>Keywords: prime number, square root limit, palindrome number, reverse digits, Armstrong
 * number, factorial, BigInteger, long overflow.
 *
 * <p>First written: 2026-11-24.
 */
public class ClassicNumberPrograms {

    public static void main(String[] args) {
        System.out.println("Is 29 prime? " + isPrime(29));                       // true
        System.out.println("Is 121 a palindrome? " + isPalindrome(121));         // true
        System.out.println("Is 153 an Armstrong number? " + isArmstrong(153));   // true
        System.out.println("25! = " + factorial(25));   // 15511210043330985984000000
    }

    // A prime number is bigger than 1 and can only be divided by 1 and by itself. We only
    // need to try divisors up to the square root: if n = a x b, then a or b is at most the
    // square root of n. (i is a long, so i * i can't overflow for a big n.)
    private static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    // A palindrome number reads the same both ways, like 121. Reverse the digits (as in
    // ReverseInteger) and compare. "reversed" is a long, so reversing a big int can't
    // overflow. For words, see strings/Palindrome.
    private static boolean isPalindrome(int n) {
        if (n < 0) {
            return false;       // -121 backwards is 121-
        }
        long reversed = 0;
        for (int rest = n; rest > 0; rest /= 10) {
            reversed = reversed * 10 + rest % 10;
        }
        return reversed == n;
    }

    // An Armstrong number is the sum of its digits, each raised to the power of the number
    // of digits: 153 = 1^3 + 5^3 + 3^3 = 1 + 125 + 27.
    private static boolean isArmstrong(int n) {
        int digits = String.valueOf(n).length();
        long sum = 0;
        for (int rest = n; rest > 0; rest /= 10) {
            sum += (long) Math.pow(rest % 10, digits);
        }
        return sum == n;
    }

    // 20! is the biggest factorial that fits in a long. 21! is too big, so a long would give
    // a wrong (negative) answer. BigInteger has no fixed size: it grows as the number grows.
    private static BigInteger factorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}
