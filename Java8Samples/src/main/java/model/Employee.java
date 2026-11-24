package model;

/**
 * An employee, used by the employee examples in the {@code streams} package.
 *
 * <p>Keywords: record, immutable data class, accessor methods.
 *
 * <p>First written: 2026-01-24.
 */
public record Employee(int id, String name, Department department, int salary) {
}
