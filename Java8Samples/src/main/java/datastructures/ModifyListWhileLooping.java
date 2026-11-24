package datastructures;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Removing items from a list in a for-each loop usually throws ConcurrentModificationException.
 * Three safe ways to do it: Iterator.remove, removeIf and CopyOnWriteArrayList.
 *
 * <p>Keywords: ConcurrentModificationException, fail-fast iterator, Iterator.remove, removeIf,
 * CopyOnWriteArrayList, fail-safe, remove(Object) vs remove(int).
 *
 * <p>First written: 2026-11-24.
 */
public class ModifyListWhileLooping {

    public static void main(String[] args) {
        wrongWay();
        withIterator();
        withRemoveIf();
        withCopyOnWriteList();
    }

    private static List<Integer> oneToSix() {
        return new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
    }

    // A for-each loop uses an iterator. The iterator sees that the list was changed without
    // it, and throws on its next step. This is called "fail-fast".
    // Not guaranteed: if you remove the second-last item, the loop just ends, with no
    // exception, and never checks the last item ([1, 3, 4, 6] ends as [1, 3, 6]).
    // (n is an Integer, so remove(n) removes the value n, not the item at index n.)
    private static void wrongWay() {
        List<Integer> numbers = oneToSix();
        try {
            for (Integer n : numbers) {
                if (n % 2 == 0) {
                    numbers.remove(n);
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("for-each + list.remove(): ConcurrentModificationException");
        }
    }

    // Way 1: remove through the iterator itself, so the iterator knows about the change.
    private static void withIterator() {
        List<Integer> numbers = oneToSix();
        Iterator<Integer> iterator = numbers.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() % 2 == 0) {
                iterator.remove();
            }
        }
        System.out.println("Iterator.remove(): " + numbers);      // [1, 3, 5]
    }

    // Way 2 (Java 8, the simplest): removeIf() does the loop for you.
    private static void withRemoveIf() {
        List<Integer> numbers = oneToSix();
        numbers.removeIf(n -> n % 2 == 0);
        System.out.println("removeIf(): " + numbers);             // [1, 3, 5]
    }

    // Way 3: CopyOnWriteArrayList makes a new copy of its array on every change, and the loop
    // keeps reading the old copy, so it never throws ("fail-safe"). It is thread-safe and
    // good when reads are many and changes are few (like a list of listeners), but slow when
    // a big list changes often.
    private static void withCopyOnWriteList() {
        List<Integer> numbers = new CopyOnWriteArrayList<>(oneToSix());
        for (Integer n : numbers) {
            if (n % 2 == 0) {
                numbers.remove(n);
            }
        }
        System.out.println("CopyOnWriteArrayList: " + numbers);   // [1, 3, 5]
    }
}
