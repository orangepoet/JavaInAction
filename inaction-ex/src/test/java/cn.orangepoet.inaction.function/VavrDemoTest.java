package cn.orangepoet.inaction.function;

import cn.orangepoet.inaction.ex.vavr.VavrDemo;
import org.junit.Test;

/**
 * @author chengzhi
 * @date 2021/01/15
 */
public class VavrDemoTest {

    @Test
    public void listEx() {
        VavrDemo.listEx();
    }

    @Test
    public void tryEx() {
        VavrDemo.tryEx();
    }

    @Test
    public void matchEx() {
        VavrDemo.matchEx();
    }

    @Test
    public void memorizeEx() {
        VavrDemo.memorizeEx();
    }

    @Test
    public void eitherEx() {
        VavrDemo.eitherEx();
    }

    @Test
    public void lazyEx() {
        VavrDemo.lazy();
    }

    @Test
    public void optionEx() {
        VavrDemo.option();
    }
}