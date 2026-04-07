package testers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AugSeventeen {
    //        move all zeros to front of the array
    public static void main(String[] args) {
        int []a= {1,0,3,0,4,0,9,6};
        int index = a.length -1 ; // 7

        for (int i = a.length -1; i >= 0 ; i--){

            if (a[i] != 0){
                System.out.println("value:" +a[i]);
                a[index] = a[i];
                System.out.println("index:" +index);
                index--;
            }
        }
        Arrays.stream(a).forEach(e-> System.out.print("op:" +e));
        System.out.println("index:::::" + index);
        while (index >= 0) {
            a[index] = 0;
            index--;
        }
        Arrays.stream(a).forEach(e-> System.out.println("op:" +e));

//        findRepeatedIntegers();

//        groupingByCharacters();

        //        move all zeros to end of the array
        moveZerosToEnd();
    }

    private static void moveZerosToEnd() {
        int []a= {1,0,3,0,4,0,9,0};
        int index = 0 ;

        for(int i=0; i < a.length ; i++){
            if(a[i]!= 0){
                a[index] = a[i];
                index ++;
                System.out.println("inside loop:" + index);
            }
        }
        Arrays.stream(a).forEach(e-> System.out.print("Zeros to end::" + e));

        while(index < a.length){
            a[index] = 0;
            index ++;
        }
        Arrays.stream(a).forEach(e-> System.out.println("Final::" + e));
        Arrays.sort(a);
    }

    private static void groupingByCharacters() {
        Map<Character, List<String>> collect = Stream.of("apple", "apricot", "banana", "brown", "cat", "crown").collect(Collectors.groupingBy(e -> e.charAt(0)));
        System.out.println("collect characters:" + collect);
    }

    private static void findRepeatedIntegers() {
        int []a= {1,5,8,9,3,5,7,7,8};

        HashSet<Integer> integerHashSet = new HashSet<>();
        List<Integer> collected = Arrays.stream(a).boxed().filter(e -> !integerHashSet.add(e)).toList();

        collected.forEach(e -> System.out.println("my list" + e));
    }
}
