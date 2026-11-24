package datastructures;

import java.util.HashMap;
import java.util.Map;

/**
 * Design a data structure that follows the rules of a Least Recently Used (LRU) cache,
 * without extending any Map class.
 *
 * <p>{@code get(key)} returns the value of the key if it exists, otherwise -1.
 * {@code put(key, value)} updates the value if the key exists, otherwise adds it. If the
 * number of keys goes over the capacity, the least recently used key is removed.
 * Both must run in O(1) average time.
 *
 * <p>How it works: the map finds a node in O(1), and a doubly linked list keeps the order of use.
 * The node after {@code oldest} is the least recently used; the node before {@code latest}
 * is the most recently used.
 *
 * <p>Keywords: LRU cache, HashMap, doubly linked list, dummy head and tail, O(1).
 *
 * <p>First written: 2026-04-07 (old location: {@code model.LRUCache} and {@code model.Test}).
 */
public class LRUCache {

    private final int capacity;
    private final Map<Integer, Node> cache = new HashMap<>();
    // Two dummy nodes mark the ends of the list, so we never have to check for null
    private final Node oldest = new Node(0, 0);
    private final Node latest = new Node(0, 0);

    public LRUCache(int capacity) {
        this.capacity = capacity;
        oldest.next = latest;
        latest.prev = oldest;
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            // Move the node to the "latest" end, because it was just used
            remove(node);
            insert(node);
            return node.val;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            remove(cache.get(key));
        }
        Node newNode = new Node(key, value);
        cache.put(key, newNode);
        insert(newNode);

        if (cache.size() > capacity) {
            Node lru = oldest.next;
            remove(lru);
            cache.remove(lru.key);
        }
    }

    // Unlink a node from the list
    private void remove(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
    }

    // Add a node just before the "latest" dummy node (the most recently used position)
    private void insert(Node node) {
        Node prev = latest.prev;
        prev.next = node;
        node.prev = prev;
        node.next = latest;
        latest.prev = node;
    }

    // Example with capacity 2
    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);
        lruCache.put(1, 1);                  // cache is {1=1}
        lruCache.put(2, 2);                  // cache is {1=1, 2=2}
        System.out.println(lruCache.get(1)); // 1
        lruCache.put(3, 3);                  // key 2 was least recently used, cache is {1=1, 3=3}
        System.out.println(lruCache.get(2)); // -1 (not found)
        lruCache.put(4, 4);                  // key 1 was least recently used, cache is {3=3, 4=4}
        System.out.println(lruCache.get(1)); // -1 (not found)
        System.out.println(lruCache.get(3)); // 3
        System.out.println(lruCache.get(4)); // 4
    }

    private static class Node {
        final int key;
        final int val;
        Node prev;
        Node next;

        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
}
