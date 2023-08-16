package cn.orangepoet.inaction.gof.observer.concrete;

import cn.orangepoet.inaction.gof.observer.Observer;
import cn.orangepoet.inaction.gof.observer.Subject;

public class ConcreteObserver extends Observer {

    public ConcreteObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println("Do my updates...");
    }
}
