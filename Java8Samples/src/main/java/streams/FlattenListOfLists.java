package streams;

import java.util.List;

/**
 * Flatten a list of lists into one list, and also remove duplicates and sort it.
 *
 * <p>Keywords: flatMap, {@code List<List<T>>}, distinct, sorted, Stream.toList.
 *
 * <p>First written: 2025-10-21 and 2026-11-23 (old location: {@code testers.Basic} and
 * Questions.md coding question 12).
 */
public class FlattenListOfLists {

    public static void main(String[] args) {
        // flatMap joins the inner lists into one list
        List<List<String>> lists = List.of(List.of("a", "z"), List.of("q", "p"));
        List<String> flattened = lists.stream().flatMap(List::stream).toList();
        System.out.println("Flattened list: " + flattened); // [a, z, q, p]

        // Flatten, remove duplicates and sort: [1, 2, 3, 4, 5, 6, 7]
        List<List<Integer>> nestedList =
                List.of(List.of(1, 2, 3), List.of(3, 4, 5), List.of(5, 6, 7));
        List<Integer> distinctSorted = nestedList.stream()
                .flatMap(List::stream)
                .distinct()
                .sorted()
                .toList();
        System.out.println("Flattened, distinct and sorted: " + distinctSorted);
    }
}
