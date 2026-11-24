package functional;

/**
 * Calls the three kinds of interface methods in {@link Calculator}: abstract, default and static.
 * Then it implements Calculator with a lambda, which still gets the default method.
 *
 * <p>Keywords: interface, abstract method, default method, static method, implements, lambda.
 *
 * <p>First written: 2023-10-23 and 2026-11-25 (old location: {@code samples.InterfaceTests}).
 */
public class InterfaceMethodsDemo implements Calculator {

    public static void main(String[] args) {
        System.out.println("Testing abstract, default and static methods in an interface.");
        int input = 10;

        int positive = Calculator.positive(input); // static: called through the interface name
        System.out.println("Check positive number: " + positive);

        InterfaceMethodsDemo demo = new InterfaceMethodsDemo();
        double sqrt = demo.sqrt(input); // default method: inherited from Calculator
        System.out.println("Square root: " + sqrt);

        int square = demo.calculate(input); // abstract method: implemented below
        System.out.println("Square: " + square);

        // A lambda can implement Calculator too: the lambda is the body of calculate(),
        // and the object still gets the default method sqrt().
        Calculator doubler = a -> a * 2;
        System.out.println("Lambda calculate(5): " + doubler.calculate(5));       // 10
        System.out.println("Default sqrt(16) on the lambda: " + doubler.sqrt(16)); // 4.0
    }

    @Override
    public int calculate(int a) {
        return a * a;
    }
}
