package cn.orangepoet.inaction.gof.state.concrete;

import cn.orangepoet.inaction.gof.state.AbstractLiftState;
import cn.orangepoet.inaction.gof.state.Context;
import cn.orangepoet.inaction.gof.state.LiftState;

/**
 * @author chengz
 * @since 2018/8/7
 */
public class ClosingState extends AbstractLiftState {
    @Override
    public void onStop(Context context) {
        LiftState liftState = Context.STOPPING_STATE;
        context.setState(liftState);
        liftState.onStop(context);
    }

    @Override
    public void onRun(Context context) {
        LiftState liftState = Context.RUNNING_STATUE;
        context.setState(liftState);
        liftState.onRun(context);
    }

    @Override
    public void onOpen(Context context) {
        LiftState liftState = Context.OPENING_STATE;
        context.setState(liftState);
        liftState.onOpen(context);
    }
}
