package cn.orangepoet.inaction.tools.codegen;

/**
 * @author chengzhi
 * @date 2019/09/19
 */
public class Process {
    public static void main(String[] args) {
        Woo woo = new Woo();
        woo.setName("woo");

        Foo foo = MyMapper.INSTANCE.convert2Foo(woo);
        System.out.println(foo);
    }
}
