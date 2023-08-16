package cn.orangepoet.inaction.parallel.ttl;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @author chengzhi
 * @date 2022/03/03
 */
@Slf4j
public class TtlDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TransmittableThreadLocal<String> ttl = new TransmittableThreadLocal<String>() {
            @Override
            protected void afterExecute() {
                remove();
            }
        };
        ttl.set("hello, world");

        ThreadLocal<String> tl = new ThreadLocal<>();
        tl.set("minemine");

        Thread t = new Thread(() -> {
            log.info("tl1: {}", ttl.get());
            log.info("tl2: {}", tl.get());
        });
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("end, ttl: {}", ttl.get());
    }
}
