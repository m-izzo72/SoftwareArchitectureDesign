package org.softwarearchitecturedesigngroup10.controller.adapters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.shapesdata.PolygonData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.RectangleData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

public class PolygonAdapter implements ShapeAdapterInterface{
    @Override
    public Shape toFXShape(ShapeData data) {
        if (!(data instanceof PolygonData pd)) {
            throw new IllegalArgumentException("Invalid data type for RectangleAdapter");
        }
        Polygon polygon = new Polygon();
        Color fillColor = pd.getFillColor() != null ? Color.valueOf(pd.getFillColor()) : Color.TRANSPARENT;
        Color strokeColor = pd.getStrokeColor() != null ? Color.valueOf(pd.getStrokeColor()) : Color.BLACK;
        polygon.setFill(fillColor);
        polygon.setStroke(strokeColor);
        polygon.setStrokeWidth(pd.getStrokeWidth());

        polygon.getPoints().addAll(pd.getPoints());

        polygon.setRotate(0);
        polygon.setScaleY(1.0); polygon.setScaleX(1.0);
        if(pd.isYFlipped() && pd.isXFlipped()) {
            polygon.setRotate(pd.getRotationAngle());
            polygon.setScaleY(-1); polygon.setScaleX(-1);
        } else if(pd.isYFlipped() && !pd.isXFlipped()) {
            polygon.setRotate(-pd.getRotationAngle());
            polygon.setScaleY(-1); polygon.setScaleX(1);
        } else if(pd.isXFlipped() && !pd.isYFlipped()) {
            polygon.setRotate(-pd.getRotationAngle());
            polygon.setScaleY(1);
            polygon.setScaleX(-1);
        } else {
            polygon.setRotate(pd.getRotationAngle());
            polygon.setScaleY(1); polygon.setScaleX(1);
        }

        return polygon;
    }
}
