package testers;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
        reverseString();
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
        String s1 ="rada";


        StringBuilder stringBuilder = new StringBuilder(s1);

        if (stringBuilder.reverse().toString().equals(s1)){
            System.out.println("String is palindrome:" + s1);
        }else {
            System.out.println("String is not a palindrome:" + s1);
        }
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
    }

    private static void reverseString(){
        String str ="mystring";

        String c = "";
        for (int i = str.length()-1; i >=0; i--) {
            c = c + str.charAt(i) ;
        }
        System.out.println("reversed:" + c);
    }

}
