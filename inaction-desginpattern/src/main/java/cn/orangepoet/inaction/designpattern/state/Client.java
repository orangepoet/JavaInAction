package cn.orangepoet.inaction.designpattern.state;

import cn.orangepoet.inaction.designpattern.state.concrete.StoppingState;

/**
 * @author chengz
 * @since 2018/8/7
 */
public class Client {
    public static void main(String[] args) {
        Context context = new Context();
        context.setState(new StoppingState());

        context.run();
        context.stop();
        context.open();
        context.close();
    }
}
