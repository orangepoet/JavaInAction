package cn.orangepoet.inaction.spring.base.lifecycle;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author chengzhi
 * @date 2022/06/01
 */
@Component
public class Woo {
    private Foo foo;

    @Lazy
    public Woo(Foo foo) {
        this.foo = foo;
    }
}
