package cn.orangepoet.inaction.app;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

/**
 * @author chengzhi
 * @date 2020/06/29
 */
public class App1 {
    public static void main(String[] args) {
        //int buff = calculateWaterBuffer(21, 137, 100);
        //System.out.println(buff);
        //
        //System.out.println(12 * 1.15);

        List<String> fruits = Arrays.asList("orange", "apple");
        String result = fruits.stream()
            .map(name -> String.format("[%s]", name))
            .reduce((a, b) -> a + "," + b)
            .orElse("");
        System.out.println(result);
    }

    public static int calculateWaterBuffer(int currentWater, int rate, int extRate) {
        //currentWater/(rate/100)
        // 附加系数
        BigDecimal extRateDecimal = new BigDecimal(extRate).divide(new BigDecimal(100), 4, RoundingMode.DOWN);
        return new BigDecimal(currentWater).multiply(extRateDecimal).divide(
            new BigDecimal(rate).divide(new BigDecimal(100), 4, RoundingMode.DOWN), 4, RoundingMode.DOWN).setScale(0,
            RoundingMode.DOWN).intValue();
    }
}
