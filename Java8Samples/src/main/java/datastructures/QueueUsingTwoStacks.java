package datastructures;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Build a queue (first in, first out) using two stacks (last in, first out).
 *
 * <p>Keywords: queue, two stacks, FIFO, LIFO, amortized O(1), ArrayDeque.
 *
 * <p>First written: 2026-11-24.
 */
public class QueueUsingTwoStacks {

    // New items go on the "in" stack. To take an item out, we need the oldest one, which is
    // at the bottom of "in". So when "out" is empty, move everything from "in" to "out". This
    // flips the order, and the oldest item ends up on top of "out".
    // Each item is moved at most once, so add() and remove() take O(1) time on average
    // (this is called "amortized O(1)").
    private final Deque<Integer> in = new ArrayDeque<>();
    private final Deque<Integer> out = new ArrayDeque<>();

    void add(int value) {
        in.push(value);
    }

    int remove() {
        moveIfNeeded();
        return out.pop();
    }

    int peek() {
        moveIfNeeded();
        return out.peek();
    }

    private void moveIfNeeded() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }
    }

    public static void main(String[] args) {
        QueueUsingTwoStacks queue = new QueueUsingTwoStacks();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        System.out.println("Removed: " + queue.remove());   // 1
        queue.add(4);
        System.out.println("Removed: " + queue.remove());   // 2
        System.out.println("Next: " + queue.peek());        // 3
    }
}
