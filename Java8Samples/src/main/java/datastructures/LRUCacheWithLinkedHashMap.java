package datastructures;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The short way to build an LRU cache: extend LinkedHashMap, keep the entries in access
 * order, and remove the least recently used entry when the cache is full. LRUCache builds
 * the same thing by hand.
 *
 * <p>Keywords: LRU cache, LinkedHashMap, access order, removeEldestEntry, serialVersionUID.
 *
 * <p>First written: 2026-11-24.
 */
public class LRUCacheWithLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    // LinkedHashMap is Serializable, so a class that extends it should have a version number.
    private static final long serialVersionUID = 1L;

    private final int capacity;

    LRUCacheWithLinkedHashMap(int capacity) {
        // The third argument, true, means access order: get() and put() move an entry to the
        // end, so the least recently used entry is always first. The default, false, keeps
        // the order in which the keys were added.
        super(16, 0.75f, true);
        this.capacity = capacity;
    }

    // LinkedHashMap calls this after put() adds a new key. Returning true removes the eldest
    // (first) entry, which is the least recently used one.
    // Not thread-safe: for many threads, use a cache library such as Caffeine.
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }

    public static void main(String[] args) {
        LRUCacheWithLinkedHashMap<Integer, String> cache = new LRUCacheWithLinkedHashMap<>(2);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.get(1);                   // 1 is now the most recently used
        cache.put(3, "three");          // the cache is full, so 2 (the least recently used) goes
        System.out.println(cache);      // {1=one, 3=three}
    }
}
