package cn.orangepoet.inaction.parallel.demo.crawl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileIndexUtility {

    private static final int N_CUSTOMERS = 3;

    public static void main(String[] args) {
        if (args == null || args.length == 0)
            return;

        List<File> files = new ArrayList<File>();
        File file;
        for (String path : args) {
            file = new File(path);
            if (file.exists()) {
                files.add(file);
            }
        }
        startIndexing(files.toArray(new File[files.size()]));
    }

    public static void startIndexing(File[] roots) {
        BlockingQueue<File> queue = new LinkedBlockingQueue<File>();

        for (File root : roots) {
            FileCrawler fileCrawler = new FileCrawler(root, queue);
            new Thread(fileCrawler).run();
        }

        for (int i = 0; i < N_CUSTOMERS; i++) {
            FileIndexer indexer = new FileIndexer(queue);
            new Thread(indexer).run();
        }

    }
}
