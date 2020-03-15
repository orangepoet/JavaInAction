package cn.orangepoet.annotationprocessing.usage.compatible.downgrade;

import cn.orangepoet.annotationprocessing.usage.compatible.downgrade.Fruit;

import java.util.Collections;
import java.util.List;

public interface FruitService {
    Fruit showFruit();

    default List<Fruit> listFruit() {
        return Collections.emptyList();
    }
}
