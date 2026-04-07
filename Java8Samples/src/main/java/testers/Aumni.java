package testers;

import java.util.*;
import java.util.stream.Stream;

public class Aumni {

    public static void main(String[] args) {
//        Input: nums = [2,7,11,15], target = 9
//        Output: [0,1]
//        Explanation: Because nums[0] + nums[1] == 9, we return [0, 1]
        int[] numArray = {2,7,11,15};
        int target = 9;
        int[] twoSum = twoSum(numArray, 9);
        System.out.println("twoSum:" + Arrays.toString(twoSum));
//        returnMaxStringLength();
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];  // 9 - 2 = 7 ; 9 - 7 = 2 ; 9 -11 = 2 ; 9 -15 = 6
            if (numMap.containsKey(complement)) {
                numMap.forEach((key, value) -> System.out.println("key :" + key + "value:" + value));
                return new int[] {numMap.get(complement), i};
            }
            numMap.put(nums[i], i); // <2 , 0 >
        }

        return new int[] {};  // return an empty array if no solution is found
    }

    private static void returnMaxStringLength() {
        List<String> myList = new ArrayList<>();
        myList.add("laptop");
        myList.add("mobile");
        myList.add("abcd");
        myList.add("abcz");
        myList.add("ballbat");

        Optional<String> string = myList.stream().max(Comparator.comparing(String::length));
        Optional<String> stringMin = myList.stream().min(Comparator.comparing(String::length));
        System.out.println("Min string:" + stringMin);
        Stream<String> sorted = myList.stream().sorted(Comparator.comparing(e -> e.length()));
        System.out.println("longest:" + string);
        System.out.println("longest sorted:" + sorted);
    }
}
