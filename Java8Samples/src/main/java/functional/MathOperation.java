package functional;

/**
 * A custom functional interface: one abstract method, so a lambda can implement it.
 *
 * <p>Keywords: {@code @FunctionalInterface}, custom functional interface, lambda target type.
 *
 * <p>First written: 2025-10-21 (old location: {@code samples.MyFunctionalInterface}).
 */
@FunctionalInterface
public interface MathOperation {

    // Takes two numbers and returns the result of a math operation
    double operate(int a, int b);
}
