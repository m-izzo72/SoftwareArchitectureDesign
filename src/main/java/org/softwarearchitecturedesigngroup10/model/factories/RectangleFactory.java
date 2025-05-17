package org.softwarearchitecturedesigngroup10.model.factories;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class RectangleFactory implements ShapeFactory{

    @Override
    public Shape createShape(double startX, double startY, double endX, double endY, Color fill, Color stroke, double strokeWidth) {
        Rectangle rectangle = new Rectangle();

        // Gestisce correttamente il caso in cui l'utente trascina in direzioni diverse
        double x = Math.min(startX, endX);
        double y = Math.min(startY, endY);
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);

        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setWidth(width);
        rectangle.setHeight(height);

        // Imposta lo stile
        rectangle.setFill(fill);
        rectangle.setStroke(stroke);
        rectangle.setStrokeWidth(strokeWidth);

        return rectangle;
    }
}
