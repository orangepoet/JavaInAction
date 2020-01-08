package cn.orangepoet.inaction.parallel.ttl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chengzhi
 * @date 2019/12/05
 */
public class Application {
    private static ExecutorService executorService = Executors.newFixedThreadPool(8);

    public static void main(String[] args) {
        Process process = new Process();
        process.setContext(new Context("OrangeCheng"));

        RunCommand command = new RunCommand(process.getContext());
        executorService.execute(command);

        executorService.shutdown();
    }
}
