package cn.orangepoet.inaction.ex.reactor.rxjava;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class CodeHotEg {
    public static void main(String[] args) {
        //Observable<String> observable = getObservableCold();
        Observable<String> observable = getObservableHot();

        Consumer<String> consumer1 = s -> {
            System.out.println("consumer1: " + s);
        };
        Consumer<String> consumer2 = s -> {
            System.out.println("consumer2: " + s);
        };

        // hot observable operations:
        ((ConnectableObservable)observable).connect();
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

    private static void getObservableHot(Observable<String> observable) {
        ConnectableObservable<String> hotObservable = observable.publish();
    }

    private static Observable<String> getObservableCold() {
        Observable<String> observable = Observable.just("a", "b", "c");
        //        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("a");
//                emitter.onNext("b");
//                emitter.onNext("c");
//            }
//        });
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
}
