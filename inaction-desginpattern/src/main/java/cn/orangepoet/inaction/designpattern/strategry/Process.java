package cn.orangepoet.inaction.designpattern.strategry;

import cn.orangepoet.inaction.designpattern.strategry.concrete.ConcreteStrategryA;

public class Process {

    public static void main(String[] args) {
        Strategry strategry = new ConcreteStrategryA();
        Process context = new Process();
        context.setStrategy(strategry);
        context.algorithm();
    }

    private Strategry strategy;

    private void setStrategy(Strategry strategry) {
        this.strategy = strategry;
    }

    public int algorithm() {
        return strategy.algorithm();
    }

}
