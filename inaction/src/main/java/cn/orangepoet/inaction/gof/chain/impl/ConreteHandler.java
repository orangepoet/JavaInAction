package cn.orangepoet.inaction.gof.chain.impl;

import cn.orangepoet.inaction.gof.chain.Handler;

public class ConreteHandler extends Handler {

    @Override
    public void handleRequest() {
        // this handler perform action
        System.out.println("pass");

        if (getSuccessor() != null) {
            // next handler perform action
            getSuccessor().handleRequest();
        }
    }

}
