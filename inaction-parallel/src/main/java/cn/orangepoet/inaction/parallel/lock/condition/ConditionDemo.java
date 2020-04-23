package cn.orangepoet.inaction.parallel.lock.condition;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 条件队列的使用, Java 监视器锁和等待/通知机制的高级版
 * 参照 {@link NotifyDemo}, 使用了Condition对象的await()和signalAll()方法协调等待/通知
 */
public class ConditionDemo {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    private static final int WAITER_COUNT = 10;
    private static CountDownLatch startGate = new CountDownLatch(WAITER_COUNT);
    private static CountDownLatch endGate = new CountDownLatch(WAITER_COUNT);

    public static void main(String[] args) {

        for (int i = 0; i < WAITER_COUNT; i++) {
            Thread waiterThread = new Thread(new Waiter(), "waiter-" + (i + 1));
            waiterThread.start();
        }

        Thread notifierThread = new Thread(new Notifer(), "notifer");
        notifierThread.start();

        try {
            endGate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("all run finish");
    }

    public static class Waiter implements Runnable {
        @Override
        public void run() {
            startGate.countDown();
            System.out.println(getThreadName() + ": prepare to run");
//            synchronized (lock) {
            lock.lock();
            try {
                condition.await();
                System.out.println(getThreadName() + ": receive notify and go on");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println(getThreadName() + ": finish");
            endGate.countDown();
        }

        public String getThreadName() {
            return Thread.currentThread().getName();
        }
    }

    public static class Notifer implements Runnable {
        @Override
        public void run() {
            int i = 0;
            while (true) {
                try {
                    startGate.await();
//                    synchronized (lock) {
//                        System.out.println(Thread.currentThread().getName() + ": prepare to notify ");
//                        lock.notifyAll();
//                    }
                    lock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName() + ": prepare to notify ");
                        condition.signalAll();
                        i++;
                    } finally {
                        lock.unlock();
                    }
                    if (i >= WAITER_COUNT) {
                        break;
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
