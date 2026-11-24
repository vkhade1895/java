package arrays;

import java.util.Arrays;

/**
 * Merge two sorted arrays in place: nums1 has empty slots at the end for the numbers of nums2.
 *
 * <p>Keywords: two pointers from the end, in-place merge, O(m + n).
 *
 * <p>First written: 2026-11-23 (old location: Questions.md, coding question 7).
 */
public class MergeSortedArrays {

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0}; // 3 real numbers + 3 empty slots
        int[] nums2 = {2, 5, 6};
        merge(nums1, 3, nums2, 3);
        System.out.println("Merged: " + Arrays.toString(nums1)); // [1, 2, 2, 3, 5, 6]
    }

    // Fill nums1 from the back with the bigger of the two last numbers,
    // so no number in nums1 is overwritten before it has been moved.
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;    // last real number in nums1
        int p2 = n - 1;    // last number in nums2
        int p = m + n - 1; // last slot in nums1
        while (p1 >= 0 && p2 >= 0) {
            if (nums1[p1] > nums2[p2]) {
                nums1[p--] = nums1[p1--];
            } else {
                nums1[p--] = nums2[p2--];
            }
        }
        // Copy what is left of nums2 (what is left of nums1 is already in place)
        while (p2 >= 0) {
            nums1[p--] = nums2[p2--];
        }
    }
}
