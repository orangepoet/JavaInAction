package cn.orangepoet.inaction.parallel.ratelimit;

import java.util.concurrent.Executor;

/**
 * 流控调度执行器
 */
public interface ScheduleExecutor extends Executor {
    void schedule();

    void close();
}
