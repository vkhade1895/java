package samples;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
	}

}
