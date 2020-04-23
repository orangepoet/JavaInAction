package cn.orangepoet.inaction.parallel.lock.condition;

import java.util.concurrent.CountDownLatch;

/**
 * 对比使用 通知/通知和条件队列, 参照 {@link ConditionDemo}条件队列
 * 演示线程wait() 和notifyAll(), 等待/通知机制
 * 使用CountDownLatch控制程序的并发执行, 同步栅栏, 线程调用await()阻塞直到CountDownLatch初始化状态值变量被一次次调用countDown()
 * 方法减少到0时, 栅栏释放, 等待线程继续执行, 分别使用了开始门和结束门, 控制程序并发执行和等待所有并发执行完成;
 */
public class NotifyDemo {
    private static Object lock = new Object();
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
            synchronized (lock) {
                try {
                    lock.wait();
                    System.out.println(getThreadName() + ": receive notify and go on");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
            while (true) {
                try {
                    startGate.await();
                    synchronized (lock) {
                        System.out.println(Thread.currentThread().getName() + ": prepare to notify ");
                        lock.notifyAll();
                    }
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
