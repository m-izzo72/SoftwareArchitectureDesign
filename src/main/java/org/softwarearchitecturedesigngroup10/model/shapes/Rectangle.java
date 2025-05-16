package org.softwarearchitecturedesigngroup10.model.shapes;

import javafx.scene.canvas.GraphicsContext;


public  class Rectangle extends Shape{
    private double width,height;  // declare the attributes

    //Getter and Setter for width and height

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
        // Set the fill color for the rectangle
        gc.setFill(this.getFillColor());
        // Draw a filled rectangle at (x1, y1) with the specified width and height
        gc.fillRect(this.getX1(), this.getY1(), width, height);

        // Set the stroke (border) color for the rectangle outline
        gc.setStroke(this.getBorderColor());
        // Draw the outline of the rectangle at the same position and dimensions
        gc.strokeRect(this.getX1(), this.getY1(), width, height);
    }



}
