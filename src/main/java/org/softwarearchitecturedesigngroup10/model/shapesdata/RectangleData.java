package org.softwarearchitecturedesigngroup10.model.shapesdata;

public class RectangleData extends ShapeData {

    private double width;
    private double height;

    @Override
    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    @Override
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    public RectangleData() {
        setType("RD");
    }

    @Override
    public void resize(double newWidth, double newHeight) {
        setWidth(newWidth);
        setHeight(newHeight);
    }
}