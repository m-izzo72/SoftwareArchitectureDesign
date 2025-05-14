package org.softwarearchitecturedesigngroup10.model.shapes;

import javafx.scene.canvas.GraphicsContext;


public  class Rectangle extends Shape{
    private double width,height;

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

    @Override
     public void draw(GraphicsContext gc){
        gc.setFill(this.getFillColor());
        gc.fillRect(this.getX(), this.getY(), width, height);

        gc.setStroke(this.getBorderColor());
        gc.strokeRect(this.getX(), this.getY(), width, height);
    }



}
