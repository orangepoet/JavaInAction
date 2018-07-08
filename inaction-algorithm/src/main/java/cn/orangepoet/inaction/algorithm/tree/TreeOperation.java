package cn.orangepoet.inaction.algorithm.tree;

/**
 * Created by Orange on 16/8/27.
 */
public class TreeOperation {
    public static void main(String[] args) {
        BinaryNode root = BinaryNode.prepareTree();
        root.print(0);

        boolean isContain = contain(4, root);
        System.out.println(String.format("contain 4: %s", isContain));

        BinaryNode removedItem = remove(4, root);

        BinaryNode newItem = add(7, root);
        root.print(0);

        add0(9, root);
        root.print(0);
    }

    private static boolean contain(int x, BinaryNode node) {
        if (node == null)
            return false;

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

    private static BinaryNode remove(int x, BinaryNode node) {
        if (node == null)
            return node;

        if (x < node.element)
            node.left = remove(x, node.left);
        else if (x > node.element)
            node.right = remove(x, node.right);
        else if (node.left != null && node.right != null) {
            node.element = findMin(node.right).element;
            node.right = remove(node.element, node.right);
        } else {
            node = (node.left != null) ? node.left : node.right;
        }

        return node;
    }

    private static BinaryNode findMin(BinaryNode node) {
        if (node != null)
            while (node.left != null)
                node = node.left;
        return node;
    }

    private static BinaryNode add(int x, BinaryNode node) {
        if (node == null)
            return new BinaryNode(x, null, null);

        if (x < node.element) {
            node.left = add(x, node.left);
        } else if (x > node.element) {
            node.right = add(x, node.right);
        } else {
            // do nothing, duplicate
        }
        return node;
    }

    private static void add0(int x, BinaryNode node) {
        if (node == null)
//            return new BinaryNode(x, null, null);
            throw new NullPointerException("node");

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
                if (t.right != null)
                    t = t.right;
                else {
                    t.right = new BinaryNode(x, null, null);
                    break;
                }
            } else {
                // do nothing, duplicate
            }
        }
    }
}
