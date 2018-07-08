package cn.orangepoet.inaction.designpattern.chain.impl;

import cn.orangepoet.inaction.designpattern.chain.Handler;

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
