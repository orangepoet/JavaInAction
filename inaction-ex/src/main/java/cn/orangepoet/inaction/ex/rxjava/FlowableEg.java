package cn.orangepoet.inaction.ex.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FlowableEg {

    public static void main(String[] args) throws InterruptedException {
        test1();
//        test2();
//        test3();
//        test4();
//        test5();
    }

    /**
     * 1. 上游模拟循环生产100个数据, 下游模拟每次处理消耗50ms;
     * 2. 背压策略: 使用 BackpressureStrategy.ERROR
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
//                .subscribeOn(Schedulers.newThread());
                .observeOn(Schedulers.io())
                .doOnComplete(() -> countDownLatch.countDown())
                .subscribe(next -> {
                    mockRun(50);
                    log.info("downstream: {}", next);
                });
        countDownLatch.await();
    }

    /**
     * 订阅者的匿名对象及方法实现
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
                System.out.println("subscribe");
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
//                disposable.dispose();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("complete");
            }
        });
    }

    /**
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

    private static void test4() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ConnectableObservable<Long> connectableObservable = Observable.interval(1, TimeUnit.SECONDS)
                .doOnNext(next -> log.info("produce one: {}", next))
                .take(6)
                .replay();

        connectableObservable
                .doOnComplete(() -> countDownLatch.countDown())
                .subscribe(next -> log.info("first consumer: " + next));

        connectableObservable.delaySubscription(3, TimeUnit.SECONDS)
                .doOnComplete(() -> countDownLatch.countDown())
                .subscribe(next -> log.info("second consumer: {}", next));

        connectableObservable.connect();

        countDownLatch.await();
    }

    private static void test5() {
//        Observable<String> observable = getObservableCold();
        Observable<String> observable = getObservableHot();

        Consumer<String> consumer1 = s -> {
            System.out.println("consumer1: " + s);
        };
        Consumer<String> consumer2 = s -> {
            System.out.println("consumer2: " + s);
        };

        // hot observable operations:
        ((ConnectableObservable) observable).connect();
        observable.subscribe(consumer1);
        observable.subscribe(consumer2);

        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Consumer<String> consumer3 = s -> {
            System.out.println("consumer3: " + s);
        };
        observable.subscribe(consumer3);

        try {
            Thread.sleep(50000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Observable<String> getObservableCold() {
//        Observable<String> observable = Observable.just("a", "b", "c");
        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("a");
            emitter.onNext("b");
            emitter.onNext("c");
            emitter.onComplete();
        });
        return observable;
    }

    private static Observable<String> getObservableHot() {
        Observable<String> observable = Observable.interval(100, TimeUnit.MILLISECONDS, Schedulers.computation())
                .take(20)
                .map(l -> String.valueOf(l))
                .subscribeOn(Schedulers.newThread())
                .publish();
        return observable;
    }

    private static void mockRun(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }
}