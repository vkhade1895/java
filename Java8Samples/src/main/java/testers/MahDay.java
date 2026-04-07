package testers;

import java.util.*;
import java.util.stream.Collectors;

public class MahDay {

	public static void main(String[] args) {

		//		2nd highest element in an array
		int []a= {1,6,9,0,2,3};

		Optional<Integer> first = Arrays.stream(a).boxed().sorted(Comparator.reverseOrder()).skip(1).findFirst();
		System.out.println("element is:"+ first);

		// sort elements in a hashmap based on keys
		Map<Integer, String> map = new HashMap<>();
		map.put(9, "a");
		map.put(118, "c");
		map.put(4, "z");

		Map<Integer, String> collect = map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		Map<Integer, String> sortedByKeys = map.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(e1, e2) -> e1,
						LinkedHashMap::new
				));
		System.out.println("my map in ascending keys::"+ sortedByKeys );
		System.out.println("my map::"+ collect );
		TreeMap treeMap = new TreeMap<>(map);
		System.out.println("treemap:" + treeMap);
		
		Map<Integer, String> map2 = new HashMap<>();
		map2.put(1, "z");
		map2.put(2, "c");
		map2.put(3, "b");
		
		Map<Integer, String> collect2 = map2.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a1,b) -> a1,LinkedHashMap::new));
		System.out.println("collect2::" + collect2);
	}

}
