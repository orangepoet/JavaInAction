package cn.orangepoet.inaction.spring.base.aspect.subpub;

public interface Subject {
    void attach(Observer observer);
    void onMessage();
}
