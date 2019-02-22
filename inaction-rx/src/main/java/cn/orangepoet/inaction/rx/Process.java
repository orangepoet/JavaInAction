package cn.orangepoet.inaction.rx;

public class Process {
    public static void main(String[] args) {
        Joo joo = s -> "hello, " + s;
        System.out.println("new line");
//        joo("Orange");
        try {
            joo.handle("Orange");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
