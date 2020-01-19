package cn.orangepoet.annotationprocessing.usage;

import cn.orangepoet.annotationprocessing.processor.BuilderProperty;

public class PersonBuilder {

    private Person instance = new Person();

    public Person setAge(int age) {
        this.instance.setAge(age);
        return this;
    }

    public Person setName(String name) {
        this.instance.setName(name);
        return this;
    }
}
