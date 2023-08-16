package cn.orangepoet.inaction.gof.template;

public abstract class AbstractClass {

    public abstract void step1();

    public abstract void step2();

    public void templateMethod() {
        step1();
        step2();
    }
}