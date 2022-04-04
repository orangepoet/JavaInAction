package cn.orangepoet.inaction.algorithm.dp;

import javax.swing.plaf.metal.MetalIconFactory;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Solution {
    public static class TreeNode {
        int val;
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
    public int makeChange(int[] coins, int n, Map<Integer, Integer> retMap) {
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
    public int makeChange2(int[] coins, int n) {
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
            minChange = minChange == Integer.MAX_VALUE ? -1 : minChange;
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

    /**
     * 爬楼算法, 1阶1种(1步), 2阶2种(1步或2步), n>2 可分解为先1阶或先2阶的策略合, 等同于斐波那契数
     *
     * @param n
     * @param stairCnt
     * @return
     */
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
        int cnt = climbStairs0(n - 1, stairCnt) + climbStairs0(n - 2, stairCnt);
        stairCnt.put(n, cnt);
        return cnt;
    }

    /**
     * 最大子序列的和
     *
     * @param nums
     * @return
     */
    public static int getMaxSubSet(int[] nums) {
        int maxSubSum = Integer.MIN_VALUE;
        int thisSubSum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (thisSubSum < 0) {
                thisSubSum = 0;
            }
            thisSubSum += nums[i];
            if (thisSubSum > maxSubSum) {
                maxSubSum = thisSubSum;
            }
        }
        return maxSubSum;
    }

    /**
     * 斐波那契数列 (非递归解法)
     *
     * @param seq
     * @return result
     */
    public int fibonacci0(int seq) {
        if (seq < 0) {
            throw new IllegalArgumentException("seq");
        }

        int[] arr = new int[seq];
        for (int i = 0; i < seq; i++) {
            if (i == 0 || i == 1) {
                arr[i] = 1;
            } else {
                arr[i] = arr[i - 1] + arr[i - 2];
            }
        }
        return arr[seq - 1];
    }

    /**
     * 斐波那契数列(递归解法)
     *
     * @param seq
     * @return result
     */
    public int fibonacci1(int seq) {
        if (seq == 1 || seq == 2) {
            return 1;
        } else {
            return fibonacci1(seq - 1) + fibonacci1(seq - 2);
        }
    }

    /**
     * 给定一个非空且只包含非负数的整数数组nums, 数组的度的定义是指数组里任一元素出现频数的最大值。
     * <p>
     * 你的任务是找到与nums拥有相同大小的度的最短连续子数组，返回其长度
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

    /**
     * 树的最大深度
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ListNode listNode = (ListNode)o;
            if (val == listNode.val) {
                if (this.next == null) {
                    if (listNode.next == null) {
                        return true;
                    }
                    return false;
                }
                return this.next.equals(listNode.next);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }
    }

    /**
     * 查找目标数的开始和结束位置
     *
     * <p>
     * 1. 折半查找 找到目标数
     *
     * <p>
     * 2. 通过滑动窗口, 判断目标数位置的左右是否相同
     *
     * @param nums   升序的数组
     * @param target 目标数值
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        int low = -1;
        int high = -1;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (right + left) >> 1;
            if (target < nums[mid]) {
                right = mid - 1;
            } else if (target > nums[mid]) {
                left = mid + 1;
            } else {
                low = mid;
                high = mid;
                while (low - 1 >= 0 && nums[low - 1] == target) {
                    low--;
                }
                while (high + 1 < nums.length && nums[high + 1] == target) {
                    high++;
                }
                break;
            }
        }

        return new int[] {low, high};
    }

    /**
     * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。 candidates 中的数字可以无限制重复被选取。 说明：
     * <p>
     * 所有数字（包括 target）都是正整数。 解集不能包含重复的组合。
     *
     * <p>
     * 示例 1:
     * <p>
     * 输入: candidates = [2,3,6,7], target = 7, 所求解集为: [ [7], [2,2,3] ]
     * <p/>
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (target <= 0) {
            return Collections.emptyList();
        }
        Map<Integer, List<List<Integer>>> targetMap = new HashMap<>();
        targetMap.put(0, Collections.emptyList());

        for (int num = 1; num <= target; num++) {
            Set<List<Integer>> targetAns = new HashSet<>();

            for (int c : candidates) {
                int subNum = num - c;
                if (subNum >= 0 && targetMap.containsKey(subNum)) {
                    List<List<Integer>> subAns = targetMap.get(subNum);

                    List<Integer> integers;
                    if (subAns.isEmpty()) {
                        integers = new ArrayList<>();
                        integers.add(c);
                        targetAns.add(integers);
                    } else {
                        for (List<Integer> each : subAns) {
                            integers = new ArrayList<>();
                            integers.addAll(each);
                            integers.add(c);
                            Collections.sort(integers);
                            targetAns.add(integers);
                        }
                    }
                }
            }
            if (!targetAns.isEmpty()) {
                targetMap.put(num, new ArrayList<>(targetAns));
            }
        }
        //TODO: 时间复杂度高, 需要优化
        return targetMap.getOrDefault(target, Collections.emptyList());
    }

    /**
     * 给定一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，使得a + b + c = 0 ？找出所有满足条件且不重复的三元组。
     * <p>
     * 答案中不可以包含重复的三元组。
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();

        Arrays.sort(nums);

        Integer last = null;
        for (int i = 0; i < nums.length; i++) {
            if (last != null && nums[i] == last) {
                continue;
            }
            last = nums[i];
            if (nums[i] > 0) {
                break;
            }

            int L = i + 1;
            int R = nums.length - 1;

            while (L < R) {
                while (L < R && nums[i] + nums[L] + nums[R] < 0) {
                    L++;
                }
                while (L < R && nums[i] + nums[L] + nums[R] > 0) {
                    R--;
                }
                if (L < R && nums[i] + nums[L] + nums[R] == 0) {
                    ans.add(Arrays.asList(nums[i], nums[L], nums[R]));
                }
                L++;
                while (L < R && nums[L] == nums[L - 1]) {
                    L++;
                }
            }
        }

        return ans;
    }

    /**
     * 给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。
     *
     * @param root
     * @param sum
     * @return
     */
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        if (root == null) {
            return null;
        }
        if (root.val == sum) {
            return Collections.singletonList(Collections.singletonList(root.val));
        }
        if (root.left == null || root.right == null) {
            return null;
        }
        if (root.val > sum) {
            return null;
        }

        List<List<Integer>> result = new ArrayList<>();
        List<List<Integer>> subAns = pathSum(root.left, sum - root.val);
        if (subAns != null) {
            for (List<Integer> subAn : subAns) {
                subAn.add(root.val);
            }
            result.addAll(subAns);
        }
        List<List<Integer>> subAns2 = pathSum(root.right, sum - root.val);
        if (subAns2 != null) {
            for (List<Integer> each : subAns2) {
                each.add(root.val);
            }
            result.addAll(subAns2);
        }

        return result;
    }

    /**
     * 数组是否单调
     *
     * @param A
     * @return
     */
    public boolean isMonotonic(int[] A) {
        int lastCompare = 0;
        for (int i = 1; i <= A.length - 1; i++) {
            int compare = Integer.compare(A[i], A[i - 1]);
            if (compare == 0) {
                continue;
            }
            if (lastCompare == 0) {
                lastCompare = compare;
            } else if (lastCompare != compare) {
                return false;
            }

        }
        return true;
    }

    /**
     * 给定一个树，按中序遍历重新排列树，使树中最左边的结点现在是树的根，并且每个结点没有左子结点，只有一个右子结点。
     *
     * @param
     * @return
     */
    //    public TreeNode increasingBST(TreeNode root) {
    //        if (root == null) {
    //            return root;
    //        }
    //        Stack<TreeNode> treeNodes = new Stack<>();
    //
    //
    ////        List<Integer> values = new ArrayList<>();
    //        TreeNode current = root;
    //        while (current != null) {
    //            if (current.right != null)
    //                treeNodes.push(current.right);
    //            treeNodes.push(current);
    ////            if (current.left != null)
    ////                treeNodes.push(current.left);
    //
    //            current = current.left;
    //        }
    //
    //        while (current != null) {
    //
    //        }
    //    }

    /**
     * 判断数独是否合理
     *
     * @param board
     * @return
     */
    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    continue;
                }
                boolean isDuplicate = isNumDuplicate(board, i, j);
                if (isDuplicate) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 数组分组后的最大值 (分割为K个), 分组后的每个成员变成组中最大那个
     *
     * @param A
     * @param K
     * @return
     */
    public int maxSumAfterSplit(int[] A, int K) {
        List<Integer> topK = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            updateTopK(topK, A[i], K);
        }
        int topKTail = topK.get(0);
        int prev = -1;
        int ans = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] >= topKTail) {
                if (prev == -1) {
                    ans += i * A[i];
                    prev = i;
                } else {
                    ans += A[prev] + Math.max(A[i], A[prev]) * (i - prev - 1);
                    prev = i;
                }
            }
        }
        if (prev != -1) {
            ans += (A.length - prev) * A[prev];
        }
        return ans;
    }

    private void updateTopK(List<Integer> topK, int num, int maxSize) {
        if (topK.isEmpty()) {
            topK.add(num);
            return;
        }
        if (num < topK.get(0)) {
            return;
        }

        int index = 0;
        for (; index < topK.size(); index++) {
            if (num <= topK.get(index)) {
                break;
            }
        }
        if (topK.size() < maxSize) {
            topK.add(index, num);
        } else {
            topK.add(index, num);
            topK.remove(0);
        }
    }

    /**
     * 给出整数数组 A，将该数组分隔为长度最多为 K 的几个（连续）子数组。分隔完成后，每个子数组的中的值都会变为该子数组中的最大值。
     *
     * @param A
     * @param K
     * @return
     */
    public int maxSumAfterPartitioning(int[] A, int K) {
        int len = A.length;
        int[] dp = new int[len];
        for (int i = 0; i < len; i++) {
            /* 分别计算最后一段区间长度 j ∈[1, K]时的解，并更新位置i时的最优解 */
            int domainMax = A[i];
            for (int j = 1; j <= K && i - j + 1 >= 0; j++) {
                domainMax = Math.max(domainMax, A[i - j + 1]);
                if (i - j >= 0) {
                    dp[i] = Math.max(dp[i], dp[i - j] + j * domainMax);
                } else {
                    dp[i] = Math.max(dp[i], j * domainMax);
                }
            }
        }
        return dp[len - 1];
    }

    private boolean isNumDuplicate(char[][] board, int i, int j) {
        int num = board[i][j];
        int gX = i / 3 * 3;
        int gY = j / 3 * 3;
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                if (!(i == (gX + m) && j == (gY + n)) && (int)board[gX + m][gY + n] == num) {
                    return true;
                }
            }
        }

        for (int k = 0; k < 9; k++) {
            if (k != i && (int)board[k][j] == num) {
                return true;
            }
            if (k != j && (int)board[i][k] == num) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字母移位
     *
     * @param S
     * @param shifts
     * @return
     */
    public String shiftingLetters(String S, int[] shifts) {
        int size = shifts.length;
        long[] shifts2 = new long[size];
        shifts2[size - 1] = shifts[size - 1];

        for (int i = size - 2; i >= 0; i--) {
            shifts2[i] = shifts[i] + shifts2[i + 1];
        }
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if (i < shifts2.length) {
                int index = (int)c - 97;
                int newIndex = (int)((index + shifts2[i]) % 26);
                c = (char)(newIndex + 97);
            }
            ans.append(c);
        }
        return ans.toString();
    }

    /**
     * 字母模式匹配
     *
     * @param pattern
     * @param str
     * @return
     */
    public boolean wordPattern(String pattern, String str) {
        if (pattern == null || pattern.length() == 0 || str == null || str.length() == 0) {
            return false;
        }
        String[] parts = str.split(" ");
        if (parts.length != pattern.length()) {
            return false;
        }
        Map<Character, String> patternMap = new HashMap<>();

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            char c = pattern.charAt(i);
            if (patternMap.containsKey(c)) {
                if (!patternMap.get(c).equals(part)) {
                    return false;
                }
            } else {
                if (patternMap.containsValue(part)) {
                    return false;
                }
                patternMap.put(c, part);
            }
        }
        return true;
    }

    /**
     * 分糖果
     *
     * @param candies
     * @return
     */
    public int distributeCandies(int[] candies) {
        if (candies == null || candies.length == 0) {
            return 0;
        }
        Set<Integer> candySet = new HashSet<>();
        for (int candy : candies) {
            candySet.add(candy);
        }
        return Math.min(candySet.size(), candies.length / 2);
    }

    /**
     * 猜数字
     *
     * @param secret
     * @param guess
     * @return
     */
    public String getHint(String secret, String guess) {
        int a = 0;
        int b = 0;
        int[] guessNum = new int[10];
        int[] secretNum = new int[10];
        for (int i = 0; i < secret.length(); i++) {
            char tmp = secret.charAt(i);
            if (tmp == guess.charAt(i)) {
                a++;
            } else {
                guessNum[guess.charAt(i) - '0'] += 1;
                secretNum[tmp - '0'] += 1;
            }
        }
        for (int i = 0; i < 10; i++) {
            b += Math.min(guessNum[i], secretNum[i]);
        }
        return a + "A" + b + "B";
    }

    /**
     * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回该数组中和为 k 的连续子数组的个数。
     * <p>
     *
     * @param nums 原始数组
     * @param k    子数组的和
     * @return 子数组的个数
     */
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int sum = 0;
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k)) {
                ans += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }

    public boolean repeatedSubstringPattern(String s) {
        int half = s.length() / 2;
        boolean ans;
        for (int i = 1; i <= half; i++) {
            if ((s.length() % i) != 0) {
                continue;
            }
            ans = true;
            for (int j = 0, k = j + i; k < s.length(); j++, k++) {
                if (s.charAt(j) != s.charAt(k)) {
                    ans = false;
                    break;
                }
            }
            if (ans) {
                return true;
            }
        }
        return false;
    }

    public String intToRoman(int num) {
        return resolve(num, 0);
    }

    private String resolve(int num, int position) {
        if (num < 10) {
            if (num == 0) {
                return "";
            }
            if (position == 0) {
                switch (num) {
                    case 1:
                        return "I";
                    case 2:
                        return "II";
                    case 3:
                        return "III";
                    case 4:
                        return "IV";
                    case 5:
                        return "V";
                    case 6:
                        return "VI";
                    case 7:
                        return "VII";
                    case 8:
                        return "VIIII";
                    case 9:
                        return "IX";
                    default:
                        return "";
                }
            } else if (position == 1) {
                switch (num) {
                    case 1:
                        return "X";
                    case 2:
                        return "XX";
                    case 3:
                        return "XXX";
                    case 4:
                        return "XL";
                    case 5:
                        return "L";
                    case 6:
                        return "LX";
                    case 7:
                        return "LXX";
                    case 8:
                        return "LXXX";
                    case 9:
                        return "XC";
                    default:
                        return "";
                }
            } else if (position == 2) {
                switch (num) {
                    case 1:
                        return "C";
                    case 2:
                        return "CC";
                    case 3:
                        return "CCC";
                    case 4:
                        return "CD";
                    case 5:
                        return "D";
                    case 6:
                        return "DC";
                    case 7:
                        return "DCC";
                    case 8:
                        return "DCCC";
                    case 9:
                        return "CM";
                    default:
                        return "";
                }
            } else if (position == 3) {
                switch (num) {
                    case 1:
                        return "M";
                    case 2:
                        return "MM";
                    case 3:
                        return "MMM";
                    case 4:
                        return "MMMM";
                    default:
                        return "";
                }
            } else {
                return "";
            }
        } else {
            int rest = num % 10;
            return resolve(num / 10, position + 1) + resolve(rest, position);
        }
    }

    /**
     * 给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
     * <p>
     * 示例:
     * <p>
     * 输入:[1,2,3,null,5,null,4] 输出:[1, 3, 4] 解释:
     * <p>
     * 1            <--- /   \ 2     3         <--- \     \ 5     4       <---
     * <p>
     *
     * @param root
     * @return
     */
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Integer> ans = new ArrayList<>();

        List<TreeNode> lst = new ArrayList<>();
        lst.add(root);

        while (!lst.isEmpty()) {
            ans.add(lst.get(0).val);

            List<TreeNode> lst2 = new ArrayList<>();
            for (TreeNode treeNode : lst) {
                if (treeNode.right != null) {
                    lst2.add(treeNode.right);
                }
                if (treeNode.left != null) {
                    lst2.add(treeNode.left);
                }
            }
            lst = lst2;
        }
        return ans;
    }

    /**
     * 有效的括号
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        Map<Character, Character> map = new HashMap<Character, Character>() {{
            put('?', '?');
            put('{', '}');
            put('(', ')');
            put('[', ']');
        }};
        LinkedList<Character> stack = new LinkedList<Character>() {{
            add('?');
        }};
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // 左进栈
            if (map.containsKey(c)) {
                stack.push(c);
            } else {
                Character c1 = stack.pop();
                if (map.get(c1) != c) {
                    return false;
                }
            }
        }
        return stack.size() == 1;
    }

    /**
     * 大小为K且平均值大于等于阈值的子数组数目
     * <p>
     * 滑动窗口
     *
     * @param arr
     * @param k
     * @param threshold
     * @return
     */
    public int numOfSubarrays(int[] arr, int k, int threshold) {
        int nums = 0;

        int sum = 0;
        for (int i = 0, j = i + k - 1; j < arr.length; i++, j++) {
            if (i == 0) {
                for (int m = i; m <= j; m++) {
                    sum += arr[m];
                }
            } else {
                sum -= arr[i - 1];
                sum += arr[j];
            }
            if (sum / k >= threshold) {
                nums++;
            }
        }
        return nums;
    }

    /**
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     * <p>
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     * <p>
     * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * <p>
     * <p>
     * 示例:
     *
     * <p>
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * </p>
     * <p>
     * 输出：7 -> 0 -> 8
     * </p>
     * <p>
     * 原因：342 + 465 = 807
     * </p>
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        List<ListNode> nodes = new ArrayList<>();

        int exceed = 0;
        while (l1 != null || l2 != null) {
            int sum = 0;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            if (exceed > 0) {
                sum += exceed;
                exceed = 0;
            }

            if (sum >= 10) {
                sum -= 10;
                exceed = 1;
            }
            ListNode node = new ListNode(sum);
            nodes.add(node);
        }
        if (exceed > 0) {
            nodes.add(new ListNode(exceed));
        }
        for (int i = 0; i < nodes.size() - 1; i++) {
            nodes.get(i).next = nodes.get(i + 1);
        }
        return nodes.get(0);
    }

    /**
     * 无重复字符的最长子串
     * <p>
     * 解法: 滑动窗口
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        int max = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int start = 0, end = 0; end < s.length(); end++) {
            char c = s.charAt(end);
            if (map.containsKey(c)) {
                start = Math.max(start, map.get(c));
            }
            max = Math.max(max, end - start + 1);

            map.put(c, end + 1);
        }

        return max;
    }

    /**
     * 判断树是否平衡
     *
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }

        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        return Math.abs(leftHeight - rightHeight) <= 1
            && isBalanced(root.left)
            && isBalanced(root.right);
    }

    private int height(TreeNode treeNode) {
        if (treeNode == null) {
            return 0;
        }
        int deep = Math.max(height(treeNode.left), height(treeNode.right)) + 1;
        return deep;
    }

    /**
     * 邻近的重复数 (重复数nums[i]和nums[j], j和i的距离<=k
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> numPos = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (numPos.containsKey(num)) {
                Integer dupPos = numPos.get(num);
                if (i != dupPos && i - dupPos <= k) {
                    return true;
                }
            }
            numPos.put(num, i);
        }
        return false;
    }

    /**
     * 交叉点
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode pA = headA;
        ListNode pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }

    /**
     * 判断IP地址
     *
     * @param IP
     * @return
     */
    public static String validIPAddress(String IP) {
        if (IP == null || IP.length() == 0) {
            return "Neither";
        }
        if (IP.contains(".")) {
            String[] segments = IP.split("\\.", -1);
            if (segments.length != 4) {
                return "Neither";
            }
            Set<String> set = new HashSet<>();
            for (int j = 0; j <= 9; j++) {
                set.add(String.valueOf(j));
            }

            for (int i = 0; i < segments.length; i++) {
                String seg = segments[i];
                if (seg.length() == 0 || seg.length() > 4) {
                    return "Neither";
                }
                if (seg.length() > 1 && seg.startsWith("0")) {
                    return "Neither";
                }
                for (int j = 0; j < seg.length(); j++) {
                    char c = seg.charAt(j);
                    if (!set.contains(String.valueOf(c))) {
                        return "Neither";
                    }
                }

                try {
                    int num = Integer.parseInt(seg);
                    if (num < 0 || num > 255) {
                        return "Neither";
                    }
                } catch (NumberFormatException e) {
                    return "Neither";
                }
            }
            return "IPv4";
        } else if (IP.contains(":")) {
            String[] segments = IP.split(":", -1);
            if (segments.length != 8) {
                return "Neither";
            }
            Set<Character> cSet = new HashSet(Arrays.asList(
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F'
            ));
            for (int i = 0; i < segments.length; i++) {
                String seg = segments[i];
                if (seg.length() == 0 || seg.length() > 4) {
                    return "Neither";
                }
                for (int j = 0; j < seg.length(); j++) {
                    if (!cSet.contains(seg.charAt(j))) {
                        return "Neither";
                    }
                }
            }
            return "IPv6";
        }
        return "Neither";
    }

    /**
     * 求和 (递归)
     *
     * @param nums
     * @param S
     * @return
     */
    public int findTargetSumWays(int[] nums, int S) {
        if (nums.length == 0) {
            return S == 0 ? 1 : 0;
        }
        int cnt = 0;

        int[] subNums = new int[nums.length - 1];
        System.arraycopy(nums, 1, subNums, 0, subNums.length);
        cnt += findTargetSumWays(subNums, S - nums[0]);
        cnt += findTargetSumWays(subNums, S + nums[0]);

        return cnt;
    }

    /**
     * 求和 (动规)
     * <p>
     * S可能是负数, 最大-1000
     *
     * @param nums
     * @param S
     * @return
     */
    public int findTargetSumWays1(int[] nums, int S) {
        // 只计算数组和最大1000内的动态规划
        if (S > 1000 || S < -1000) {
            return 0;
        }
        // x: 前N个数, y: 计算和 从0到2001
        int[][] dp = new int[nums.length][2001];
        for (int j = -1000; j <= 1000; j++) {
            int ans = 0;
            if (nums[0] == j) {
                ans += 1;
            }
            if (nums[0] == -j) {
                ans += 1;
            }
            dp[0][j + 1000] = ans;
        }
        for (int i = 1; i < nums.length; i++) {
            for (int j = -1000; j <= 1000; j++) {
                int k = j + 1000;
                if (k - nums[i] >= 0 && k - nums[i] <= 2000) {
                    dp[i][k] += dp[i - 1][k - nums[i]];
                }
                if (k + nums[i] >= 0 && k + nums[i] <= 2000) {
                    dp[i][k] += dp[i - 1][k + nums[i]];
                }
            }
        }
        return dp[nums.length - 1][S + 1000];
    }

    /**
     * 搜索单词, 返回匹配的 字母排序的top3
     *
     * @param products
     * @param searchWord
     * @return
     */
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);
        boolean[][] dp = new boolean[searchWord.length()][products.length];
        for (int i = 0; i < searchWord.length(); i++) {
            for (int j = 0; j < products.length; j++) {
                if (i == 0) {
                    dp[i][j] = products[j].length() > i && products[j].charAt(i) == searchWord.charAt(i);
                } else {
                    dp[i][j] = dp[i - 1][j] && products[j].length() > i && products[j].charAt(i) == searchWord.charAt(i);
                }
            }
        }

        List<List<String>> ans = new ArrayList<>();
        for (int i = 0; i < dp.length; i++) {
            List<String> matchedProducts = new ArrayList<>();
            for (int j = 0; j < products.length; j++) {
                if (dp[i][j]) {
                    matchedProducts.add(products[j]);
                    if (matchedProducts.size() >= 3) {
                        break;
                    }
                }
            }
            ans.add(matchedProducts);
        }
        return ans;
    }

    /**
     * 给定两个整数，分别表示分数的分子 numerator 和分母 denominator，以字符串形式返回小数。
     * <p>
     * 如果小数部分为循环小数，则将循环的部分括在括号内。
     *
     * @param numerator
     * @param denominator
     * @return
     */
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) {
            return "0";
        }
        if (denominator == 0) {
            throw new IllegalArgumentException("denominator is invalid");
        }

        Map<Long, Integer> numeratorMap = new HashMap<>();
        StringBuilder ans = new StringBuilder();
        boolean isFirst = true;
        long numerator0 = numerator;
        long denominator0 = denominator;

        numerator0 = Math.abs(numerator0);
        denominator0 = Math.abs(denominator0);
        boolean isNegative = numerator == numerator0 ^ denominator == denominator0;
        while (true) {
            long ret = numerator0 / denominator0;
            long mod = numerator0 % denominator0;

            ans.append(ret);
            if (mod == 0) {
                break;
            }

            if (isFirst) {
                ans.append(".");
                isFirst = false;
            }
            if (numeratorMap.containsKey(mod)) {
                int start = numeratorMap.get(mod);
                ans.insert(start, "(");
                ans.append(")");
                break;
            }
            numeratorMap.put(mod, ans.length());
            numerator0 = mod * 10;
        }
        if (isNegative) {
            ans.insert(0, "-");
        }
        return ans.toString();
    }

    /**
     * 最长重复子字符串
     *
     * @param s
     * @return
     */
    public String longestDupSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        Map<Character, List<Integer>> characterPositions = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (characterPositions.containsKey(c)) {
                characterPositions.get(c).add(i);
            } else {
                List<Integer> positions = new ArrayList<>();
                positions.add(i);
                characterPositions.put(c, positions);
            }
        }

        Set<String> dups = new HashSet<>();

        for (Map.Entry<Character, List<Integer>> entry : characterPositions.entrySet()) {
            if (entry.getValue() == null || entry.getValue().size() <= 1) {
                continue;
            }
            String maxDup = findMaxDup(s, entry.getValue());
            dups.add(maxDup);
        }
        return dups.stream().max(Comparator.comparingInt(String::length)).orElse("");
    }

    private String findMaxDup(String s, List<Integer> positions) {
        if (positions == null || positions.size() <= 1) {
            return "";
        }
        Set<String> dups = new HashSet<>();
        for (int i = 0; i < positions.size() - 1; i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                String dup = getMaxDupSubStr(s, positions.get(i), positions.get(j));
                dups.add(dup);
            }
        }
        return dups.stream().max(Comparator.comparingInt(String::length)).orElse("");
    }

    private String getMaxDupSubStr(String s, int left, int right) {
        int dupOffset = 0;
        while (right + dupOffset <= s.length() - 1 && s.charAt(left + dupOffset) == s.charAt(right + dupOffset)) {
            dupOffset++;
        }
        return s.substring(left, left + dupOffset);
    }

    /**
     * 删列造序
     *
     * @param A
     * @return
     */
    public int minDeletionSize(String[] A) {
        if (A == null || A.length <= 0) {
            return 0;
        }

        int ans = 0;
        int[] weight = new int[A.length];
        for (int i = 0; i < A[0].length(); i++) {
            boolean isSorted = true;
            for (int j = 1; j < A.length; j++) {
                if (weight[j] == weight[j - 1] && A[j].charAt(i) < A[j - 1].charAt(i)) {
                    ans++;
                    isSorted = false;
                    break;
                }
            }
            if (isSorted) {
                for (int j = 0; j < A.length; j++) {
                    weight[j] = weight[j] * 26 + (int)A[j].charAt(i);
                }
            }
        }
        return ans;
    }

    /**
     * 后续遍历 (递归算法)
     *
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();

        if (root.left != null) {
            ans.addAll(postorderTraversal(root.left));
        }
        if (root.right != null) {
            ans.addAll(postorderTraversal(root.right));
        }
        ans.add(root.val);
        return ans;
    }

    /**
     * 后续遍历 (非递归算法)
     *
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal1(TreeNode root) {
        List<Integer> ans = new LinkedList<>();
        if (root == null) {
            return ans;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode treeNode = stack.pop();
            ans.add(0, treeNode.val);
            if (treeNode.left != null) {
                stack.push(treeNode.left);
            }
            if (treeNode.right != null) {
                stack.push(treeNode.right);
            }
        }
        return ans;
    }

    public boolean splitArraySameAverage(int[] A) {
        Arrays.sort(A);

        int sum = 0;
        for (int i = 0; i < A.length; i++) {
            sum += A[i];
        }

        double avg = sum * 1.0 / A.length;

        int mid = 0;
        int left = 0;
        int right = A.length - 1;
        for (; mid < A.length; mid++) {
            if (A[mid] == avg) {
                left = mid - 1;
                right = mid + 1;
                break;
            } else if (A[mid] > avg) {
                left = mid - 1;
                right = mid;
                break;
            }

        }
        List<Integer> subSet = new ArrayList<>();
        while (left > 0 && right < A.length - 1) {
            if (!subSet.isEmpty()) {
                double avg0 = getAvg(subSet);
                if (avg0 < avg) {
                    subSet.add(A[right++]);
                } else if (avg0 > avg) {
                    subSet.add(A[left--]);
                } else {
                    subSet.clear();
                }
            } else {
                subSet.add(A[left--]);
                subSet.add(A[right++]);
            }
        }
        while (left > 0) {
            subSet.add(A[left--]);
        }
        while (right < A.length - 1) {
            subSet.add(A[right++]);
        }
        return subSet.isEmpty() || getAvg(subSet) == avg;
    }

    private double getAvg(List<Integer> lst) {
        if (lst.isEmpty()) {
            return 0;
        }
        int sum = 0;
        for (int i : lst) {
            sum += i;
        }
        return sum * 1.0 / lst.size();
    }

    /**
     * 计算四因数, 返回该数组中恰有四个因数的这些整数的各因数之和。
     *
     * @param nums
     * @return
     */
    public int sumFourDivisors(int[] nums) {
        int sum = 0;

        for (int num : nums) {

            int factorNum = 0;
            int factorSum = 0;
            for (int i = 1; i * i <= num; i++) {
                if (num % i == 0) {
                    factorNum++;
                    factorSum += i;

                    if (i * i != num) {
                        factorNum++;
                        factorSum += num / i;
                    }
                    if (factorNum > 4) {
                        break;
                    }
                }
            }
            if (factorNum == 4) {
                sum += factorSum;
            }

        }
        return sum;
    }

    /**
     * 合并区间
     *
     * @param intervals
     * @return
     */
    public int[][] merge0(int[][] intervals) {
        if (intervals == null) {
            return null;
        }
        if (intervals.length == 1) {
            return intervals;
        }

        int m = -1;
        int[][] ans = new int[intervals.length][2];

        int[] lastRange = null;
        for (int i = 0; i < intervals.length; i++) {
            if (lastRange == null) {
                lastRange = intervals[i];
                ans[++m] = lastRange;
            } else {
                int[] current = intervals[i];
                if ((current[0] <= lastRange[1] && current[1] >= lastRange[1])
                    || (current[0] <= lastRange[0] && current[1] >= lastRange[0])) {
                    lastRange[1] = Math.max(current[1], lastRange[1]);
                    lastRange[0] = Math.min(current[0], lastRange[0]);
                } else {
                    lastRange = current;
                    ans[++m] = lastRange;
                }
            }
        }

        int[][] ans0 = new int[m + 1][2];
        System.arraycopy(ans, 0, ans0, 0, m + 1);
        return ans0;
    }

    public int[][] generateMatrix(int n) {
        int[][] ans = new int[n][n];
        int left = 0;
        int right = n - 1;
        int top = 0;
        int bottom = n - 1;

        int num = 1;
        int max = n * n;

        while (num <= max) {
            for (int x = left; x <= right; x++) {
                ans[top][x] = num++;
            }
            top++;
            for (int y = top; y <= bottom; y++) {
                ans[y][right] = num++;
            }
            right--;
            for (int x = right; x >= left; x--) {
                ans[bottom][x] = num++;
            }
            bottom--;
            for (int y = bottom; y >= top; y--) {
                ans[y][left] = num++;
            }
            left++;

        }

        return ans;
    }

    /**
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        String path = "";
        appendBrack(ans, n, n, path);
        return ans;
    }

    private void appendBrack(List<String> ans, int left, int right, String path) {
        if (left == 0 && right == 0) {
            ans.add(path);
            return;
        }
        if (left > 0) {
            appendBrack(ans, left - 1, right, path + "(");
        }
        if (left < right) {
            appendBrack(ans, left, right - 1, path + ")");
        }
    }

    /**
     * 交换链表中的元素
     *
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        //  swap dummy  prev

        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode prev = dummy;

        while (head != null && head.next != null) {
            ListNode first = head;
            ListNode second = head.next;

            prev.next = second;
            first.next = second.next;
            second.next = first;

            prev = first;
            head = first.next;
        }
        return dummy.next;
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> list = new LinkedList<>();
        Arrays.sort(candidates);//先排序
        backtrack(list, new ArrayList<>(), candidates, target, 0);
        return list;
    }

    private void backtrack(List<List<Integer>> list, List<Integer> cur, int[] candidates, int target, int start) {
        if (target == 0) {
            list.add(new ArrayList<>(cur));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (target < candidates[i]) {break;}
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue; //去掉重复的
            }
            cur.add(candidates[i]);
            backtrack(list, cur, candidates, target - candidates[i], i + 1);
            cur.remove(cur.size() - 1);
        }
    }

    public int firstMissingPositive(int[] nums) {
        int max = 0;
        Set<Integer> numSet = new HashSet<>();
        for (int i : nums) {
            if (i <= 0) {
                continue;
            }

            if (i > max) {
                max = i;
            }

            numSet.add(i);
        }

        for (int i = 1; i < max; i++) {
            if (!numSet.contains(i)) {
                return i;
            }
        }
        return max + 1;
    }

    public String countAndSay(int n) {
        String str = "1";
        for (int i = 0; i < n - 1; i++) {
            int lastP = -1;
            char p = '0';

            StringBuilder sb = new StringBuilder();

            int j = 0;
            for (; j < str.length(); j++) {
                char c = str.charAt(j);

                if (p != c) {
                    if (p != '0') {
                        sb.append(j - lastP).append(p);
                    }

                    p = c;
                    lastP = j;
                }
            }
            if (lastP < j) {
                sb.append(j - lastP).append(p);
            }
            str = sb.toString();
            System.out.println(str);
        }
        return str;
    }

    //public int strStr(String haystack, String needle) {
    //    if (needle.isEmpty()) {
    //        return 0;
    //    }
    //
    //    char first = needle.charAt(0);
    //    for (int i = 0; i < haystack.length(); i++) {
    //        if (haystack.charAt(i) == first) {
    //            boolean isMatched = true;
    //            int p = i;
    //            for (int j = 0; j < needle.length(); j++) {
    //                if (p >= haystack.length()) {
    //                    isMatched = false;
    //                    break;
    //                }
    //                if (haystack.charAt(p) != needle.charAt(j)) {
    //                    isMatched = false;
    //                    break;
    //                }
    //                p++;
    //            }
    //            if (isMatched) {
    //                return i;
    //            }
    //        }
    //    }
    //    return -1;
    //}

    public int strStr(String haystack, String needle) {
        int needleLength = needle.length();
        int haystackLength = haystack.length();

        if (needleLength > haystackLength) {
            return -1;
        }

        if (needleLength == 0 && haystackLength == 0) {
            return 0;
        }

        for (int i = 0; i < haystackLength; i++) {
            int scanIdx = 0;

            boolean matched = true;
            while (scanIdx < needleLength) {
                if (haystack.charAt(i + scanIdx) != needle.charAt(scanIdx)) {
                    matched = false;
                    break;
                }
                scanIdx++;
            }
            if (matched) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 回文字
     */
    public String longestPalindrome(String s) {
        Map<Character, List<Integer>> charMap = new HashMap<>();
        int maxLength = Integer.MIN_VALUE;
        int start = 0;
        int end = 0;
        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            List<Integer> idx = charMap.get(c);
            if (idx == null) {
                idx = new ArrayList<>();
                idx.add(j);
                charMap.put(c, idx);
            } else {
                for (int i : idx) {
                    if (longestPalindrome0(i, j, s)) {
                        int length0 = j - i;
                        if (length0 > maxLength) {
                            start = i;
                            end = j;
                            maxLength = length0;
                            break;
                        }
                    }
                }
                idx.add(j);
            }
        }
        return s.substring(start, end + 1);
    }

    private boolean longestPalindrome0(int start, int end, String s) {
        for (int i = start, j = end; i <= j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode first = head;
        ListNode p = null;
        ListNode prev = null;
        for (int i = 0; i < n; i++) {
            prev = p;
            p = head.next;
        }
        if (p != null && prev != null) {
            prev.next = p.next;
        }
        return first;
    }

    public int numDecodings(String s) {
        if (s.length() == 0 || s.charAt(0) == '0') {
            return 0;
        }
        return comb(s);

    }

    private int comb(String s) {
        if (s == null || s.length() == 0) {
            return 1;
        }
        if (s.charAt(0) == '0') {
            return 0;
        }

        if (s.length() >= 2) {
            char first = s.charAt(0);
            char second = s.charAt(1);
            if (first > '2' || (first == '2' && second > '6')) {
                return comb(s.substring(1));
            } else {
                return comb(s.substring(1)) + comb(s.substring(2));
            }
        }
        return comb(s.substring(1));
    }

    /**
     * 螺旋矩阵
     * <p>
     * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
     * <p>
     * <p>
     * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]] 输出：[1,2,3,6,9,8,7,4,5]
     *
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return new ArrayList<>();
        }

        int left = 0, right = matrix[0].length - 1;
        int top = 0, bottom = matrix.length - 1;

        List<Integer> ans = new ArrayList<>();
        while (left < right && top < bottom) {
            for (int i = left; i < right; i++) {
                ans.add(matrix[top][i]);
            }
            for (int i = top; i < bottom; i++) {
                ans.add(matrix[i][right]);
            }
            for (int i = right; i > left; i--) {
                ans.add(matrix[bottom][i]);
            }
            for (int i = bottom; i > top; i--) {
                ans.add(matrix[i][left]);
            }
            left++;
            right--;
            top++;
            bottom--;
        }

        if (top == bottom) {
            for (int i = left; i <= right; i++) {
                ans.add(matrix[top][i]);
            }
        } else if (left == right) {
            for (int i = top; i <= bottom; i++) {
                ans.add(matrix[i][top]);
            }
        }
        return ans;
    }
}
