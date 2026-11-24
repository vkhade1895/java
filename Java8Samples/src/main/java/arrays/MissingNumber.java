package arrays;

/**
 * The array has the numbers 1 to n with one missing. Find the missing number.
 *
 * <p>Keywords: count array, numbers 1 to n, sum formula n * (n + 1) / 2.
 *
 * <p>First written: 2026-04-07 (old location: {@code testers.Basic}).
 */
public class MissingNumber {

    public static void main(String[] args) {
        System.out.println("Missing integer: " + findMissingInteger()); // 6
    }

    // Count each number, then find the one that was never seen.
    // (Another way: n * (n + 1) / 2 minus the sum of the array.)
    private static int findMissingInteger() {
        int[] arr = {8, 2, 4, 5, 3, 7, 1};
        int n = arr.length + 1; // 8
        int[] count = new int[n + 1]; // index 0 is not used
        for (int value : arr) {
            count[value]++;
        }
        for (int i = 1; i <= n; i++) {
            if (count[i] == 0) {
                return i;
            }
        }
        return -1;
    }
}
