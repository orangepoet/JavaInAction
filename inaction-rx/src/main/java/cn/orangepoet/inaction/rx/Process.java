package cn.orangepoet.inaction.rx;

import cn.orangepoet.inaction.tools.Common;

public class Process {
    public static void main(String[] args) {
        Joo joo = s -> "hello, " + s;
//        System.out.println("new line");
//        System.out.println("new line2");
//        System.out.println("new line3............");

        Common.sayHi();
//        joo("Orange");
        try {
            joo.handle("Orange");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
