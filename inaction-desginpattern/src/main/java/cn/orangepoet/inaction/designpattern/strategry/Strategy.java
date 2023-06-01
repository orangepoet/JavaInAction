package cn.orangepoet.inaction.designpattern.strategry;

/**
 * 定义一组算法，将每个算法都封装起来，并且使他们之间可以互换
 */
public abstract class Strategy {
    public abstract int operate(int a, int b);
}

class AddOperation extends Strategy {

    @Override
    public int operate(int a, int b) {
        return a + b;
    }
}

class MultipleOperation extends Strategy {

    @Override
    public int operate(int a, int b) {
        return a * b;
    }
}

class Context {
    private Strategy strategy;

    private void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int execute(int a, int b) {
        return strategy.operate(a, b);
    }

    public static void main(String[] args) {
        Strategy strategyA = new AddOperation();
        Strategy strategyB = new MultipleOperation();
        Context context = new Context();
        context.setStrategy(strategyA);
        System.out.println(context.execute(1, 2));

        context.setStrategy(strategyB);
        System.out.println(context.execute(1, 2));
    }
}