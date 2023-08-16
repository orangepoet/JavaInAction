package cn.orangepoet.inaction.gof.state;

import cn.orangepoet.inaction.gof.state.concrete.StoppingState;

/**
 * @author chengz
 * @since 2018/8/7
 */
public class App {
    public static void main(String[] args) {
        Context context = new Context();
        context.setState(new StoppingState());

        context.run();
        context.stop();
        context.open();
        context.close();
    }
}
