package cn.orangepoet.inaction.function.memoize;

/**
 * @author chengzhi
 * @date 2021/01/15
 */
public class RemoteClientImpl implements RemoteClient {
    @Override
    public String callRemote(Integer i) {
        return String.valueOf(i * 2);
    }
}
