package cn.orangepoet.inaction.gof.chain;

import cn.orangepoet.inaction.gof.chain.impl.ConreteHandler;
import cn.orangepoet.inaction.gof.chain.impl.ConreteHandler2;

public class Process {

    public static void main(String[] args) {
        Handler handler1 = new ConreteHandler();
        Handler handler2 = new ConreteHandler2();

        handler1.setSuccessor(handler2);
        handler1.handleRequest();
    }
}
