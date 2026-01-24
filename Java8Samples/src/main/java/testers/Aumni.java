package testers;

import java.util.*;

public class Aumni {

    public static void main(String[] args) {
//        Input: nums = [2,7,11,15], target = 9
//        Output: [0,1]
//        Explanation: Because nums[0] + nums[1] == 9, we return [0, 1]
        int[] numArray = {2,7,11,15};
        int target = 9;
        int[] twoSum = twoSum(numArray, 9);
        System.out.println("twoSum:" + Arrays.toString(twoSum));
        returnMaxStringLength();
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (numMap.containsKey(complement)) {
                return new int[] {numMap.get(complement), i};
            }
            numMap.put(nums[i], i);
        }
        return new int[] {};  // return an empty array if no solution is found
    }

    private static void returnMaxStringLength() {
        List<String> myList = new ArrayList<>();
        myList.add("laptop");
        myList.add("mobile");
        myList.add("abcd");
        myList.add("ballbat");

        Optional<String> string = myList.stream().max(Comparator.comparing(String::length));
        System.out.println("longest:" + string);
    }
}
