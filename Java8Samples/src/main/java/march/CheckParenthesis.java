package march;

import java.util.Stack;

public class CheckParenthesis {

    /**
     * Checks if the input string has properly closed and nested parentheses.
     * Supports (), {}, and [].
     * @param s input string containing parentheses
     * @return true if properly closed, false otherwise
     */
    public static boolean isValidParenthesis(String s) {
        Stack<Character> stack = new Stack<>();
        for (char ch : s.toCharArray()) {
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            } else if (ch == ')' || ch == '}' || ch == ']') {
                if (stack.isEmpty()) return false;
                char popped = stack.pop();
                if (!isMatchingPair(popped, ch)) return false;
            }
        }
        return stack.isEmpty();
    }

    private static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
                (open == '{' && close == '}') ||
                (open == '[' && close == ']');
    }

    public static void main(String[] args) {
        String[] testCases = {
                "()",
                "([])",
                "([)]",
                "{[()]}",
                "((())",
                "",
                "([{}])",
                "([)]",
                "((()))",
                "([{})"
        };

        for (String test : testCases) {
            System.out.printf("Input: %-10s => %s%n", test, isValidParenthesis(test));
        }
    }
}
