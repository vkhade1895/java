package oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * An immutable class used as a HashMap key, and what goes wrong without
 * {@code equals} and {@code hashCode}.
 *
 * <p>Keywords: immutable class, final fields, defensive copy, equals and hashCode contract,
 * HashMap key.
 *
 * <p>First written: 2026-11-24.
 */
public class ImmutableKey {

    // Immutable: a final class, private final fields, no setters,
    // and a copy of any list it receives. (A record gives you equals and hashCode for free.)
    static final class Account {
        private final String id;
        private final List<String> owners;

        Account(String id, List<String> owners) {
            this.id = id;
            this.owners = List.copyOf(owners); // a copy that can't be changed
        }

        List<String> owners() {
            return owners;
        }

        // Equal objects must have the same hash code, or a HashMap can't find them
        @Override
        public boolean equals(Object o) {
            return o instanceof Account other && id.equals(other.id) && owners.equals(other.owners);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, owners);
        }
    }

    // No equals and hashCode: two objects with the same data still count as different
    static final class AccountWithoutEquals {
        private final String id;

        AccountWithoutEquals(String id) {
            this.id = id;
        }
    }

    public static void main(String[] args) {
        Map<Account, Integer> balances = new HashMap<>();
        balances.put(new Account("A1", List.of("Asha")), 100);
        System.out.println("Found with equals and hashCode: "
                + balances.get(new Account("A1", List.of("Asha"))));          // 100

        Map<AccountWithoutEquals, Integer> broken = new HashMap<>();
        broken.put(new AccountWithoutEquals("A1"), 100);
        System.out.println("Found without equals and hashCode: "
                + broken.get(new AccountWithoutEquals("A1")));                // null

        List<String> owners = new ArrayList<>(List.of("Asha"));
        Account account = new Account("A2", owners);
        owners.add("Ravi"); // change the caller's list after creating the account
        System.out.println("Account owners did not change: " + account.owners()); // [Asha]
    }
}
