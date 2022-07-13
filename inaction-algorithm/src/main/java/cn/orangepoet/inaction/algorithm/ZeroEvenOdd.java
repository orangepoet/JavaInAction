package cn.orangepoet.inaction.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * @author chengzhi
 * @date 2022/07/13
 */
@Slf4j
public class ZeroEvenOdd {
    /**
     * 现有函数 printNumber 可以用一个整数参数调用，并输出该整数到控制台。
     * <p>
     * 例如，调用 printNumber(7) 将会输出 7 到控制台。 给你类 ZeroEvenOdd 的一个实例，该类中有三个函数：zero、even 和 odd 。ZeroEvenOdd 的相同实例将会传递给三个不同线程：
     * <p>
     * 线程 A：调用 zero() ，只输出 0 线程 B：调用 even() ，只输出偶数 线程 C：调用 odd() ，只输出奇数 修改给出的类，以输出序列 "010203040506..." ，其中序列的长度必须为 2n 。
     * <p>
     * 实现 ZeroEvenOdd 类：
     * <p>
     * ZeroEvenOdd(int n) 用数字 n 初始化对象，表示需要输出的数。 void zero(printNumber) 调用 printNumber 以输出一个 0 。 void even(printNumber) 调用printNumber 以输出偶数。
     * void odd(printNumber) 调用 printNumber 以输出奇数。
     * <p>
     * <p>
     * 输入：n = 2 输出："0102" 解释：三条线程异步执行，其中一个调用 zero()，另一个线程调用 even()，最后一个线程调用odd()。正确的输出为 "0102"。
     * <p>
     * 输入：n = 5 输出："0102030405"
     */

    private final ReentrantLock lock1 = new ReentrantLock();
    private final Condition zeroCondition = lock1.newCondition();
    private final Condition oddCondition = lock1.newCondition();
    private final Condition evenCondition = lock1.newCondition();

    private final int n;

    private volatile int i = 0;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        while (true) {
            try {
                lock1.lock();
                if (i > n) {
                    break;
                }
                printNumber.accept(0);
                if (i == 0) {
                    i = 1;
                }
                if (i % 2 == 1) {
                    oddCondition.signal();
                } else {
                    evenCondition.signal();
                }
                zeroCondition.await();
            } finally {
                lock1.unlock();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        while (true) {
            try {
                lock1.lock();
                if (i % 2 == 0) {
                    printNumber.accept(i);
                    i++;
                    zeroCondition.signal();
                }
                if (i > n) {
                    break;
                }
                evenCondition.await();
            } finally {
                lock1.unlock();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        while (true) {
            try {
                lock1.lock();
                if (i % 2 == 1) {
                    printNumber.accept(i);
                    i++;
                    zeroCondition.signal();
                }
                if (i >= n) {
                    break;
                }
                oddCondition.await();
            } finally {
                lock1.unlock();
            }
        }
    }

    public static void main(String[] args) {
        IntConsumer consumer = (int i) -> log.info("printNumber: {}", i);
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(8);

        new Thread(() -> {
            try {
                zeroEvenOdd.even(consumer);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                zeroEvenOdd.odd(consumer);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                zeroEvenOdd.zero(consumer);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
