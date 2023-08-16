package cn.orangepoet.inaction.parallel.demo.buffer;

public class ConditionBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    protected ConditionBoundedBuffer(int capacity) {
        super(capacity);
    }

    @Override
    public synchronized void put(V obj) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        doPut(obj);
        notifyAll();
    }

    @Override
    public synchronized V take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        V v = doTake();
        notifyAll();
        return v;
    }
}
