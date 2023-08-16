package cn.orangepoet.inaction.gof.builder.classic;

public class Product {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
