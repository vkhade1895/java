package exceptions;

/**
 * Tricky question: the try block throws, catch returns -2 and finally returns -3.
 * What does the method return?
 *
 * <p>Keywords: try/catch/finally, return in finally, swallowed exception.
 *
 * <p>First written: 2025-10-21 (old location: {@code testers.Trying}).
 */
public class FinallyReturn {

    public static void main(String[] args) {
        System.out.println("Return value: " + finallyOverridesReturn()); // -3
    }

    // finally always runs, and its return replaces the -2 from catch.
    // Never return from finally in real code: it also hides exceptions.
    @SuppressWarnings("finally") // on purpose: this is the trick being tested
    private static int finallyOverridesReturn() {
        try {
            throw new Exception("a");
        } catch (Exception e) {
            return -2;
        } finally {
            return -3;
        }
    }
}
