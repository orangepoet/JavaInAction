package cn.orangepoet.inaction.algorithm.tree;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 树的操作, 左子树的值都小于右子树
 *
 * @author Orange
 * @date 16/8/27.
 */
public class TreeAlgo {
    /**
     * 二分查找, 小于走左, 大于走右
     *
     * @param node
     * @param x
     * @return
     */
    public static boolean contain(TreeNode node, int x) {
        if (node == null) {
            return false;
        }

        TreeNode t = node;
        while (t != null) {
            if (x < t.element) {
                t = t.left;
            } else if (x > t.element) {
                t = t.right;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 递归查找到x所在的子树, 如果就是当前节点 ->
     * <p>
     * 1. 从右子树选一个最小的作为 root
     * </p>
     * <p>
     * 2. 从右子树中 删除1中的root节点, 递归下去 ->1
     * </p>
     *
     * @param node
     * @param x
     * @return
     */
    public static TreeNode remove(TreeNode node, int x) {
        if (node == null) {
            return node;
        }

        if (x < node.element) {
            node.left = remove(node.left, x);
        } else if (x > node.element) {
            node.right = remove(node.right, x);
        } else if (node.left != null && node.right != null) {
            node.element = findMin(node.right).element;
            node.right = remove(node.right, node.element);
        } else {
            node = (node.left != null) ? node.left : node.right;
        }
        return node;
    }

    public static TreeNode findMin(TreeNode node) {
        if (node != null) {
            while (node.left != null) {
                node = node.left;
            }
        }
        return node;
    }

    public static TreeNode add(int x, TreeNode node) {
        if (node == null) {
            return new TreeNode(x, null, null);
        }

        if (x < node.element) {
            node.left = add(x, node.left);
        } else if (x > node.element) {
            node.right = add(x, node.right);
        } else {
            // do nothing, duplicate
        }
        return node;
    }

    public static void add0(int x, TreeNode node) {
        if (node == null)
        //            return new BinaryNode(x, null, null);
        {
            throw new NullPointerException("node");
        }

        TreeNode t = node;

        while (t != null) {
            if (x < t.element) {
                if (t.left != null) {
                    t = t.left;
                } else {
                    t.left = new TreeNode(x, null, null);
                    break;
                }
            } else if (x > t.element) {
                if (t.right != null) {
                    t = t.right;
                } else {
                    t.right = new TreeNode(x, null, null);
                    break;
                }
            } else {
                // do nothing, duplicate
            }
        }
    }

    /**
     * 广度优先遍历, 使用队列实现
     *
     * @param node
     */
    public static List<Integer> wideFirst(TreeNode node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<Integer> ret = new ArrayList<>();

        Queue<TreeNode> queue = new ArrayBlockingQueue<TreeNode>(10);
        queue.add(node);
        while (!queue.isEmpty()) {
            TreeNode node1 = queue.poll();
            ret.add(node1.getElement());

            if (node1.left != null)
                queue.add(node1.left);
            if (node1.right != null)
                queue.add(node1.right);
        }
        return ret;
    }

    /**
     * 深度优先遍历, 使用栈实现
     *
     * @param node
     */
    public static void dfs(TreeNode node) {
        Stack<TreeNode> stack = new Stack<TreeNode>();

        stack.push(node);
        while (!stack.empty()) {
            TreeNode node1 = stack.pop();
            System.out.println(node1.element);

            if (node1.right != null)
                stack.push(node1.right);
            if (node1.left != null)
                stack.push(node1.left);
        }

        BigDecimal x = new BigDecimal("");
    }

    /**
     * 先序遍历
     *
     * @param node
     */
    public static void ParentFirst(TreeNode node) {
        if (node != null) {
            System.out.println(node.element);
            ParentFirst(node.left);
            ParentFirst(node.right);
        }
    }

    /**
     * 后序遍历, 先子树后根节点
     *
     * @param node
     */
    public static void ChildrenFirst(TreeNode node) {
        if (node != null) {
            ChildrenFirst(node.left);
            ChildrenFirst(node.right);
            System.out.println(node.element);
        }
    }
}
