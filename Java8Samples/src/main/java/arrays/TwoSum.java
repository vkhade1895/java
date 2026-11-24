package arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Two Sum: find the positions of the two numbers that add up to a target.
 *
 * <p>Keywords: HashMap, complement lookup, one pass, O(n).
 *
 * <p>First written: 2026-01-24 (old location: {@code testers.Aumni}; the same answer was
 * also Questions.md coding question 10).
 */
public class TwoSum {

    public static void main(String[] args) {
        // Input: nums = [2, 7, 11, 15], target = 9
        // Output: [0, 1], because nums[0] + nums[1] == 9
        int[] numArray = {2, 7, 11, 15};
        int target = 9;
        int[] result = twoSum(numArray, target);
        System.out.println("twoSum: " + Arrays.toString(result));
    }

    // For each number, check whether the number it needs (target - number) was already seen
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numToIndex = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i]; // 9 - 2 = 7 (not seen yet), 9 - 7 = 2 (seen at 0)
            if (numToIndex.containsKey(complement)) {
                return new int[] {numToIndex.get(complement), i};
            }
            numToIndex.put(nums[i], i);
        }
        return new int[] {}; // no solution found
    }
}
