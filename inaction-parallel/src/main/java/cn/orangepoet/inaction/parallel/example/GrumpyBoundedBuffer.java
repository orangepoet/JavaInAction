package cn.orangepoet.inaction.parallel.example;

public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    public GrumpyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) {
        if (isFull()) {
            throw new BufferFullException();
        }

        doPut(v);
    }

    public synchronized V take() {
        if (isEmpty()) {
            throw new BufferEmptyException();
        }
        return doTake();
    }
}
