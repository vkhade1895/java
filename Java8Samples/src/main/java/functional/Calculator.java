package functional;

/**
 * A functional interface: exactly one abstract method, plus a default and a static method.
 *
 * <p>Keywords: functional interface, default method, static interface method, Java 8.
 *
 * <p>First written: 2023-10-23 (old location: {@code samples.I1}).
 */
@FunctionalInterface
public interface Calculator {

    int calculate(int a);

    // Default method: has a body, and every class that implements Calculator gets it
    default double sqrt(int a) {
        return Math.sqrt(positive(a));
    }

    // Static method: call it as Calculator.positive(...); it is not inherited
    static int positive(int a) {
        return a > 0 ? a : 0;
    }
}
