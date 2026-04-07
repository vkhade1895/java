package samples;

import java.util.*;
import java.util.stream.Collectors;

public class LambdaExample {

	public static void main(String[] args) {
		List<String> myList = Arrays.asList("kiwi", "apple", "peach", "orange");

		Collections.sort(myList);
		System.out.println("Printing myList:" + myList);

		Collections.sort(myList, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}

		});
		System.out.println("Custom sorting in ascending order:" + myList);

		// Java 1.8 for above code in single line using lambda
		myList.sort((o1, o2) -> o2.compareTo(o1));
		System.out.println("Custom sort in descending order:" + myList);

		// Using Functional interface for providing custom logic for abstract method.
		MyFunctionalInterface addition = Integer::sum;
		MyFunctionalInterface subtraction = (a, b) -> a - b;
		MyFunctionalInterface multiplication = (a, b) -> a * b;
		MyFunctionalInterface division = (a, b) -> (double) a / b;

		double addResult = addition.mathOperation(5, 4);
		double subtractionResult = subtraction.mathOperation(5, 4);
		double multiplicationResult = multiplication.mathOperation(5, 4);
		double divisionResult = division.mathOperation(20, 4);

		System.out.println("Addition output:" + addResult);
		System.out.println("Subtraction output:" + subtractionResult);
		System.out.println("Multiplication output:" + multiplicationResult);
		System.out.println("Division output:" + divisionResult);

		m1();
	}


//Group a list of employees by department .
// Calculate the average salary of employees overall, or group them by gender .
// Count active vs. inactive users in a collection .
// Find the sum of all salaries using reduce() .
	public static void m1(){
		MyEmployee myEmployee = new MyEmployee("Male", 1, 100);
		MyEmployee myEmployee1 = new MyEmployee("Female", 2, 1000);
		MyEmployee myEmployee2 = new MyEmployee("Female", 3, 50);
		MyEmployee myEmployee3 = new MyEmployee("Male", 4, 10);

		List<MyEmployee> myEmployeesList = new ArrayList<>();
		myEmployeesList.add(myEmployee);
		myEmployeesList.add(myEmployee1);
		myEmployeesList.add(myEmployee2);
		myEmployeesList.add(myEmployee3);

		OptionalDouble average = myEmployeesList.stream().mapToInt(MyEmployee::salary).average();
		System.out.println("average::" + average);

		Integer reduce = myEmployeesList.stream().map(MyEmployee::salary).reduce(1, (a, b) -> a + b);
		System.out.println("Sum ::" + reduce);

		// Average salary grouped by gender
		Map<String, Double> avgSalaryByGender = myEmployeesList.stream()
				.collect(Collectors.groupingBy(
						MyEmployee::gender,
						Collectors.averagingInt(MyEmployee::salary)
				));
		System.out.println("Average salary by gender: " + avgSalaryByGender);
	}
}

record MyEmployee(String gender, int id, int salary){

}
