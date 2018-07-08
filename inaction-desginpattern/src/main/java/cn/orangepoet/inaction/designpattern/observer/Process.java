package cn.orangepoet.inaction.designpattern.observer;

import cn.orangepoet.inaction.designpattern.observer.concrete.ConcreteObserver;
import cn.orangepoet.inaction.designpattern.observer.concrete.ConcreteSubject;

/**
 * 定义对象间的一种一对多依赖关系，使得每当一个对象状态发生改变时，其相关依赖对象皆得到通知并被自动更新
 * 
 * @author orange
 *
 */
public class Process {

    public static void main(String[] args) {
        Subject subject = new ConcreteSubject();
        Observer observer1 = new ConcreteObserver(subject);
        subject.attach(observer1);
        subject.publish();
    }
}
