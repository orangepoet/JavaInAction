package cn.orangepoet.inaction.function.memoize;

/**
 * @author chengzhi
 * @date 2021/01/15
 */
public interface RemoteClient {
    /**
     * 远端调用
     *
     * @param i
     * @return
     */
    String callRemote(Integer i);
}
