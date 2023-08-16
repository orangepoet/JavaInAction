package cn.orangepoet.inaction.gof.chain.d2;

/**
 * @author chengzhi
 * @date 2019/03/08
 */
public class Chain extends Handler {
    private Handler first = new Handler() {
        @Override
        public void handle() {
            System.out.println("start");
            super.fireHandle();
        }
    };
    private Handler end = first;

    public void addFirst(Handler handler) {
        handler.setNext(this.first.getNext());
        this.first.setNext(handler);
    }

    public void addEnd(Handler handler) {
        this.end.setNext(handler);
        this.end = handler;
    }

    @Override
    public void handle() {
        this.first.handle();
    }
}
