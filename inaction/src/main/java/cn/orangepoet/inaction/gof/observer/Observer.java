package cn.orangepoet.inaction.gof.observer;

public abstract class Observer {

    public Observer(Subject subject) {
        subject.attach(this);
    }

    public abstract void update();
}
