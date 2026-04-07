package testers;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Occurrences {

    public static void main(String[] args) {
        // Example array
        int[] array = {1, 2, 3, 2, 3, 1, 1};

        // Create a HashMap to store the occurrences of each integer
        HashMap<Integer, Integer> occurrences = new HashMap<>();

        // Iterate through the array and count the occurrences of each integer
        for (int num : array) {
            if (occurrences.containsKey(num)) {
                occurrences.put(num, occurrences.get(num) + 1);
            } else {
                occurrences.put(num, 1);
            }
        }
        System.out.print("map-" + occurrences);
//        java8IntOccurence();
       /* char[] arrayStr = {'a', 'b', 'c', 'a', 'b', 'c', 'a', 'b', 'c'};

        Map<Character, Long> occurrences1 = java.util.stream.IntStream.range(0, arrayStr.length)
                .mapToObj(i -> arrayStr[i]).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        System.out.print("STR MAP-" + occurrences1);*/

//        stringOcurences();
        getStringOccurence();
    }

    public static void java8IntOccurence() {
        Integer[] array = {1, 2, 3, 2, 3, 1, 3, 2, 1, 2};

        // create a stream from the array
        Map<Integer, Long> occurrences = Arrays.stream(array)
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()));
/*
        array[x]
         2 , 6 when array[1] + array[3]
         3 , 3 + when array[2]
         2 ,
        */
       /* Comparator<Integer> c = (o1, o2) -> {
            if (o2 > o1)
                return 1;
            else
                return -1;
        };*/
        Arrays.sort(array, (o1, o2) -> {
            if (o2 > o1)
                return 1;
            else
                return -1;
        });
        System.out.println("\nint occ-" + occurrences);

        for (int a : array) {
            System.out.println("array is -" + a);
        }

    }

    public static void stringOcurences() {
        System.out.printf("");
        System.out.println();
        String str = "Pelican is a fintech company.";
        int count = 0;
        HashMap<Character, Integer> h = h = new HashMap<>();
        Map<Character, Long> counts = null;

        for (int i = 0; i < str.length(); i++) {

            if (h.containsKey(str.charAt(i))) {
                h.put(str.charAt(i), h.get(str.charAt(i)) + 1);
            } else {
                h.put(str.charAt(i), 1);
            }
        }

        for(char x: str.toCharArray()){
            if(h.containsKey(x)){
                h.put(x,h.getOrDefault(x,1) + 1);
            }
        }

        counts = str.chars()
                .mapToObj(c -> (char) c)
                .peek(e -> System.out.print(" val-" + e))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


        System.out.println("map j8-" + counts);
        System.out.println("count of {c}" + count + "\n map-" + h);

    }

    private static void getStringOccurence(){
        String str= "My name is Pelican";
        Set<Character> characterSet = new HashSet<>();
        List<Character> characterList = str.chars().mapToObj(e -> (char) e).filter(characterSet::add).toList();
        System.out.println("characterList::" + characterList);
    }
}

