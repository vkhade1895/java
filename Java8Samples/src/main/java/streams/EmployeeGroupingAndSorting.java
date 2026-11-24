package streams;

import model.Department;
import model.Employee;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Common stream questions on an employee list: count and list the names per department,
 * split into two groups, join the names into one string, and sort by several fields.
 *
 * <p>Keywords: groupingBy, counting, mapping, partitioningBy, joining, Comparator.thenComparing.
 *
 * <p>First written: 2026-11-24.
 */
public class EmployeeGroupingAndSorting {

    private static final List<Employee> EMPLOYEES = List.of(
            new Employee(1, "Asha", new Department("IT", 1), 900),
            new Employee(2, "Ravi", new Department("IT", 1), 700),
            new Employee(3, "Meena", new Department("HR", 2), 900),
            new Employee(4, "John", new Department("HR", 2), 500),
            new Employee(5, "Sara", new Department("Ops", 3), 700)
    );

    public static void main(String[] args) {
        // How many employees in each department? TreeMap keeps the departments sorted.
        Map<String, Long> countPerDepartment = EMPLOYEES.stream()
                .collect(Collectors.groupingBy(e -> e.department().name(), TreeMap::new,
                        Collectors.counting()));
        System.out.println("Count per department: " + countPerDepartment); // {HR=2, IT=2, Ops=1}

        // The names in each department: mapping() turns each employee into a name
        Map<String, List<String>> namesPerDepartment = EMPLOYEES.stream()
                .collect(Collectors.groupingBy(e -> e.department().name(), TreeMap::new,
                        Collectors.mapping(Employee::name, Collectors.toList())));
        System.out.println("Names per department: " + namesPerDepartment);

        // Split into two groups: true = salary above 600, false = the rest
        Map<Boolean, List<String>> bySalary = EMPLOYEES.stream()
                .collect(Collectors.partitioningBy(e -> e.salary() > 600,
                        Collectors.mapping(Employee::name, Collectors.toList())));
        System.out.println("Salary above 600: " + bySalary.get(true)
                + ", the rest: " + bySalary.get(false));

        // All the names in one string
        String allNames = EMPLOYEES.stream()
                .map(Employee::name)
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("All names: " + allNames);

        // Sort by department name, then salary (highest first), then name
        List<String> sorted = EMPLOYEES.stream()
                .sorted(Comparator.comparing((Employee e) -> e.department().name())
                        .thenComparing(Employee::salary, Comparator.reverseOrder())
                        .thenComparing(Employee::name))
                .map(e -> e.department().name() + " " + e.salary() + " " + e.name())
                .toList();
        System.out.println("Sorted: " + sorted);
    }
}
