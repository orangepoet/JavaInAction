package cn.orangepoet.inaction.rx;


import io.reactivex.Observable;

public class OnEventEx {
    public static void main(String[] args) {
        Observable.just("orange")
                .doOnNext(s -> System.out.println("doOnNext"))
                .subscribe(s -> System.out.println("onNext"));
    }
}
