package cn.orangepoet.annotationprocessing.usage.compatible;

import cn.orangepoet.annotationprocessing.processor.compatible.VersionContext;

import java.util.List;

//@Component
public class FruitServiceImpl implements FruitService {
    @Override
    public Fruit showFruit() {
        String currentVersion = VersionContext.currentVersion();

        // version 2.0
        if (VersionContext.compare(currentVersion, "2.0") >= 0) {
            Fruit apple = new Fruit();
            apple.setWeight(10);
            apple.setPrice(2.0);
            apple.setColor("red");
            apple.setName("apple");
            return apple;
        }

        // version 1.0
        Fruit apple = new Fruit();
        apple.setWeight(10);
        apple.setPrice(1.1);
        apple.setColor("green");
        apple.setName("apple");
        return apple;
    }
}
