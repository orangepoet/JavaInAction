package cn.orangepoet.inaction.designpattern.bridge;

import cn.orangepoet.inaction.designpattern.bridge.impl.CircleShape;
import cn.orangepoet.inaction.designpattern.bridge.impl.DrawingAPI1;

/**
 * The bridge design designpattern is meant to
 * "decouple abstraction from its implementation so that two can vary independently"
 * . The bridge use encapsulation, aggregation, and can use inheritance to
 * separate responsibilities into different classes;
 * 
 * @author orange
 *
 */
public class Process {
    public static void main(String[] args) {
        DrawingAPI api = new DrawingAPI1();
        Shape shape = new CircleShape(1, 1, 3, api);
        shape.draw();
    }
}
