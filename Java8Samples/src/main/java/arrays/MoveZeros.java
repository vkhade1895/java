package arrays;

import java.util.Arrays;

/**
 * Move all zeros to the front or to the end of an array, keeping the order of the other numbers.
 *
 * <p>Keywords: two pointers, write index, in-place, stable order.
 *
 * <p>First written: 2025-10-21 (to the front) and 2026-04-07 (to the end)
 * (old location: {@code testers.AugSeventeen}).
 */
public class MoveZeros {

    public static void main(String[] args) {
        moveZerosToFront();
        moveZerosToEnd();
    }

    // Input: [1, 0, 3, 0, 4, 0, 9, 6]  Output: [0, 0, 0, 1, 3, 4, 9, 6]
    private static void moveZerosToFront() {
        int[] a = {1, 0, 3, 0, 4, 0, 9, 6};
        int index = a.length - 1; // next free position, filled from the right
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] != 0) {
                a[index] = a[i];
                index--;
            }
        }
        while (index >= 0) {
            a[index] = 0;
            index--;
        }
        System.out.println("Zeros moved to the front: " + Arrays.toString(a));
    }

    // Input: [1, 0, 3, 0, 4, 0, 9, 0]  Output: [1, 3, 4, 9, 0, 0, 0, 0]
    private static void moveZerosToEnd() {
        int[] a = {1, 0, 3, 0, 4, 0, 9, 0};
        int index = 0; // next free position, filled from the left
        for (int i = 0; i < a.length; i++) {
            if (a[i] != 0) {
                a[index] = a[i];
                index++;
            }
        }
        while (index < a.length) {
            a[index] = 0;
            index++;
        }
        System.out.println("Zeros moved to the end: " + Arrays.toString(a));
    }
}
