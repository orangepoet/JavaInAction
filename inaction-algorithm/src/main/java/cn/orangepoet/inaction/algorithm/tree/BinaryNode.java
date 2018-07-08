package cn.orangepoet.inaction.algorithm.tree;

/**
 * Created by Orange on 16/8/25.
 */
public class BinaryNode {
    public BinaryNode(int x, BinaryNode left, BinaryNode right) {
        this.element = x;
        this.left = left;
        this.right = right;
    }

    BinaryNode left;
    BinaryNode right;
    int element;

    public void print(int depth) {
        System.out.println(getPadding(depth) + this.element);
        if (this.left != null)
            this.left.print(depth + 1);
        if (this.right != null)
            this.right.print(depth + 1);
    }

    private String getPadding(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("-");
        }
        return sb.toString();
    }

    public static BinaryNode prepareTree() {
        BinaryNode one = new BinaryNode(1, null, null);
        BinaryNode four = new BinaryNode(4, null, null);
        BinaryNode three = new BinaryNode(3, null, four);
        BinaryNode five = new BinaryNode(5, three, null);
        BinaryNode tow = new BinaryNode(2, one, five);
        BinaryNode eight = new BinaryNode(8, null, null);
        BinaryNode six = new BinaryNode(6, tow, eight);
        return six;
    }
}
