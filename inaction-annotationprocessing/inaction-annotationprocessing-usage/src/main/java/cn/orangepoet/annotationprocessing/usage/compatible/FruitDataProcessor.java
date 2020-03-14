package cn.orangepoet.annotationprocessing.usage.compatible;

import cn.orangepoet.annotationprocessing.processor.compatible.CompatibleProcessor;
import cn.orangepoet.annotationprocessing.processor.compatible.VersionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FruitDataProcessor implements CompatibleProcessor<Fruit> {
    @Autowired
    private VersionContext versionContext;

    @Override
    public void process(Fruit data) {
        String currentVersion = versionContext.getCurrentVersion();
        if (versionContext.compare(currentVersion, "2.0") < 0) {
            data.setWeight(10);
            data.setPrice(1.1);
            data.setColor("green");
            data.setName("apple");
        }
    }
}
