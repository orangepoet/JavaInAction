package cn.orangepoet.inaction.algorithm;

/**
 * 硬币找零的贪心算法, 该算法先得到一个解作为最优解, 然后不断的得出其他解作为比较得出最优解
 * <p>
 * Created by Orange on 16/8/26.
 */
public class CoinIssue {
    public static void main(String[] args) {
        int bigNumber = 40;
        makeChanges(bigNumber);
    }


    private static void makeChanges(int money) {
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
            if (minCount > count) {
                minCount = count;
                System.out.println(String.format("This Best>> A: %d, B: %d, C: %d, Count: %d", i, j, k, count));
                thisA = i;
                thisB = j;
                thisC = k;
            }
        }
        System.out.println(String.format("Best>> A: %d, B: %d, C: %d, Count: %d", thisA, thisB, thisC, minCount));
    }
}