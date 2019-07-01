package cn.orangepoet.inaction.designpattern.chain.d2;

/**
 * @author chengzhi
 * @date 2019/03/08
 */
public class Handler2 extends Handler {

    @Override
    public void handle() {
        System.out.println("handler2");
        fireHandle();
    }
}
