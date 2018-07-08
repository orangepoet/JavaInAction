package cn.orangepoet.inaction.algorithm.tree;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * <p>树的遍历算法</p>
 * <p>
 * Created by Orange on 16/8/27.
 */
public class TreeIterator {
    public static void main(String[] args) {
        BinaryNode leftleft = new BinaryNode(4, null, null);
        BinaryNode leftright = new BinaryNode(5, null, null);
        BinaryNode left = new BinaryNode(2, leftleft, leftright);

        BinaryNode rightleft = new BinaryNode(6, null, null);
        BinaryNode rightright = new BinaryNode(7, null, null);
        BinaryNode right = new BinaryNode(3, rightleft, rightright);

        BinaryNode node = new BinaryNode(1, left, right);


        // 遍历算法
        System.out.println("先序遍历");
        ParentFirst(node);
        System.out.println("后序遍历");
        ChildrenFirst(node);
        System.out.println("深度遍历");
        DeepFirst(node);
        System.out.println("广度遍历");
        WideFirst(node);
    }

    /**
     * 广度优先遍历, 使用队列实现
     *
     * @param node
     */
    private static void WideFirst(BinaryNode node) {
        Queue<BinaryNode> queue = new ArrayBlockingQueue<BinaryNode>(10);
        queue.add(node);
        while (!queue.isEmpty()) {
            BinaryNode node1 = queue.poll();
            System.out.println(node1.element);
            if (node1.left != null)
                queue.add(node1.left);
            if (node1.right != null)
                queue.add(node1.right);
        }
    }

    /**
     * 深度优先遍历, 使用栈实现
     *
     * @param node
     */
    private static void DeepFirst(BinaryNode node) {
        Stack<BinaryNode> stack = new Stack<BinaryNode>();

        stack.push(node);
        while (!stack.empty()) {
            BinaryNode node1 = stack.pop();
            System.out.println(node1.element);

            if (node1.right != null)
                stack.push(node1.right);
            if (node1.left != null)
                stack.push(node1.left);
        }
    }

    /**
     * 先序遍历
     *
     * @param node
     */
    private static void ParentFirst(BinaryNode node) {
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
    private static void ChildrenFirst(BinaryNode node) {
        if (node != null) {
            ChildrenFirst(node.left);
            ChildrenFirst(node.right);
            System.out.println(node.element);
        }
    }
}
