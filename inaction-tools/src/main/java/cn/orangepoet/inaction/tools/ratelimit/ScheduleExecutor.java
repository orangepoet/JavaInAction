package cn.orangepoet.inaction.tools.ratelimit;

import java.util.concurrent.Executor;

public interface ScheduleExecutor extends Executor {
    void schedule();
    void close();
}
