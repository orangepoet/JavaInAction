package cn.orangepoet.annotationprocessing.usage.getter;

import java.time.LocalDate;
import java.util.Map;

import cn.orangepoet.annotationprocessing.processor.getter.Getter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.NonNull;

/**
 * @author chengzhi
 * @date 2020/03/29
 */
@Getter
public class Foo {
    private String name;
    private Integer count;

    public Foo(@NonNull String name, @NonNull Integer count) {
        this.name = name;
        this.count = count;
    }

    public static void main(String[] args) {
        System.out.println(LocalDate.now().toString());
        //Foo foo = new Foo("", 1);
        //String name = foo.getName();
        //Integer count = foo.getCount();

        Map<Long, String> map = null;
        //map.put(1L, "a");
        //map.put(2L, "b");
        String s = JSON.toJSONString(map);

        long start = System.currentTimeMillis();
        Map<Long, String> map1 = null;
        for (int i = 0; i < 10000000; i++) {
            map1 = JSON.parseObject(s, Map.class);
        }
        long end = System.currentTimeMillis();
        printElapsed(start, end);

        System.out.println(map1);

        Map<Long, String> map2 = null;
        long start1 = System.currentTimeMillis();
        TypeReference<Map<Long, String>> typeReference = new TypeReference<Map<Long, String>>() {};
        for (int i = 0; i < 10000000; i++) {
            map2 = JSON.parseObject(s, typeReference);
        }
        long end1 = System.currentTimeMillis();
        printElapsed(start1, end1);

        System.out.println(map2);
    }

    private static void printElapsed(long start1, long end1) {
        long elapsed = end1 - start1;
        System.out.println("elapsed: " + elapsed);
    }
}
