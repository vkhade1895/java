package oop;

/**
 * Which overloaded method runs for {@code call(null)} when there is a
 * {@code call(String)} and a {@code call(Object)}?
 *
 * <p>Keywords: overloading, null argument, most specific method, compile-time polymorphism.
 *
 * <p>First written: 2026-04-07 (old location: {@code testers.A}).
 */
public class MethodOverloading {

    public static void main(String[] args) {
        // Both call(String) and call(Object) accept null. Java picks the most specific type,
        // and String is a subclass of Object, so this prints "string called".
        call(null);
    }

    private static void call(String s) {
        System.out.println("string called");
    }

    private static void call(Object o) {
        System.out.println("Object called");
    }
}
