package org.softwarearchitecturedesigngroup10.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends Shape{
    private double x2, y2;

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(this.getBorderColor());
        gc.setLineWidth(this.isSelected() ? 3 : 1); // linea più spessa se selezionata
        gc.strokeLine(this.getX(), this.getY(), x2, y2);
    }


    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }


}
