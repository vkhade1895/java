package functional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Sorting with an anonymous class vs a lambda, and lambdas that implement the
 * custom functional interface {@link MathOperation}.
 *
 * <p>Keywords: lambda expression, anonymous class, Comparator, method reference.
 *
 * <p>First written: 2025-10-21 (old location: {@code samples.LambdaExample}).
 */
public class LambdaDemo {

    public static void main(String[] args) {
        List<String> myList = Arrays.asList("kiwi", "apple", "peach", "orange");
        System.out.println("Original list: " + myList);

        // Before Java 8: a Comparator written as an anonymous class (ascending order)
        Collections.sort(myList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("Custom sorting in ascending order: " + myList);

        // Java 8: a Comparator as a one-line lambda (this one sorts in descending order)
        myList.sort((o1, o2) -> o2.compareTo(o1));
        System.out.println("Custom sort in descending order: " + myList);

        // Each lambda (or method reference) gives the body of the one abstract method, operate
        MathOperation addition = Integer::sum;
        MathOperation subtraction = (a, b) -> a - b;
        MathOperation multiplication = (a, b) -> a * b;
        MathOperation division = (a, b) -> (double) a / b; // cast, or 5 / 2 would be 2

        double addResult = addition.operate(5, 4);
        double subtractionResult = subtraction.operate(5, 4);
        double multiplicationResult = multiplication.operate(5, 4);
        double divisionResult = division.operate(5, 2);
        System.out.println("Addition output: " + addResult);
        System.out.println("Subtraction output: " + subtractionResult);
        System.out.println("Multiplication output: " + multiplicationResult);
        System.out.println("Division output: " + divisionResult);
    }
}
