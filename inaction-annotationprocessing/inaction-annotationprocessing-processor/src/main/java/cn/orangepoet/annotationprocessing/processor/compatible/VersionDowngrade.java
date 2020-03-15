package cn.orangepoet.annotationprocessing.processor.compatible;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据降级
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface VersionDowngrade {
    /**
     * 降级的版本, 低于此版本(不包含) 都需要降级处理
     *
     * @return
     */
    String value();
}
