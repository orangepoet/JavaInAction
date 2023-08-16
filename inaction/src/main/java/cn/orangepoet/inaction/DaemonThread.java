package cn.orangepoet.inaction;

/**
 * A daemon thread is a thread that does not prevent the JVM from exiting when the program finishes but the thread is
 * still running. An example for a daemon thread is the garbage collection. You can use the setDaemon(boolean) method to
 * change the Thread daemon properties before the thread starts.
 *
 * @author chengzhi
 * @date 2019/09/20
 */
public class DaemonThread {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("hello, world");
            }
        });
        thread.setDaemon(true);
        thread.start();

        System.out.println("main exists");
    }
}
