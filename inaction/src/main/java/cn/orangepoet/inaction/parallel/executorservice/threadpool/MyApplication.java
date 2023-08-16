package cn.orangepoet.inaction.parallel.executorservice.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chengzhi
 * @date 2022/03/03
 */
@Slf4j
public class MyApplication {
    private static final MyThreadPoolExecutor executor = new MyThreadPoolExecutor(8, 16, 60, TimeUnit.SECONDS, new SynchronousQueue<>(),
        new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
        try {
            MyContext.setRpcContext("orange");
            Integer ret = executor.submit(() -> add(1, 2)).get();
            log.info("main.end, ret: {}", ret);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();

        log.info("end");
    }

    public static int add(int i, int j) {
        try {
            log.info("add.context:{}", MyContext.currentRpcContext());
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i + j;
    }
}
