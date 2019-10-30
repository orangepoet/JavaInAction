package cn.orangepoet.inaction.algorithm

import cn.orangepoet.inaction.algorithm.dp.Solution
import spock.lang.Specification

/**
 * 使用Spock示例
 *
 * @author chengzhi* @date 2019/09/27
 */
class SolutionTest extends Specification {
    def "最大连续子序列"() {
        given:
        def solution = new Solution()
        expect:
        solution.getMaxSubSet(sequence) == max

        where:
        sequence                       | max

        [1, 2, 3] as int[]             | 6
        [-1, 2, -2, 1, 1, -3] as int[] | 2
    }

    def "斐波那契数列 (非递归解法)"() {
        given:
        def solution = new Solution()
        expect:
        solution.fibonacci0(n) == arr

        where:
        n | arr
        1 | 1
        2 | 1
        3 | 2
        4 | 3

    }

    def "斐波那契数列(递归解法)"() {
        given:
        def solution = new Solution()
        expect:
        solution.fibonacci1(n) == arr

        where:
        n | arr
        1 | 1
        2 | 1
        3 | 2
        4 | 3

    }
}
