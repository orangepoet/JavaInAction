package cn.orangepoet.inaction.demo.function;

import io.vavr.Function0;
import io.vavr.collection.List;
import io.vavr.control.Try;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

/**
 * @author chengzhi
 * @date 2019/04/12
 */
public class Application {
    public static void main(String[] args) {
        memorizeTest();
    }

    private static void collectionTest() {
        List<Integer> list1 = List.of(1, 2, 3);
        List<Integer> list2 = list1.take(2).map(i -> i + 1).filter(i -> i > 1);
        System.out.println(list2.toString());

        //java.util.List<Integer> list3 = list1.asJavaMutable();
        java.util.List<Integer> list3 = list1.asJava();

        list3.add(4);

        System.out.println(list1);
        System.out.println(list3);

        List.of("a", "b", "c").intersperse(",");
    }

    private static void tryTest() {
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

    private static void matchTest() {
        String str = "123";

        Integer result = Match(str).of(
            Case($("1"), s -> 1),
            Case($("2"), s -> 2)
        );
        System.out.println(result);
    }

    private static void memorizeTest() {
        Function0<Double> hashCache =
            Function0.of(Math::random).memoized();

        double randomValue1 = hashCache.apply();
        double randomValue2 = hashCache.apply();

        System.out.println(randomValue1 == randomValue2);

        //Either<Integer, Object> either = Option.of(null).toEither(1);
    }
}
