package cn.orangepoet.inaction.parallel.notify;

import java.util.Queue;
import java.util.Random;

/**
 * 生产者
 * 使用等待/通知机制实现,参见Consumer的说明
 */
public class Producer implements Runnable {
    private final Queue<Integer> queue;
    private final int maxSize;
    private final String name;
    private static final Random rnd = new Random();

    public Producer(Queue<Integer> queue, int maxSize, String name) {
        this.queue = queue;
        this.maxSize = maxSize;
        this.name = name;
    }

    @Override
    public void run() {
        logInfo("start..");
        while (true) {
            synchronized (queue) {
                while (this.queue.size() >= maxSize) {
                    try {
                        queue.wait();
                        logInfo("wait()");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                int i = rnd.nextInt(100);
                queue.add(i);
                logInfo("produce: " + i);
                //queue.notifyAll();
                // 条件队列中的等待线程属于同类, 当生产一个通知其中一个消费也可以,
                // 如果被通知的等待线程不是同类, 则notify单个可能会让被通知的线程错过;
                queue.notify();
            }

            try {
                Thread.sleep(rnd.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void logInfo(String msg) {
        String header = String.format("Producer[%s]: ", this.name);
        System.out.println(header + msg);
    }
}
