package cn.orangepoet.inaction.app.exception;

/**
 * @author chengzhi
 * @date 2021/06/28
 */
public class Application {
    public static void main(String[] args) {
        // this is my method invocation;
        // throw new exception to show cause stack;
        callMethod1();
    }

    private static void callMethod1() {
        throw new MyException("my exception", new UnsupportedOperationException("not impl"));
    }
}
