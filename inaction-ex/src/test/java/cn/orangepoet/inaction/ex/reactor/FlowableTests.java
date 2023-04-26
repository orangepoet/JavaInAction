package cn.orangepoet.inaction.ex.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

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
        Flux.range(1, 100)
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
}
