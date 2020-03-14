package cn.orangepoet.annotationprocessing.usage.compatible;

//@Component
public class FruitServiceImpl implements FruitService {
    @Override
    public Fruit showFruit() {
        String currentVersion = VersionContextImpl.getCurrentVersion();

        // version 2.0
        if (VersionContextImpl.compare(currentVersion, "2.0") >= 0) {
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
