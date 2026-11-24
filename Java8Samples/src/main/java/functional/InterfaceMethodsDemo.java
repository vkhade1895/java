package functional;

/**
 * Calls the three kinds of interface methods in {@link Calculator}: abstract, default and static.
 *
 * <p>Keywords: interface, abstract method, default method, static method, implements.
 *
 * <p>First written: 2023-10-23 (old location: {@code samples.InterfaceTests}).
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
    }

    @Override
    public int calculate(int a) {
        return a * a;
    }
}
