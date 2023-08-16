package cn.orangepoet.inaction.reactor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SyncUser {

    public static final int BATCH_SIZE = 10_000;
    public static final int USER_COUNT = 1_000;

    /**
     * * 像 service {getOpenIds, getUsers}, db {saveUsers, saveTags, saveEs}, publishEvents 都是阻塞的操作,
     * * 即便使用了线程池, 在单个线程内操作上下游阻塞.
     * *
     * * 线程池: ForkJoinPool, 并发度: 8个, main+7*forkJoinPool.
     */
    public static void sync() {
        int nextOpenid = 0;
        int max = 5000;
        for (int i = 0; i < 100000; i++) {
            log.info("test2|seq->{}", i);
            List<Integer> openIds = userIdList(nextOpenid); // 1500
            if (openIds.isEmpty()) {
                break;
            }
            ListUtils.partition(openIds, 100)
                    .parallelStream()
                    .forEach(partition -> {
                        List<User> users = getUsers(partition);
                        saveUsers(users);
                        saveTags(users);
                        saveEs(users);
                        publishEvents(users);
                    });
            nextOpenid = openIds.get(openIds.size() - 1);
        }
    }


    public static void flowSync() {
        Scheduler userGetScheduler = Schedulers.newParallel("user-get", 2);
        Scheduler userHandleScheduler = Schedulers.newParallel("user-handle", 4);

        Flux.<List<Integer>, Integer>generate(() -> 0, (state, sink) -> {
                    List<Integer> userIds = userIdList(state);
                    if (CollectionUtils.isEmpty(userIds)) {
                        sink.complete();
                        return state;
                    } else {
                        sink.next(userIds);
                        return userIds.get(userIds.size() - 1);
                    }
                })
                .flatMap(userIdList ->
                        Flux.fromStream(ListUtils.partition(userIdList, 100).stream()))
                .flatMap(batch ->
                        Flux.defer(() -> Mono.just(getUsers(batch)))
                                .subscribeOn(userGetScheduler))
                .flatMap(users ->
                        Flux.defer(() -> {
                                    saveUsers(users);
                                    saveTags(users);
                                    saveEs(users);
                                    publishEvents(users);
                                    return Mono.empty();
                                })
                                .subscribeOn(userHandleScheduler)
                )
                .blockFirst();

        userGetScheduler.dispose();
        userHandleScheduler.dispose();
    }


    private static List<Integer> userIdList(int nextid) {
        log.info("userIdList|nextid->{}", nextid);

        mockRun(1000);

        if (nextid >= USER_COUNT) {
            return Collections.emptyList();
        }
        List<Integer> result = new ArrayList<>();
        int start = nextid + 1;
        int end = Math.min(USER_COUNT, start + BATCH_SIZE);

        for (int i = start; i <= end; i++) {
            result.add(i);
        }
        log.info("userIdList|return {}~{}", result.get(0), result.get(result.size() - 1));
        return result;
    }

    private static List<User> getUsers(List<Integer> openIds) {
        log.info("getUsers|range|{}~{}", openIds.get(0), openIds.get(openIds.size() - 1));
        List<User> users = openIds.stream().map(id -> new User(id)).collect(Collectors.toList());
        mockRun(1000);
        return users;
    }

    private static void saveUsers(List<User> users) {
        log.info("saveUsers|range|{}~{}", users.get(0).getId(), users.get(users.size() - 1).getId());
        mockRun(1000);
    }

    private static void saveTags(List<User> users) {
        log.info("saveTags|range|{}~{}", users.get(0).getId(), users.get(users.size() - 1).getId());
        mockRun(1000);
    }

    private static void saveEs(List<User> users) {
        log.info("saveEs|range|{}~{}", users.get(0).getId(), users.get(users.size() - 1).getId());
        mockRun(1000);
    }

    private static void publishEvents(List<User> users) {
        log.info("publishEvents|range|{}~{}", users.get(0).getId(), users.get(users.size() - 1).getId());
        mockRun(1000);
    }

    private static void mockRun(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    private static class User {
        private Integer id;
    }
}
