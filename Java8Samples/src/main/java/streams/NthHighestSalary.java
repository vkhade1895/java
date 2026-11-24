package streams;

import model.Department;
import model.Employee;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Find the Nth highest salary, and the employees who earn it. Salaries can repeat.
 *
 * <p>Keywords: distinct, sorted, Comparator.reverseOrder, skip, findFirst, Optional, filter.
 *
 * <p>First written: 2026-11-24.
 */
public class NthHighestSalary {

    public static void main(String[] args) {
        List<Employee> employees = List.of(
                new Employee(1, "Asha", new Department("IT", 1), 900),
                new Employee(2, "Ravi", new Department("IT", 1), 700),
                new Employee(3, "Meena", new Department("HR", 2), 900),
                new Employee(4, "John", new Department("HR", 2), 500),
                new Employee(5, "Sara", new Department("Ops", 3), 700)
        );
        int n = 2;

        // distinct(): two people earning 900 count as one salary
        Optional<Integer> nthSalary = employees.stream()
                .map(Employee::salary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(n - 1)
                .findFirst();
        System.out.println("2nd highest salary: " + nthSalary.orElse(null)); // 700

        // More than one person can earn that salary
        List<String> earners = employees.stream()
                .filter(e -> nthSalary.isPresent() && e.salary() == nthSalary.get())
                .map(Employee::name)
                .toList();
        System.out.println("Employees who earn it: " + earners); // [Ravi, Sara]
    }
}
