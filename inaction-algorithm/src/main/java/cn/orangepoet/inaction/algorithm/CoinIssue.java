package cn.orangepoet.inaction.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * </p>
 * 硬币找零的贪心算法, 该算法先得到一个解作为最优解, 然后不断的得出其他解作为比较得出最优解
 * <p>
 * <p>
 * 动态规划的方式一种: 使用递归法穷举
 * <p>
 * 对比第一种效率要高很多, 动态规划需要改进;
 * Created by Orange on 16/8/26.
 */
public class CoinIssue {
    public static void main(String[] args) {
        int money = 100;
        int coinNum0 = makeChanges(money);
        System.out.println(coinNum0);

        int coinNum = makeChanges0(money, new HashMap<>());
        System.out.println(coinNum);
    }

    /**
     * 按币种面值枚举, 面值高优先
     *
     * @param money
     */
    private static int makeChanges(int money) {
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
//                System.out.println(String.format("This Best>> A: %d, B: %d, C: %d, Count: %d", i, j, k, count));
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
     * @param money
     */
    private static int makeChanges0(int money, Map<Integer, Integer> mapMoneyCoinTotalCnt) {

        if (mapMoneyCoinTotalCnt.containsKey(money)) {
            return mapMoneyCoinTotalCnt.get(money);
        }
        System.out.println("calculate changes, money: " + money);
        int coinNum;
        if (money > 9) {
            coinNum = Math.min(
                    1 + makeChanges0(money - 9, mapMoneyCoinTotalCnt),
                    1 + makeChanges0(money - 7, mapMoneyCoinTotalCnt)
            );
        } else if (money > 7) {
            coinNum = money / 7 + money % 7;
        } else {
            coinNum = money;
        }
        mapMoneyCoinTotalCnt.put(money, coinNum);
        return coinNum;
    }
}