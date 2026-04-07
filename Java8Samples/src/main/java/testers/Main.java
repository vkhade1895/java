package testers;

import model.Department;
import model.Employee;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        I1.type();

        I1 ii = () -> {
            System.out.println("Calling abstract");
        };

        ii.check();
        ii.speed();

        returnHighestSalaryByDepartment();
    }

    private static void returnHighestSalaryByDepartment() {
        List<Employee> empList = new ArrayList<>();
        Employee employee = new Employee(1, "one" , new Department("IT", 1), 100);
        Employee employee1 = new Employee(2, "TWO" , new Department("IT", 1), 200);
        Employee employee2 = new Employee(3, "THREE" , new Department("IT", 1), 500);
        Employee employee3 = new Employee(4, "FOUR" , new Department("DEV", 2), 400);
        Employee employee4 = new Employee(5, "FIVE" , new Department("DEV", 2), 500);

        String s= "";
        String repeat = s.repeat(100);


        empList.add(employee);
        empList.add(employee1);
        empList.add(employee2);
        empList.add(employee3);
        empList.add(employee4);

        Map<Department, Optional<Employee>> collected = empList.stream().collect(Collectors.groupingBy(Employee::department, Collectors.maxBy(Comparator.comparing(Employee::salary))));

        collected.forEach((k,v) -> System.out.print(":->:" + k +"::Value::"+ v.get()));
// Print only Employee name and salary
        collected.forEach((k, v) -> {
            if (v.isPresent()) {
                Employee e = v.get();
                System.out.println(e.name() + " " + e.salary());
            }
        });
    }

}