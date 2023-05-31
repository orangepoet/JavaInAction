package cn.orangepoet.inaction.algorithm.leetcode

import java.util.*
import kotlin.math.max

/**
 * 上下翻转二叉树
 */
fun upsideDownBinaryTree(treeNode: TreeNode?): TreeNode? {
    if (treeNode == null || treeNode.left == null) {
        return treeNode
    }
    val stack = LinkedList<TreeNode>()
    dfs(stack, treeNode)
    var prev: TreeNode? = null
    var root: TreeNode? = null
    var left = true
    while (stack.isNotEmpty()) {
        val node = stack.pop()
        if (node != null) {
            node.left = null
            node.right = null
        }
        // 第一次
        if (prev == null) {
            prev = node
            root = prev
        } else {
            if (left) {
                prev.left = node
                left = false
            } else {
                prev.right = node
                prev = node
                left = true
            }
        }
    }
    return root
}

private fun dfs(stack: LinkedList<TreeNode>, root: TreeNode?) {
    if (root == null) {
        return
    }
    stack.push(root)
    stack.push(root.right)
    dfs(stack, root.left)
}

/**
 *从未排序的链表中移除重复元素
 */
fun deleteDuplicatesUnsorted(head: ListNode?): ListNode? {
    val map = HashMap<Int, Int>()
    var p = head
    while (p != null) {
        map.compute(p.value) { _, v: Int? -> if (v == null) 1 else v + 1 }
        p = p.next
    }
    val h = ListNode(-1)
    var prev = h
    p = head
    while (p != null) {
        val next = p.next
        if (map[p.value] == 1) {
            prev.next = p
            p.next = null
            prev = p
        }
        p = next
    }
    return h.next
}

private var max = 0
private val depthMap: MutableMap<Node?, Int> = HashMap()

fun diameter(root: Node?): Int {
    if (root == null) {
        return 0
    }
    max = Math.max(max, diameter0(root))
    for (child in root.children) {
        diameter(child)
    }
    return max
}

private fun diameter0(root: Node): Int {
    val children = root.children
    if (children == null || children.size == 0) {
        return 0
    }
    if (children.size == 1) {
        return depth0(children[0])
    }
    val size = root.children.size
    val depths = IntArray(size)
    for (i in 0 until size) {
        depths[i] = depth0(root.children[i])
    }
    Arrays.sort(depths)
    return depths[depths.size - 1] + depths[depths.size - 2]
}

private fun depth0(node: Node?): Int {
    if (depthMap.containsKey(node)) {
        return depthMap[node]!!
    }
    if (node == null) {
        return 0
    }
    var depth = 1
    if (node.children != null) {
        for (child in node.children) {
            depth = Math.max(depth, depth0(child) + 1)
        }
    }
    depthMap[node] = depth
    return depth
}

/**
 * 返回比当前数大的最小回文素数
 */
fun primePalindrome(n: Int): Int {
    var num = n
    while (true) {
        if (num == reverse(num) && isPrime(num)) {
            return num
        }
        num++
    }
}

private fun reverse(n: Int): Int {
    var m = n
    var ans = 0
    while (m > 0) {
        ans = 10 * ans + m % 10
        m /= 10
    }
    return ans
}

private fun isPrime(n: Int): Boolean {
    val to = Math.sqrt(n.toDouble())
    var i = 2
    while (i <= to) {
        if (n % i == 0) {
            return false
        }
        i++
    }
    return true
}

/**
 * 到达终点数字
 */
fun reachNumber(target: Int): Int {
    return reachNumber0(0, 0, target)
}

private fun reachNumber0(i: Int, n: Int, target: Int): Int {
    var k = i
    if (n == target) {
        return k
    }
    k = k + 1
    val a = Math.abs(target - n + k)
    val b = Math.abs(target - n - k)
    val n1 = if (a <= b) n - k else n + k
    return reachNumber0(k, n1, target)
}

/**
 * 划分数组为连续数字的集合
 */
fun isPossibleDivide(nums: IntArray, k: Int): Boolean {
    Arrays.sort(nums)
    var count = 0
    val len = nums.size
    while (count < len) {
        var j = 0
        var prev = -1
        for (i in 0 until k) {
            while (j < len && (nums[j] == 0 || nums[j] == prev)) {
                j++
            }
            if (j >= len) {
                return false
            }
            if (prev == -1 || nums[j] == prev + 1) {
                prev = nums[j]
                nums[j] = 0
                j++
                count++
            } else {
                return false
            }
        }
    }
    return true
}

/**
 * 二叉树最长连续序列
 */
fun longestConsecutive(root: TreeNode): Int {
    val max0 = longestConsecutive0(-1, root, 0)
    max = Math.max(max, max0)
    if (root.left != null) {
        longestConsecutive(root.left!!)
    }
    if (root.right != null) {
        longestConsecutive(root.right!!)
    }
    return max
}

private fun longestConsecutive0(prev: Int, node: TreeNode, depth: Int): Int {
    if (prev != -1 && node.`val` != prev + 1) {
        return depth + 1
    }
    var ans = depth + 1
    if (node.left != null) {
        ans = max(ans, longestConsecutive0(node.`val`, node.left!!, depth + 1))
    }
    if (node.right != null) {
        ans = max(ans, longestConsecutive0(node.`val`, node.right!!, depth + 1))
    }
    return ans
}

fun shortestBridge(grid: Array<IntArray>): Int {
    var min = Int.MAX_VALUE
    val x = grid.size
    val y = grid[0].size
    var first = true
    val A: MutableList<IntArray> = ArrayList()
    for (i in 0 until x) {
        for (j in 0 until y) {
            if (grid[i][j] == 1) {
                if (first) {
                    dfs(grid, i, j, x, y, A)
                    first = false
                } else {
                    val bread = bread0(i, j, A) - 1
                    min = Math.min(min, bread)
                }
            }
        }
    }
    return min
}

//标记2
private fun dfs(grid: Array<IntArray>, i: Int, j: Int, n: Int, m: Int, A: MutableList<IntArray>) {
    if (!(i >= 0 && i < n && j >= 0 && j < m)) {
        return
    }
    if (grid[i][j] != 1) {
        return
    }
    grid[i][j] = 0
    A.add(intArrayOf(i, j))
    dfs(grid, i, j + 1, n, m, A)
    dfs(grid, i, j - 1, n, m, A)
    dfs(grid, i + 1, j, n, m, A)
    dfs(grid, i - 1, j, n, m, A)
}

private fun bread0(i: Int, j: Int, A: List<IntArray>): Int {
    var path = Int.MAX_VALUE
    for (p in A) {
        val p0 = Math.abs(i - p[0]) + Math.abs(j - p[1])
        path = Math.min(path, p0)
    }
    return path
}

fun compareVersion(version1: String, version2: String): Int {
    val part1 = version1.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    val part2 = version2.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    val len = Math.min(part1.size, part2.size)
    for (i in 0 until len) {
        val left = trimZero(part1[i]).toInt()
        println("left$left")
        val right = trimZero(part2[i]).toInt()
        println("right$right")
        val compare = Integer.compare(left, right)
        if (compare != 0) {
            return compare
        }
    }
    return if (part1.size > len) {
        if (isZero(part1, len)) 0 else 1
    } else if (part2.size > len) {
        if (isZero(part2, len)) 0 else -1
    } else {
        0
    }
}

private fun trimZero(s: String): String {
    var i = 0
    while (i < s.length - 1 && s[i] == '0') {
        i++
    }
    return if (i > 0) s.substring(i) else s
}

private fun isZero(arr: Array<String>, start: Int): Boolean {
    for (i in start until arr.size) {
        for (j in 0 until arr[i].length) {
            if (arr[i][j] != '0') {
                return false
            }
        }
    }
    return true
}

fun orderOfLargestPlusSign(n: Int, mines: Array<IntArray>): Int {
    val ans = Array(n) { IntArray(n) }
    for (i in 0 until n) {
        for (j in 0 until n) {
            ans[i][j] = 1
        }
    }
    for (i in mines.indices) {
        val p = mines[i]
        ans[p[0]][p[1]] = 0
    }
    var max = 0
    for (i in 0 until n) {
        for (j in 0 until n) {
            if (ans[i][j] == 1 && i > 0 && i < n - 1 && j > 0 && j < n - 1) {
                val `val` = plusSign0(ans, i, j, n)
                ans[i][j] += `val`
            }
            max = Math.max(ans[i][j], max)
        }
    }
    return max
}

private fun plusSign0(ans: Array<IntArray>, i: Int, j: Int, n: Int): Int {
    var h = 0
    run {
        var k = 0
        while (true) {
            val left = j - k
            val right = j + k
            if (left < 0 || right >= n || ans[i][left] == 0 || ans[i][right] == 0) {
                break
            }
            h = k
            k++
        }
    }
    var v = 0
    var k = 0
    while (true) {
        val up = i - k
        val down = i + k
        if (up < 0 || down >= n || ans[up][j] == 0 || ans[down][j] == 0 || k > h) {
            break
        }
        v = k
        k++
    }
    return Math.min(h, v)
}

fun serialize(root: TreeNode?): String? {
    if (root == null) {
        return null
    }
    val list: MutableList<Int?> = ArrayList()
    val queue = LinkedList<TreeNode?>()
    queue.offer(root)
    while (!queue.isEmpty()) {
        var hasNext = false
        val size = queue.size
        for (i in 0 until size) {
            val tn = queue.poll()
            list.add(tn?.`val`)
            if (tn != null) {
                queue.offer(tn.left)
                queue.offer(tn.right)
                if (tn.left != null || tn.right != null) {
                    hasNext = true
                }
            }
        }
        if (!hasNext) {
            break
        }
    }
    val sb = StringBuilder()
    for (i in list.indices) {
        sb.append(list[i])
        if (i != list.size - 1) {
            sb.append(",")
        }
    }
    return sb.toString()
}

// Decodes your encoded data to tree.
fun deserialize(data: String?): TreeNode? {
    if (data == null || data.length == 0) {
        return null
    }
    val trees0 = data.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    val trees = arrayOfNulls<Int>(trees0.size)
    for (i in trees0.indices) {
        trees[i] = if (trees0[i] == "null") null else trees0[i].toInt()
    }
    var idx = 0
    val root = TreeNode(trees[idx]!!, null, null)
    val queue = LinkedList<TreeNode>()
    queue.offer(root)
    while (!queue.isEmpty()) {
        val size = queue.size
        for (i in 0 until size) {
            if (idx >= trees.size - 1) {
                return root
            }
            val tn = queue.poll()
            tn.left = if (trees[++idx] != null) TreeNode(trees[idx]!!, null, null) else null
            tn.right = if (trees[++idx] != null) TreeNode(trees[idx]!!, null, null) else null
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
 * 丑数
 */
fun isUgly(n: Int): Boolean {
    if (n <= 0) {
        return false
    }
    var i = n
    var j = 2
    while (j <= Math.sqrt(i.toDouble())) {
        if (i % j == 0) {
            i = i / j
        } else {
            j++
        }
        if (j > 5) {
            return false
        }
    }
    return i <= 5
}

/**
 * 第n丑数
 */
fun nthUglyNumber(n: Int): Int {
    val factors = intArrayOf(2, 3, 5)
    val heap = PriorityQueue<Long>()
    heap.offer(1L)
    val seen: MutableSet<Long> = HashSet()
    var ugly = 1
    for (i in 0 until n) {
        val curr = heap.poll()
        ugly = curr.toInt()
        for (j in factors.indices) {
            val e = curr * factors[j]
            if (seen.add(e)) {
                heap.offer(e)
            }
        }
    }
    return ugly
}

/**
 * 会议室冲突
 */
fun canAttendMeetings(intervals: Array<IntArray>): Boolean {
    if (intervals.isEmpty()) {
        return true
    }
    intervals.sortBy { it[0] }
    var prev: IntArray = intervals[0]
    for (i in 1..intervals.size - 1) {
        val curr = intervals[i]
        if (curr[0] < prev[1]) return false
        prev = curr
    }
    return true
}

/**
 * 会议室2
 */
fun minMeetingRooms(intervals: Array<IntArray>): Int {
    Arrays.sort(intervals, Comparator.comparingInt { x: IntArray ->
        x[0]
    })
    val size = intervals.size
    val taken = IntArray(size)
    var i = 0
    var rooms = 0
    var prev: IntArray? = null
    while (i < size) {
        for (j in 0 until size) {
            if (taken[j] == 1) {
                continue
            }
            if (prev == null || intervals[j][0] >= prev[1]) {
                taken[j] = 1
                i++
                prev = intervals[j]
            }
        }
        rooms++
        prev = null
    }
    return rooms
}

/**
 * 摆动排序
 */
fun wiggleSort(nums: IntArray) {
    if (nums.size <= 1) {
        return
    }
    var dir = 1
    for (i in 0 until nums.size - 1) {
        if (dir < 0 && nums[i] < nums[i + 1] || dir > 0 && nums[i] > nums[i + 1]) {
            swap(nums, i, i + 1)
        }
        dir = -1 * dir
    }
}

private fun swap(nums: IntArray, i: Int, j: Int) {
    val tmp = nums[i]
    nums[i] = nums[j]
    nums[j] = tmp
}

/**
 * 排列
 */
fun permute(count: Int): List<IntArray> {
    val ans = ArrayList<IntArray>()
    val arr = IntArray(count)
    for (i in 0 until count) {
        arr[i] = i + 1
    }
    permute0(ans, arr, 0)
    return ans
}

private fun permute0(ans: ArrayList<IntArray>, arr: IntArray, first: Int) {
    if (first == arr.size) {
        println(arr.joinToString())
        ans.add(arr.copyOf())
        return
    }
    for (i in first until arr.size) {
        swap(arr, first, i)
        permute0(ans, arr, first + 1)
        swap(arr, i, first)
    }
}

/**
 * 组合
 */
fun combine(count: Int): List<IntArray> {
    val ans = ArrayList<IntArray>()
    val arr = IntArray(count)
    for (i in 0 until count) {
        arr[i] = i + 1
    }
    combine0(ans, arr, mutableListOf<Int>(), 0)

    return ans
}

private fun combine0(result: MutableList<IntArray>, arr: IntArray, current: MutableList<Int>, start: Int) {
    result.add(current.toIntArray())

    for (i in start until arr.size) {
        current.add(arr[i])
        combine0(result, arr, current, i + 1)
        current.removeAt(current.size - 1)
    }
}

/**
 * 荷兰国旗问题
 *
 * @param nums
 */
fun sortColors(nums: IntArray) {
    /**
     * [0,p0) 0
     * [p0, i)  1
     * (p2, n-1]  2
     */
    var p0 = 0
    var i = 0
    var p2 = nums.size - 1

    var count = 0
    while (i <= p2) {
        count++
        when (nums[i]) {
            0 -> {
                assert(nums[p0] == 1 || nums[p0] == 0 && p0 == i)
                swap(nums, i, p0)
                i++
                p0++
            }

            1 -> {
                i++
            }

            2 -> {
                swap(nums, i, p2)
                p2--
            }
        }
    }
}


val ans = mutableListOf<List<Int>>()
val cur = mutableListOf<Int>()

/**
 * k数组合
 */
fun combine(n: Int, k: Int): List<List<Int>> {
    dfs(n, k, 1)
    return ans
}

fun dfs(n: Int, k: Int, p: Int) {
    if (cur.size + (n - p + 1) < k) {
        return
    }

    if (cur.size == k) {
        ans.add(cur.toList())
        return
    }

    cur.add(p)
    dfs(n, k, p + 1)

    cur.removeAt(cur.size - 1)
    dfs(n, k, p + 1)
}