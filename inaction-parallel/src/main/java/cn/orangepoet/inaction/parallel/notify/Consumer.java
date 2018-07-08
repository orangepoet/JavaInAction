package cn.orangepoet.inaction.parallel.notify;

import java.util.Queue;
import java.util.Random;

/**
 * 消费者;
 * 使用等待/通知机制, 在队列中存在元素时才消费;
 * 等待通知机制的样板:
 * while ( 条件不满足) {
 * 对象.wait()
 * }
 * <p>
 * 通知方遵循:
 * 1) 获取对象的锁 (此处是queue)
 * 2) 改变条件 (queue的出列或入列)
 * 3) 通知所有在等待对象上的线程 (当存在多个生产者和多个消费者的情况需要通知所有, 防止错误通知, 即使用notifyall(), 而非notify())
 */
public class Consumer implements Runnable {
    private final Queue<Integer> queue;
    private final String name;
    private static final Random rnd = new Random();

    public Consumer(Queue<Integer> queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    @Override
    public void run() {
        logInfo("start");
        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        logInfo("wait()");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Integer i = queue.poll();
                if (i != null)
                    logInfo("consume " + String.valueOf(i));
                queue.notifyAll();
            }

            try {
                Thread.sleep(rnd.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void logInfo(String msg) {
        String header = String.format("consumer[%s]: ", this.name);
        System.out.println(header + msg);
    }
}
