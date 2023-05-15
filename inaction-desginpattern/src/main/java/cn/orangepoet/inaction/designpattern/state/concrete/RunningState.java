package cn.orangepoet.inaction.designpattern.state.concrete;

import cn.orangepoet.inaction.designpattern.state.AbstractLiftState;
import cn.orangepoet.inaction.designpattern.state.Context;
import cn.orangepoet.inaction.designpattern.state.LiftState;

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
