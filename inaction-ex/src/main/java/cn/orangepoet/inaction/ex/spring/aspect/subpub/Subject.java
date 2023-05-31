package cn.orangepoet.inaction.ex.spring.aspect.subpub;

public interface Subject {
    void attach(Observer observer);
    void onMessage();
}
