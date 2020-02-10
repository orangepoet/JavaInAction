package cn.orangepoet.annotationprocessing.processor.compatible;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CompatibleProcess {
    /**
     * 数据兼容处理器
     *
     * @return
     */
    String value();
}
