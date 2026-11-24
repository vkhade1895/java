package streams;

import model.Department;
import model.Employee;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The highest-paid employee in each department.
 *
 * <p>Keywords: groupingBy, maxBy, Comparator.comparing, Optional, record as map key.
 *
 * <p>First written: 2026-01-24 (old location: {@code testers.Main}).
 */
public class HighestPaidByDepartment {

    public static void main(String[] args) {
        List<Employee> employees = List.of(
                new Employee(1, "one", new Department("IT", 1), 100),
                new Employee(2, "TWO", new Department("IT", 1), 200),
                new Employee(3, "THREE", new Department("IT", 1), 500),
                new Employee(4, "FOUR", new Department("DEV", 2), 400),
                new Employee(5, "FIVE", new Department("DEV", 2), 500)
        );

        // Department is a record, so equal departments become one group
        Map<Department, Optional<Employee>> highestPaid = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.maxBy(Comparator.comparing(Employee::salary))
                ));

        // Print only the department, employee name and salary
        highestPaid.forEach((department, employee) -> {
            if (employee.isPresent()) {
                Employee e = employee.get();
                System.out.println(department.name() + ": " + e.name() + " " + e.salary());
            }
        });
    }
}
