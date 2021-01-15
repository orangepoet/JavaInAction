package cn.orangepoet.inaction.function;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @Author: chengzhi
 * @Date: 2020/4/28
 */
@Slf4j
public class StreamDemo {
    /**
     * 统计同类项
     *
     * @return
     */
    public static void groupBy() {
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

        log.info("lst2: {}", lst2);
    }

    /**
     * 使用map reduce 操作集合, 分治合并算法
     */
    public static void mapReduce() {
        List<Integer> lst = Arrays.asList(1, 2, 3);

        Integer total = lst.stream().map(i -> i * 10).reduce(0, Integer::sum);
        log.info("total:{}", total);
        log.info("processors cnt: {}", Runtime.getRuntime().availableProcessors());
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
