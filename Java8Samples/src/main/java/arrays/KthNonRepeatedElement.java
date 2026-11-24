package arrays;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Find the k-th element that appears only once.
 *
 * <p>Keywords: LinkedHashMap, frequency count, insertion order.
 *
 * <p>First written: 2026-04-16 (old location: {@code march.SampleCodes}).
 */
public class KthNonRepeatedElement {

    // Input: [1, 2, 1, 2, 3, 4], k = 2
    // Output: 4 (the elements that appear once are 3 and 4)
    public static void main(String[] args) {
        int[] array = {1, 2, 1, 2, 3, 4};
        int k = 2;
        Map<Integer, Integer> counts = new LinkedHashMap<>(); // keeps the original order
        for (int n : array) {
            counts.put(n, counts.getOrDefault(n, 0) + 1);
        }
        int found = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getValue() == 1) {
                found++;
                if (found == k) {
                    System.out.println("Non-repeated element number " + k + ": " + entry.getKey());
                    return;
                }
            }
        }
        System.out.println("There are fewer than " + k + " non-repeated elements");
    }
}
