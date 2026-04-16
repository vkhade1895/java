package march;

import java.util.*;

public class SampleCodes {

    public static void main(String[] args) {
        groupAnagramsTogether();
        detectLoopInLinkedList();
        printMatrixInSpiralOrder();
        intersectionOfTwoArrays();
        inputArrayOfStringsIdentifyPalindrome();
        leftRotateAnArray();
        rightRotateAnArray();
    }

    private static void rightRotateAnArray() {


    }

    private static void inputArrayOfStringsIdentifyPalindrome() {
//        Input: words = ["abcd","dcba","lls","s","sssll"]
//        Output: [[0,1],[1,0],[3,2],[2,4]]
//        Explanation:
//        The palindromes are ["dcbaabcd","abcddcba","slls","llssssll"]
        List<String> stringList = List.of("abcd", "dcba", "lls", "s", "sssll");
        List<List<Integer>> results = new ArrayList<>();

        for (int i = 0; i < stringList.size(); i++) {
            for (int j = 0; j < stringList.size(); j++) {
                if (i == j) continue; // Skip same index
                String combined = stringList.get(i) + stringList.get(j);
                if (isPalindrome(combined)) {
                    results.add(Arrays.asList(i, j));
                }
            }
        }

        System.out.println("Palindrome pairs: " + results);
    }

    private static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }

    private static void intersectionOfTwoArrays() {
//        Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
//        Output: [9,4]
//        Explanation: [4,9] is also accepted.
        int[] nums1= {4,9,5};
        int[] nums2 ={9,4,9,8,4};
        Set<Integer> set1 = new HashSet<>();
        for (Integer n : nums1) {
            set1.add(n);
        }
        Set<Integer> set2 = new HashSet<>();
        for (Integer n : nums2) {
            set2.add(n);
        }
        set1.retainAll(set2);
        int[] output = new int[set1.size()];
        int idx = 0;
        for (int s : set1) {
            output[idx++] = s;
        }
//        return output;
        System.out.println("output of intersection::" + Arrays.toString(output));
    }

    private static void printMatrixInSpiralOrder() {
//        Input: mat[][] = [[1,   2,   3,   4],
//                          [5,    6,   7,   8],
//                          [9,   10,  11,  12],
//                          [13,  14,  15,  16]]
//        Output: [1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10]
//        Refer Spiral matrix class
    }

    private static void detectLoopInLinkedList() {
    // Refer floyd cycle algorithm
    }

    private static void groupAnagramsTogether() {
        String[] input = {"eat", "tea", "tan", "ate", "nat", "bat"};

        Map<String, List<String>> map = new HashMap<>();

        for (String s : input) {
            // Convert the string to a character array, sort it, and convert back to a string
            char[] charArray = s.toCharArray();
            Arrays.sort(charArray);
            String sortedKey = String.valueOf(charArray); // The key for the HashMap

            // If the key is not in the map, add it with a new list
            // computeIfAbsent is a concise way to do this
            map.computeIfAbsent(sortedKey, k -> new ArrayList<>()).add(s);
        }
        map.forEach((k,v) ->System.out.println("key::" + k + "valuee::" + v));
        // Return all the values from the map, which are the grouped anagrams
        System.out.println("output anagrams::" + new ArrayList<>(map.values()));
    }

    private static void leftRotateAnArray(){
//        There is an integer array in ascending order with distinct values
//        Prior to being passed to your function nums is possibly left rotated at an unknown index x
//        such that the resulting array is

//        for example [0,1, 2 ,4,5,6,7] might be rotated by 3 indices and become [4,5,6,7, 0,1,2]
        int[] arr = {0,1, 2 ,4,5,6,7};
        int k = 3;
        int n = arr.length;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = arr[(i + k) % n];
        }

        System.out.println("array op::" + Arrays.toString(result));
    }

    private static void findKthRepeatedElementInArray(){
        int k = 2;
        int[] array={1,2,1,2,3,4};
        // output = 4
     }

     private static void findFirstNonRepeatedCharacterInAString(){
        String str = "swiss";
//        op = 'w'
     }
}
