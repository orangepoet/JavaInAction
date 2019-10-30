package cn.orangepoet.inaction.algorithm

import cn.orangepoet.inaction.algorithm.dp.Fibonacci
import spock.lang.Specification

/**
 * 使用Spock示例
 *
 * @author chengzhi* @date 2019/09/27
 */
class FibonacciTest extends Specification {
    def "斐波那契数列 (非递归解法)"() {
        Fibonacci fibonacci = new Fibonacci();
        expect:
        fibonacci.fibonacci0(n) == arr

        where:
        n | arr
        1 | 1
        2 | 1
        3 | 2
        4 | 3

    }

    def "斐波那契数列(递归解法)"() {
        Fibonacci fibonacci = new Fibonacci();
        expect:
        fibonacci.fibonacci1(n) == arr

        where:
        n | arr
        1 | 1
        2 | 1
        3 | 2
        4 | 3

    }
}
