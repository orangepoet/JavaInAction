package cn.orangepoet.inaction.parallel.demo.buffer;

/**
 * 基于休眠的阻塞操作实现
 *
 * @param <V>
 */
public class SleepBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    private static final long SLEEP_GRANULARITY = 500;

    protected SleepBoundedBuffer(int capacity) {
        super(capacity);
    }

    public void put(V v) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    return;
                }
            }
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }

    public V take() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isEmpty()) {
                    return doTake();
                }
            }
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }
}
