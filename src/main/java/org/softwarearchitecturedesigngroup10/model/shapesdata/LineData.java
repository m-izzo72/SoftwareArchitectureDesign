package org.softwarearchitecturedesigngroup10.model.shapesdata;

public class LineData extends ShapeData {

    private double endX;
    private double endY;

    public double getEndX() {
        return endX;
    }
    public void setEndX(double endX) {
        this.endX = endX;
    }
    public double getEndY() {
        return endY;
    }
    public void setEndY(double endY) {
        this.endY = endY;
    }

    @Override
    public double getWidth() {
        return Math.abs(getEndX() - getX());
    }

    @Override
    public double getHeight() {
        return Math.abs(getEndY() - getY());
    }

    @Override
    public void resize(double newWidth, double newHeight) {
        setEndX(getX() + newWidth);
        setEndY(getY() + newHeight);
    }
}