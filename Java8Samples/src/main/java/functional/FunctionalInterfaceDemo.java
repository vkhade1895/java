package functional;

/**
 * A lambda implements a functional interface that also has a default and a static method.
 *
 * <p>Keywords: functional interface, lambda, default method, static method.
 *
 * <p>First written: 2023-01-05 (old location: {@code testers.I1} and {@code testers.Main}).
 */
public class FunctionalInterfaceDemo {

    // One abstract method (check), so a lambda can implement it
    @FunctionalInterface
    interface Checker {

        void check();

        default void speed() {
            System.out.println("inside speed method.");
        }

        static void type() {
            System.out.println("inside type");
        }
    }

    public static void main(String[] args) {
        Checker.type(); // static method: called through the interface name

        Checker checker = () -> System.out.println("Calling abstract"); // the lambda is check()
        checker.check();
        checker.speed(); // default method
    }
}
