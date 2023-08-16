package cn.orangepoet.inaction.gof.state.concrete;

import cn.orangepoet.inaction.gof.state.AbstractLiftState;
import cn.orangepoet.inaction.gof.state.Context;
import cn.orangepoet.inaction.gof.state.LiftState;

/**
 * @author chengz
 * @since 2018/8/7
 */
public class RunningState extends AbstractLiftState {
    @Override
    public void onStop(Context context) {
        LiftState liftState = Context.STOPPING_STATE;
        context.setState(liftState);
        liftState.onStop(context);
    }
}
