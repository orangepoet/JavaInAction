package cn.orangepoet.inaction.tools;

import java.util.Arrays;
import java.util.List;

/**
 * @author chengzhi
 * @date 2019/07/16
 */
public class Process {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("1", "2", "3");

        if (list.contains("2")) {
            System.out.println("2 in list");
        }

        if (list.contains("5")) {
            System.out.println("5 in list");
        }

        System.out.println(list.toString());
    }
}
