package modern;

import java.util.List;

/**
 * Newer syntax that makes everyday code shorter: {@code var} (Java 10), switch expressions
 * (Java 14) and text blocks (Java 15).
 *
 * <p>Keywords: var, local variable type inference, switch expression, arrow case, yield,
 * no fall-through, text block, String.formatted.
 *
 * <p>First written: 2026-11-24.
 */
public class NewerSyntax {

    enum Day { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }

    public static void main(String[] args) {
        useVar();
        System.out.println(dayTypeOldWay(Day.SUNDAY));     // Weekend
        System.out.println(dayType(Day.SUNDAY));           // Weekend
        System.out.println(workHours(Day.FRIDAY));         // 6
        System.out.print(employeeJson("Asha", "developer"));
    }

    // var (Java 10): the compiler works out the type from the value on the right. The type
    // is still fixed: "names" is a List<String> and can never hold anything else.
    // var works for local variables that get a value straight away (not for fields, not for
    // method parameters, and not "var x = null"). Use it when the type is easy to see.
    private static void useVar() {
        var names = List.of("Asha", "Ravi");    // List<String>
        var letters = 0;                        // int
        for (var name : names) {
            letters += name.length();
        }
        System.out.println("Letters in all names: " + letters);   // 8
    }

    // The old switch statement: a case without "break" falls through into the next case,
    // which is an easy bug to make.
    private static String dayTypeOldWay(Day day) {
        String type;
        switch (day) {
            case SATURDAY:
            case SUNDAY:
                type = "Weekend";
                break;
            default:
                type = "Weekday";
        }
        return type;
    }

    // Switch expression (Java 14): it returns a value, and one case can list several values.
    // The arrow (->) means no fall-through, so no break. For an enum, the compiler checks
    // that every value is covered, so no default is needed.
    private static String dayType(Day day) {
        return switch (day) {
            case SATURDAY, SUNDAY -> "Weekend";
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "Weekday";
        };
    }

    // When a case needs more than one line, use a block and give back its value with yield.
    private static int workHours(Day day) {
        return switch (day) {
            case SATURDAY, SUNDAY -> 0;
            case FRIDAY -> {
                int fullDay = 8;
                yield fullDay - 2;              // Friday is a short day
            }
            default -> 8;
        };
    }

    // Text block (Java 15): a string over several lines between triple quotes, with no \n and
    // no + signs. Java removes the indentation that all the lines share (the line with the
    // closing quotes counts too), so the JSON starts at the left edge.
    private static String employeeJson(String name, String role) {
        return """
                {
                  "name": "%s",
                  "role": "%s"
                }
                """.formatted(name, role);
    }
}
