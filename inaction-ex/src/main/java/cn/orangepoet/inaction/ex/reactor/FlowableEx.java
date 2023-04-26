package cn.orangepoet.inaction.ex.reactor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Slf4j
public class FlowableEx {
    public static void main(String[] args) {
        test2();
    }


    /**
     * Mono的同步方法与异步方法
     */
    private static void test1() {
        log.info("task1|start");
        CountDownLatch cdl = new CountDownLatch(2);
        Mono.fromSupplier(() -> mockCall(2000, 1))
                .doOnTerminate(() -> cdl.countDown())
                .subscribe(next -> log.info("task1|result|{}", next));
        log.info("task1|end");

        log.info("task2|start");
        Mono.fromFuture(CompletableFuture.supplyAsync(() -> mockCall(2000, 1)))
                .doOnTerminate(() -> cdl.countDown())
                .subscribe(next -> log.info("task2|result|{}", next));
        log.info("task2|end");

        try {
            cdl.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void run2() {
        int nextOpenid = 0;
        int max = 5000;
        for (int i = 0; i < 100000; i++) {
            log.info("test2|seq->{}", i);
            List<Integer> openIds = userIdList(nextOpenid, max, 10000); // 1500
            if (openIds == null || openIds.isEmpty()) {
                break;
            }
            /**
             * 像 service {getOpenIds, getUsers}, db {saveUsers, saveTags, saveEs}, publishEvents 都是阻塞的操作,
             * 即便使用了线程池, 在单个线程内操作上下游阻塞.
             *
             * 线程池: ForkJoinPool, 并发度: 8个, main+7*forkJoinPool.
             */

            ListUtils.partition(openIds, 100)
                    .parallelStream()
                    .forEach(partition -> {
                        getUsers(partition)
                                .subscribe(users -> {
                                    saveUsers(users).block();
                                    saveTags(users).block();
                                    saveEs(users).block();
                                    publishEvents(users).block();
                                });
                    });
            nextOpenid = openIds.get(openIds.size() - 1);
        }
    }

    /**
     * 背压的测试
     */
    private static void test2() {
        Scheduler userGetScheduler = Schedulers.newParallel("user-get", 2);
        Scheduler userHandleScheduler = Schedulers.newParallel("user-handle", 16);

        int max = 5_000;
        Flux<List<User>> publisher = userIdList1(max, 10_000)
                .doOnComplete(() -> log.info("user-get|complete"))
                .flatMap(batchUserId -> Flux.fromIterable(ListUtils.partition(batchUserId, 100)))
                .flatMap(unit -> getUsers(unit)
                        .subscribeOn(userGetScheduler))
                .doOnComplete(() -> {
                    log.info("complete");
                });

        publisher
                .publishOn(userHandleScheduler)
                .subscribe(users -> saveUsers(users)
                        .then(saveTags(users))
                        .then(saveEs(users))
                        .then(publishEvents(users))
                        .subscribe()
                );
    }

    private static List<Integer> userIdList(int nextid, int max, int batchSize) {
        log.info("userIdList|nextid->{}", nextid);

        List<Integer> result = new ArrayList<>();
        if (nextid > max) {
            return result;
        }
        int start = nextid + 1;
        int end = Math.min(max, start + batchSize);

        for (int i = start; i <= end; i++) {
            result.add(i);
        }
        return mockCall(1000, result);
    }

    private static Flux<List<Integer>> userIdList1(int max, int batchSize) {
        return Flux.generate(() -> 0, (state, sink) -> {
            List<Integer> userIds = userIdList(state, max, batchSize);
            if (CollectionUtils.isEmpty(userIds)) {
                sink.complete();
                return state;
            } else {
                sink.next(userIds);
                return userIds.get(userIds.size() - 1);
            }
        });
    }

    private static Mono<List<User>> getUsers(List<Integer> openIds) {
        return Mono.fromCallable(() -> {
            log.info("getUsers|range|{}~{}", openIds.get(0), openIds.get(openIds.size() - 1));
            List<User> users = openIds.stream().map(id -> new User(id)).collect(Collectors.toList());
            mockRun(1000);
            return users;
        });
    }

    private static Mono<Void> saveUsers(List<User> users) {
        return Mono.fromRunnable(() -> {
            log.info("saveUsers|range|{}~{}", users.get(0).getId(), users.get(users.size() - 1).getId());
            mockRun(1000);
        });
    }

    private static Mono<Void> saveTags(List<User> users) {
        return Mono.fromRunnable(() -> {
            log.info("saveTags|range|{}~{}", users.get(0).getId(), users.get(users.size() - 1).getId());
            mockRun(1000);
        });
    }

    private static Mono<Void> saveEs(List<User> users) {
        return Mono.fromRunnable(() -> {
            log.info("saveEs|range|{}~{}", users.get(0).getId(), users.get(users.size() - 1).getId());
            mockRun(1000);
        });
    }

    private static Mono<Void> publishEvents(List<User> users) {
        return Mono.fromRunnable(() -> {
            log.info("publishEvents|range|{}~{}", users.get(0).getId(), users.get(users.size() - 1).getId());
            mockRun(1000);
        });

    }

    private static <T> T mockCall(long ms, T returnValue) {
        try {
            Thread.sleep(ms);
            return returnValue;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void mockRun(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    @AllArgsConstructor
    private static class User {
        private Integer id;
    }
}
