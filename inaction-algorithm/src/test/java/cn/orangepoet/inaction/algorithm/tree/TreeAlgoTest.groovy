package cn.orangepoet.inaction.algorithm.tree


import spock.lang.Specification

/**
 * @author chengzhi* @date 2019/10/30
 */
class TreeAlgoTest extends Specification {
    def "树包含节点"() {
        root.print(0)

        boolean isContain = TreeAlgo.contain(root, 4)
        System.out.println(String.format("contain 4: %s", isContain))


    }

    def "节点移除"() {
        when:
        TreeNode removedItem = TreeAlgo.remove(root, 4)

        then:
        removedItem != null

    }

    def "发现最小"() {
    }

    def "新增节点"() {
        TreeNode newItem = TreeAlgo.add(7, root)
        root.print(0)
    }

    def "广度遍历"() {
        given:
        def node = TreeNode.parse([1, 2, 3, 4, 5, 6, 7] as Integer[])
        when:
        def ret = TreeAlgo.wideFirst(node)
        then:
        ret.equals([6, 2, 8, 1, 5] as int[])
    }

    def "深度遍历"() {
    }

    def "后序遍历"() {
    }
}
