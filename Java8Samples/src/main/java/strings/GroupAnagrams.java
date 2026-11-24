package strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Group words that are made of the same letters, with a loop and with streams.
 *
 * <p>Keywords: anagram, HashMap, computeIfAbsent, Collectors.groupingBy, sorted letters as key.
 *
 * <p>First written: 2026-04-07 and 2026-11-23 (old location: {@code march.SampleCodes} and
 * Questions.md coding question 6).
 */
public class GroupAnagrams {

    // Input: ["eat", "tea", "tan", "ate", "nat", "bat"]
    // Output: [[eat, tea, ate], [tan, nat], [bat]] (any order)
    public static void main(String[] args) {
        String[] input = {"eat", "tea", "tan", "ate", "nat", "bat"};
        groupWithLoop(input);
        groupWithStreams(input);
    }

    private static void groupWithLoop(String[] input) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : input) {
            map.computeIfAbsent(sortedLetters(s), k -> new ArrayList<>()).add(s);
        }
        System.out.println("Grouped anagrams: " + new ArrayList<>(map.values()));
    }

    // The same idea with streams: groupingBy uses the sorted letters as the key
    private static void groupWithStreams(String[] input) {
        List<List<String>> groups = new ArrayList<>(Arrays.stream(input)
                .collect(Collectors.groupingBy(GroupAnagrams::sortedLetters))
                .values());
        System.out.println("Grouped anagrams (streams): " + groups);
    }

    // Words with the same letters get the same key after sorting: "eat" -> "aet"
    private static String sortedLetters(String word) {
        char[] letters = word.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }
}
