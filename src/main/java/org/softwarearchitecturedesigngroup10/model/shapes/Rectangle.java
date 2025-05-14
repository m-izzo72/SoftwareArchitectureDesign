package org.softwarearchitecturedesigngroup10.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.Shape;

public class Rectangle extends Shape {
    public double width,height;
    public Rectangle(double width, double height, double y, double x, Color borderColor, Color fillColor, boolean isSelected) {
        super(x,y,borderColor,fillColor,isSelected);
        this.width = width;
        this.height = height;

    }

    @Override
     void draw(GraphicsContext gc){

    }



}
