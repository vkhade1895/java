package concurrency;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Producer-consumer: one thread puts items into a box with limited space, another thread
 * takes them out. Shown with a BlockingQueue (the easy way) and with wait and notify
 * (the classic way).
 *
 * <p>Keywords: BlockingQueue, ArrayBlockingQueue, put and take, wait and notifyAll,
 * bounded buffer.
 *
 * <p>First written: 2026-11-24.
 */
public class ProducerConsumer {

    private static final int ITEMS = 5;

    public static void main(String[] args) throws InterruptedException {
        withBlockingQueue();
        withWaitAndNotify();
    }

    // put() waits when the queue is full; take() waits when it is empty
    private static void withBlockingQueue() throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2); // room for 2 items
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= ITEMS; i++) {
                    queue.put(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= ITEMS; i++) {
                    System.out.println("BlockingQueue consumer took: " + queue.take());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        startAndWait(producer, consumer);
    }

    private static void withWaitAndNotify() throws InterruptedException {
        BoundedBuffer buffer = new BoundedBuffer(2);
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= ITEMS; i++) {
                    buffer.put(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= ITEMS; i++) {
                    System.out.println("wait/notify consumer took: " + buffer.take());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        startAndWait(producer, consumer);
    }

    private static void startAndWait(Thread producer, Thread consumer) throws InterruptedException {
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }

    // A box with limited space, written with wait() and notifyAll()
    private static class BoundedBuffer {
        private final Queue<Integer> items = new ArrayDeque<>();
        private final int capacity;

        BoundedBuffer(int capacity) {
            this.capacity = capacity;
        }

        synchronized void put(int item) throws InterruptedException {
            // "while", not "if": after waking up, check again. Another thread may have
            // filled the box first, and a thread can sometimes wake up without a notify.
            while (items.size() == capacity) {
                wait();
            }
            items.add(item);
            notifyAll(); // wake up a consumer that is waiting for an item
        }

        synchronized int take() throws InterruptedException {
            while (items.isEmpty()) {
                wait();
            }
            int item = items.remove();
            notifyAll(); // wake up a producer that is waiting for space
            return item;
        }
    }
}
