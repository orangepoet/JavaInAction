package cn.orangepoet.inaction.designpattern.builder.classic;

public class Director {

    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public Product constructor() {
        builder.buildPart();
        return builder.build();
    }
}
