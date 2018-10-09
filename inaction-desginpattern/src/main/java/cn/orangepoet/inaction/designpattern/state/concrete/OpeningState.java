package cn.orangepoet.inaction.designpattern.state.concrete;

import cn.orangepoet.inaction.designpattern.state.Context;
import cn.orangepoet.inaction.designpattern.state.LiftState;

/**
 * @author chengz
 * @since 2018/8/7
 */
public class OpeningState implements LiftState {
    @Override
    public void stop(Context context) {
    }

    @Override
    public void run(Context context) {
    }

    @Override
    public void open(Context context) {
        System.out.println("opening");
    }

    @Override
    public void close(Context context) {
        LiftState liftState = Context.CLOSING_STATE;
        context.setState(liftState);
        liftState.close(context);
    }
}
