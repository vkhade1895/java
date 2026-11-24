package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Many threads add 1 to the same counter. Why {@code volatile} is not enough,
 * and what to use instead.
 *
 * <p>Keywords: race condition, count++ is not atomic, volatile, AtomicInteger, synchronized.
 *
 * <p>First written: 2026-11-24.
 */
public class CounterRace {

    private static final int THREADS = 4;
    private static final int ADDS_PER_THREAD = 100_000;
    private static final Object LOCK = new Object();

    private static volatile int volatileCount = 0;
    private static final AtomicInteger atomicCount = new AtomicInteger();
    private static int synchronizedCount = 0;

    public static void main(String[] args) throws InterruptedException {
        int expected = THREADS * ADDS_PER_THREAD;

        // count++ is three steps: read, add 1, write. Two threads can read the same value,
        // and then one of the adds is lost. volatile makes changes visible, not atomic.
        runInThreads(() -> volatileCount++);
        System.out.println("volatile int:  " + volatileCount
                + " (expected " + expected + ", usually less)");

        // AtomicInteger does the add in one safe step (compare-and-swap)
        runInThreads(atomicCount::incrementAndGet);
        System.out.println("AtomicInteger: " + atomicCount.get());

        // synchronized lets only one thread at a time do the add
        runInThreads(() -> {
            synchronized (LOCK) {
                synchronizedCount++;
            }
        });
        System.out.println("synchronized:  " + synchronizedCount);
    }

    // Runs the add in 4 threads at the same time, 100,000 times in each thread
    private static void runInThreads(Runnable add) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int t = 0; t < THREADS; t++) {
            Thread thread = new Thread(() -> {
                for (int i = 0; i < ADDS_PER_THREAD; i++) {
                    add.run();
                }
            });
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
