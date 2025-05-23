package org.softwarearchitecturedesigngroup10.controller.adapters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.shapesdata.RectangleData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

public class RectangleAdapter implements ShapeAdapterInterface {
    @Override
    public Shape toFXShape(ShapeData data) {
        if (!(data instanceof RectangleData rd)) {
            throw new IllegalArgumentException("Invalid data type for RectangleAdapter");
        }
        Rectangle rect = new Rectangle(rd.getX(), rd.getY(), rd.getWidth(), rd.getHeight());
        Color fillColor = rd.getFillColor() != null ? Color.valueOf(rd.getFillColor()) : Color.TRANSPARENT; // Default a trasparente
        Color strokeColor = rd.getStrokeColor() != null ? Color.valueOf(rd.getStrokeColor()) : Color.BLACK;
        rect.setFill(fillColor);
        rect.setStroke(strokeColor);
        rect.setStrokeWidth(rd.getStrokeWidth());
        return rect;
    }
}
