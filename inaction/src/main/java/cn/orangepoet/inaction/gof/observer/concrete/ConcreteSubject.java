package cn.orangepoet.inaction.gof.observer.concrete;

import cn.orangepoet.inaction.gof.observer.Observer;
import cn.orangepoet.inaction.gof.observer.Subject;

import java.util.ArrayList;

public class ConcreteSubject implements Subject {

    private ArrayList<Observer> observers = new ArrayList<Observer>();

    @Override
    public void attach(Observer obs) {
        if (obs != null) {
            observers.add(obs);
        }
    }

    @Override
    public void detach(Observer obs) {
        if (obs != null) {
            observers.remove(obs);
        }
    }

    @Override
    public void publish() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
