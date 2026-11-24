package concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * Use 2 threads: one multiplies 1 to 10 (1 x 2 x 3 x ... x 10), the other adds 20 to 30
 * (20 + 21 + ... + 30). Print product + sum.
 *
 * <p>Keywords: CompletableFuture, supplyAsync, thenCombine, join, ForkJoinPool.
 *
 * <p>First written: 2026-04-16 (old location: {@code march.T}).
 */
public class ParallelProductAndSum {

    public static void main(String[] args) {
        System.out.println("Product + sum: " + multiplyAndSumInParallel()); // 3629075
    }

    static int multiplyAndSumInParallel() {
        // supplyAsync runs each task in the background on a thread from the common ForkJoin pool.
        // rangeClosed includes the last number; range(1, 10) would stop at 9.
        CompletableFuture<Integer> product = CompletableFuture.supplyAsync(
                () -> IntStream.rangeClosed(1, 10).reduce(1, (a, b) -> a * b));
        CompletableFuture<Integer> sum = CompletableFuture.supplyAsync(
                () -> IntStream.rangeClosed(20, 30).sum());

        // thenCombine adds the two results when both are ready; join() waits for the answer
        return product.thenCombine(sum, Integer::sum).join();
    }
}
