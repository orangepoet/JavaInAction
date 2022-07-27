package cn.orangepoet.inaction.algorithm;

import cn.orangepoet.inaction.algorithm.model.ListNode;
import cn.orangepoet.inaction.algorithm.model.Node;
import cn.orangepoet.inaction.algorithm.model.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    private int max = 0;
    private Map<Node, Integer> depthMap = new HashMap<>();

    public int diameter(Node root) {
        if (root == null) {
            return 0;
        }
        max = Math.max(max, diameter0(root));
        for (Node child : root.children) {
            diameter(child);
        }
        return max;
    }

    private int diameter0(Node root) {
        List<Node> children = root.children;
        if (children == null || children.size() == 0) {
            return 0;
        }
        if (children.size() == 1) {
            return depth0(children.get(0));
        }
        int size = root.children.size();
        int[] depths = new int[size];
        for (int i = 0; i < size; i++) {
            depths[i] = depth0(root.children.get(i));
        }

        Arrays.sort(depths);
        return depths[depths.length - 1] + depths[depths.length - 2];
    }

    private int depth0(Node node) {
        if (depthMap.containsKey(node)) {
            return depthMap.get(node);
        }
        if (node == null) {
            return 0;
        }
        int depth = 1;
        if (node.children != null) {
            for (Node child : node.children) {
                depth = Math.max(depth, depth0(child) + 1);
            }
        }
        depthMap.put(node, depth);
        return depth;
    }

    public void setZeroes(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        List<Integer> zCols = new ArrayList<>();
        List<Integer> zRows = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 0) {
                    zRows.add(i);
                    zCols.add(j);
                }
            }
        }
        for (int r : zRows) {
            for (int i = 0; i < cols; i++) {
                matrix[r][cols] = 0;
            }
        }
        for (int c : zCols) {
            for (int i = 0; i < rows; i++) {
                matrix[i][c] = 0;
            }
        }
    }

    public int primePalindrome(int n) {
        int num = n;
        while (true) {
            if (num == reverse(num) && isPrime(num)) {
                return num;
            }
            num++;
        }
    }

    public int reverse(int N) {
        int ans = 0;
        while (N > 0) {
            ans = 10 * ans + (N % 10);
            N /= 10;
        }
        return ans;
    }

    private boolean isPrime(int n) {
        double to = Math.sqrt(n);
        for (int i = 2; i <= to; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int reachNumber(int target) {
        return reachNumber0(0, 0, target);
    }

    private int reachNumber0(int i, int n, int target) {
        if (n == target) {
            return i;
        }
        i = i + 1;

        int a = Math.abs(target - n + i);
        int b = Math.abs(target - n - i);
        int n1 = a <= b ? (n - i) : (n + i);
        return reachNumber0(i, n1, target);
    }

    public boolean isPossibleDivide(int[] nums, int k) {
        Arrays.sort(nums);
        int count = 0;
        int len = nums.length;
        while (count < len) {
            int j = 0;
            int prev = -1;
            for (int i = 0; i < k; i++) {
                while (j < len && (nums[j] == 0 || nums[j] == prev)) {
                    j++;
                }
                if (j >= len) {
                    return false;
                }
                if (prev == -1 || nums[j] == prev + 1) {
                    prev = nums[j];
                    nums[j] = 0;
                    j++;
                    count++;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    //private int max = 0;
    public int longestConsecutive(TreeNode root) {
        int max0 = longestConsecutive0(-1, root, 0);
        max = Math.max(max, max0);

        if (root.left != null) {longestConsecutive(root.left);}
        if (root.right != null) {longestConsecutive(root.right);}
        return max;
    }

    private int longestConsecutive0(int prev, TreeNode node, int depth) {
        if (prev != -1 && node.val != prev + 1) {
            return depth;
        }
        depth = depth + 1;
        int ans = depth;
        if (node.left != null) {ans = Math.max(ans, longestConsecutive0(node.val, node.left, depth));}
        if (node.right != null) {ans = Math.max(ans, longestConsecutive0(node.val, node.right, depth));}
        return ans;
    }

    public int shortestBridge(int[][] grid) {
        int min = Integer.MAX_VALUE;
        int x = grid.length;
        int y = grid[0].length;
        boolean first = true;
        List<int[]> A = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (grid[i][j] == 1) {
                    if (first) {
                        dfs(grid, i, j, x, y, A);
                        first = false;
                    } else {
                        int bread = bread0(i, j, A) - 1;
                        min = Math.min(min, bread);
                    }
                }
            }
        }
        return min;
    }

    //标记2
    private void dfs(int[][] grid, int i, int j, int n, int m, List<int[]> A) {
        if (!(i >= 0 && i < n && j >= 0 && j < m)) {
            return;
        }
        if (grid[i][j] != 1) {
            return;
        }
        grid[i][j] = 0;
        A.add(new int[] {i, j});
        dfs(grid, i, j + 1, n, m, A);
        dfs(grid, i, j - 1, n, m, A);
        dfs(grid, i + 1, j, n, m, A);
        dfs(grid, i - 1, j, n, m, A);
    }

    private int bread0(int i, int j, List<int[]> A) {
        int path = Integer.MAX_VALUE;
        for (int[] p : A) {
            int p0 = Math.abs(i - p[0]) + Math.abs(j - p[1]);
            path = Math.min(path, p0);
        }
        return path;
    }
}
