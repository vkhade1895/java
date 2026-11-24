package oop;

import functional.Calculator;

/**
 * Extends an abstract class and implements an interface at the same time.
 *
 * <p>Keywords: extends, implements, abstract class, interface.
 *
 * <p>First written: 2025-10-21 (old location: {@code samples.ExtendAbstractClass}).
 */
public class ConcreteChild extends AbstractParent implements Calculator {

    @Override
    void m1() {
        System.out.println("this is m1 method");
    }

    @Override
    void m3() {
        System.out.println("this is m3 method");
    }

    @Override
    public int calculate(int a) {
        return a * a;
    }
}
