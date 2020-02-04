package cn.orangepoet.annotationprocessing.processor.compatible;

/**
 * 版本上下文
 */
public class VersionContext {
    private static ThreadLocal<String> serviceVersion = new ThreadLocal<>();

    static String currentVersion() {
        return serviceVersion.get();
    }

    static void setCurrentVersion(String version) {
        serviceVersion.set(version);
    }

    /**
     * 版本比较
     *
     * @param version1
     * @param version2
     * @return 1: version1版本高于version2, 0: 版本一致, -1: version1版本低于version2
     */
    static int compare(String version1, String version2) {
        // todo: mock impl compare
        return version1.compareTo(version2);
    }
}