package org.softwarearchitecturedesigngroup10.model.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Ellipse extends Shape {

    private double radiusX, radiusY;



    @Override
    public void draw(GraphicsContext gc) {
        // Save the actual state of the drawing area
        gc.save();

        // Set the colour
        gc.setStroke(getBorderColor());
        gc.setFill(getFillColor());



        // Draw the colored ellipse

        gc.fillOval(getX1() - radiusX, getY1() - radiusY, radiusX * 2, radiusY * 2);
        gc.strokeOval(getX1() - radiusX, getY1() - radiusY, radiusX * 2, radiusY * 2);

        gc.restore();
    }

    // Getters e setters per radiusX e radiusY
    public double getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(double radiusX) {
        this.radiusX = radiusX;
    }

    public double getRadiusY() {
        return radiusY;
    }

    public void setRadiusY(double radiusY) {
        this.radiusY = radiusY;
    }
}
