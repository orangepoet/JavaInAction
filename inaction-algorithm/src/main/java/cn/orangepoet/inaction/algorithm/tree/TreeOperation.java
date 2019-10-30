package cn.orangepoet.inaction.algorithm.tree;

/**
 * 树的操作, 左子树的值都小于右子树
 *
 * @author Orange
 * @date 16/8/27.
 */
public class TreeOperation {
    /**
     * 二分查找, 小于走左, 大于走右
     *
     * @param node
     * @param x
     * @return
     */
    public static boolean contain(BinaryNode node, int x) {
        if (node == null) { return false; }

        BinaryNode t = node;
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
    public static BinaryNode remove(BinaryNode node, int x) {
        if (node == null) { return node; }

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

    public static BinaryNode findMin(BinaryNode node) {
        if (node != null) { while (node.left != null) { node = node.left; } }
        return node;
    }

    public static BinaryNode add(int x, BinaryNode node) {
        if (node == null) { return new BinaryNode(x, null, null); }

        if (x < node.element) {
            node.left = add(x, node.left);
        } else if (x > node.element) {
            node.right = add(x, node.right);
        } else {
            // do nothing, duplicate
        }
        return node;
    }

    public static void add0(int x, BinaryNode node) {
        if (node == null)
        //            return new BinaryNode(x, null, null);
        { throw new NullPointerException("node"); }

        BinaryNode t = node;

        while (t != null) {
            if (x < t.element) {
                if (t.left != null) {
                    t = t.left;
                } else {
                    t.left = new BinaryNode(x, null, null);
                    break;
                }
            } else if (x > t.element) {
                if (t.right != null) { t = t.right; } else {
                    t.right = new BinaryNode(x, null, null);
                    break;
                }
            } else {
                // do nothing, duplicate
            }
        }
    }
}
