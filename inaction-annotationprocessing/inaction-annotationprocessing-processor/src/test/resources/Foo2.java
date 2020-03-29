package cn.orangepoet.annotationprocessing.usage.getter;

public class Foo {
    private String name;
    private Integer count;

    public Integer getCount() {
        return this.count;
    }

    public String getName() {
        return this.name;
    }

    public static void main(String[] args) {
        Foo foo = new Foo();
        String name = foo.getName();
        Integer count = foo.getCount();
    }
}
