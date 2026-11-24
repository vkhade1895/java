package streams;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Sort a map by key, and by value from highest to lowest.
 *
 * <p>Keywords: Map.Entry.comparingByKey, comparingByValue, Collectors.toMap,
 * LinkedHashMap, TreeMap.
 *
 * <p>First written: 2025-10-21 (old location: {@code testers.MahDay}).
 */
public class SortMapByKeyAndValue {

    public static void main(String[] args) {
        sortMapByKey();
        sortMapByValueDescending();
    }

    private static void sortMapByKey() {
        Map<Integer, String> map = new HashMap<>();
        map.put(9, "a");
        map.put(118, "c");
        map.put(4, "z");

        // Collect into a LinkedHashMap to keep the sorted order (a HashMap would lose it).
        // (v1, v2) -> v1 is the merge function: it only runs if two entries had the same key.
        Map<Integer, String> sortedByKeys = map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
        System.out.println("Map sorted by key: " + sortedByKeys);

        // Simpler way: a TreeMap always keeps its keys sorted
        Map<Integer, String> treeMap = new TreeMap<>(map);
        System.out.println("TreeMap: " + treeMap);
    }

    private static void sortMapByValueDescending() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "b");
        map.put(2, "z");
        map.put(3, "c");

        Map<Integer, String> sortedByValue = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
        System.out.println("Sorted by value, highest first: " + sortedByValue); // {2=z, 3=c, 1=b}
    }
}
