package cn.orangepoet.inaction.ex.reactor.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.CountDownLatch;

public class FlowableEg {

    private static final int SIZE = 100;
    private static final CountDownLatch countDownLatch = new CountDownLatch(SIZE);

    public static void main(String[] args) {
        Flowable<Integer> flowable = Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                        for (int i = 0; i < SIZE; i++) {
                            System.out.println("upstream: " + i);
                            emitter.onNext(i);
                        }

                    }
                }, BackpressureStrategy.ERROR)
//                .subscribeOn(Schedulers.newThread());
                .observeOn(Schedulers.io());

        Consumer<? super Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Thread.sleep(50);
                System.out.printf("downstream: %d, threadname: %s%n", integer,Thread.currentThread().getName());
                countDownLatch.countDown();
            }
        };
        flowable.subscribe(consumer);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}