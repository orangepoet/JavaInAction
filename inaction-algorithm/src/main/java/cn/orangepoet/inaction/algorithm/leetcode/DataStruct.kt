package cn.orangepoet.inaction.algorithm.leetcode

import java.util.*


data class Node(@JvmField val `val`: Int, @JvmField val children: List<Node> = listOf())

data class TreeNode(
    @JvmField val `val`: Int,
    @JvmField var left: TreeNode? = null,
    @JvmField var right: TreeNode? = null,
) {
    constructor(`val`: Int) : this(`val`, null, null)

    override fun toString() = "$`val`[${left?.`val` ?: "null"}, ${right?.`val` ?: "null"}]"
}


class ListNode(
    @JvmField var value: Int,
    @JvmField var next: ListNode?,
) {
    constructor(`val`: Int) : this(`val`, null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (this.javaClass != other.javaClass) return false
        val other0 = other as ListNode

        return if (this.next == null && other0.next == null) {
            true
        } else if (this.next != null && other0.next != null) {
            this.next == other0.next
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return Objects.hash(value)
    }

    override fun toString(): String {
        return "${this.value}->${this.next?.toString()}"
    }
}

