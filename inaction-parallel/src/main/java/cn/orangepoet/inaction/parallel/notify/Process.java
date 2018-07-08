package cn.orangepoet.inaction.parallel.notify;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Process {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * 演示线程的等待/通知机制
     *
     * @param args
     */
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        List<Consumer> consumers = Arrays.asList(
                new Consumer(queue, "c1"),
                new Consumer(queue, "c2")
        );

        List<Producer> producers = Arrays.asList(
                new Producer(queue, 5, "p1"),
                new Producer(queue, 5, "p2")
        );

        for (Consumer consumer : consumers) {
            executorService.submit(consumer);
        }
        for (Producer producer : producers) {
            executorService.submit(producer);
        }
        executorService.shutdown();
    }
}
