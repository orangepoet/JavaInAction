package cn.orangepoet.inaction.app.func;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @Author: chengzhi
 * @Date: 2020/4/28
 */
public class StreamDemo {
    /**
     * 统计同类项
     *
     * @return
     */
    private static List<Foo> combineSame() {
        List<Foo> lst = Arrays.asList(
                new Foo("1", "a", 1),
                new Foo("1", "a", 1),
                new Foo("1", "a", 1),
                new Foo("2", "b", 1),
                new Foo("2", "b", 1)
        );

        List<Foo> lst2 = lst.parallelStream()
                .collect(Collectors.collectingAndThen(groupingBy(f -> f.id), group -> group.values().stream()
                        .map(items -> {
                            int sum = items.stream().mapToInt(f -> f.count).sum();
                            Foo first = items.get(0);
                            return new Foo(first.id, first.name, sum);
                        })
                        .collect(toList()))
                );
        System.out.println(lst2);
        return lst2;
    }

    @Getter
    @Setter
    @ToString
    public static class Foo {
        private String id;
        private String name;
        private int count;

        public Foo(String id, String name, int count) {
            this.id = id;
            this.name = name;
            this.count = count;
        }
    }
}
