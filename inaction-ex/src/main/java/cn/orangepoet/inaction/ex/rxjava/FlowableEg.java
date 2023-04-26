package cn.orangepoet.inaction.ex.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FlowableEg {

    public static void main(String[] args) throws InterruptedException {
//        test1();
//        test2();
//        test3();
//        test4();
        test5();
    }

    /**
     * 1. 上游模拟循环生产100个数据, 下游模拟每次处理消耗50ms;
     * 2. 背压策略: 使用 BackpressureStrategy.ERROR
     * 3. 发布和订阅者使用专属的线程池
     *
     * @throws InterruptedException
     */
    private static void test1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Flowable.create((FlowableOnSubscribe<Integer>) emitter -> {
                    for (int i = 0; i < 100; i++) {
                        log.info("upstream: {}", i);
                        emitter.onNext(i);
                    }
                    emitter.onComplete();
                }, BackpressureStrategy.ERROR)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .doOnComplete(() -> countDownLatch.countDown())
                .subscribe(next -> {
                    mockRun(50);
                    log.info("downstream: {}", next);
                });
        countDownLatch.await();
    }

    /**
     * 主要: 订阅者的匿名对象及方法实现
     */
    private static void test2() {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onNext("cheng");
            emitter.onNext("zhi");
            emitter.onComplete();
        }).subscribe(new Observer<String>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable disposable) {
                this.disposable = disposable;
                log.info("subscribe");
            }

            @Override
            public void onNext(String s) {
                log.info(s);
//                disposable.dispose();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                log.info("complete");
            }
        });
    }

    /**
     * 主要: 发布者生产数据的中间转换方法 flatMap
     *
     * @throws InterruptedException
     */
    private static void test3() throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(1);
        Flowable.just(1, 2, 3)
                .subscribeOn(Schedulers.computation())
                .doOnNext(next -> log.info("doOnNext: {}", next))
                .flatMap((Integer i) -> {
                    mockRun(1000);
                    log.info("flatMap1 finish");
                    return Flowable.just(i);
                })
                .flatMap((Integer i) -> {
                    mockRun(1000);
                    log.info("flatMap2 finish");
                    return Flowable.just(i);
                })
                .doOnComplete(() -> cdl.countDown())
                .subscribe(next -> log.info("consumer: {}", next));

        cdl.await();
    }

    /**
     * 1. 发布者的最大数量控制
     * 2. 发布者的replay方法, 将upstream的数据(包括一开始)打包给下游
     * 2. 延迟订阅的示例
     *
     * @throws InterruptedException
     */
    private static void test4() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ConnectableObservable<Long> connectableObservable = Observable.interval(1, TimeUnit.SECONDS)
                .doOnNext(next -> log.info("producer -> next: {}", next))
                .take(10)
                .replay();

        connectableObservable
                .doOnComplete(() -> countDownLatch.countDown())
                .subscribe(next -> log.info("consumer-1: " + next));

        connectableObservable.delaySubscription(4, TimeUnit.SECONDS)
                .doOnComplete(() -> countDownLatch.countDown())
                .subscribe(next -> log.info("consumer-2: {}", next));

        connectableObservable.connect();

        countDownLatch.await();
    }

    /**
     * 1. 热流和冷流的示例, 热流upstream不会给新订阅downstream老的数据, 冷流则不然
     */
    private static void test5() throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(3);
        CountDownLatch cdl2 = new CountDownLatch(1);
//        Observable<String> observable = getObservableCold();
        Observable<Long> observable = getObservableHot();

        // hot observable operations:
        ((ConnectableObservable) observable).connect();
        observable
                .doOnComplete(() -> cdl.countDown())
                .subscribe(s1 -> log.info("consumer-1: {}", s1));
        observable
                .doOnNext(next -> {
                    if (next > 3) cdl2.countDown();
                })
                .doOnComplete(() -> cdl.countDown())
                .subscribe(s1 -> log.info("consumer-2: {}", s1));

        cdl2.await();

        observable
                .doOnComplete(() -> cdl.countDown())
                .subscribe(s -> log.info("consumer-3: " + s));

        cdl.await();
    }

    private static Observable<Long> getObservableCold() {
//        Observable<Long> observable = Observable.just(1L, 2L, 3L);
        Observable<Long> observable = Observable.create(emitter -> {
            emitter.onNext(1L);
            emitter.onNext(2L);
            emitter.onNext(3L);
            emitter.onComplete();
        });
        return observable;
    }

    private static Observable<Long> getObservableHot() {
        return Observable.interval(100, TimeUnit.MILLISECONDS, Schedulers.computation())
                .take(20)
                .subscribeOn(Schedulers.newThread())
                .publish();
    }

    private static void mockRun(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            log.error("mockRun|error", e);
        }
    }
}