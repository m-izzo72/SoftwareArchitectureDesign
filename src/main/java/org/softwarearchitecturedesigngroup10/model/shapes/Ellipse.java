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

        // Draw Ellipse
        /* Se la forma è selezionata, si potrebbe voler aggiungere un effetto visivo
        if (isSelected()) {
            // Opzionale: disegna un indicatore di selezione, ad esempio un contorno tratteggiato
            gc.setLineDashes(3);
            gc.strokeOval(getX() - radiusX, getY() - radiusY, radiusX * 2, radiusY * 2);
            gc.setLineDashes(null);
        }*/

        // Draw the colored ellipse
        gc.fillOval(getRadiusX() - radiusX, getRadiusY() - radiusY, radiusX * 2, radiusY * 2);
        gc.strokeOval(getRadiusX() - radiusX, getRadiusY() - radiusY, radiusX * 2, radiusY * 2);

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
