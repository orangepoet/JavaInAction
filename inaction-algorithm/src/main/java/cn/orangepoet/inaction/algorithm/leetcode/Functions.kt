package cn.orangepoet.inaction.algorithm.leetcode

import java.util.*

/**
 * 通过数组组装链表
 *
 * @param arr 数组
 */
fun makeListNode(arr: IntArray): ListNode {
    val root = ListNode(arr[0])
    var prev = root
    for (i in 1 until arr.size) {
        val node = ListNode(arr[i])
        prev.next = node
        prev = node
    }
    return root
}

/**
 * 通过数组组装树
 *
 * @param arr tree的数组
 * @return 返回根节点
 */
fun makeTree(arr: Array<Int?>): TreeNode {
    var idx = 0
    var root = TreeNode(arr[idx]!!, null, null)
    val queue = LinkedList<TreeNode>()
    queue.offer(root)

    while (!queue.isEmpty()) {
        val size = queue.size
        for (i in 0 until size) {
            if (idx >= arr.size - 1) {
                return root
            }
            var tn = queue.poll()
            tn.left = if (arr[++idx] != null) TreeNode(arr[idx]!!, null, null) else null
            tn.right = if (arr[++idx] != null) TreeNode(arr[idx]!!, null, null) else null
            if (tn.left != null) {
                queue.offer(tn.left)
            }
            if (tn.right != null) {
                queue.offer(tn.right)
            }
        }
    }
    return root
}

/**
 * 链表转数组
 */
fun toIntArr(root: ListNode?): IntArray {
    var next = root
    val list = mutableListOf<Int>()
    while (next != null) {
        list.add(next.value)
        next = next.next
    }
    return list.toIntArray()
}

fun toIntArr(root: TreeNode?): Array<Int?> {
    if (root == null) {
        return emptyArray()
    }
    var queue = LinkedList<TreeNode?>()
    queue.add(root)
    val list = mutableListOf<Int?>()

    while (!queue.isEmpty()) {
        val size = queue.size
        var hasNext = false
        for (i in 0 until size) {
            val node = queue.pop()
            list.add(node?.`val`)
            queue.add(node?.left)
            queue.add(node?.right)
            if (node != null && (node.left != null || node.right != null)) {
                hasNext = true
            }
        }
        if (!hasNext) {
            break
        }
    }
    return list.toTypedArray()
}

fun isSorted(arr: IntArray): Boolean {
    for (i in 1..arr.size - 1) {
        if (arr[i] < arr[i - 1]) {
            return false
        }
    }
    return true
}

private val random = Random()

fun randomSequence(count: Int): IntArray {
    val ret = IntArray(count)
    for (i in 0 until count) {
        ret[i] = random.nextInt(3)
    }
    return ret
}