package modern;

/**
 * Java 21 style: a sealed interface, records, and pattern matching for {@code switch}.
 *
 * <p>Keywords: sealed interface, permits, record, pattern matching for switch, record patterns,
 * when condition.
 *
 * <p>First written: 2026-11-24.
 */
public class ShapeArea {

    // Only these three records can be a Shape
    sealed interface Shape permits Circle, Square, Rectangle { }

    record Circle(double radius) implements Shape { }

    record Square(double side) implements Shape { }

    record Rectangle(double width, double height) implements Shape { }

    // No default branch: the compiler knows every kind of Shape.
    // If someone adds a new Shape, this switch stops compiling until the new kind is handled.
    static double area(Shape shape) {
        return switch (shape) {
            case Circle c -> Math.PI * c.radius() * c.radius();
            case Square(double side) -> side * side;             // record pattern: reads the field
            case Rectangle(double width, double height) -> width * height;
        };
    }

    // "when" adds a condition. "case null" handles null without a NullPointerException.
    static String describe(Object obj) {
        return switch (obj) {
            case null -> "nothing";
            case Square s when s.side() > 10 -> "a big square";
            case Shape s -> "a shape with area " + area(s);
            case String text -> "the text '" + text + "'";
            default -> "something else";
        };
    }

    public static void main(String[] args) {
        System.out.printf("Circle area: %.2f%n", area(new Circle(1)));  // 3.14
        System.out.println("Square area: " + area(new Square(2)));       // 4.0
        System.out.println("Rectangle area: " + area(new Rectangle(2, 3))); // 6.0
        System.out.println(describe(new Square(20)));                    // a big square
        System.out.println(describe(new Square(2)));                     // a shape with area 4.0
        System.out.println(describe("hi"));                              // the text 'hi'
        System.out.println(describe(null));                              // nothing
    }
}
