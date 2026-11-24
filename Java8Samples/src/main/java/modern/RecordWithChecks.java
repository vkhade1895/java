package modern;

import java.util.ArrayList;
import java.util.List;

/**
 * A record that checks its values when it is created, using a compact constructor.
 *
 * <p>Keywords: record, compact constructor, validation, IllegalArgumentException, defensive
 * copy, List.copyOf, immutable.
 *
 * <p>First written: 2026-11-24.
 */
public class RecordWithChecks {

    // A compact constructor has no parameter list. It runs before the fields are set, so it
    // can check the values and throw an exception, or change them (here: trim the name and
    // copy the list). Java sets the fields after it, so you can't write "this.payee = payee".
    record Payment(String payee, int amount, List<String> tags) {
        Payment {
            if (payee == null || payee.isBlank()) {
                throw new IllegalArgumentException("payee is required");
            }
            if (amount <= 0) {
                throw new IllegalArgumentException("amount must be above 0: " + amount);
            }
            payee = payee.trim();
            tags = List.copyOf(tags);    // an unchangeable copy, so the record stays immutable
        }
    }

    public static void main(String[] args) {
        List<String> tags = new ArrayList<>(List.of("rent"));
        Payment payment = new Payment("  Asha ", 100, tags);
        tags.add("late");                // changes our list, but not the copy in the payment
        System.out.println(payment);     // Payment[payee=Asha, amount=100, tags=[rent]]

        try {
            new Payment("Ravi", -5, List.of());
        } catch (IllegalArgumentException e) {
            System.out.println("Not created: " + e.getMessage());   // amount must be above 0: -5
        }
    }
}
