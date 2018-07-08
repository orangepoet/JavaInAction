package cn.orangepoet.inaction.designpattern.observer.concrete;

import cn.orangepoet.inaction.designpattern.observer.Observer;
import cn.orangepoet.inaction.designpattern.observer.Subject;

public class ConcreteObserver extends Observer {

    public ConcreteObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println("Do my updates...");
    }
}
