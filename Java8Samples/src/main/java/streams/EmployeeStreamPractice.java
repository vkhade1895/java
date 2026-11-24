package streams;

import model.Department;
import model.Employee;

import java.util.List;

/**
 * Stream practice on employees: a 10% raise, and upper-case names of employees
 * earning more than 100. (The highest salary per department is in
 * {@link HighestPaidByDepartment}.)
 *
 * <p>Keywords: map, filter, Stream.toList.
 *
 * <p>First written: 2026-04-07 (old location: {@code testers.Test9}).
 */
public class EmployeeStreamPractice {

    public static void main(String[] args) {
        List<Employee> employeeList = List.of(
                new Employee(1, "a", new Department("Eng", 1), 110),
                new Employee(2, "z", new Department("Eng", 1), 100),
                new Employee(3, "b", new Department("HR", 2), 100),
                new Employee(4, "q", new Department("HR", 2), 100),
                new Employee(5, "l", new Department("Ops", 3), 1)
        );

        // Increase every salary by 10%
        List<Double> raisedSalaries = employeeList.stream()
                .map(e -> e.salary() * 0.1 + e.salary())
                .toList();
        System.out.println("Salaries after a 10% raise: " + raisedSalaries);

        // Names in upper case, only for employees earning more than 100
        List<String> upperCaseNames = employeeList.stream()
                .filter(e -> e.salary() > 100)
                .map(e -> e.name().toUpperCase())
                .toList();
        System.out.println("Upper-case names (salary > 100): " + upperCaseNames);
    }
}
