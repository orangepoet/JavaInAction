package cn.orangepoet.inaction.algorithm.dp

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

    def '测试目标数解集'() {
        given:
        def solution = new Solution()

        expect:
        solution.combinationSum(candidates, target) == ans

        where:
        candidates            | target | ans
        [2, 3, 6, 7] as int[] | 7      | [[2, 2, 3], [7]]
    }
    
    def '三数之和'() {
        given:
        def solution = new Solution()
        expect:
        solution.threeSum(nums) == result

        where:
        nums                           | result
        [-1, 0, 1, 2, -1, -4] as int[] | [[-1, 0, 1], [-1, -1, 2]] as List
    }

    def '树的路径总和'() {
        given:
        Solution.TreeNode n6 = new Solution.TreeNode(2)
        Solution.TreeNode n5 = new Solution.TreeNode(7)
        Solution.TreeNode n4 = new Solution.TreeNode(11, n5, n6)
        Solution.TreeNode n2 = new Solution.TreeNode(4, n4, null)

        Solution.TreeNode n10 = new Solution.TreeNode(1)
        Solution.TreeNode n9 = new Solution.TreeNode(5)
        Solution.TreeNode n8 = new Solution.TreeNode(4, n9, n10)
        Solution.TreeNode n7 = new Solution.TreeNode(13)
        Solution.TreeNode n3 = new Solution.TreeNode(8, n7, n8)

        Solution.TreeNode n1 = new Solution.TreeNode(5, n2, n3)

        n1.left
        def solution = new Solution()
        expect:
        solution.pathSum(root, sum) == ret

        where:
        root                           | sum | ret
        [-1, 0, 1, 2, -1, -4] as int[] | 22  | [[5, 4, 11, 2] as int[], [5, 8, 4, 5] as int[]] as int[]
    }

    def '数组单调测试'() {
        given:
        def solution = new Solution()

        expect:
        solution.isMonotonic(arr) == ans

        where:
        arr                   | ans
        [1, 3, 2] as int[]    | false
        [1, 2, 4, 5] as int[] | true
    }

}
