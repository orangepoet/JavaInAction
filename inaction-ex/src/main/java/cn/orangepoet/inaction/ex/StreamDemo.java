package cn.orangepoet.inaction.ex;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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

    public static void main(String[] args) {
        Integer a = null;
        log.info("result->{}", a);
        List<Integer> openIds = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            openIds.add(i);
        }
        AtomicInteger acc = new AtomicInteger();
        ListUtils.partition(openIds, 100)
                .parallelStream()
                .map(x -> {
                    log.info("partition:{}~{}", x.get(0), x.get(x.size() - 1));
                    int ret = acc.addAndGet(x.size());
                    return ret;
                })
                .forEachOrdered(x -> log.info("state->{}", x));
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
