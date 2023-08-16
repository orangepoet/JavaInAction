package cn.orangepoet.inaction.parallel.task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class CompletableFutureDemo {
    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture
            .supplyAsync(() -> {
                log.info("supplyAsync");
                return 1;
            })
            .thenCombine(CompletableFuture.supplyAsync(() -> {
                log.info("thenCombine");
                return 2;
            }), (i, j) -> i + j)
            .thenCompose(r -> CompletableFuture.supplyAsync(() -> {
                log.info("thenCompose");
                return String.valueOf(r);
            }));
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    private static CompletableFuture<Integer> calculateAsync0(int x, int y) {
        CompletableFuture future = new CompletableFuture();
        new Thread(() -> {
            int result = calculate(x, y);
            try {
                future.complete(result);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        }).start();
        return future;
    }

    private static CompletableFuture<Integer> calculateAsync(int x, int y) {
        return CompletableFuture.supplyAsync(() -> calculate(x, y));
    }

    private static int calculate(int x, int y) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return x + y;
    }
}
