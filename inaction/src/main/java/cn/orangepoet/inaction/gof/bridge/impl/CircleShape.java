package cn.orangepoet.inaction.gof.bridge.impl;

import cn.orangepoet.inaction.gof.bridge.DrawingAPI;
import cn.orangepoet.inaction.gof.bridge.Shape;

public class CircleShape extends Shape {

    private double radius;
    private final double x;
    private final double y;

    public CircleShape(double x, double y, double radius, DrawingAPI api) {
        super(api);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw() {
        drawingAPI.drawingCircle(x, y, radius);
    }

    @Override
    public void resizeByPercentage(double pct) {
        radius *= pct;
    }
}
