package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Virtual threads (Java 21): run thousands of tasks that wait, compare with a normal thread pool,
 * and limit how many tasks call a slow service at the same time.
 *
 * <p>Keywords: virtual threads, Executors.newVirtualThreadPerTaskExecutor, fixed thread pool,
 * Semaphore, tasks that wait (I/O).
 *
 * <p>First written: 2026-11-24.
 */
public class VirtualThreadsDemo {

    public static void main(String[] args) {
        // 10,000 tasks that each wait 200 ms. Each task gets its own virtual thread.
        long virtualMs = runTasks(Executors.newVirtualThreadPerTaskExecutor(), 10_000);
        System.out.printf("10,000 tasks on virtual threads took about %.1f seconds%n",
                virtualMs / 1000.0);

        // Only 1,000 tasks here, on 100 normal threads: they run in 10 rounds of 200 ms
        long poolMs = runTasks(Executors.newFixedThreadPool(100), 1_000);
        System.out.printf("1,000 tasks on 100 normal threads took about %.1f seconds%n",
                poolMs / 1000.0);

        limitWithSemaphore();
    }

    // Starts the tasks, waits until all of them finish, and returns the time in milliseconds
    private static long runTasks(ExecutorService executor, int taskCount) {
        long start = System.currentTimeMillis();
        try (executor) {
            for (int i = 0; i < taskCount; i++) {
                executor.submit(() -> {
                    Thread.sleep(200); // waiting, like a slow database call
                    return null;
                });
            }
        } // close() waits here until every task has finished
        return System.currentTimeMillis() - start;
    }

    // A Semaphore with 3 permits: at most 3 tasks can be inside the "slow service" at once
    private static void limitWithSemaphore() {
        Semaphore permits = new Semaphore(3);
        AtomicInteger running = new AtomicInteger();
        AtomicInteger mostAtOnce = new AtomicInteger();
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 20; i++) {
                executor.submit(() -> {
                    permits.acquire(); // wait for a free permit
                    try {
                        mostAtOnce.accumulateAndGet(running.incrementAndGet(), Math::max);
                        Thread.sleep(50); // the slow service call
                    } finally {
                        running.decrementAndGet();
                        permits.release(); // give the permit back
                    }
                    return null;
                });
            }
        }
        System.out.println("Most tasks inside the slow service at once: " + mostAtOnce.get()); // 3
    }
}
