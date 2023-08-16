package cn.orangepoet.inaction.reactor;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ReactorTests {
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

    private static void mockRun(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * parallel Flux
     */
    @Test
    public void FlapMap_Parallel() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Flux.range(1, 100)
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .map(next -> {
                    log.info("flatMap({})", next);
                    mockRun(1000);
                    return next * 10;
                })
                .log()
                .doOnComplete(latch::countDown)
                .subscribe();
        latch.await();
    }

    /**
     * Mono的同步方法与异步方法
     */
    @Test
    public void test1() {
        CountDownLatch cdl = new CountDownLatch(2);
        Mono.fromSupplier(() -> {
                    mockRun(2000);
                    return 1;
                })
                .doOnTerminate(cdl::countDown)
                .log()
                .subscribe();


        Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
                    mockRun(2000);
                    return 1;
                }))
                .doOnTerminate(cdl::countDown)
                .log()
                .subscribe();

        try {
            cdl.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * subscribe 拉取模式
     */
    @Test
    public void SubscribePullRequest() {
        Flux.<Integer>create(emitter -> {
                    for (int i = 0; i < 100; i++) {
                        log.info("emitter->{}", i);
                        emitter.next(i);
                    }
                    emitter.complete();
                })
                .log()
                .subscribe(new BaseSubscriber<Integer>() {
                    private final int bufferSize = 8;
                    private int count;

                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(bufferSize);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        mockRun(1000);
                        count++;
//                         reached the maximum buffer size, request more items
                        if (count == bufferSize) {
                            count = 0;
                            request(bufferSize);
                        }
                    }
                });
        log.info("end");
    }

    @Test
    public void testSyncUsers() {
        SyncUser.sync();
    }

    @Test
    public void testFlowSyncUsers() throws InterruptedException {
        SyncUser.flowSync();
    }

    @Test
    public void testParallel() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Flux.range(1, 10)
                .subscribeOn(Schedulers.parallel())
                .map(i -> {
                    mockRun(1000);
                    return i * 10;
                })
                .doOnComplete(() -> {
                    log.info("complete");
                    latch.countDown();
                })
                .subscribe(next -> log.info("next->{}", next));
        latch.await();
    }

    @Test
    public void testBlock() {
        Flux.range(1, 10)
                .doOnNext(item -> System.out.println("Processing item " + item))
                .blockFirst();


        Flux.range(1, 10)
                .doOnNext(item -> System.out.println("Processing item " + item))
                .blockLast();

    }
}
