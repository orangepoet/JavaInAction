package cn.orangepoet.inaction.algorithm.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solution {

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
            changeRet.put(amount, minChange);
        }
        return changeRet.get(n);
    }

    /**
     * 按币种面值枚举, 面值高优先
     *
     * @param money
     */
    private static int makeChanges3(int money) {
        int range = money / 9;
        int minCount = Integer.MAX_VALUE;
        int count;

        int thisA = 0;
        int thisB = 0;
        int thisC = 0;
        for (int i = 0; i < range; i++) {
            int j = (money - 9 * i) / 7;
            int k = money - 9 * i - 7 * j;

            count = i + j + k;
            System.out.println("calcuate count>>");
            if (minCount > count) {
                minCount = count;
                //                System.out.println(String.format("This Best>> A: %d, B: %d, C: %d, Count: %d", i,
                //                j, k, count));
                thisA = i;
                thisB = j;
                thisC = k;
            }
        }
        System.out.println(String.format("Best>> A: %d, B: %d, C: %d, Count: %d", thisA, thisB, thisC, minCount));
        return minCount;
    }

    /**
     * 使用动态规则
     *
     * @param amount
     */
    private static int makeChanges4(int amount, Map<Integer, Integer> resultMap) {

        if (resultMap.containsKey(amount)) {
            return resultMap.get(amount);
        }
        int coinNum;
        if (amount > 9) {
            coinNum = Math.min(
                    1 + makeChanges4(amount - 9, resultMap),
                    1 + makeChanges4(amount - 7, resultMap)
            );
        } else if (amount > 7) {
            coinNum = amount / 7 + amount % 7;
        } else {
            coinNum = amount;
        }
        resultMap.put(amount, coinNum);
        return coinNum;
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
    }

    /**
     * 链表反转
     *
     * @param head
     * @param m
     * @param n
     * @return
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || m < 1 || n < 1 || m > n) {
            return head;
        }

        ListNode left = null;
        ListNode right = null;
        ListNode cur = head;
        int t = 0;
        while (cur != null) {
            t += 1;
            if (t == m - 1) {
                left = cur;
            }
            if (t == n + 1) {
                right = cur;
            }
            cur = cur.next;
        }

        int len = n - m;
        left = left == null ? head : left;
        ListNode next = null;
        while (len > 0) {
            len--;
        }
        //TODO: 未完待续

        return head;
    }

    /**
     * 查找目标数的开始和结束位置
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

        return new int[]{low, high};
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
     * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
     * <p>
     * 答案中不可以包含重复的三元组。
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int max = 1 << nums.length - 1;
        Arrays.sort(nums);
        for (int i = 1; i <= max; i++) {
            List<Integer> combination = new ArrayList<>();
            int sum = 0;
            for (int j = 0; j < nums.length; j++) {
                int bit = 1 << j;
                if ((i & bit) == bit) {
                    sum += nums[j];
                    combination.add(nums[j]);
                }
            }
            HashSet<String> hashSet = new HashSet<>();
            if (sum == 0 && combination.size() == 3) {
                result.add(combination);
            }
        }
        return result;
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
                if (!(i == (gX + m) && j == (gY + n)) && (int) board[gX + m][gY + n] == num) {
                    return true;
                }
            }
        }

        for (int k = 0; k < 9; k++) {
            if (k != i && (int) board[k][j] == num) {
                return true;
            }
            if (k != j && (int) board[i][k] == num) {
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
                int index = (int) c - 97;
                int newIndex = (int) ((index + shifts2[i]) % 26);
                c = (char) (newIndex + 97);
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
     * 求解数组nums的子数组, 要求满足和为K的, 给出子数组的个数。
     * <p>
     *
     * @param nums 原始数组
     * @param k    子数组的和
     * @return 子数组的个数
     */
    public int subArrayIsK(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int sum = 0;
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k))
                ans += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }

    public int hammingWeight(int n) {
        String binary = Integer.toBinaryString(n);
        int ans = 0;
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                ans++;
            }
        }
        return ans;
//        int ans = 0;
//        for (int i = 0; ; i++) {
//            int m = 1 << i;
//            if (m > n) {
//                break;
//            }
//            if ((n & m) == m) {
//                ans++;
//            }
//        }
//        return ans;
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

    /**
     * 给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
     * <p>
     * 示例:
     * <p>
     * 输入: [1,2,3,null,5,null,4]
     * 输出: [1, 3, 4]
     * 解释:
     * <p>
     * 1            <---
     * /   \
     * 2     3         <---
     * \     \
     * 5     4       <---
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
                if (treeNode.right != null)
                    lst2.add(treeNode.right);
                if (treeNode.left != null)
                    lst2.add(treeNode.left);
            }
            lst = lst2;
        }
        return ans;
    }
}
