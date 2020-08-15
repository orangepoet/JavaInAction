package cn.orangepoet.annotationprocessing.usage.getter;

import java.time.LocalDate;
import java.util.Map;

import cn.orangepoet.annotationprocessing.processor.getter.Getter;
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

    private static void printElapsed(long start1, long end1) {
        long elapsed = end1 - start1;
        System.out.println("elapsed: " + elapsed);
    }
}
