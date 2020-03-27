package cn.orangepoet.inaction.spring.samples;

/**
 * @author chengzhi
 * @date 2020/03/25
 */
public interface FetchData<T extends DBData> {
    T fetch();
}
