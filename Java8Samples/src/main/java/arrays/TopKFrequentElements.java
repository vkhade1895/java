package arrays;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Return the k numbers that appear most often in an array.
 *
 * <p>Keywords: frequency map, PriorityQueue, min-heap of size k, O(n log k).
 *
 * <p>First written: 2026-11-23 (old location: Questions.md, coding question 9).
 */
public class TopKFrequentElements {

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3};
        System.out.println("Top 2 frequent: " + topKFrequent(nums, 2)); // [2, 1] (any order)
    }

    // Count each number, then keep a min-heap of at most k entries ordered by count.
    // When it grows past k, the least frequent is removed, so the k most frequent stay.
    public static List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Long> freqMap = Arrays.stream(nums)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        PriorityQueue<Map.Entry<Integer, Long>> minHeap =
                new PriorityQueue<>(Comparator.comparingLong(Map.Entry::getValue));

        for (Map.Entry<Integer, Long> entry : freqMap.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        return minHeap.stream().map(Map.Entry::getKey).toList();
    }
}
