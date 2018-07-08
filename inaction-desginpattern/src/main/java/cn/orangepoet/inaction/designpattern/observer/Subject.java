package cn.orangepoet.inaction.designpattern.observer;

public interface Subject {
    void attach(Observer obs);

    void detach(Observer obs);

    void publish();
}
