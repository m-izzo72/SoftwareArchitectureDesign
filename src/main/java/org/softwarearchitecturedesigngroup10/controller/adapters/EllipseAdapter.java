package org.softwarearchitecturedesigngroup10.controller.adapters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.shapesdata.EllipseData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

public class EllipseAdapter implements ShapeAdapterInterface {
    @Override
    public Shape toFXShape(ShapeData data) {
        if (!(data instanceof EllipseData ed)) {
            throw new IllegalArgumentException();
        }
        Ellipse ellipse = new Ellipse(ed.getCenterX(), ed.getCenterY(), ed.getRadiusX(), ed.getRadiusY());
        Color fillColor = ed.getFillColor() != null ? Color.valueOf(ed.getFillColor()) : Color.TRANSPARENT;
        Color strokeColor = ed.getStrokeColor() != null ? Color.valueOf(ed.getStrokeColor()) : Color.BLACK;
        ellipse.setFill(fillColor);
        ellipse.setStroke(strokeColor);
        ellipse.setStrokeWidth(ed.getStrokeWidth());
        ellipse.setRotate(ed.getRotationAngle());

        ellipse.setRotate(0);
        if(ed.isYFlipped() && ed.isXFlipped()) {
            ellipse.setRotate(ed.getRotationAngle() + 180);
        } else if(ed.isYFlipped() && !ed.isXFlipped()) {
            ellipse.setRotate(-ed.getRotationAngle());
        } else if(ed.isXFlipped() && !ed.isYFlipped()) {
            ellipse.setRotate(-ed.getRotationAngle() + 180);
        } else {
            ellipse.setRotate(ed.getRotationAngle());
        }

        return ellipse;
    }
}
