package cn.orangepoet.inaction.spring.base.lifecycle;

import org.springframework.stereotype.Component;

/**
 * @author chengzhi
 * @date 2022/06/01
 */
@Component
public class Foo {
    private Woo woo;

    public Foo(Woo woo) {
        this.woo = woo;
    }
}
