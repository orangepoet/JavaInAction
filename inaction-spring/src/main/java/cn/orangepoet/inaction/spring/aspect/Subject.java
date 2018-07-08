package cn.orangepoet.inaction.spring.aspect;

public interface Subject {
    void attach(Observer observer);
    void onMessage();
}
