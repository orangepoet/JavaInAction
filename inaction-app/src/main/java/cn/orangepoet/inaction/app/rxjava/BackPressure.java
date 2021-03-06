package cn.orangepoet.inaction.app.rxjava;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class BackPressure {
    public static void main(String[] args) {
//        observablePressure();
//        flowablePressure();
        flowablePressure2();


        try {
            while (true) {
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void observablePressure() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            for (int i = 0; i < 200; i++) {
                emitter.onNext(i);
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.single())
                .doOnNext(next -> System.out.println("doOnNext: " + next))
                .subscribe(next -> {
                    Thread.sleep(200);
                    System.out.println(String.format("consumer: " + next));
                });
    }

    private static void flowablePressure() {
        Flowable.create(emitter -> {
            for (int i = 0; i < 200; i++) {
                emitter.onNext(i);
                System.out.println("provider: " + i);
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.single())
                .doOnNext(next -> System.out.println("doOnNext: " + next))
                .subscribe(next -> System.out.println(String.format("consumer: " + next)));
    }

    private static void flowablePressure2() {
        Flowable.interval(10, TimeUnit.MILLISECONDS)
                .onBackpressureDrop(drop -> {
                    System.out.println("drop: " + drop);
                })
                .subscribeOn(Schedulers.newThread())
                .doOnNext(next -> System.out.println("doOnNext: " + next))
                .observeOn(Schedulers.single())
                .subscribe(next -> {
                    Thread.sleep(20);
                    System.out.println(String.format("consumer: " + next));
                });
    }
}
