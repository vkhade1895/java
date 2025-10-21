package samples;

import java.util.function.Function;

public class MethodReferences {

	public static void main(String[] args) {

		Function<String, Integer> parsingStringToInt = Integer::parseInt;

		int parsedToIntOp = parsingStringToInt.apply("789");
		System.out.println("parsed string to int:" + parsedToIntOp);
	}

}
