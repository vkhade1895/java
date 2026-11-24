package datastructures;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Check that every bracket in a string is closed by the same type, in the right order.
 * Supports (), {} and [].
 *
 * <p>Keywords: stack, ArrayDeque, Deque, push and pop, bracket matching.
 *
 * <p>First written: 2026-04-07 (old location: {@code march.CheckParenthesis}).
 */
public class ValidParentheses {

    /**
     * Checks if the input string has properly closed and nested parentheses.
     *
     * @param s input string containing parentheses
     * @return true if properly closed, false otherwise
     */
    public static boolean isValidParenthesis(String s) {
        // ArrayDeque is the recommended stack in Java (Stack is an old, synchronized class)
        Deque<Character> stack = new ArrayDeque<>();
        for (char ch : s.toCharArray()) {
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            } else if (ch == ')' || ch == '}' || ch == ']') {
                if (stack.isEmpty()) {
                    return false;
                }
                char popped = stack.pop();
                if (!isMatchingPair(popped, ch)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')')
                || (open == '{' && close == '}')
                || (open == '[' && close == ']');
    }

    public static void main(String[] args) {
        String[] testCases = {
                "()", "([])", "([)]", "{[()]}", "((())", "", "([{}])", "((()))", "([{})"
        };
        for (String test : testCases) {
            System.out.printf("Input: %-10s => %s%n", test, isValidParenthesis(test));
        }
    }
}
