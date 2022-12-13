package cn.orangepoet.inaction.parallel.mono;

import java.util.concurrent.CompletableFuture;

import reactor.core.publisher.Mono;

public class Application {
    public static void main(String[] args) {
        Mono<Integer> mono = Mono.fromFuture(CompletableFuture.supplyAsync(() -> caculate()));
        Integer result = mono.block();
        System.out.println(result);
    }

    private static int caculate() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 10;
    }
}
