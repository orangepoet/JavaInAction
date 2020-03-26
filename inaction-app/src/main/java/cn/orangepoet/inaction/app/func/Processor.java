package cn.orangepoet.inaction.app.func;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author chengzhi
 * @date 2019/08/04
 */
public class Processor {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        List<Integer> lst = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> ret = lst.stream()
            .filter(i -> i % 2 == 1)
            .map(i -> i + 10)
            .filter(r -> r > 15)
            .collect(Collectors.toList());
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //String ret = dateFormat.format(System.currentTimeMillis());
        System.out.println(ret);

        Long l = 1L;
        //BigDecimal bigDecimal = new BigDecimal(l);
        //System.out.println(bigDecimal);
        double d = new Long(1).doubleValue();
        BigDecimal bigDecimal = new BigDecimal(1);
        System.out.println(d);
        System.out.println(bigDecimal);
        //List<Long> lst = new ArrayList<>();
        //lst.addAll(new ArrayList<>());

        System.out.println("done");

        int[] arr = new int[] {3, 2, 1};
        Map<Integer, Integer> map = new HashMap<>();
        //
        for (int i = 0; i < 6000; i++) {
            int rnd = randomWeight2(arr);
            Integer times = map.getOrDefault(rnd, 0);
            map.put(rnd, times + 1);
        }

        System.out.println(map);
    }

    public static int randomWeight2(int[] weights) {
        //所有权重值加起来
        int totalWeight = Arrays.stream(weights).sum();
        int rndWeight = RANDOM.nextInt(totalWeight);

        int currWeight = 0;
        for (int i = 0; i < weights.length; i++) {
            currWeight += weights[i];
            if (currWeight > rndWeight) {
                return i;
            }
        }
        return -1;
    }
}