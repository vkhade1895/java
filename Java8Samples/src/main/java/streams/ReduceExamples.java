package streams;

import java.util.List;
import java.util.OptionalDouble;

/**
 * Sum, product and average of a list using streams.
 *
 * <p>Keywords: Stream.reduce, identity value, mapToInt, average, OptionalDouble.
 *
 * <p>First written: 2025-10-21 (old location: {@code testers.Sun}).
 */
public class ReduceExamples {

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 6, 7, 9, 2);

        // reduce(identity, accumulator): the identity must not change the result (0 for +, 1 for *)
        Integer sum = numbers.stream().reduce(0, (a, b) -> a + b);
        Integer product = numbers.stream().reduce(1, (a, b) -> a * b);
        OptionalDouble average = numbers.stream().mapToInt(e -> e).average();

        System.out.println("Sum: " + sum);                       // 25
        System.out.println("Product: " + product);               // 756
        System.out.println("Average: " + average.getAsDouble()); // 5.0
    }
}
