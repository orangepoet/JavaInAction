package cn.orangepoet.inaction.parallel.task;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {
    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> 1)
                .thenCombine(CompletableFuture.supplyAsync(() -> 2),
                        (i, j) -> i + j)
//                .thenApply(i -> String.valueOf(i));
                .thenCompose(r -> CompletableFuture.supplyAsync(() -> String.valueOf(r)));
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
