package cn.orangepoet.inaction.parallel.threadpool;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 简单工作池实现, 基于workers & jobs, jobs为当前任务列表, workers为当前工作者列表, 当新来一个任务时, 尝试新加一个工作者来处理,
 * 数量由最大和最小工作者数控制. 工作和任务通过等待/通知机制阻塞协调; 当工作者处理完一个任务后, 从列表中移除并关闭;
 */
public class CustomThreadPoolImpl implements CustomThreadPool {
    private static final int MAX_THREAD_POOL_SIZE = 10;
    private static final int MIN_THREAD_POOL_SIZE = 1;
    private static final int DEFAULT_WORKER_NUMBERS = 5;

    private List<Worker> workers = new ArrayList<>();
    private LinkedList<Job> jobs = new LinkedList<>();
    /**
     * 当前工作数
     */
    private int workerNum = DEFAULT_WORKER_NUMBERS;

    public CustomThreadPoolImpl(int workerNum) {
        initializeWorkers(DEFAULT_WORKER_NUMBERS);
    }

    /**
     * 初始化工作群, 每个工作者被包含在一个线程中运行;
     *
     * @param n
     */
    public void initializeWorkers(int n) {
        int num = n > MAX_THREAD_POOL_SIZE ? MAX_THREAD_POOL_SIZE : n < MIN_THREAD_POOL_SIZE ? MIN_THREAD_POOL_SIZE : n;
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker);
            thread.start();
        }
    }

    /**
     * 增加工作者
     * <p>
     * 加入新的工作者调度, 调用 {@link #initializeWorkers} 初始化更多工作者
     * </p>
     *
     * @param num
     */
    @Override
    public void addWorker(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("num < 0 ");
        }
        synchronized (jobs) {
            if (num + workers.size() > MAX_THREAD_POOL_SIZE) {
                num = MAX_THREAD_POOL_SIZE - workers.size();
            }

            initializeWorkers(num);
            this.workerNum += num;
        }
    }

    /**
     * 减少工作者
     *
     * @param num
     */
    @Override
    public void removeWorker(int num) {
        synchronized (jobs) {
            if (num > workers.size()) {
                throw new IllegalArgumentException("num > workers count");
            }

            int count = 0;
            for (int i = 0; i < num; i++) {
                Worker worker = workers.get(i);
                workers.remove(worker);
                worker.shutDown();
                count++;
            }

            this.workerNum -= count;
        }
    }

    /**
     * 执行任务
     *
     * @param job
     */
    @Override
    public void execute(Job job) {
        if (job != null) {
            synchronized (jobs) {
                jobs.add(job);
                jobs.notify();
            }
        }
    }

    /**
     * 关闭线程池
     */
    @Override
    public void shutDown() {
        for (Worker worker : workers) {
            worker.shutDown();
        }
    }

    public class Worker implements Runnable {
        private volatile boolean runnable = true;

        @Override
        public void run() {
            Job job = null;
            while (runnable) {
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }
            }
            if (job != null) {
                try {
                    job.run();
                } catch (Exception e) {
                    // ignore
                }
            }
        }

        public void shutDown() {
            this.runnable = false;
        }
    }

}
