package cn.orangepoet.inaction.parallel.threadpool;

public interface CustomThreadPool {
    /**
     * 增加工作者
     *
     * @param num
     */
    void addWorker(int num);

    /**
     * 减少工作者
     *
     * @param num
     */
    void removeWorker(int num);

    /**
     * 执行任务
     *
     * @param job
     */
    void execute(Job job);

    /**
     * 关闭线程池
     */
    void shutDown();
}

interface Job extends Runnable {

}
