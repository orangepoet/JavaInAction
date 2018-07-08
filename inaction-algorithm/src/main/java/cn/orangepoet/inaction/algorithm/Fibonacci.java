package cn.orangepoet.inaction.algorithm;

/**
 * 斐波那契数列, 求第N个数
 */
public class Fibonacci {

    /**
     * 斐波那契数列 (非递归解法)
     *
     * @param seq
     * @return result
     */
    public int get(int seq) {
        if (seq < 0)
            throw new IllegalArgumentException("seq");

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
    public int get0(int seq) {
        if (seq == 1 || seq == 2) {
            return 1;
        } else {
            return get0(seq - 1) + get0(seq - 2);
        }
    }
}
