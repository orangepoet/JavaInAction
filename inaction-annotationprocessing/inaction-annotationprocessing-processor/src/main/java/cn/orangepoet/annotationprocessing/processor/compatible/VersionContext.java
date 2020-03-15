package cn.orangepoet.annotationprocessing.processor.compatible;

/**
 * 版本上下文
 */
public interface VersionContext {

    /**
     * 获取请求版本信息
     *
     * @return
     */
    String getRequestVersion();

    /**
     * 设置请求的版本信息
     *
     * @param version 版本信息
     */
    void setRequestVersion(String version);

    /**
     * 版本比较
     *
     * @param version1
     * @param version2
     * @return 1: version1版本高于version2, 0: 版本一致, -1: version1版本低于version2
     */
    int compare(String version1, String version2);
}
