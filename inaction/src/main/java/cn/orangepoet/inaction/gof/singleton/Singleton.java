package cn.orangepoet.inaction.gof.singleton;

public class Singleton {
    /**
     * 防止指令重排序
     */
    private static volatile Singleton instance;
    private static final Object LOCK = new Object();

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) { // 判断条件1
            synchronized (LOCK) {
                if (instance == null) {
                    // 此处一个操作有三个指令，1. 创建内存空间, 2. 初始化 3. 将内存空间地址赋值给instance.
                    // 重排序后 -> 1. 创建内存空间, 3.将内存空间地址赋值给instance, 2.初始化
                    // 重排序后 此时  判断条件1 处 发现instance不为null 直接返回了一个未初始化的对象；
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
