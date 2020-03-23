package cn.orangepoet.inaction.demo.lombok;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

/**
 * @author chengzhi
 * @date 2019/09/19
 */
public class Application {
    public static void main(String[] args) {
//        method1();
        greet(null);
    }

    private static void method1() {
        String s = StringUtils.stripEnd("任务已完成，xx奖励已发放,", ",");
        System.out.println(s);
        Woo woo = new Woo();
        woo.setName("woo");

        Foo foo = MyMapper.INSTANCE.convert2Foo(woo);
        System.out.println(foo);
    }

    private static void greet(@NonNull String words) {
        System.out.println(words);
    }
}
