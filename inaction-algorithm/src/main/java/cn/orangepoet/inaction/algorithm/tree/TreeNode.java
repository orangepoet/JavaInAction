package cn.orangepoet.inaction.algorithm.tree;

import lombok.Getter;

/**
 * Created by Orange on 16/8/25.
 */
@Getter
public class TreeNode {
    TreeNode left;
    TreeNode right;
    Integer element;

    public TreeNode(Integer element) {
        this.element = element;
    }

    public TreeNode(int x, TreeNode left, TreeNode right) {
        this.element = x;
        this.left = left;
        this.right = right;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    /**
     * [1,2,3,4,5,6,7]
     *
     * @param elements
     * @return
     */
    public static TreeNode parse(Integer[] elements) {
        if (elements == null || elements.length == 0) {
            return null;
        }
        TreeNode root = null;
        int maxDeep = (int) (Math.log(elements.length + 1) / Math.log(2));
        for (int deep = 1; deep <= maxDeep; deep++) {
            TreeNode current;
            int index = (int) Math.pow(2, deep - 1);
            if (deep != maxDeep) {
                TreeNode left = null;
                TreeNode right = null;
                if (elements[index * 2 + 1] != null) {
                    left = new TreeNode(elements[index * 2 + 1]);
                }
                if (elements[index * 2 + 2] != null) {
                    right = new TreeNode(elements[index * 2 + 2]);
                }
                current = new TreeNode(elements[index], left, right);
            } else {
                current = new TreeNode(elements[index]);
            }

            if (deep == 1) {
                root = current;
            }
        }
        return root;
    }
}
