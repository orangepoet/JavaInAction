package cn.orangepoet.inaction.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chengzhi
 * @date 2018/09/28
 */
public class LogStarter {
    private static Logger logger = LoggerFactory.getLogger(LogStarter.class);

    public static void main(String[] args) {
        logger.error("my error, code: {}, message: {}, error: {}", 101, "my error", "e1",
            new RuntimeException("my exception"));

        logger.error("my error, code: {}, message: {}, error: {}", 101, "my error",
            "some error");
    }
}
