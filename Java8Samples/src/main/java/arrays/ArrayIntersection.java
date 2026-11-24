package arrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The values found in both arrays, each one listed once.
 *
 * <p>Keywords: HashSet, retainAll, set intersection, unique values.
 *
 * <p>First written: 2026-04-07 (old location: {@code march.SampleCodes}).
 */
public class ArrayIntersection {

    // Input: nums1 = [4, 9, 5], nums2 = [9, 4, 9, 8, 4]
    // Output: [4, 9] (any order is accepted)
    public static void main(String[] args) {
        int[] nums1 = {4, 9, 5};
        int[] nums2 = {9, 4, 9, 8, 4};
        Set<Integer> set1 = new HashSet<>();
        for (int n : nums1) {
            set1.add(n);
        }
        Set<Integer> set2 = new HashSet<>();
        for (int n : nums2) {
            set2.add(n);
        }
        set1.retainAll(set2); // keep only the values that are in both sets

        int[] output = new int[set1.size()];
        int idx = 0;
        for (int s : set1) {
            output[idx++] = s;
        }
        System.out.println("Intersection: " + Arrays.toString(output));
    }
}
