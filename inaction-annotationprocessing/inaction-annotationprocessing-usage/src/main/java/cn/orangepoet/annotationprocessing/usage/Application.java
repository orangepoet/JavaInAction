package cn.orangepoet.annotationprocessing.usage;

/**
 * @author chengzhi
 * @date 2020/01/20
 */
public class Application {
    public static void main(String[] args) {
        PersonBuilder personBuilder = new PersonBuilder();
        Person orange = personBuilder.setAge(12).setName("orange").build();
        System.out.println(orange);
    }
}
