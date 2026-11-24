package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * Make threads wait for each other with a CountDownLatch (wait until N tasks are done) and a
 * CyclicBarrier (N threads wait at a meeting point, then all go on together).
 *
 * <p>Keywords: CountDownLatch, countDown, await, CyclicBarrier, barrier action, reusable,
 * Semaphore.
 *
 * <p>First written: 2026-11-24.
 */
public class LatchAndBarrier {

    // CountDownLatch vs CyclicBarrier:
    // - Latch: some threads count down, and other threads wait until the count reaches 0.
    //   It can be used only once.
    // - Barrier: a group of threads wait for each other. When the last one arrives, they all
    //   go on. Then it resets itself and can be used again (that is why it is "cyclic").
    // The third helper, Semaphore, limits how many threads can run a piece of code at the
    // same time: see VirtualThreadsDemo.

    public static void main(String[] args) throws InterruptedException {
        waitWithLatch();
        meetAtBarrier();
    }

    // The main thread waits until 3 services have started. Each service calls countDown()
    // once, and await() returns when the count reaches 0. The "started" lines can come in any
    // order, but "All 3 services started" always comes after them.
    private static void waitWithLatch() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        for (String service : List.of("database", "cache", "queue")) {
            new Thread(() -> {
                System.out.println(service + " started");
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println("All 3 services started, the app can take requests");
    }

    // 3 workers each finish part 1, then wait at the barrier. When the last one arrives, the
    // barrier action runs once, and then all 3 workers start part 2.
    private static void meetAtBarrier() throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(3,
                () -> System.out.println("All workers finished part 1"));
        List<Thread> workers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            int id = i;
            Thread worker = new Thread(() -> {
                System.out.println("Worker " + id + " finished part 1");
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                } catch (BrokenBarrierException e) {
                    return;     // another worker failed, so the barrier is broken
                }
                System.out.println("Worker " + id + " started part 2");
            });
            workers.add(worker);
            worker.start();
        }
        for (Thread worker : workers) {
            worker.join();      // wait until every worker has finished
        }
    }
}
