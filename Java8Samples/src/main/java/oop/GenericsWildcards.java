package oop;

import java.util.ArrayList;
import java.util.List;

/**
 * Generics with wildcards: {@code ? extends} to read from a list and {@code ? super} to add
 * to a list (PECS: Producer Extends, Consumer Super), a generic method, and type erasure.
 *
 * <p>Keywords: generics, wildcard, ? extends, ? super, PECS, invariance, generic method, type
 * erasure.
 *
 * <p>First written: 2026-11-24.
 */
public class GenericsWildcards {

    // A List<Integer> is NOT a List<Number>, even though an Integer is a Number. If it were,
    // you could add a Double to a list of Integers. Wildcards make methods more flexible.
    // The JDK uses them too: Collections.copy(List<? super T> dest, List<? extends T> src).

    // ? extends Number: the list gives us Numbers (it is a "producer"), so we can read them.
    // It accepts List<Integer>, List<Double> and List<Number>. We can't add to it (except
    // null), because Java doesn't know if it is a list of Integers or a list of Doubles.
    private static double sum(List<? extends Number> numbers) {
        double total = 0;
        for (Number n : numbers) {
            total += n.doubleValue();
        }
        return total;
    }

    // ? super Integer: the list takes Integers from us (it is a "consumer"), so we can add to
    // it. It accepts List<Integer>, List<Number> and List<Object>. Reading from it only gives
    // an Object.
    private static void addOneTwoThree(List<? super Integer> target) {
        target.add(1);
        target.add(2);
        target.add(3);
    }

    // A generic method: <T> lets one method work with any type, and the compiler still checks
    // the types, so the caller needs no cast.
    private static <T> T firstItem(List<T> list) {
        return list.get(0);
    }

    public static void main(String[] args) {
        System.out.println(sum(List.of(1, 2, 3)));      // 6.0
        System.out.println(sum(List.of(1.5, 2.5)));     // 4.0

        List<Number> numbers = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        addOneTwoThree(numbers);
        addOneTwoThree(objects);
        System.out.println(numbers + " " + objects);    // [1, 2, 3] [1, 2, 3]

        String first = firstItem(List.of("a", "b"));   // no cast needed
        System.out.println(first);                      // a

        // Type erasure: the compiler checks the generic types and then removes them. At run
        // time both lists are just an ArrayList, so this prints true.
        List<String> strings = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();
        System.out.println(strings.getClass() == integers.getClass());   // true
    }
}
