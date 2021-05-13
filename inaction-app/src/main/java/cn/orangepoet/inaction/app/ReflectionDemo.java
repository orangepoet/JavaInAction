package cn.orangepoet.inaction.app;

import lombok.Data;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.StopWatch;

/**
 * @author chengzhi
 * @date 2021/05/10
 */
public class ReflectionDemo {
    public static void main(String[] args) {
        Foo foo = new Foo();
        foo.setName("chengzhi");

        String name = "";
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            for (int i = 0; i < 10_000_000; i++) {
                name = (String)FieldUtils.readDeclaredField(foo, "name", true);
            }
            stopWatch.stop();;
            //try {
            //    Field[] declaredFields = foo.getClass().getDeclaredFields();
            //    Field field = foo.getClass().getDeclaredField("name");
            //    field.setAccessible(true);
            //    String name = (String)field.get(foo);
            //    System.out.println(name);
            //} catch (NoSuchFieldException e) {
            //    e.printStackTrace();
            //}
            System.out.println(stopWatch.getTime());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(name);
    }

    @Data
    public static class Foo {
        private String name;
    }
}
