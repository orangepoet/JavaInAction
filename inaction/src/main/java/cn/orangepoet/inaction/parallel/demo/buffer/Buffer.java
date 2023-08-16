package cn.orangepoet.inaction.parallel.demo.buffer;

/**
 * Buffer接口演示了 限界队列带有put & take 操作;
 *
 * @param <V>
 */
public interface Buffer<V> {
    void put(V obj) throws InterruptedException;

    V take() throws InterruptedException;
}
