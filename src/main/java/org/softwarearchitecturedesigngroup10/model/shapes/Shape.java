package org.softwarearchitecturedesigngroup10.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {

    private double x, y;
    private Color borderColor;
    private Color fillColor;
    private boolean isSelected;

    /* public Shape(double y, double x, Color borderColor, Color fillColor, boolean isSelected) {
        this.y = y;
        this.x = x;
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.isSelected = isSelected;
    }*/


    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setShapePosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setShapeBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setShapeFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public abstract void draw(GraphicsContext gc);

}
