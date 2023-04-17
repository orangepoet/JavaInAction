package cn.orangepoet.inaction.ex.reactor.rxjava;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.Random;

public class FlatMapEg {
    private static final Random rnd = new Random();

    public static void main(String[] args) {
        Flowable.just(1, 2, 3)
                .subscribeOn(Schedulers.computation())
                .doOnNext(next -> {
                    System.out.println("doOnNext: " + next);
                })
                .flatMap((Integer i) -> {
                    System.out.println("flatMap1 execute");
                    Thread.sleep(1000);
                    return Flowable.just(i);
                })
                .flatMap((Integer i) -> {
                    System.out.println("flatMap2 execute");
                    Thread.sleep(1000);
                    return Flowable.just(i);
                })
                .subscribe(next -> {
                    System.out.println("consumer: " + next);
                });

        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
