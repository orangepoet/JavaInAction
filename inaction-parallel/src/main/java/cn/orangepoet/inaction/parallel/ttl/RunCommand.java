package cn.orangepoet.inaction.parallel.ttl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chengzhi
 * @date 2019/12/05
 */
@Slf4j
public class RunCommand implements Runnable {

    private ThreadLocal<Context> context = new ThreadLocal<>();

    public RunCommand(Context ctx) {
        context.set(ctx);
    }

    @Override
    public void run() {
        if (this.context.get() != null) {
            log.info("userId: " + this.context.get().getUserId());
        }
    }
}
