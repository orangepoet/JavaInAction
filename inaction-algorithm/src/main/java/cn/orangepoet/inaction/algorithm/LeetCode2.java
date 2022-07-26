package cn.orangepoet.inaction.algorithm;

import cn.orangepoet.inaction.algorithm.model.ListNode;
import cn.orangepoet.inaction.algorithm.model.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public ListNode deleteDuplicatesUnsorted(ListNode head) {
        Map<Integer, Integer> map = new HashMap<>();
        ListNode p = head;
        while (p != null) {
            map.compute(p.val, (k, v) -> v == null ? 1 : v + 1);
            p = p.next;
        }
        ListNode h = new ListNode(-1);
        ListNode prev = h;
        p = head;
        while (p != null) {
            ListNode next = p.next;
            if (map.get(p.val) == 1) {
                prev.next = p;
                p.next = null;
                prev = p;
            }
            p = next;
        }
        return h.next;
    }
}
