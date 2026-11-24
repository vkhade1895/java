package arrays;

/**
 * Find the largest sum of a contiguous subarray (Kadane's algorithm).
 *
 * <p>Keywords: Kadane's algorithm, dynamic programming, running sum, O(n).
 *
 * <p>First written: 2026-11-23 (old location: Questions.md, coding question 8).
 */
public class MaximumSubarraySum {

    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Maximum subarray sum: " + maxSubArray(nums)); // 6, from [4, -1, 2, 1]
        System.out.println("All negative numbers: " + maxSubArray(new int[] {-3, -1, -2})); // -1
    }

    // At each number, either start a new subarray there or extend the current one,
    // whichever gives the bigger sum, and remember the best sum seen so far.
    public static int maxSubArray(int[] nums) {
        int maxSoFar = nums[0];
        int currentMax = nums[0];
        for (int i = 1; i < nums.length; i++) {
            currentMax = Math.max(nums[i], currentMax + nums[i]);
            maxSoFar = Math.max(maxSoFar, currentMax);
        }
        return maxSoFar;
    }
}
