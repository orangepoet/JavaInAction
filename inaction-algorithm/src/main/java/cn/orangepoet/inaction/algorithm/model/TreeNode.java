package cn.orangepoet.inaction.algorithm.model;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int x) {
        val = x;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return this.val
            + "["
            + (this.left != null ? left.val : "null")
            + ", "
            + (this.right != null ? this.right.val : "null")
            + "]";
    }
}
