package cn.orangepoet.inaction.spring.deps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author chengzhi
 * @date 2020/03/17
 */
@Primary
@Component
public class WordMakerImpl implements WordMaker {

    @Autowired
    private Greeter greeter;

    @Override
    public String make() {
        return "hello, world";
    }

    @Override
    public void greet() {
        greeter.greet();
    }
}
