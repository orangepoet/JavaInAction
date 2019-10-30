package cn.orangepoet.inaction.algorithm.tree

import spock.lang.Specification

/**
 * @author chengzhi* @date 2019/10/30
 */
class TreeOperationTest extends Specification {
    BinaryNode root

    def setup() {
        BinaryNode one = new BinaryNode(1, null, null)
        BinaryNode four = new BinaryNode(4, null, null)
        BinaryNode three = new BinaryNode(3, null, four)
        BinaryNode five = new BinaryNode(5, three, null)
        BinaryNode tow = new BinaryNode(2, one, five)
        BinaryNode eight = new BinaryNode(8, null, null)
        BinaryNode six = new BinaryNode(6, tow, eight)

        root = six
    }

    def "树包含节点"() {
        root.print(0)

        boolean isContain = TreeOperation.contain(root, 4)
        System.out.println(String.format("contain 4: %s", isContain))


    }

    def "节点移除"() {
        when:
        BinaryNode removedItem = TreeOperation.remove(root, 4)

        then:
        removedItem != null

    }

    def "发现最小"() {
    }

    def "新增节点"() {
        BinaryNode newItem = BinaryNode.add(7, root)
        root.print(0)
    }

    def "新增节点2"() {
        BinaryNode.add0(9, root)
        root.print(0)
    }
}
