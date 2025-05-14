package org.softwarearchitecturedesigngroup10.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {

    double x, y;
    Color borderColor;
    Color fillColor;
    boolean isSelected;

    public Shape(double y, double x, Color borderColor, Color fillColor, boolean isSelected) {
        this.y = y;
        this.x = x;
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.isSelected = isSelected;
    }

    void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    void setShapePosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    void setShapeBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    void setShapeFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }


    public boolean isSelected() {
        return isSelected;
    }



    abstract void draw(GraphicsContext gc);

}
