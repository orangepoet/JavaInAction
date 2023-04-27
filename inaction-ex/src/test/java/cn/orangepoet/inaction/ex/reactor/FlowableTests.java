package cn.orangepoet.inaction.ex.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class FlowableTests {
    private static void mockRun(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void requestOnDemand() {
        Flux.<Integer>create(emitter -> {
                    for (int i = 0; i < 100; i++) {
                        log.info("emitter:{}", i);
                        emitter.next(i);
                    }
                    emitter.complete();
                })
                .subscribe(new BaseSubscriber<Integer>() {
                    private final int bufferSize = 2;
                    private int count;

                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(bufferSize);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        log.info("value:{}", value);

                        mockRun(1000);
                        count++;
                        // reached the maximum buffer size, request more items
//                        if (count == bufferSize) {
//                            count = 0;
//                            request(bufferSize);
//                        }
                    }
                });
        log.info("end");
    }

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