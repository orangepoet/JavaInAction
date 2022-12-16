package cn.orangepoet.inaction.function;

import java.util.function.Function;

import io.vavr.Function0;
import io.vavr.Lazy;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

/**
 * @author chengzhi
 * @date 2019/04/12
 */
@Slf4j
public class VavrDemo {

    /**
     * vavr的集合操作
     */
    public static void listEx() {
        List<Integer> list1 = List.of(1, 2, 3);
        List<Integer> list2 = list1.take(2).map(i -> i + 1).filter(i -> i > 1);

        log.info("list2: {}", list2);

        java.util.List<Integer> list3 = list1.toJavaList();

        list3.add(4);

        log.info("list1: {}", list1);
        log.info("list3: {}", list3);

        List<String> intersperse = List.of("a", "b", "c").intersperse(",");
        log.info("intersperse: {}", intersperse);
    }

    /**
     * 异常的包装, 不用catch异常块处理
     */
    public static void tryEx() {
        Try<Integer> integerTry = Try.of(() -> {
            int i = 0;
            int j = 1;
            return i / j;
            //return j / i;
        });

        //Integer result = integerTry.getOrElse(100);
        //System.out.println(result);
        integerTry
                //.recover((t, i) -> {
                //});
                .onSuccess(System.out::println)
                .onFailure(Throwable::printStackTrace);
    }

    /**
     * 模式匹配
     */
    public static void matchEx() {
        String str = "123";

        Integer result = Match(str).of(
                Case($("1"), s -> 1),
                Case($("2"), s -> 2)
        );
        System.out.println(result);
    }

    /**
     * 记忆化
     */
    public static void memorizeEx() {
        Function0<Double> hashCache =
                Function0.of(Math::random).memoized();

        double randomValue1 = hashCache.apply();
        double randomValue2 = hashCache.apply();

        log.info("randomValue1: {}", randomValue1);
        log.info("randomValue2: {}", randomValue2);
    }

    public static void eitherEx() {
        Function<Integer, Either<String, Integer>> eitherFunction = x -> {
            if (x % 2 == 0) {
                return Either.right(x * x);
            }
            return Either.left("not a even");
        };

        Either<String, Integer> either1 = eitherFunction.apply(4);
        if (either1.isRight()) {
            System.out.println(either1.get());
        } else {
            System.out.println(either1.getLeft());
        }

        Either<String, Integer> either2 = eitherFunction.apply(5);
        if (either2.isRight()) {
            System.out.println(either2.get());
        } else {
            System.out.println(either2.getLeft());
        }
    }

    public static void lazy() {
        Lazy<Double> lazy = Lazy.of(Math::random);
        Double d1 = lazy.get();
        log.info("get1: {}", d1);
        Double d2 = lazy.get();
        log.info("get2: {}", d2);
    }

    public static void option() {
        Option<String> option = Option.of(null);
        if (option.isEmpty()) {
            System.out.println(option);
        }
    }
}
