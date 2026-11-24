package modern;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Sequenced collections (Java 21): the same methods give the first item, the last item and a
 * reversed view of a List and a LinkedHashSet. A LinkedHashMap gets firstEntry, lastEntry and
 * reversed.
 *
 * <p>Keywords: SequencedCollection, SequencedMap, getFirst, getLast, reversed, addFirst,
 * addLast, firstEntry, lastEntry.
 *
 * <p>First written: 2026-11-24.
 */
public class SequencedCollectionsDemo {

    public static void main(String[] args) {
        // List. Before Java 21: list.get(0) and list.get(list.size() - 1).
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3));
        System.out.println(numbers.getFirst() + " and " + numbers.getLast());   // 1 and 3
        numbers.addFirst(0);
        numbers.addLast(4);
        System.out.println(numbers);                    // [0, 1, 2, 3, 4]
        System.out.println(numbers.reversed());         // [4, 3, 2, 1, 0]

        // A LinkedHashSet keeps the order in which items were added, so it has a first and a
        // last item too. Before Java 21 you needed set.iterator().next() for the first one.
        LinkedHashSet<String> names = new LinkedHashSet<>(List.of("Asha", "Ravi", "Meena"));
        System.out.println(names.getFirst() + " and " + names.getLast());       // Asha and Meena

        // LinkedHashMap: the first and the last entry, and a reversed view of the map.
        LinkedHashMap<String, Integer> salaries = new LinkedHashMap<>();
        salaries.put("Asha", 900);
        salaries.put("Ravi", 700);
        System.out.println("First: " + salaries.firstEntry());   // First: Asha=900
        System.out.println("Last: " + salaries.lastEntry());     // Last: Ravi=700
        System.out.println(salaries.reversed());                 // {Ravi=700, Asha=900}

        // Watch out:
        // - On an empty list, getFirst() throws NoSuchElementException.
        // - reversed() doesn't copy anything. It is a view, so changes to the list show in it.
        // - A HashSet or a HashMap has no order, so it doesn't have these methods.
    }
}
