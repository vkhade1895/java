package testers;

import model.Department;
import model.Employee;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test9 {


    public static void main(String[] args) {
//        Group Employees by Department → Highest Salary per Dept
        List<Employee> employeeList = List.of(
                new Employee(1, "a" , new Department("Eng" , 1), 110),
                new Employee(1, "z" , new Department("Eng" , 1), 100),
                new Employee(1, "b" , new Department("HR" , 1), 100),
                new Employee(1, "q" , new Department("HR" , 1), 100),
                new Employee(1, "l" , new Department("Ops" , 1), 1)
        );

        Map<Department, Optional<Employee>> collected = employeeList.stream().
                collect(Collectors.groupingBy(Employee::department, Collectors.maxBy(Comparator.comparing(Employee::salary))));
        System.out.println("output ::" + collected);

        // Increase salary by 10% of all the employees
        List<Double> doubleList = employeeList.stream().map(e -> e.salary() * 0.1 + e.salary()).toList();
        System.out.println("double list::" + doubleList);

        // Convert all names to upper case
        Stream<String> stringStream = employeeList.stream().filter(e-> e.salary() > 100).map(e -> e.name().toUpperCase());
        stringStream.forEach(e-> System.out.println("printing::" + e));
        // filter out only employee name and salary
//         collected.entrySet().stream().
    }
}
