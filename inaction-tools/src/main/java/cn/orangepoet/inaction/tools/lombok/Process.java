package cn.orangepoet.inaction.tools.lombok;

public class Process {
    public static void main(String[] args) {
        Foo foo = new Foo("foo",null);
//        foo.setName("foo");
//        foo.setCount(1);

        System.out.println(foo);
    }
}
