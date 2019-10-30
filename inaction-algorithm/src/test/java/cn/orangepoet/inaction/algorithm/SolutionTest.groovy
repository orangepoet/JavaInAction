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
        [-1] as int[]                  | -1
        [-1, 1] as int[]               | 1
        [2, -1] as int[]               | 2
        [-2, 3] as int[]               | 3
        [-2, 1] as int[]               | 1
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

    def "硬币找零"() {
        given:
        def solution = new Solution()
        expect:
        solution.makeChange(coins, n, new HashMap<>()) == cnt
        solution.makeChange2(coins, n) == cnt

        where:
        coins              | n    | cnt
        [1, 7, 9] as int[] | 3000 | 334
        [1, 7, 9] as int[] | 1    | 1
        [1, 7, 9] as int[] | 2    | 2
        [1, 7, 9] as int[] | 6    | 6
        [1, 7, 9] as int[] | 7    | 1
        [1, 7, 9] as int[] | 8    | 2
        [1, 7, 9] as int[] | 9    | 1
        [1, 7, 9] as int[] | 10   | 2
        [1, 7, 9] as int[] | 0    | 0
        [1, 7, 9] as int[] | 16   | 2
        [1, 7, 9] as int[] | 17   | 3
    }

    def '查找目标数的范围'() {
        given:
        def solution = new Solution()

        expect:
        solution.searchRange(nums, target) == range

        where:
        nums                         | target | range
        [5, 7, 7, 8, 8, 10] as int[] | 6      | [-1, -1] as int[]
        [5, 7, 7, 8, 8, 10] as int[] | 7      | [1, 2] as int[]
        [5, 7, 7, 8, 8, 10] as int[] | 8      | [3, 4] as int[]
    }
}