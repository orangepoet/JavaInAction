package cn.orangepoet.annotationprocessing.usage.compatible;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fruit {
    private String name;
    private String color;
    private int weight;
    private double price;
}
