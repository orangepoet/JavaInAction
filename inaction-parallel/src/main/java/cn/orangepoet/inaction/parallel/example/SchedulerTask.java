package cn.orangepoet.inaction.parallel.example;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SchedulerTask {
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

    public void execute() {
        LocalDateTime now = LocalDateTime.now();
        List<ScheduledTask> scheduledTasks = Arrays.asList(
                new ScheduledTask(now.plusSeconds(3), buildTask("hello, world - 1")),
                new ScheduledTask(now.plusSeconds(3), buildTask("hello, world - 1")),
                new ScheduledTask(now.plusSeconds(5), buildTask("hello, world - 2")),
                new ScheduledTask(now.plusSeconds(5), buildTask("hello, world - 2")),
                new ScheduledTask(now.plusSeconds(10), buildTask("hello, world - 3")),
                new ScheduledTask(now.plusSeconds(10), buildTask("hello, world - 3"))
        );


        scheduledTasks.forEach(task -> {
            long duration = Duration.between(now, task.getPlanTime()).getSeconds();
            long delaySecond = duration > 0 ? duration : 0;
            log.info("delaySecond: {}", delaySecond);
            scheduledExecutorService.schedule(task.getRunnable(), delaySecond, TimeUnit.SECONDS);
        });
        scheduledExecutorService.shutdown();
    }

    private Runnable buildTask(String s) {
        return () -> log.info(s);
    }

    @Getter
    private static class ScheduledTask implements Runnable {
        private LocalDateTime planTime;
        private final Runnable runnable;

        public ScheduledTask(LocalDateTime planTime, Runnable runnable) {
            if (runnable == null) {
                throw new NullPointerException("runnable is null");
            }
            this.runnable = runnable;
            this.planTime = planTime;
        }

        @Override
        public void run() {
            this.runnable.run();
        }
    }
}
