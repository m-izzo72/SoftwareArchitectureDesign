package org.softwarearchitecturedesigngroup10.model.factories;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class LineFactory implements ShapeFactory{

    @Override
    public Shape createShape(double startX, double startY, double endX, double endY, Color fill, Color stroke, double strokeWidth) {
        Line line = new Line();

        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);

        // Imposta lo stile
        line.setFill(fill);
        line.setStroke(stroke);
        line.setStrokeWidth(strokeWidth);

        return line;
    }

}
