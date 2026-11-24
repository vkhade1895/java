package arrays;

import java.util.Arrays;

/**
 * Rearrange an array of only 0s and 1s in place, so all 0s come first and all 1s after them.
 *
 * <p>Keywords: two pointers, in-place, binary array, O(n).
 *
 * <p>First written: 2026-11-23 (old location: Questions.md, coding question 3).
 */
public class SeparateZerosAndOnes {

    public static void main(String[] args) {
        int[] arr = {1, 0, 1, 0, 0, 1, 1, 0};
        sortZerosAndOnes(arr);
        // Expected: [0, 0, 0, 0, 1, 1, 1, 1]
        System.out.println("0s and 1s separated: " + Arrays.toString(arr));
    }

    // left looks for a 1 from the start, right looks for a 0 from the end; swap them and go on
    public static void sortZerosAndOnes(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            while (arr[left] == 0 && left < right) {
                left++;
            }
            while (arr[right] == 1 && left < right) {
                right--;
            }
            if (left < right) {
                arr[left] = 0;
                arr[right] = 1;
                left++;
                right--;
            }
        }
    }
}
