package datastructures;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Reverse a linked list.
 *
 * <p>Keywords: LinkedList, Collections.reverse, three pointers (prev, curr, next).
 *
 * <p>First written: 2025-10-21 (old location: {@code testers.Basic}).
 */
public class ReverseLinkedList {

    public static void main(String[] args) {
        List<String> list = new LinkedList<>(List.of("a", "b", "q", "p"));
        System.out.println("Original linked list: " + list);
        Collections.reverse(list);
        // For your own Node class: walk once with three pointers (prev, curr, next)
        // and point each node back to prev.
        System.out.println("Reversed linked list: " + list);
    }
}
