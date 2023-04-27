package cn.orangepoet.inaction.ex.reactor;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class ReactorTests {
    private static void mockRun(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 限制发送速率
     *
     * @throws InterruptedException
     */
    @Test
    public void requestOnRate() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Flux.<Integer>create(emitter -> {
                    for (int i = 0; i < 100; i++) {
                        emitter.next(i);
                    }
                    emitter.complete();
                })
                .log()
                .subscribeOn(Schedulers.single())
                .limitRate(5, 3)
                .publishOn(Schedulers.boundedElastic())
                .doOnComplete(() -> countDownLatch.countDown())
                .subscribe(next -> {
                    log.info("next->{}", next);
                    mockRun(1000);
                });
        countDownLatch.await();
        log.info("end");
    }

    /**
     * 通过flatMap并发
     */
    @Test
    public void concurrencyTest() {
        Flux.range(1, 100)
                .doOnSubscribe(s -> s.request(8))
                .flatMap(next -> process(next), 4)
                .subscribe(x -> log.info("x->{}", x));
    }


    private Mono<Integer> process(int next) {
        mockRun(1000);
        return Mono.just(next * 10);
    }
}
