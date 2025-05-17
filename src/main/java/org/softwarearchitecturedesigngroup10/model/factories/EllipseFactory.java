package org.softwarearchitecturedesigngroup10.model.factories;



import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

public class EllipseFactory implements ShapeFactory{

    public Shape createShape(double startX, double startY, double endX, double endY, Color fill, Color stroke, double strokeWidth) {
        Ellipse ellipse = new Ellipse();

        double centerX = (startX + endX) / 2;
        double centerY = (startY + endY) / 2;
        double radiusX = Math.abs(endX - startX) / 2;
        double radiusY = Math.abs(endY - startY) / 2;

        ellipse.setCenterX(centerX);
        ellipse.setCenterY(centerY);
        ellipse.setRadiusX(radiusX);
        ellipse.setRadiusY(radiusY);

        // Imposta lo stile
        ellipse.setFill(fill);
        ellipse.setStroke(stroke);
        ellipse.setStrokeWidth(strokeWidth);

        return ellipse;
    }
}

