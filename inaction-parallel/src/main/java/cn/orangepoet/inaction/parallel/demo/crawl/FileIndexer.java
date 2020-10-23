package cn.orangepoet.inaction.parallel.demo.crawl;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * 文件标记器, 从阻塞队列中获取文件名并打印处理
 */
public class FileIndexer implements Runnable {
    private final BlockingQueue<File> queue;

    public FileIndexer(BlockingQueue<File> queue) {
        if (queue == null)
            throw new NullPointerException("queue");
        this.queue = queue;
    }

    public void index() {
        try {
            while (queue.isEmpty()) {
                File file = queue.take();
                indexFile(file);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void indexFile(File file) {
        System.out.println("index file...");
    }

    @Override
    public void run() {
        index();
    }
}
