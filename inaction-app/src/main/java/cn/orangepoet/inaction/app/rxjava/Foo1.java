package cn.orangepoet.inaction.app.rxjava;

import io.reactivex.Single;

public class Foo1 {
    public static void main(String[] args) {
        Single.just(add(1, 2)).subscribe(System.out::println);
    }

    private static int add(int i, int j) {
        return i + j;
    }
}