package cn.orangepoet.inaction.parallel.threadpool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.stream.Collectors.toList;

/**
 * 比较Java8 ParallelStream并行任务和CompletableFuture执行的差异;
 * CompletableFuture的优势是可以配置线程池大小;
 * 当CPU密集型时, 使用ParallelStream很好, 默认线程数为核数,
 * 当IO密集型时, 考虑使用CompletableFuture, N(thread) = N(CPU) * U(CPU) * (1+W/C)
 * W/C是等待时间与计算时间的比率
 */
public class ThreadPoolExecutorDemo {
    private static final int PARALLEL_REQUEST = 40;
    private static Integer[] tasks;


    public static void main(String[] args) {
        List<Worker> workers = new ArrayList<>();
        for (int i = 0; i < PARALLEL_REQUEST; i++) {
            Worker worker = new CompletableFutureWorker();
            workers.add(worker);
        }
        System.out.println("processor: " + Runtime.getRuntime().availableProcessors());
        System.out.println("main start..");
        long start = System.currentTimeMillis();
        if (PARALLEL_REQUEST > 1)
            workers.parallelStream().forEach(w -> w.doWork());
        else if (PARALLEL_REQUEST == 1)
            workers.get(0).doWork();
        else
            throw new IllegalStateException("invalid parallel request");
        long end = System.currentTimeMillis();
        System.out.println("main finish, elapse: " + (end - start));
    }

    private interface Worker {
        void doWork();
    }

    private static abstract class AbstractWorker implements Worker {
        private static final int PARALLEL_TASK = 16;
        private static final Integer[] TASKS;

        static {
            TASKS = new Integer[PARALLEL_TASK];
            for (int i = 0; i < PARALLEL_TASK; i++) {
                TASKS[i] = i + 1;
            }
        }

        @Override
        public void doWork() {
            long startTime = System.currentTimeMillis();
            System.out.println("worker start..");

            List<Integer> result = workCore();
            System.out.println(result);

            long endTime = System.currentTimeMillis();
            System.out.println("worker finish, elapse:" + (endTime - startTime));
        }

        protected abstract List<Integer> workCore();

        protected static Integer calculate(Integer i) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return i;
        }


    }

    private static class CompletableFutureWorker extends AbstractWorker {
        private static ExecutorService workService = Executors.newFixedThreadPool(8);

        @Override
        protected List<Integer> workCore() {
            List<CompletableFuture<Integer>> futureStream = Arrays.asList(tasks)
                    .parallelStream()
                    .map(i -> CompletableFuture.supplyAsync(() -> calculate(i), workService))
                    .collect(toList());

            return futureStream.stream()
                    .map(CompletableFuture::join)
                    .collect(toList());
        }
    }

    private static class ParallelStreamWorker extends AbstractWorker {
        @Override
        protected List<Integer> workCore() {
            return Arrays.stream(tasks)
                    .parallel()
                    .map(AbstractWorker::calculate).collect(toList());
        }
    }
}
