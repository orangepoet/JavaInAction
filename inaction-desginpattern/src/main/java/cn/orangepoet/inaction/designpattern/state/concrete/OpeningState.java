package cn.orangepoet.inaction.designpattern.state.concrete;

import cn.orangepoet.inaction.designpattern.state.AbstractLiftState;
import cn.orangepoet.inaction.designpattern.state.Context;
import cn.orangepoet.inaction.designpattern.state.LiftState;

/**
 * @author chengz
 * @since 2018/8/7
 */
public class OpeningState extends AbstractLiftState {

    @Override
    public void onClose(Context context) {
        LiftState liftState = Context.CLOSING_STATE;
        context.setState(liftState);
        liftState.onClose(context);
    }
}
