package samples;

@FunctionalInterface
interface I1 {
    int calculate(int a);

	default double sqrt(int a) {
		return Math.sqrt(positive(a));
	}

	static int positive(int a) {
		return a > 0 ? a : 0;
	}
}

class InterfaceTests implements I1 {
	public static void main(String[] args) {
		System.out.println("This is a basic interface for testing.");
		System.out.println(
				"Here we are testing interface functionality for abstract , static and default methods in interface.");
		int input = 10;
		int positive = I1.positive(input); // calling static function from interface
		System.out.println("Check positive number:" + positive);
		InterfaceTests m = new InterfaceTests();
		double sqrt = m.sqrt(input);
		System.out.println("Sqaure root:" + sqrt);
		int calculateOp = m.calculate(input);
		System.out.println("Square output:" + calculateOp);

	}

	@Override
	public int calculate(int a) {
		return a * a;
	}
}
