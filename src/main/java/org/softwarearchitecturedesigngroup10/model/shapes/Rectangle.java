package org.softwarearchitecturedesigngroup10.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public  class Rectangle extends Shape{
    private double width,height;
    public Rectangle(double width, double height, double y, double x, Color borderColor, Color fillColor, boolean isSelected) {
        super(y, x, borderColor, fillColor, isSelected);
        this.width = width;
        this.height = height;

    }

    @Override
     public void draw(GraphicsContext gc){
        gc.setFill(this.getFillColor());
        gc.fillRect(this.getX(), this.getY(), width, height);

        gc.setStroke(this.getBorderColor());
        gc.strokeRect(this.getX(), this.getY(), width, height);

        if (this.isSelected()) {
            gc.setStroke(Color.BLUE);
            gc.strokeRect(this.getX() - 2, this.getY() - 2, width + 4, height + 4);
        }
    }



}
