package cn.orangepoet.annotationprocessing.usage.getter;

import cn.orangepoet.annotationprocessing.processor.getter.Getter;

/**
 * @author chengzhi
 * @date 2020/03/29
 */
@Getter
public class Foo {
    private String name;
    private Integer count;

    public static void main(String[] args) {
        Foo foo = new Foo();
        String name = foo.getName();
        Integer count = foo.getCount();
    }
}
