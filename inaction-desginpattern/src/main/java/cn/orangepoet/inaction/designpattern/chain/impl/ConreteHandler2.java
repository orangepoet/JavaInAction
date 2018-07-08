package cn.orangepoet.inaction.designpattern.chain.impl;

import cn.orangepoet.inaction.designpattern.chain.Handler;

public class ConreteHandler2 extends Handler {

    @Override
    public void handleRequest() {
        if (getSuccessor() != null) {
            getSuccessor().handleRequest();
        }
    }
}
