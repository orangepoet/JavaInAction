package cn.orangepoet.inaction.function.memoize;

import io.vavr.Function1;
import io.vavr.Function2;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chengzhi
 * @date 2021/01/15
 */
@Slf4j
public class Application {

    public static void main(String[] args) {
        Function1<Integer, String> getRemote =
            ((Function1<Integer, String>)Application::getRemoteValue).memoized();

        Function2<Integer, Integer, String> getRemoteValue2 =
            ((Function2<Integer, Integer, String>)Application::getRemoteValue).memoized();

        String output = getRemote.apply(1);
        log.info("output:{}", output);

        String output2 = getRemote.apply(1);
        log.info("output2:{}", output2);

        Function1<Integer, String> getRemote1 = getRemoteValue2.curried().apply(1);
        String output3 = getRemote1.apply(10);
        log.info("output3:{}", output3);

        String output4 = getRemote1.apply(10);
        log.info("output4:{}", output4);
    }

    private static String getRemoteValue(int x, int y) {
        log.info("getRemoteValue2, x:{},y:{}", x, y);
        return String.valueOf(x * y);
    }

    private static String getRemoteValue(int value) {
        log.info("input: {}", value);
        RemoteClient remoteClient = new RemoteClientImpl();

        return remoteClient.callRemote(value);
    }
}
