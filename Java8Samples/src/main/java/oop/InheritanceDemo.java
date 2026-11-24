package oop;

/**
 * Runs the inheritance examples: method overriding, runtime polymorphism and an abstract class.
 *
 * <p>Keywords: runtime polymorphism, dynamic dispatch, method overriding, abstract class.
 *
 * <p>First written: 2025-10-21 (old location: {@code samples.MyClass}).
 */
public class InheritanceDemo {

    public static void main(String[] args) {
        Shape shape = new Shape();
        shape.m1(); // inside Shape

        Circle circle = new Circle();
        circle.m1(); // inside circle

        // Runtime polymorphism: the variable's type is Shape, but the object is a Circle,
        // so Circle's m1() runs
        Shape circleAsShape = new Circle();
        circleAsShape.m1(); // inside circle

        // Abstract class: m1() and m3() are implemented by the child class,
        // display() is inherited from AbstractParent
        ConcreteChild child = new ConcreteChild();
        child.m1();
        child.m3();
        child.display();
    }
}
