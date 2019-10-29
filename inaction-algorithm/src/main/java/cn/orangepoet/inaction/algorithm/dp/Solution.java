package cn.orangepoet.inaction.algorithm.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;
        while (p1 >= 0 && p2 >= 0) {
            nums1[p--] = nums1[p1] > nums2[p2] ? nums1[p1--] : nums2[p2--];
        }
        System.arraycopy(nums2, 0, nums1, 0, p2 + 1);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
//        int[] nums1 = {1, 2, 3, 0, 0, 0};
//        int m = 3;
//        int[] nums2 = {2, 5, 6};
//        int n = 3;
//
//        new Solution().merge(nums1, m, nums2, n);
//        for (int i : nums1) {
//            System.out.println(i);
//        }
        List<List<Integer>> ret = s.combinationSum3(3, 7);
        for (List<Integer> integers : ret) {
            Arrays.toString(integers.toArray());
        }
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    public boolean isSymmetric(TreeNode root) {
        return isMirror(root, root);
    }

    private boolean isMirror(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return left.val == right.val
                && isMirror(left.right, right.left)
                && isMirror(left.left, right.right);
    }

    public int change(int amount, int[] coins) {
        int total = 0;
        for (int coin : coins) {
            if (amount > coin) {
                int ret = makeChange(amount - coin, coins);
                if (ret == 0) {
                    total++;
                }
            }
        }
        return total;
    }

    private int makeChange(int amount, int[] coins) {
        if (amount == 0) {
            return 1;
        }
        return 0;
    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        if (k > 9) {
            return Collections.emptyList();
        }
        Set<List<Integer>> result = new HashSet<>();
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int max = 1 << 9;
        for (int i = 1; i < max; i++) {
            List<Integer> combination = new ArrayList<>();
            int sum = 0;
            for (int j = 1; j <= arr.length; j = j << 1) {
                if ((i & j) == j) {
                    sum += arr[j - 1];
                    combination.add(arr[j - 1]);
                }
            }
            if (sum == n && combination.size() == k) {
                result.add(combination);
            }
        }

        return new ArrayList<>(result);
    }
}
