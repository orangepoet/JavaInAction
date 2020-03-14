package cn.orangepoet.annotationprocessing.processor.compatible;

public interface VersionContext {

    String getCurrentVersion();

    void setCurrentVersion(String version);

    int compare(String version1, String version2);
}
