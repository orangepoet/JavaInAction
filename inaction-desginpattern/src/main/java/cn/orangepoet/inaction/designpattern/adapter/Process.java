package cn.orangepoet.inaction.designpattern.adapter;

/**
 * @author chengzhi
 * @date 2019/10/10
 */
public class Process {
    public static void main(String[] args) {
        Target target = new Adapter(new Adaptee());

        int ret = target.request(1);
        System.out.println(ret);
    }
}
