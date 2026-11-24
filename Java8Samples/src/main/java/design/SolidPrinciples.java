package design;

import java.util.List;

/**
 * The five SOLID principles. Each one has a small example that breaks the rule, the problem
 * that causes, and the same example written the right way.
 *
 * <p>Keywords: SOLID, single responsibility (SRP), open/closed (OCP), Liskov substitution
 * (LSP), interface segregation (ISP), dependency inversion (DIP), Strategy pattern,
 * constructor injection.
 *
 * <p>First written: 2026-11-24.
 */
public class SolidPrinciples {

    // SOLID is five rules that keep classes easy to change, test and reuse:
    //   S - Single responsibility: a class does only one job.
    //   O - Open/closed: add new features by adding new code, not by editing old code.
    //   L - Liskov substitution: a child class must work everywhere its parent class works.
    //   I - Interface segregation: many small interfaces are better than one big one.
    //   D - Dependency inversion: depend on an interface, and get the real object from outside.
    //
    // Each part below has:
    //   Rule            - the principle in simple words
    //   In real life    - an everyday example
    //   Breaks the rule - code that ignores the rule (its class names start with "Bad")
    //   Problem         - what goes wrong when the rule is broken
    //   Fix             - the same code written so that it follows the rule

    // =========================================================================================
    // S - Single responsibility principle (SRP)
    //
    // Rule: a class should do only one job, so it has only one reason to change.
    // In real life: in a restaurant, the chef cooks and the cashier takes the money.
    //
    // Breaks the rule: BadInvoice works out the total, prints it and saves it.
    // Problem: the class changes for three different reasons. A change to the printing can
    // break the total by mistake. The class keeps growing, and you can't test the total on
    // its own.
    // Fix: Invoice works out the total, InvoicePrinter prints it and InvoiceRepository saves
    // it. Spring apps use the same idea: Controller, Service and Repository classes.
    // =========================================================================================

    // Breaks the rule: one class, three jobs.
    record BadInvoice(List<Integer> prices) {
        int total() {
            return prices.stream().mapToInt(Integer::intValue).sum();
        }

        void print() {
            System.out.println("BadInvoice printed the total: " + total());
        }

        void save() {
            // A real app would write to a database here.
            System.out.println("BadInvoice saved the invoice, total " + total());
        }
    }

    // Follows the rule: one job for each class.
    record Invoice(List<Integer> prices) {
        int total() {
            return prices.stream().mapToInt(Integer::intValue).sum();
        }
    }

    static class InvoicePrinter {
        void print(Invoice invoice) {
            System.out.println("InvoicePrinter printed the total: " + invoice.total());
        }
    }

    static class InvoiceRepository {
        void save(Invoice invoice) {
            // A real app would write to a database here.
            System.out.println("InvoiceRepository saved the invoice, total " + invoice.total());
        }
    }

    static void showSingleResponsibility() {
        BadInvoice badInvoice = new BadInvoice(List.of(100, 50));
        badInvoice.print();
        badInvoice.save();

        Invoice invoice = new Invoice(List.of(100, 50));
        new InvoicePrinter().print(invoice);
        new InvoiceRepository().save(invoice);
    }

    // =========================================================================================
    // O - Open/closed principle (OCP)
    //
    // Rule: code should be open for extension (you can add new behaviour) but closed for
    // modification (you don't edit code that already works).
    // In real life: you add a new app to your phone without changing the phone itself.
    //
    // Breaks the rule: badPrice() has a switch on the customer type. To add a new type, such
    // as "PLATINUM", you must edit this method.
    // Problem: every new type means editing code that already works and is tested, so you
    // can break the old types by mistake. The switch keeps growing, and if it is copied to
    // other places, you must find and edit all of them.
    // Fix: each discount is its own class that implements Discount. A new discount is just a
    // new class, and checkout() never changes. This is also called the Strategy pattern.
    // =========================================================================================

    // Breaks the rule: a new customer type means editing this method.
    static int badPrice(String customerType, int price) {
        return switch (customerType) {
            case "REGULAR" -> price;
            case "GOLD" -> price - price / 10;
            default -> throw new IllegalArgumentException("Unknown type: " + customerType);
        };
    }

    // Follows the rule: one class for each discount.
    interface Discount {
        int apply(int price);
    }

    static class NoDiscount implements Discount {           // for regular customers
        @Override
        public int apply(int price) {
            return price;
        }
    }

    static class GoldDiscount implements Discount {         // 10% off
        @Override
        public int apply(int price) {
            return price - price / 10;
        }
    }

    // Added later. No code above had to change.
    static class PlatinumDiscount implements Discount {     // 20% off
        @Override
        public int apply(int price) {
            return price - price / 5;
        }
    }

    // Works with every Discount, even ones written after this method.
    static int checkout(int price, Discount discount) {
        return discount.apply(price);
    }

    static void showOpenClosed() {
        System.out.println("badPrice() for GOLD: " + badPrice("GOLD", 200));
        System.out.println("checkout() with NoDiscount: " + checkout(200, new NoDiscount()));
        System.out.println("checkout() with GoldDiscount: " + checkout(200, new GoldDiscount()));
        System.out.println("checkout() with PlatinumDiscount: "
                + checkout(200, new PlatinumDiscount()));
    }

    // =========================================================================================
    // L - Liskov substitution principle (LSP)
    //
    // Rule: if code works with a parent class, it must also work with any child class. A
    // child class must keep every promise its parent makes.
    // In real life: if someone asks you for a pen, any pen you give them must write.
    //
    // Breaks the rule: BadSquare extends BadRectangle. A square must keep its sides equal,
    // so its setWidth() also changes the height. resizeAndGetArea() sets the width to 5 and
    // the height to 4, so it expects an area of 20, but a BadSquare gives 16.
    // Problem: code that works for the parent gives wrong results, or crashes, for a child.
    // People then add "instanceof" checks to treat the child differently, and every new
    // child class can break code that used to work.
    // Another well-known example: a Penguin class that extends Bird, but throws an
    // exception in fly() because penguins can't fly.
    // Fix: Square does not extend Rectangle. Both implement Shape, and neither can change
    // after it is created, so there is no promise to break. Use inheritance only when the
    // child can really do everything the parent can.
    // =========================================================================================

    // Breaks the rule: BadSquare changes how setWidth() and setHeight() behave.
    static class BadRectangle {
        private int width;
        private int height;

        void setWidth(int width) {
            this.width = width;
        }

        void setHeight(int height) {
            this.height = height;
        }

        int area() {
            return width * height;
        }
    }

    static class BadSquare extends BadRectangle {
        @Override
        void setWidth(int width) {
            super.setWidth(width);
            super.setHeight(width);
        }

        @Override
        void setHeight(int height) {
            super.setWidth(height);
            super.setHeight(height);
        }
    }

    // Written for BadRectangle: it expects the width and the height to change separately.
    static int resizeAndGetArea(BadRectangle rectangle) {
        rectangle.setWidth(5);
        rectangle.setHeight(4);
        return rectangle.area();
    }

    // Follows the rule: Rectangle and Square are separate shapes.
    interface Shape {
        int area();
    }

    record Rectangle(int width, int height) implements Shape {
        @Override
        public int area() {
            return width * height;
        }
    }

    record Square(int side) implements Shape {
        @Override
        public int area() {
            return side * side;
        }
    }

    static void showLiskovSubstitution() {
        System.out.println("BadRectangle area: " + resizeAndGetArea(new BadRectangle()));
        System.out.println("BadSquare area: " + resizeAndGetArea(new BadSquare())
                + " (wrong, the code expected 20)");

        List<Shape> shapes = List.of(new Rectangle(5, 4), new Square(4));
        for (Shape shape : shapes) {
            System.out.println(shape + " area: " + shape.area());
        }
    }

    // =========================================================================================
    // I - Interface segregation principle (ISP)
    //
    // Rule: don't make a class depend on methods it doesn't use. Many small interfaces are
    // better than one big interface.
    // In real life: a remote control where half the buttons do nothing is hard to use.
    //
    // Breaks the rule: BadMachine has print() and scan(). BadSimplePrinter can't scan, but
    // it must still have a scan() method, so that method just throws an exception.
    // Problem: callers see scan() and trust it, but the program crashes when they call it.
    // Also, a new method added to the big interface forces every class to change, even
    // classes that will never use it.
    // Fix: one small interface for each ability (Printer and Scanner). A class implements
    // only what it can really do, so calling scan() on a SimplePrinter doesn't compile.
    // =========================================================================================

    // Breaks the rule: every machine must have scan(), even if it can't scan.
    interface BadMachine {
        void print(String document);

        void scan(String document);
    }

    static class BadSimplePrinter implements BadMachine {
        @Override
        public void print(String document) {
            System.out.println("BadSimplePrinter printed " + document);
        }

        @Override
        public void scan(String document) {
            throw new UnsupportedOperationException("BadSimplePrinter can't scan");
        }
    }

    // Follows the rule: one small interface for each ability.
    interface Printer {
        void print(String document);
    }

    interface Scanner {
        void scan(String document);
    }

    static class SimplePrinter implements Printer {
        @Override
        public void print(String document) {
            System.out.println("SimplePrinter printed " + document);
        }
    }

    static class OfficePrinter implements Printer, Scanner {
        @Override
        public void print(String document) {
            System.out.println("OfficePrinter printed " + document);
        }

        @Override
        public void scan(String document) {
            System.out.println("OfficePrinter scanned " + document);
        }
    }

    static void showInterfaceSegregation() {
        BadMachine badPrinter = new BadSimplePrinter();
        badPrinter.print("report.pdf");
        try {
            badPrinter.scan("report.pdf");
        } catch (UnsupportedOperationException e) {
            System.out.println("scan() failed at run time: " + e.getMessage());
        }

        // SimplePrinter has no scan() method, so the same mistake is caught by the compiler.
        new SimplePrinter().print("report.pdf");
        OfficePrinter officePrinter = new OfficePrinter();
        officePrinter.print("report.pdf");
        officePrinter.scan("report.pdf");
    }

    // =========================================================================================
    // D - Dependency inversion principle (DIP)
    //
    // Rule: high-level code (the business logic) should not depend on low-level code (like
    // sending emails or saving to a database). Both should depend on an interface. The real
    // object is passed in from outside, usually through the constructor.
    // In real life: a lamp uses a plug and a socket instead of being wired into the wall, so
    // it is easy to move or swap.
    //
    // Breaks the rule: BadOrderService creates its own EmailSender with "new".
    // Problem: the order code is stuck with email. To send an SMS instead, you must edit
    // BadOrderService. In a unit test you can't swap in a fake sender, so the test would
    // send real emails.
    // Fix: OrderService only knows the MessageSender interface and gets a sender through its
    // constructor. You can pass email, SMS, or a fake sender in a test, without changing
    // OrderService.
    // Interview tip: dependency inversion is the rule. Dependency injection (passing the
    // object in) is the usual way to follow it. Spring does it for you with constructor
    // injection.
    // =========================================================================================

    interface MessageSender {
        void send(String message);
    }

    static class EmailSender implements MessageSender {
        @Override
        public void send(String message) {
            System.out.println("Email: " + message);
        }
    }

    static class SmsSender implements MessageSender {
        @Override
        public void send(String message) {
            System.out.println("SMS: " + message);
        }
    }

    // Breaks the rule: tied to EmailSender.
    static class BadOrderService {
        private final EmailSender sender = new EmailSender();      // stuck with email

        void placeOrder(String item) {
            sender.send("Order placed for " + item);
        }
    }

    // Follows the rule: depends only on the MessageSender interface.
    static class OrderService {
        private final MessageSender sender;

        OrderService(MessageSender sender) {                      // any sender can be passed in
            this.sender = sender;
        }

        void placeOrder(String item) {
            sender.send("Order placed for " + item);
        }
    }

    static void showDependencyInversion() {
        new BadOrderService().placeOrder("a book");
        new OrderService(new SmsSender()).placeOrder("a book");

        // In a unit test, pass a fake sender. A lambda works here because MessageSender has
        // only one method.
        new OrderService(message -> System.out.println("Fake sender: " + message))
                .placeOrder("a book");
    }

    public static void main(String[] args) {
        System.out.println("S - Single responsibility");
        showSingleResponsibility();
        System.out.println("\nO - Open/closed");
        showOpenClosed();
        System.out.println("\nL - Liskov substitution");
        showLiskovSubstitution();
        System.out.println("\nI - Interface segregation");
        showInterfaceSegregation();
        System.out.println("\nD - Dependency inversion");
        showDependencyInversion();
    }
}
