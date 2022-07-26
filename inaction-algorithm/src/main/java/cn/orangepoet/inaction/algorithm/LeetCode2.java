package cn.orangepoet.inaction.algorithm;

import cn.orangepoet.inaction.algorithm.model.TreeNode;

import java.util.LinkedList;

public class LeetCode2 {

    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null || root.left == null) {
            return root;
        }
        LinkedList<TreeNode> stack = new LinkedList<>();
        dfs(stack, root);

        TreeNode p = null;
        TreeNode ret = null;
        boolean left = true;
        while (!stack.isEmpty()) {
            TreeNode n = stack.pop();
            if (n != null) {
                n.left = null;
                n.right = null;
            }
            if (p == null) {
                p = n;
                ret = p;
            } else {
                if (left) {
                    p.left = n;
                    left = false;
                } else {
                    p.right = n;
                    p = n;
                    left = true;
                }
            }
        }
        return ret;
    }

    private void dfs(LinkedList<TreeNode> stack, TreeNode root) {
        if (root == null) {
            return;
        }
        stack.push(root);
        stack.push(root.right);
        dfs(stack, root.left);
    }
}
