package cn.orangepoet.inaction.parallel.example;

public interface Buffer<V> {
    void put(V obj) throws InterruptedException;
    V take() throws InterruptedException;
}
