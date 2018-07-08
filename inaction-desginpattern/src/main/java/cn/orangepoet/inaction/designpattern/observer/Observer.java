package cn.orangepoet.inaction.designpattern.observer;

public abstract class Observer {

    public Observer(Subject subject) {
        subject.attach(this);
    }

    public abstract void update();
}
