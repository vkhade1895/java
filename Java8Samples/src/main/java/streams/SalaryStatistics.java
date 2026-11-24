package streams;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * Salary statistics with streams: the average, the total with reduce, and the average by gender.
 *
 * <p>Keywords: mapToInt, average, reduce, Collectors.groupingBy, Collectors.averagingInt.
 *
 * <p>First written: 2026-04-07 (old location: {@code samples.LambdaExample}).
 */
public class SalaryStatistics {

    public static void main(String[] args) {
        List<Worker> workers = List.of(
                new Worker("Male", 100),
                new Worker("Female", 1000),
                new Worker("Female", 50),
                new Worker("Male", 10)
        );

        OptionalDouble average = workers.stream().mapToInt(Worker::salary).average();
        System.out.println("Average salary: " + average.orElse(0)); // 290.0

        // The identity (first argument) of a sum must be 0; with 1 the total would be 1161
        Integer sum = workers.stream().map(Worker::salary).reduce(0, Integer::sum);
        System.out.println("Sum: " + sum); // 1160

        Map<String, Double> avgSalaryByGender = workers.stream()
                .collect(Collectors.groupingBy(
                        Worker::gender,
                        Collectors.averagingInt(Worker::salary)
                ));
        System.out.println("Average salary by gender: " + avgSalaryByGender);
    }

    // A small record with a gender field, only used in this example
    private record Worker(String gender, int salary) {
    }
}
