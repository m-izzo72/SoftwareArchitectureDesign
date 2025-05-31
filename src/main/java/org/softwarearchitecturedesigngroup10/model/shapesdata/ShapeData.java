package org.softwarearchitecturedesigngroup10.model.shapesdata;

import java.io.Serializable;

public abstract class ShapeData implements Serializable, Cloneable {

    private String type;
    private double x;
    private double y;
    private String fillColor;
    private String strokeColor;
    private double strokeWidth;
    private double rotationAngle;
    private boolean isSelected;
    private boolean isYFlipped;
    private boolean isXFlipped;

    public boolean isXFlipped() {
        return isXFlipped;
    }

    public void setXFlipped() {
        isXFlipped = !isYFlipped;
    }

    public boolean isYFlipped() {
        return isYFlipped;
    }

    public void setYFlipped() {
        isYFlipped = !isYFlipped;
    }

    public ShapeData() { }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public String getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(String strokeColor) {
        this.strokeColor = strokeColor;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public double getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public abstract double getWidth();
    public abstract double getHeight();
    public abstract void resize(double newWidth, double newHeight);

    @Override
    public ShapeData clone() {
        try {
            return (ShapeData) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}