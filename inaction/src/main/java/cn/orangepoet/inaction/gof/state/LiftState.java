package cn.orangepoet.inaction.gof.state;

/**
 * Lift state.
 *
 * @author chengz
 * @since 2018/8/7
 */
public interface LiftState {
    void onStop(Context context);

    void onRun(Context context);

    void onOpen(Context context);

    void onClose(Context context);
}
