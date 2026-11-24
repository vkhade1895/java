package functional;

import java.util.function.Function;

/**
 * Method reference example: {@code Integer::parseInt} is a short way to write
 * {@code s -> Integer.parseInt(s)}.
 *
 * <p>Keywords: method reference, {@code Function<T, R>}, static method reference.
 *
 * <p>First written: 2025-10-21 (old location: {@code samples.MethodReferences}).
 */
public class MethodReferenceDemo {

    public static void main(String[] args) {
        Function<String, Integer> parsingStringToInt = Integer::parseInt;
        int parsedToIntOp = parsingStringToInt.apply("789");
        System.out.println("parsed string to int: " + parsedToIntOp);
    }
}
