package march;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class T {

    public static void main(String[] args) {
//        2 threads ,
//                1 thread , multi 1..10 = 1x2x3x4
//        1 thread , sum of 20...30 = 20+21+22+23...30
//
//        muli + sum = output
//
//        Completeable future

        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        int thread1MultiplicationResult = IntStream.range(1, 10).reduce(1, (a, b) -> a * b);

//        completableFuture.thenAccept()

//        Count the number of vowels in the string
        String str ="Canada"; // 3
        Set<Character> mySet = new HashSet<>();
        mySet.add('a');
        mySet.add('e');
        mySet.add('i');
        mySet.add('o');

        long count = str.chars().mapToObj(e -> (char) e).filter(mySet::contains).count();
        System.out.println("number of vowels:" + count);

    }
}
