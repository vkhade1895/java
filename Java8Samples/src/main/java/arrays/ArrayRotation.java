package arrays;

import java.util.Arrays;

/**
 * Rotate an array k places to the left or to the right.
 *
 * <p>Keywords: array rotation, modulo index {@code (i + k) % n}, extra result array.
 *
 * <p>First written: 2026-04-07 (old location: {@code march.SampleCodes}).
 */
public class ArrayRotation {

    public static void main(String[] args) {
        leftRotateAnArray();
        rightRotateAnArray();
    }

    // Move every element k places to the left; the first k elements go to the end.
    // Input: [0, 1, 2, 4, 5, 6, 7], k = 3
    // Output: [4, 5, 6, 7, 0, 1, 2]
    private static void leftRotateAnArray() {
        int[] arr = {0, 1, 2, 4, 5, 6, 7};
        int k = 3;
        int n = arr.length;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = arr[(i + k) % n];
        }
        System.out.println("Left rotated by " + k + ": " + Arrays.toString(result));
    }

    // Move every element k places to the right; the last k elements come to the front.
    // Input: [1, 2, 3, 4, 5], k = 2
    // Output: [4, 5, 1, 2, 3]
    private static void rightRotateAnArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int k = 2;
        int n = arr.length;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[(i + k) % n] = arr[i];
        }
        System.out.println("Right rotated by " + k + ": " + Arrays.toString(result));
    }
}
