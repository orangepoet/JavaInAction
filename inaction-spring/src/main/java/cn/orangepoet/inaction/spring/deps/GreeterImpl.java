package cn.orangepoet.inaction.spring.deps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chengzhi
 * @date 2020/03/17
 */
@Component
public class GreeterImpl implements Greeter {

    @Autowired
    private WordMaker wordMaker;

    @Override
    public void greet() {
        String word = wordMaker.make();
        System.out.println(word);
    }
}
