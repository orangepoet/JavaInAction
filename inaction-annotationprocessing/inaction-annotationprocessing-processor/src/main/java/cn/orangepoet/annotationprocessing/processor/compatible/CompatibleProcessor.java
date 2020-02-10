package cn.orangepoet.annotationprocessing.processor.compatible;

/**
 * 数据兼容处理器
 */
public interface CompatibleProcessor<T> {
    void process(T data);
}
