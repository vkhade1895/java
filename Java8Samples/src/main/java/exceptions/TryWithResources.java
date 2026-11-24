package exceptions;

/**
 * try-with-resources closes resources for you, even when an exception is thrown. Shown with
 * our own AutoCloseable class and our own (custom) exception.
 *
 * <p>Keywords: try-with-resources, AutoCloseable, close order, suppressed exception, custom
 * exception, checked vs unchecked exception.
 *
 * <p>First written: 2026-11-24.
 */
public class TryWithResources {

    // A custom exception. Extend Exception for a checked exception (the caller must catch it
    // or declare it with "throws"). Extend RuntimeException for an unchecked one.
    static class PaymentException extends Exception {
        // Exceptions are Serializable, so they should have a version number.
        private static final long serialVersionUID = 1L;

        PaymentException(String message) {
            super(message);
        }
    }

    // Any class that implements AutoCloseable can be used in try-with-resources.
    static class BankConnection implements AutoCloseable {
        private final String name;

        BankConnection(String name) {
            this.name = name;
            System.out.println("Open " + name);
        }

        void send(String payment) throws PaymentException {
            if (payment.isBlank()) {
                throw new PaymentException("Empty payment on " + name);
            }
            System.out.println("Sent " + payment + " on " + name);
        }

        @Override
        public void close() {
            System.out.println("Close " + name);
        }
    }

    public static void main(String[] args) {
        // Java closes the resources at the end of the try block, in reverse order: "second"
        // first, then "first". This happens before the catch block runs.
        try (BankConnection first = new BankConnection("first");
             BankConnection second = new BankConnection("second")) {
            first.send("100 GBP");
            second.send("");                // throws PaymentException
        } catch (PaymentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        // If close() also throws, you still catch the exception from the try block. The one
        // from close() is kept inside it (e.getSuppressed()). With the old try/finally, the
        // exception from finally would replace the first one, and the first one would be lost.
    }
}
