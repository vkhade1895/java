package model;

/**
 * A department. Records generate equals() and hashCode(), so it works as a map key
 * when grouping employees by department.
 *
 * <p>Keywords: record, equals and hashCode, map key.
 *
 * <p>First written: 2026-01-24.
 */
public record Department(String name, int id) {
}
