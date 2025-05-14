package org.softwarearchitecturedesigngroup10.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;



public  class Rectangle extends Shape {
    public double width,height;
    public Rectangle(double width, double height, double y, double x, Color borderColor, Color fillColor, boolean isSelected) {
        super(y, x, borderColor, fillColor, isSelected);
        this.width = width;
        this.height = height;

    }

    @Override
     void draw(GraphicsContext gc){
        gc.setFill(fillColor);
        gc.fillRect(x, y, width, height);

        gc.setStroke(borderColor);
        gc.strokeRect(x, y, width, height);

        if (isSelected) {
            gc.setStroke(Color.BLUE);
            gc.strokeRect(x - 2, y - 2, width + 4, height + 4);
        }
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
