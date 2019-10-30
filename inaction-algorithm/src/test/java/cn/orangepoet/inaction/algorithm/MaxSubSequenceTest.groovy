package cn.orangepoet.inaction.algorithm

import cn.orangepoet.inaction.algorithm.dp.MaxSubSequence
import spock.lang.Specification

/**
 * 使用Spock示例
 *
 * @author chengzhi* @date 2019/09/27
 */
class MaxSubSequenceTest extends Specification {
    def "get max"() {
        expect:
        MaxSubSequence.getMax(sequence) == max

        where:
        sequence                       | max

        [1, 2, 3] as int[]             | 6
        [-1, 2, -2, 1, 1, -3] as int[] | 1
    }
}
