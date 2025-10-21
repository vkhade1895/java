package testers;

import java.util.Arrays;

public class Sun {

	public static void main(String[] args) {
		Integer reduce = Arrays.asList(1,6,7,9,2).stream().reduce(0, (a,b) -> a+b);
		Integer reduce2 = Arrays.asList(1,6,7,9,2).stream().reduce(1, (a,b) -> a*a);
		System.out.println(reduce);
		System.out.println(reduce2);


	}

}
