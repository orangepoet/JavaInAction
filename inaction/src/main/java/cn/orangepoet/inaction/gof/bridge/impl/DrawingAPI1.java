package cn.orangepoet.inaction.gof.bridge.impl;

import cn.orangepoet.inaction.gof.bridge.DrawingAPI;

public class DrawingAPI1 implements DrawingAPI {

    @Override
    public void drawingCircle(double x, double y, double radius) {
        System.out.println("DrawingAPI1.drawingCircle()");
    }
}
