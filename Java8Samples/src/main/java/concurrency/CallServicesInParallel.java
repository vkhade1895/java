package concurrency;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Call 3 slow services at the same time, wait for all of them with allOf, and use a fallback
 * value when a service fails or is too slow.
 *
 * <p>Keywords: CompletableFuture, supplyAsync, allOf, join, exceptionally, completeOnTimeout,
 * orTimeout, fallback value, own thread pool.
 *
 * <p>First written: 2026-11-24.
 */
public class CallServicesInParallel {

    public static void main(String[] args) {
        // Use your own thread pool for slow calls (HTTP, database), not the shared common pool.
        // On Java 21, Executors.newVirtualThreadPerTaskExecutor() works well too.
        try (ExecutorService pool = Executors.newFixedThreadPool(3)) {
            long start = System.currentTimeMillis();

            // 1. Works: takes 300 ms.
            CompletableFuture<String> user = CompletableFuture
                    .supplyAsync(() -> callService("user", 300), pool);

            // 2. Fails: exceptionally() turns the error into a fallback value.
            CompletableFuture<String> orders = CompletableFuture
                    .supplyAsync(() -> failingService("orders"), pool)
                    .exceptionally(error -> "orders: none (service failed)");

            // 3. Too slow: after 500 ms, completeOnTimeout() gives a fallback value.
            //    (orTimeout() would fail with a TimeoutException instead.)
            CompletableFuture<String> offers = CompletableFuture
                    .supplyAsync(() -> callService("offers", 2000), pool)
                    .completeOnTimeout("offers: none (too slow)", 500, TimeUnit.MILLISECONDS);

            // allOf() is done when all three are done, and join() waits for that.
            CompletableFuture.allOf(user, orders, offers).join();
            long millis = System.currentTimeMillis() - start;

            // All three are done now, so each join() returns straight away.
            // [user: done, orders: none (service failed), offers: none (too slow)]
            System.out.println(List.of(user.join(), orders.join(), offers.join()));
            // About 500 ms: the calls ran at the same time, and we stopped waiting for the
            // slow one after 500 ms.
            System.out.println("Took about " + millis + " ms");
        }
        // Closing the pool waits for running tasks. completeOnTimeout() doesn't stop the slow
        // call, it only stops us waiting for it, so the program ends after about 2 seconds.
    }

    private static String callService(String name, int millis) {
        try {
            Thread.sleep(millis);       // pretend this is a slow network call
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return name + ": done";
    }

    private static String failingService(String name) {
        throw new IllegalStateException(name + " service is down");
    }
}
