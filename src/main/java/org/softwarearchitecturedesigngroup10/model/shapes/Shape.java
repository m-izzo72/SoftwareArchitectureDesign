package org.softwarearchitecturedesigngroup10.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {

    private double x1, y1, x2, y2;
    private Color borderColor;
    private Color fillColor;
    private boolean isSelected;


    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setShapePosition(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
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

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public abstract void draw(GraphicsContext gc);

}
