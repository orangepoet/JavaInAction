package cn.orangepoet.inaction.spring.base.eg;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author chengzhi
 * @date 2021/12/28
 */
@Component
public class Eg1 {
    private static Foo foo;

    @Resource
    private Foo fooinstance;

    @PostConstruct
    public void init() {
        foo = fooinstance;
    }

    public static void t() {
        if (foo != null) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
