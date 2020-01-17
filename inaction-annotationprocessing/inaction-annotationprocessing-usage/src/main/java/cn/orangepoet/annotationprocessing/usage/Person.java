package cn.orangepoet.annotationprocessing.usage;

import cn.orangepoet.annotationprocessing.processor.BuilderProperty;

/**
 * @author chengzhi
 * @date 2020/01/17
 */
public class Person {
    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    @BuilderProperty
    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    @BuilderProperty
    public void setName(String name) {
        this.name = name;
    }
}
