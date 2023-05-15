package cn.orangepoet.inaction.designpattern.state;

import cn.orangepoet.inaction.designpattern.state.concrete.ClosingState;
import cn.orangepoet.inaction.designpattern.state.concrete.OpeningState;
import cn.orangepoet.inaction.designpattern.state.concrete.RunningState;
import cn.orangepoet.inaction.designpattern.state.concrete.StoppingState;

/**
 * @author chengz
 * @since 2018/8/7
 */
public class Context {
    public static final LiftState CLOSING_STATE = new ClosingState();
    public static final LiftState OPENING_STATE = new OpeningState();
    public static final LiftState RUNNING_STATUE = new RunningState();
    public static final LiftState STOPPING_STATE = new StoppingState();

    private LiftState state;

    public void setState(LiftState state) {
        this.state = state;
    }

    public LiftState getState() {
        return this.state;
    }

    public void open() {
        this.state.onOpen(this);
    }

    public void close() {
        this.state.onClose(this);
    }

    public void run() {
        this.state.onRun(this);
    }

    public void stop() {
        this.state.onStop(this);
    }
}
