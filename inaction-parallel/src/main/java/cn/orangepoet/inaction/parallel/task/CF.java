package cn.orangepoet.inaction.parallel.task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class CF {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture.runAsync(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    throw new RuntimeException("err1");
                })
                .whenComplete((r, t) -> log.error("error", t));


        log.info("main finish");
        Thread.sleep(5000);
    }
}
