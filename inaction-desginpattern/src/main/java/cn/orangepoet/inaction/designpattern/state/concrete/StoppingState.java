package cn.orangepoet.inaction.designpattern.state.concrete;

import cn.orangepoet.inaction.designpattern.state.Context;
import cn.orangepoet.inaction.designpattern.state.LiftState;

/**
 * @author chengz
 * @since 2018/8/7
 */
public class StoppingState implements LiftState {

    @Override
    public void stop(Context context) {
        System.out.println("Stopping");
    }

    @Override
    public void run(Context context) {
        LiftState liftState = Context.RUNNING_STATUE;
        context.setState(liftState);
        liftState.run(context);
    }

    @Override
    public void open(Context context) {
        LiftState liftState = Context.OPENING_STATE;
        context.setState(liftState);
        liftState.open(context);
    }

    @Override
    public void close(Context context) {
    }
}
