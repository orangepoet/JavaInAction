package cn.orangepoet.inaction.algorithm;

/**
 * 序列最大和
 */
public class MaxSubSequence {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 3, -2, 4, 5};
        int max = getMax(arr);
        System.out.println(max);
    }

    public static int getMax(int[] sequence) {
        int maxSubSum = 0, thisSubSum = 0;
        for (int i = 0; i < sequence.length; i++) {
            thisSubSum += sequence[i];
            if (thisSubSum > maxSubSum) {
                maxSubSum = thisSubSum;
            } else if (thisSubSum < 0) {
                thisSubSum = 0;
            }
        }
        return maxSubSum;
    }
}
