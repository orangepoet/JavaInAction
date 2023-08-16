package cn.orangepoet.inaction.gof.chain.d2;

/**
 * @author chengzhi
 * @date 2019/03/08
 */
public class Process {
    public static void main(String[] args) {
        Chain chain = new Chain();

        Handler handler1 = new Handler1();
        Handler handler2 = new Handler2();

        chain.addEnd(handler1);
        chain.addEnd(handler2);

        chain.handle();
    }
}
