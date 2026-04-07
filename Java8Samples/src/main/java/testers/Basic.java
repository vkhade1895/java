package testers;

import java.util.*;
import java.util.stream.Collectors;

public class Basic {

    public static void main(String[] args) {
        List<List<String>> lists = new ArrayList<>();
        List<String> myList = new ArrayList<>();
        myList.add("a");
        myList.add("z");
        myList.add("q");
        myList.add("p");
        lists.add(myList);

        List<String> collect = lists.stream().flatMap(e -> e.stream()).collect(Collectors.toList());
        collect.forEach(System.out::println);
        //        palindrome();
//        reverseLinkedList();
//        reverseString();
        int missingInteger = findMissingInteger();
        System.out.println("missing integer is::" + missingInteger);

//        reverseInteger();
    }

    private static int reverseInteger() {

        int num = 12345;
        int reversedInt = 0;

        while (num != 0){
            int lastDigit = num % 10; // 5 , 4
            reversedInt = reversedInt * 10 + lastDigit; // 5 , 54
            num /= 10; // 1234, 123
        }
        System.out.println("reversed integer::::" + reversedInt);
        return reversedInt;
    }

    private static void reverseLinkedList() {
        LinkedList<String> stringLinkedList = new LinkedList<>();
        stringLinkedList.add("a");
        stringLinkedList.add("b");
        stringLinkedList.add("q");
        stringLinkedList.add("p");
        for (String s : stringLinkedList) {
            System.out.println("original:"+ s);
        }
        Collections.reverse(stringLinkedList);

        stringLinkedList.forEach(System.out::println);
    }

    private static void palindrome() {
        String s1 ="radar"; // level, wow , racecar
        char[] charArray = s1.toCharArray();
        Arrays.sort(charArray);


        StringBuilder stringBuilder = new StringBuilder(s1);

        if (stringBuilder.reverse().toString().equals(s1)){
            System.out.println("String is palindrome:" + s1);
        }else {
            System.out.println("String is not a palindrome:" + s1);
        }
    }

    private static void reverseString(){
        String str ="mystring";

        String c = "";
        for (int i = str.length()-1; i >=0; i--) {
            c = c + str.charAt(i) ;
        }
        System.out.println("reversed:" + c);
    }

    private static int findMissingInteger(){
        int[] arr = {8, 2, 4, 5, 3, 7, 1};

        int n = arr.length + 1; // 8

        // Create hash array of size n+1
        int[] hash = new int[n + 1]; // 9

        // Store frequencies of elements
        for (int i = 0; i < n - 1; i++) {
            hash[arr[i]]++;
        }

        Arrays.stream(hash).forEach(e -> System.out.println("hash value:" + e));
        System.out.println("hash size :" + hash.length);
        // Find the missing number
        for (int i = 1; i <= n; i++) {
            if (hash[i] == 0) {
                return i;
            }
        }
        return -1;
    }

}
