package design;

/**
 * The Builder pattern: build an object with many optional fields step by step, with readable
 * code instead of one long constructor.
 *
 * <p>Keywords: Builder pattern, telescoping constructor, method chaining, fluent API,
 * immutable object, Lombok @Builder.
 *
 * <p>First written: 2026-11-24.
 */
public class BuilderPattern {

    // The problem: new User("Asha", "asha@example.com", null, 30) is hard to read. Which value
    // is which? With many optional fields you also need many constructors (this is called a
    // "telescoping constructor"). A builder gives every value a name.
    // In real projects, Lombok's @Builder can write the Builder class for you.
    static final class User {
        private final String name;      // required
        private final String email;     // optional
        private final String phone;     // optional
        private final int age;          // optional

        // Private: the only way to make a User is through the Builder. All fields are final
        // and there are no setters, so a User can't change after it is built.
        private User(Builder builder) {
            this.name = builder.name;
            this.email = builder.email;
            this.phone = builder.phone;
            this.age = builder.age;
        }

        @Override
        public String toString() {
            return "User[name=" + name + ", email=" + email + ", phone=" + phone
                    + ", age=" + age + "]";
        }

        static final class Builder {
            private final String name;
            private String email;
            private String phone;
            private int age;

            // Required values go into the builder's constructor.
            Builder(String name) {
                this.name = name;
            }

            // Each method sets one value and returns the builder itself, so the calls can be
            // chained: .email(...).age(...)
            Builder email(String email) {
                this.email = email;
                return this;
            }

            Builder phone(String phone) {
                this.phone = phone;
                return this;
            }

            Builder age(int age) {
                this.age = age;
                return this;
            }

            // build() is the place to check the values, before the object is made.
            User build() {
                if (name == null || name.isBlank()) {
                    throw new IllegalStateException("name is required");
                }
                return new User(this);
            }
        }
    }

    public static void main(String[] args) {
        User user = new User.Builder("Asha")
                .email("asha@example.com")
                .age(30)
                .build();
        System.out.println(user);   // User[name=Asha, email=asha@example.com, phone=null, age=30]
    }
}
