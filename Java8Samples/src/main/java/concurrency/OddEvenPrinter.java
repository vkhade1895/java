package concurrency;

/**
 * Two threads print the numbers 1 to 10 in order: one prints the odd numbers,
 * the other prints the even numbers.
 *
 * <p>Keywords: wait and notify, synchronized, shared lock, taking turns between threads.
 *
 * <p>First written: 2026-11-24.
 */
public class OddEvenPrinter {

    private static final int MAX = 10;
    private static final Object LOCK = new Object();
    private static int number = 1; // the next number to print (only changed while holding LOCK)

    public static void main(String[] args) throws InterruptedException {
        Thread odd = new Thread(() -> printNumbers(1), "odd");
        Thread even = new Thread(() -> printNumbers(0), "even");
        odd.start();
        even.start();
        odd.join();
        even.join();
    }

    // remainder 1: this thread prints odd numbers. remainder 0: it prints even numbers.
    private static void printNumbers(int remainder) {
        synchronized (LOCK) {
            while (number <= MAX) {
                if (number % 2 == remainder) {
                    System.out.println(Thread.currentThread().getName() + " thread: " + number);
                    number++;
                    LOCK.notify(); // wake up the other thread: it is its turn now
                } else {
                    try {
                        LOCK.wait(); // not my turn: let go of the lock and wait
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        }
    }
}
