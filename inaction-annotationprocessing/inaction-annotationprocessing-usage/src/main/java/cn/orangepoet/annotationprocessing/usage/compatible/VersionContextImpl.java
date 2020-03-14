package cn.orangepoet.annotationprocessing.usage.compatible;

import cn.orangepoet.annotationprocessing.processor.compatible.VersionContext;
import org.springframework.stereotype.Component;

/**
 * 版本上下文
 */
@Component
public class VersionContextImpl implements VersionContext {
    private static ThreadLocal<String> serviceVersion = new ThreadLocal<>();

    @Override
    public String getCurrentVersion() {
        return VersionContextImpl.serviceVersion.get();
    }

    @Override
    public void setCurrentVersion(String version) {
        VersionContextImpl.serviceVersion.set(version);
    }

    /**
     * 版本比较
     *
     * @param version1
     * @param version2
     * @return 1: version1版本高于version2, 0: 版本一致, -1: version1版本低于version2
     */
    @Override
    public int compare(String version1, String version2) {
        // todo: mock impl compare
        return version1.compareTo(version2);
    }

}