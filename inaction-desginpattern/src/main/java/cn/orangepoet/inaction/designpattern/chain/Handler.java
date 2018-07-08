package cn.orangepoet.inaction.designpattern.chain;

/**
 * 责任链的处理者, 在自己处理完之后, 可以再交给后继者处理 (可选);
 */
public abstract class Handler {
    private Handler successor;

    public abstract void handleRequest();

    public Handler getSuccessor() {
        return successor;
    }

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }
}
