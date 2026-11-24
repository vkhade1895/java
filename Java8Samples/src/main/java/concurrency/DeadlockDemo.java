package concurrency;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * Deadlock: two threads wait for each other forever. How it happens, how to find it,
 * and how to fix it.
 *
 * <p>Keywords: deadlock, lock order, synchronized, ThreadMXBean, thread dump, jstack, tryLock.
 *
 * <p>First written: 2026-11-24.
 */
public class DeadlockDemo {

    public static void main(String[] args) throws InterruptedException {
        createDeadlock();
        fixWithSameLockOrder();
    }

    // Thread 1 takes lock A, then B. Thread 2 takes lock B, then A.
    // Each one holds one lock and waits forever for the other.
    private static void createDeadlock() throws InterruptedException {
        Object lockA = new Object();
        Object lockB = new Object();
        Thread t1 = new Thread(() -> lockBoth(lockA, lockB), "thread-1");
        Thread t2 = new Thread(() -> lockBoth(lockB, lockA), "thread-2");
        t1.setDaemon(true); // daemon threads don't stop the program from ending
        t2.setDaemon(true);
        t1.start();
        t2.start();
        Thread.sleep(500); // give them time to get stuck

        // The JVM can find deadlocks for us. A thread dump (jstack <pid>) shows the same thing.
        ThreadMXBean threads = ManagementFactory.getThreadMXBean();
        long[] stuck = threads.findDeadlockedThreads();
        System.out.println("Deadlocked threads found: " + (stuck == null ? 0 : stuck.length)); // 2
    }

    // The fix: every thread takes the locks in the same order (A, then B).
    // Then no thread can hold B while waiting for A, so nobody waits in a circle.
    // Another fix: ReentrantLock.tryLock(timeout). If the second lock is busy,
    // let go of the first lock and try again later.
    private static void fixWithSameLockOrder() throws InterruptedException {
        Object lockA = new Object();
        Object lockB = new Object();
        Thread t1 = new Thread(() -> lockBoth(lockA, lockB), "thread-1");
        Thread t2 = new Thread(() -> lockBoth(lockA, lockB), "thread-2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Same lock order: both threads finished, no deadlock");
    }

    private static void lockBoth(Object first, Object second) {
        synchronized (first) {
            sleep(100); // makes the deadlock happen every time in the first demo
            synchronized (second) {
                // both locks are held here: do the work
            }
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
