package org.softwarearchitecturedesigngroup10.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends Shape{
    private double thickness;

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(this.getBorderColor());
        gc.strokeLine(this.getX1(), this.getY1(), this.getX2(), this.getY2());
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }
}
