package numbers;

/**
 * Round 8.547 to two decimal places (8.55).
 *
 * <p>Keywords: Math.round, decimal places, BigDecimal for money.
 *
 * <p>First written: 2026-04-07 (old location: {@code testers.A}).
 */
public class RoundToTwoDecimals {

    public static void main(String[] args) {
        double input = 8.547;
        double rounded = Math.round(input * 100) / 100.0;
        // For money, use BigDecimal instead:
        // new BigDecimal("8.547").setScale(2, RoundingMode.HALF_UP)
        System.out.println(input + " rounded to 2 decimal places: " + rounded); // 8.55
    }
}
