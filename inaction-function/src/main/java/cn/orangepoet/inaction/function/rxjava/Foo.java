package cn.orangepoet.inaction.function.rxjava;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Start RXjava first.
 */
public class Foo {
    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext("cheng");
                observableEmitter.onNext("zhi");
                observableEmitter.onComplete();
            }
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


        System.out.println("main exit()");
    }
}
