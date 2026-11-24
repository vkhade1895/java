package datastructures;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A stack that can also give its smallest item in O(1) time: push, pop, top and getMin.
 *
 * <p>Keywords: min stack, two stacks, ArrayDeque as a stack, O(1) getMin.
 *
 * <p>First written: 2026-11-24.
 */
public class MinStack {

    // Two stacks: "items" holds every item. "mins" holds, for each level of the stack, the
    // smallest item at that level or below it. Both stacks always have the same size, so
    // after a pop, the top of "mins" is the smallest of the items that are left.
    // Like the LeetCode problem, this assumes you never pop or read an empty stack.
    // ArrayDeque is the stack to use in Java. The old Stack class is slower, because its
    // methods are synchronized.
    private final Deque<Integer> items = new ArrayDeque<>();
    private final Deque<Integer> mins = new ArrayDeque<>();

    void push(int value) {
        items.push(value);
        mins.push(mins.isEmpty() ? value : Math.min(value, mins.peek()));
    }

    int pop() {
        mins.pop();
        return items.pop();
    }

    int top() {
        return items.peek();
    }

    int getMin() {
        return mins.peek();
    }

    public static void main(String[] args) {
        MinStack stack = new MinStack();
        stack.push(5);
        stack.push(3);
        stack.push(7);
        System.out.println("Top: " + stack.top() + ", min: " + stack.getMin());   // 7, 3
        stack.pop();        // removes 7
        stack.pop();        // removes 3
        System.out.println("Min after two pops: " + stack.getMin());               // 5
    }
}
