package cn.orangepoet.inaction.gof.observer;

public interface Subject {
    void attach(Observer obs);

    void detach(Observer obs);

    void publish();
}
