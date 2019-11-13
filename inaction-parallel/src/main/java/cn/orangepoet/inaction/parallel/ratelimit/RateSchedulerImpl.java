package cn.orangepoet.inaction.parallel.ratelimit;

import com.google.common.base.Preconditions;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 流量控制调度器
 * <p>
 * 使用本地阻塞队列, 模拟生产-消费者模型, 当队列为空时获取元素时会等待 (有超时时间)
 * </p>
 * <p>
 * 使用mock的redisClient解决分布式流控问题;
 * </p>
 */
public class RateSchedulerImpl implements ScheduleExecutor {
    private final BlockingQueue<Runnable> commands = new LinkedBlockingQueue<>();
    private final String topic;
    private final int rate;
    private final Executor executor;
    private final ScheduledExecutorService scheduledExecutor;
    private volatile ScheduledFuture<?> scheduledFuture;

    public RateSchedulerImpl(String topic, int rate) {
        this(topic, rate,
            new ThreadPoolExecutor(10, 100, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000)),
            Executors.newScheduledThreadPool(4));
    }

    public RateSchedulerImpl(String topic, int rate, Executor executor, ScheduledExecutorService scheduledExecutor) {
        Preconditions.checkNotNull(topic);
        Preconditions.checkArgument(rate > 0);
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(scheduledExecutor);
        this.topic = topic;
        this.rate = rate;
        this.executor = executor;
        this.scheduledExecutor = scheduledExecutor;
    }

    private RedisClient redisClient = new RedisClient();

    @Override
    public void schedule() {
        scheduledFuture = scheduledExecutor.scheduleAtFixedRate(() -> {
            System.out.println("------schedule start------------");
            while (true) {
                try {
                    int times = redisClient.incr(topic, 1);
                    // 超过速率, 终止调度
                    if (times > rate) {
                        break;
                    }
                    Runnable command = commands.poll(1L, TimeUnit.SECONDS);
                    // 任务队列空, 终止调度
                    if (command == null) {
                        break;
                    }
                    executor.execute(command);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 中端异常, 终止调度
                    break;
                }
            }
            System.out.println("------schedule end------------");
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void close() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }

    @Override
    public void execute(Runnable command) {
        commands.add(command);
    }
}
