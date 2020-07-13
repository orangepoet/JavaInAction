package cn.orangepoet.inaction.spring.base.aspect;

public interface Subject {
    void attach(Observer observer);
    void onMessage();
}
