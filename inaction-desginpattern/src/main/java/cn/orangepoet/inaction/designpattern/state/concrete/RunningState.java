package cn.orangepoet.inaction.designpattern.state.concrete;

import cn.orangepoet.inaction.designpattern.state.Context;
import cn.orangepoet.inaction.designpattern.state.LiftState;

/**
 * @author chengz
 * @since 2018/8/7
 */
public class RunningState implements LiftState {
    @Override
    public void stop(Context context) {
        LiftState liftState = Context.STOPPING_STATE;
        context.setState(liftState);
        liftState.stop(context);
    }

    @Override
    public void run(Context context) {
        System.out.println("running");
    }

    @Override
    public void open(Context context) {
    }

    @Override
    public void close(Context context) {
    }
}
