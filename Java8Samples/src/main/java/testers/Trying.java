package testers;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Trying {

	public static void main(String[] args) {
//		int m1 = m1();
//		System.out.println("op:" + m1);
//		m2();
        m3();
	}

	private static int m1() {
		// TODO Auto-generated method stub
		try {
			throw new Exception("a");
		} catch (Exception e) {
			return -2;
		}finally {
			return -3;
		}
	}

	private static void m2() {
		String s= "intervienw";

		int indexOf = s.indexOf("n");
		int lastIndexOf = s.lastIndexOf("n");
		System.out.println("index:" + indexOf);
		System.out.println("lastIndexOf:" + lastIndexOf);
		Optional<Character> first = s.chars().mapToObj(c-> (char) c).filter(e-> s.indexOf(e) == s.lastIndexOf(e)).findFirst();

		System.out.println("first:" + first);
		Set<Character> set = new HashSet();
		
//		 Optional<Character> any = s.chars().mapToObj(c-> (char)c).filter(e-> set.add(e)).findFirst();
		  Stream<Character> distinct = s.chars().mapToObj(c-> (char)c).distinct();
		 System.out.println("any" );
        distinct.forEach(System.out::println);
		 
		 Map<Character, Long> collect = s.chars().mapToObj(e -> (char)e).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		 System.out.println("collect:" + collect);
	}

    // input = "tomorrow"
    // output = "t$m$$rr$$$w"
    static void  m3(){
        String s="tomorrow";
        char[] charArray = s.toCharArray();
        char c ='o';
        int count = 1;
        StringBuilder stringBuilder = new StringBuilder();

        for (char value : charArray) {
            if (value == c) {
//                stringBuilder.append("$".repeat(count));
				for (int i = 0; i < count; i++) {
					stringBuilder.append('$');
				}
				count++;
            } else {
                stringBuilder.append(value);
            }

        }

        System.out.println("s1:" + stringBuilder);
    }

}
