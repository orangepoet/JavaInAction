package cn.orangepoet.inaction.designpattern.state;

/**
 * Lift state.
 *
 * @author chengz
 * @since 2018/8/7
 */
public interface LiftState {
    void stop(Context context);

    void run(Context context);

    void open(Context context);

    void close(Context context);
}
