package cn.orangepoet.inaction.demo.function;

import io.vavr.control.Either;

/**
 * @author chengzhi
 * @date 2019/05/16
 */
public class EitherSample {
    public static void main(String[] args) {
        Either<String, Boolean> result = Either.right(true);
        System.out.println(result.get());
        System.out.println(result.isLeft());
        System.out.println(result.isRight());

        System.out.println(result.isLeft() ? result.getLeft() : "");

        Either<String, Boolean> result2 = Either.left("error");
        System.out.println(result2.isLeft());
        System.out.println(result2.isRight());
    }
}
