package cn.orangepoet.inaction.ex.reactor.rxjava;

import java.util.ArrayList;
import java.util.List;

public class Foo1 {
    public static void main(String[] args) {
        //Single.just(add(1, 2)).subscribe(System.out::println);
        List<Integer> list = new ArrayList<>(16);
        //list.add(12, 1);
        list.add(1);
        list.add(2);
        list.add(3);

        list.add(2, 4);
    }

    private static int add(int i, int j) {
        return i + j;
    }
}