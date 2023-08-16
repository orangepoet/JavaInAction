package cn.orangepoet.inaction.gof.chain.impl;

import cn.orangepoet.inaction.gof.chain.Handler;

public class ConreteHandler2 extends Handler {

    @Override
    public void handleRequest() {
        if (getSuccessor() != null) {
            getSuccessor().handleRequest();
        }
    }
}
