package cn.orangepoet.inaction.parallel.executorservice.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * 比较Java8 ParallelStream并行任务和CompletableFuture执行的差异; CompletableFuture的优势是可以配置线程池大小; 当CPU密集型时, 使用ParallelStream很好, 默认线程数为核数, 当IO密集型时,
 * 考虑使用CompletableFuture, N(thread) = N(CPU) * U(CPU) * (1+W/C) W/C是等待时间与计算时间的比率
 */
@Slf4j
public class ThreadPoolExecutorDemo {

    public static void main(String[] args) {
        log.info("main start...");
        log.info("processor: {}", Runtime.getRuntime().availableProcessors());

        final int parallelDegree = 3;

        List<Integer> tasks = IntStream.range(1, 17).boxed().collect(toList());

        List<Worker> workers = new ArrayList<>();
        for (int i = 0; i < parallelDegree; i++) {
            Worker worker = new CompletableFutureWorker(tasks);
            workers.add(worker);
        }

        long start = System.currentTimeMillis();

        workers.parallelStream().forEach(Worker::doWork);

        long end = System.currentTimeMillis();

        log.info("finish, elapse: {}ms", end - start);
    }

    /**
     *
     */
    private interface Worker {
        void doWork();
    }

    private static abstract class AbstractWorker implements Worker {
        protected final List<Integer> tasks;

        public AbstractWorker(List<Integer> tasks) {
            this.tasks = tasks;
        }

        @Override
        public void doWork() {
            log.info("start work");

            long startTime = System.currentTimeMillis();
            List<Integer> result = workCore();
            long endTime = System.currentTimeMillis();

            log.info("work finish,elapse: {}, result: {}", endTime - startTime, result);
        }

        protected abstract List<Integer> workCore();

        protected static Integer calculate(Integer i) {
            try {
                log.info("calculate {}", i);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return i;
        }

    }

    private static class CompletableFutureWorker extends AbstractWorker {
        private static final AtomicInteger threadNum = new AtomicInteger();
        private static final ExecutorService workService = Executors.newFixedThreadPool(8,
            r -> new Thread(r, "CustomThreadPool-" + threadNum.incrementAndGet()));

        public CompletableFutureWorker(List<Integer> tasks) { super(tasks); }

        @Override
        protected List<Integer> workCore() {
            List<CompletableFuture<Integer>> futureStream = tasks
                .parallelStream()
                .map(i -> CompletableFuture.supplyAsync(() -> calculate(i), workService))
                .collect(toList());

            return futureStream.stream().map(CompletableFuture::join).collect(toList());
        }
    }

    private static class ParallelStreamWorker extends AbstractWorker {
        public ParallelStreamWorker(List<Integer> tasks) {super(tasks);}

        @Override
        protected List<Integer> workCore() {
            return tasks.parallelStream().map(AbstractWorker::calculate).collect(toList());
        }
    }
}
