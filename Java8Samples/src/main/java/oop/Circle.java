package oop;

/**
 * Child class that overrides {@link Shape#m1()}.
 *
 * <p>Keywords: inheritance, {@code @Override}, runtime polymorphism.
 *
 * <p>First written: 2025-10-21 (old location: {@code samples.Circle}).
 */
public class Circle extends Shape {

    @Override
    public void m1() {
        System.out.println("inside circle");
    }
}
