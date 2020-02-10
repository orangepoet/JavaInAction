package cn.orangepoet.annotationprocessing.usage.compatible;

import cn.orangepoet.annotationprocessing.processor.compatible.CompatibleProcessor;
import cn.orangepoet.annotationprocessing.processor.compatible.VersionContext;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 水果列表兼容处理器; (数据变窄)
 * <p>
 * 3.0 版本新增pear;
 * </p>
 *
 * <p>
 * 2.0 版本新增banana;
 * </p>
 */
@Component
public class FruitListCompatibleProcessor implements CompatibleProcessor<List<Fruit>> {

    @Override
    public void process(List<Fruit> data) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }

        String currentVersion = VersionContext.currentVersion();

        if (VersionContext.compare(currentVersion, "3.0") < 0) {
            data.removeIf(fruit -> fruit.getName().equals("pear"));
        }

        if (VersionContext.compare(currentVersion, "2.0") < 0) {
            data.removeIf(fruit -> fruit.getName().equals("banana"));
        }
    }
}
