package cn.orangepoet.inaction.designpattern.strategry;

import lombok.Setter;

/**
 * 策略接口
 */
@FunctionalInterface
public interface FuncStrategy {
    int operation(int a, int b);
}

/**
 * 策略簇
 */
class Strategys {
    public static int add(int a, int b) {
        return a + b;
    }

    public static int multiple(int a, int b) {
        return a * b;
    }
}


class Context2 {
    @Setter
    private FuncStrategy strategy;

    public int execute(int a, int b) {
        return strategy.operation(a, b);
    }

    public static void main(String[] args) {
        Context2 context = new Context2();
        context.setStrategy(Strategys::add);
        // or lambda:  context.setStrategy((a,b) -> a+b);
        System.out.println(context.execute(1, 2));

        context.setStrategy(Strategys::multiple);
        // or lambda:  context.setStrategy((a,b) -> a*b);
        System.out.println(context.execute(1, 2));
    }
}