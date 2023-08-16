package cn.orangepoet.inaction.parallel.executorservice.threadpool;

/**
 * @author chengzhi
 * @date 2022/03/03
 */
public class MyContext {
    private static final ThreadLocal<Object> ctx = new ThreadLocal<>();

    public static Object currentRpcContext() {
        return ctx.get();
    }

    public static void setRpcContext(Object ctx0) {
        ctx.set(ctx0);
    }

    public static void clearRpcContext() {
        ctx.set(null);
    }
}
