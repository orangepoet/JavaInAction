package cn.orangepoet.inaction.designpattern.adapter;

/**
 * @author chengzhi
 * @date 2019/10/10
 */
public class Adapter implements Target {
    private final Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public int request(int j) {
        String str = String.valueOf(j);
        return adaptee.request(str);
    }
}
