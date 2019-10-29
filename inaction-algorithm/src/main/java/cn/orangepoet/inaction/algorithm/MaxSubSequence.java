package cn.orangepoet.inaction.algorithm;

/**
 * 序列最大和
 */
public class MaxSubSequence {

    public static void main(String[] args) {
        int[] arr = new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int max = getMax(arr);
        System.out.println(max);
    }

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
}
