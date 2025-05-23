package org.softwarearchitecturedesigngroup10.controller.adapters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

public class LineAdapter implements ShapeAdapterInterface {
    @Override
    public Shape toFXShape(ShapeData data) {
        if (!(data instanceof LineData ld)) {
            throw new IllegalArgumentException("Invalid data type for LineAdapter");
        }
        Line line = new Line(ld.getX(), ld.getY(), ld.getEndX(), ld.getEndY());
        Color strokeColor = ld.getStrokeColor() != null ? Color.valueOf(ld.getStrokeColor()) : Color.BLACK;
        line.setStroke(strokeColor);
        line.setStrokeWidth(ld.getStrokeWidth());
        return line;
    }
}
