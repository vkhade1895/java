package datastructures;

import java.util.HashSet;
import java.util.Set;

/**
 * Detect a loop (cycle) in a linked list, in two ways:
 * <ol>
 *   <li>Remember the visited nodes in a set: simple, but uses O(n) extra space.</li>
 *   <li>Floyd's cycle algorithm (tortoise and hare): uses O(1) extra space.</li>
 * </ol>
 *
 * <p>Keywords: Floyd's cycle detection, tortoise and hare, slow and fast pointers, HashSet.
 *
 * <p>First written: 2026-04-07 (old location: {@code march.FloydCycleAlgo}).
 */
public class LinkedListCycle {

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    // Way 1: if we reach a node we have already seen, there is a loop
    static boolean hasLoopUsingSet(Node head) {
        Set<Node> visited = new HashSet<>();
        Node current = head;
        while (current != null) {
            if (!visited.add(current)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Way 2 (Floyd): slow moves 1 step, fast moves 2 steps. They can only meet inside a loop.
    static boolean hasLoop(Node head) {
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // 1 -> 2 -> 3 -> 4 -> 5 -> back to 3
        Node head = new Node(1);
        Node second = new Node(2);
        Node third = new Node(3);
        Node fourth = new Node(4);
        Node fifth = new Node(5);
        head.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = fifth;
        fifth.next = third; // creates the loop

        System.out.println("Loop found using a set: " + hasLoopUsingSet(head));
        System.out.println("Loop found using Floyd's algorithm: " + hasLoop(head));

        fifth.next = null; // break the loop
        System.out.println("Loop found after breaking it: " + hasLoop(head));
    }
}
