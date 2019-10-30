package cn.orangepoet.inaction.algorithm.dp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        int[] coinsValues = new int[]{1, 7, 9};
        long start = System.currentTimeMillis();
        int cnt = s.makeChange(coinsValues, 3000, new HashMap<>());
        long end = System.currentTimeMillis();
        System.out.println(cnt);
        System.out.println("elapsed: " + (end - start));


        start = System.currentTimeMillis();
        cnt = s.makeChange2(coinsValues, 3000);
        end = System.currentTimeMillis();
        System.out.println(cnt);
        System.out.println("elapsed2: " + (end - start));

    }

    /**
     * 合并两个有序数组
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;
        while (p1 >= 0 && p2 >= 0) {
            nums1[p--] = nums1[p1] > nums2[p2] ? nums1[p1--] : nums2[p2--];
        }
        System.arraycopy(nums2, 0, nums1, 0, p2 + 1);
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 判断树是否对称
     *
     * @param root
     * @return
     */
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

    /**
     * 硬币找零 (递归 + 备忘录)
     *
     * @param coins
     * @param n
     * @return
     */
    private int makeChange(int[] coins, int n, Map<Integer, Integer> retMap) {
        if (n == 0) {
            return 0;
        }
        // 记忆性搜索 (备忘录)
        if (retMap.containsKey(n)) {
            return retMap.get(n);
        }
        int minChange = Integer.MAX_VALUE;
        for (int coin : coins) {
            int subN;
            int subChange;
            if ((subN = n - coin) >= 0 && (subChange = makeChange(coins, subN, retMap)) != -1) {
                minChange = Math.min(minChange, subChange + 1);
            }
        }
        int ret = minChange == Integer.MAX_VALUE ? -1 : minChange;

        // 备忘本次计算结果
        retMap.put(n, ret);
        return minChange;
    }

    /**
     * 硬币找零 (动态规划)
     * <p>
     * 状态转移方程
     * <p>
     * 重复子问题
     *
     * @param coins
     * @param n
     * @return
     */
    private int makeChange2(int[] coins, int n) {
        // n -> ret mapping, 找零 n=0 需要0个硬币
        Map<Integer, Integer> changeRet = new HashMap<>();
        changeRet.put(0, 0);

        for (int amount = 1; amount <= n; amount++) {
            int minChange = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (amount >= coin) {
                    int subRet = changeRet.get(amount - coin);
                    minChange = Math.min(minChange, subRet + 1);
                }
            }
            changeRet.put(amount, minChange);
        }
        return changeRet.get(n);
    }

    /**
     * 组合数 和为n, 数量为k
     *
     * @param k
     * @param n
     * @return
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        if (k > 9) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int max = 1 << 9;
        for (int i = 1; i < max; i++) {
            List<Integer> combination = new ArrayList<>();
            int sum = 0;
            for (int j = 0; j < arr.length; j++) {
                int bit = 1 << j;
                if ((i & bit) == bit) {
                    sum += arr[j];
                    combination.add(arr[j]);
                }
            }
            if (sum == n && combination.size() == k) {
                result.add(combination);
                System.out.println(Integer.toBinaryString(i));
            }
        }

        return result;
    }

    /**
     * 爬楼算法
     *
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        return climbStairs0(n, new HashMap<>());
    }

    private int climbStairs0(int n, Map<Integer, Integer> stairCnt) {
        if (n <= 0) {
            throw new IllegalArgumentException("n is invalid");
        }
        if (stairCnt.containsKey(n)) {
            return stairCnt.get(n);
        }
        if (n == 1) {
            stairCnt.put(n, 1);
            return 1;
        } else if (n == 2) {
            stairCnt.put(n, 2);
            return 2;
        }
        int cnt = climbStairs(n - 1) + climbStairs(n - 2);
        stairCnt.put(n, cnt);
        return cnt;
    }

    /**
     * 最大子序列的和
     *
     * @param nums
     * @return
     */
    public static int getMax(int[] nums) {
        int maxSubSum = Integer.MIN_VALUE;
        int thisSubSum = 0;
        for (int i = 0; i < nums.length; i++) {
            thisSubSum += nums[i];
            if (nums[i] > thisSubSum) {
                thisSubSum = nums[i];
            }
            if (thisSubSum > maxSubSum) {
                maxSubSum = thisSubSum;
            }
        }
        return maxSubSum;
    }

    /**
     * 给定一个非空且只包含非负数的整数数组 nums, 数组的度的定义是指数组里任一元素出现频数的最大值。
     * <p>
     * 你的任务是找到与 nums 拥有相同大小的度的最短连续子数组，返回其长度
     *
     * @param nums
     * @return
     */
    public int findShortestSubArray(int[] nums) {
        Map<Integer, Integer> left = new HashMap<>();
        Map<Integer, Integer> right = new HashMap<>();
        Map<Integer, Integer> count = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (!left.containsKey(x)) {
                left.put(x, i);
            }
            right.put(x, i);
            count.put(x, count.getOrDefault(x, 0) + 1);
        }
        int degree = Collections.max(count.values());
        int ans = nums.length;
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            if (entry.getValue() == degree) {
                ans = Math.min(ans, right.get(entry.getKey()) - left.get(entry.getKey()) + 1);
            }
        }
        return ans;
    }

    public int countCharacters(String[] words, String chars) {
        int cnt = 0;
        Set<Character> charsSet = new HashSet<>();
        for (int i = 0; i < chars.length(); i++) {
            charsSet.add(chars.charAt(i));
        }
        HashSet<Character> charSet2;
        for (String word : words) {
            charSet2 = new HashSet<>(charsSet);
            boolean isContain = true;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                boolean remove = charSet2.remove(c);
                if (!remove) {
                    isContain = false;
                    break;
                }
            }
            if (isContain) {
                cnt++;
            }
        }
        return cnt;
    }
}
