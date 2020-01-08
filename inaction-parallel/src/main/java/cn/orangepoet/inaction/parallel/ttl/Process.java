package cn.orangepoet.inaction.parallel.ttl;

/**
 * @author chengzhi
 * @date 2019/12/05
 */
public class Process {
    private static ThreadLocal<Context> threadLocal = new ThreadLocal<>();

    public Context getContext() {
        return threadLocal.get();
    }

    public void setContext(Context ctx) {
        threadLocal.set(ctx);
    }
}
