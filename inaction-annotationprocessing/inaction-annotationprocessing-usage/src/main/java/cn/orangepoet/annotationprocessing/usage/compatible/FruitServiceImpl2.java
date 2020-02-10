package cn.orangepoet.annotationprocessing.usage.compatible;

import cn.orangepoet.annotationprocessing.processor.compatible.CompatibleProcess;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Primary
public class FruitServiceImpl2 implements FruitService {
    @CompatibleProcess("fruitDataProcessor")
    @Override
    public Fruit showFruit() {
        return buildFruit();
    }

    @Override
    @CompatibleProcess("fruitListCompatibleProcessor")
    public List<Fruit> listFruit() {
        return new ArrayList<>(Arrays.asList(
                new Fruit("apple", "red", 10, 1.0),
                new Fruit("banana", "yellow", 4, 2.0),
                new Fruit("pear", "yellow", 4, 2.0)
        ));
    }

    private Fruit buildFruit() {
        Fruit apple = new Fruit();
        apple.setWeight(10);
        apple.setPrice(2.0);
        apple.setColor("red");
        apple.setName("apple");
        return apple;
    }
}
