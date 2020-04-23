package cn.orangepoet.inaction.parallel.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Demo的主要意图是并行执行流因为锁的限制变成串行执行
 */
public class ReentrantLockDemo {
    private static ExecutorService service = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            service.submit(new MyTask(i));
        }

        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class MyTask implements Runnable {
        private static final ReentrantLock LOCK = new ReentrantLock(true);
        private final int seq;

        public MyTask(int seq) {
            this.seq = seq;
        }

        @Override
        public void run() {
            LOCK.lock();
            try {
                try {
                    System.out.println("handling " + seq);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                LOCK.unlock();
            }
        }
    }
}
