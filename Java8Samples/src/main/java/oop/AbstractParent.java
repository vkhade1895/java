package oop;

/**
 * An abstract class can mix abstract methods (no body) with normal methods.
 * It can't be created with {@code new}; {@link ConcreteChild} extends it.
 *
 * <p>Keywords: abstract class, abstract method, concrete method.
 *
 * <p>First written: 2025-10-21 (old location: {@code samples.MyAbstractClass}).
 */
public abstract class AbstractParent {

    abstract void m1();

    abstract void m3();

    // Concrete method: child classes inherit it as it is
    public void display() {
        System.out.println("This is a concrete method in an abstract class.");
    }
}
