package cn.orangepoet.inaction.gof.bridge;

public abstract class Shape {
    protected DrawingAPI drawingAPI;

    public Shape(DrawingAPI api) {
        drawingAPI = api;
    }

    public abstract void draw();

    public abstract void resizeByPercentage(double pct);
}
