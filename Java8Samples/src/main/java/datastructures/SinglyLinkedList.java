package datastructures;

/**
 * Your own singly linked list (no java.util.LinkedList): reverse it with a loop and with
 * recursion, find the middle node, and remove the Nth node from the end.
 *
 * <p>Keywords: Node class, prev/curr/next pointers, recursion, slow and fast pointers,
 * dummy node.
 *
 * <p>First written: 2026-11-24.
 */
public class SinglyLinkedList {

    private static class Node {
        final int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        System.out.println("List: " + text(build(1, 2, 3, 4, 5)));
        System.out.println("Reversed with a loop: " + text(reverse(build(1, 2, 3, 4, 5))));
        System.out.println("Reversed with recursion: "
                + text(reverseRecursive(build(1, 2, 3, 4, 5))));
        System.out.println("Middle node: " + middle(build(1, 2, 3, 4, 5)).value);        // 3
        System.out.println("Remove the 2nd from the end: "
                + text(removeNthFromEnd(build(1, 2, 3, 4, 5), 2))); // 1 -> 2 -> 3 -> 5
    }

    // Walk once and turn each arrow around, so every node points to the one before it
    static Node reverse(Node head) {
        Node prev = null;
        Node curr = head;
        while (curr != null) {
            Node next = curr.next; // remember the rest of the list
            curr.next = prev;      // turn the arrow around
            prev = curr;           // move both pointers one step forward
            curr = next;
        }
        return prev; // the old last node is the new first node
    }

    // Reverse the rest of the list first, then put this node at its end
    static Node reverseRecursive(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node newHead = reverseRecursive(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    // slow moves 1 step, fast moves 2 steps. When fast reaches the end, slow is in the middle.
    // If the list has an even length, slow stops at the second of the two middle nodes.
    static Node middle(Node head) {
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // Move "ahead" n steps first, then move both pointers until "ahead" reaches the end.
    // "behind" then stops just before the node to remove. The dummy node in front
    // makes removing the first node work the same way.
    static Node removeNthFromEnd(Node head, int n) {
        Node dummy = new Node(0);
        dummy.next = head;
        Node ahead = dummy;
        Node behind = dummy;
        for (int i = 0; i < n; i++) {
            ahead = ahead.next;
        }
        while (ahead.next != null) {
            ahead = ahead.next;
            behind = behind.next;
        }
        behind.next = behind.next.next; // skip over the node to remove
        return dummy.next;
    }

    // Builds a list from the given values, for example 1 -> 2 -> 3
    static Node build(int... values) {
        Node dummy = new Node(0);
        Node tail = dummy;
        for (int value : values) {
            tail.next = new Node(value);
            tail = tail.next;
        }
        return dummy.next;
    }

    static String text(Node head) {
        StringBuilder result = new StringBuilder();
        for (Node node = head; node != null; node = node.next) {
            result.append(node.value);
            if (node.next != null) {
                result.append(" -> ");
            }
        }
        return result.toString();
    }
}
