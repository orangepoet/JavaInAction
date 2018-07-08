package cn.orangepoet.inaction.parallel.crawl;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

/**
 * 文件爬取器, 将文件名放入队列中;
 * 基于阻塞队列的同步实现;
 */
public class FileCrawler implements Runnable {
    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File root;

    public FileCrawler(File root, BlockingQueue<File> queue) {
        this.root = root;
        this.fileQueue = queue;
        this.fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        };
    }

    @Override
    public void run() {
        try {
            crawl(root);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void crawl(File root) throws InterruptedException {
        File[] entries = root.listFiles(fileFilter);
        for (File file : entries) {
            if (file.isDirectory()) {
                crawl(file);
            } else {
                fileQueue.put(file);
            }
        }
    }
}
