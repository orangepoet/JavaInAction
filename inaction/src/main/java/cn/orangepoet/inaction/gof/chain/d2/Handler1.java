package cn.orangepoet.inaction.gof.chain.d2;

/**
 * @author chengzhi
 * @date 2019/03/08
 */
public class Handler1 extends Handler {

    @Override
    public void handle() {
        System.out.println("handler1");
        fireHandle();
    }
}
